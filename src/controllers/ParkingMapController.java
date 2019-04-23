package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.*;
import model.enums.STATUS;
import views.Pages;
import views.ToolbarView;
import views.Transition;

/**
 * Parking map page controller
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMapController extends AbstractController {
  // Checks if first available button to click on grid is set
  private boolean firstButton = false;

  @FXML GridPane parkingGrid;

  // Parking grid column and row sizes
  private static final double COLUMN_SIZE = 40.0;
  private static final double ROW_SIZE = 60.0;

  /**
   * Constructor
   */
  public ParkingMapController() {
    super();
    // set back page
    setBackPage(Pages.MainMenuPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // Get manager
    Manager manager = getManager();
    // Get parking map
    ParkingMap map = manager.getParkingMap();
    // Get list of spots
    List<Spot> spots = manager.getSpotsAsList();

    // Get width and height of map
    int numCols = map.getWidth();
    int numRows = map.getHeight();

    // Set width and height of map on the grid
    for (int i = 0; i < numCols; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPrefWidth(COLUMN_SIZE);
      parkingGrid.getColumnConstraints().add(colConst);
    }

    for (int i = 0; i < numRows; i++) {
      RowConstraints rowConst = new RowConstraints();
      rowConst.setPrefHeight(ROW_SIZE);
      parkingGrid.getRowConstraints().add(rowConst);
    }

    // Initialize a list of buttons with initial capacity equal to number of
    // spots
    List<Button> carButtonList = new ArrayList<>(spots.size());

    // For each spot
    for (Spot spot : spots) {
      // Create a car button
      Button carButton = new JFXButton();
      // Set the car button style
      carButton.getStyleClass().add("car");

      if (spot.isReserved()) {
        // If spot is reserved, disable buttons
        carButton.setDisable(true);
      } else {
        // else enable button and set the action for the button
        carButton.setDisable(false);
        carButton.setOnAction(getSpotAction(spot.getID()));

        // Set first available car button to have default focus
        if (firstButton == false) {
          setDefaultNode(carButton);
          firstButton = true;
        }
      }
      // Set position of car button on grid
      GridPane.setConstraints(carButton, spot.getX(), spot.getY());
      // Add car button to list of buttons
      carButtonList.add(carButton);
    }
    // Add all buttons to the grid
    parkingGrid.getChildren().addAll(carButtonList);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Parking Map";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  @Override
  public void back() {
    // Reset reservation
    getManager().resetReservation();
    // Then perform default back action
    super.back();
  }

  /**
   * Spot button action to select a spot.
   * 
   * @param id ID of spot
   * @return EventHandler to be added to the car button
   */
  private EventHandler<ActionEvent> getSpotAction(final int id) {
    return (ActionEvent event) -> {
      // Confirm spot pop-up dialog
      String title = "Are you sure?";
      Button yesButton = new JFXButton("YES");
      Button noButton = new JFXButton("NO");
      JFXDialog dialog = showAlert(title, null, yesButton, noButton);

      // For no do nothing and close dialog
      noButton.setOnAction((eevent) -> dialog.close());

      // For yes,
      yesButton.setOnAction((eevent) -> {
        // Perform select spot
        STATUS status = getManager().doSelectSpot(id);

        switch (status) {
        case SUCCESS:
          // on success go to main menu
          loadPage(Pages.MainMenuPage, Transition.LTR);
          // close old dialog
          dialog.close();

          // new dialog to show success
          String ttitle = "Parked successfully!";
          String bbody = "Please head to your parking spot. Thank you!";
          Button bbutton = new JFXButton("OKAY");
          JFXDialog ddialog = showAlert(ttitle, bbody, bbutton);
          bbutton.setOnAction((eeevent) -> ddialog.close());
          break;
        default:
          throw new IllegalStateException("Impossible status: " + status);
        }

        // close dialog
        dialog.close();
      });

    };
  }
}
