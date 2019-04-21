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
import views.ToolbarView;
import views.VehicleView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMapController extends AbstractController {
  private static final int BORDER_SIZE = 1;
  private final List<Spot> spots;
  private final ParkingMap map;

  @FXML GridPane parkingGrid;

  public ParkingMapController() {
    super();
    setBackPage(Pages.MainMenuPage);
    // get manager
    Manager manager = getManager();
    // get parking map
    map = manager.getParkingMap();
    // get list of spots
    spots = manager.getSpotsAsList();
  }

  @FXML
  public void initialize() {

    // set number of rows and columns for the grid pane
    int numCols = map.getWidth() + BORDER_SIZE * 2;
    int numRows = map.getHeight() + BORDER_SIZE * 2;

    for (int i = 0; i < numCols; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPercentWidth(100.0 / numCols);
      parkingGrid.getColumnConstraints().add(colConst);
    }

    for (int i = 0; i < numRows; i++) {
      RowConstraints rowConst = new RowConstraints();
      rowConst.setPercentHeight(100.0 / numRows);
      parkingGrid.getRowConstraints().add(rowConst);
    }

    List<Button> carButtonList = new ArrayList<>(spots.size());

    for (Spot spot : spots) {
      Button carButton = new JFXButton("car");
      if (spot.isReserved()) {
        carButton.setDisable(true);
      } else {
        carButton.setOnAction(getSpotAction(spot.getID()));
      }
      GridPane.setConstraints(carButton, spot.getX(), spot.getY());
      carButtonList.add(carButton);
    }
    parkingGrid.getChildren().addAll(carButtonList);
  }

  @Override
  public void updateParentController() {
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
    // Then perform default button action
    super.back();
  }

  /**
   * Spot button action to select a spot.
   * 
   * @param id ID of spot
   * @return EventHandler to be added to the spot button
   */
  private EventHandler<ActionEvent> getSpotAction(final int id) {
    return (ActionEvent event) -> {
      // Confirm spot pop-up dialog
      String title = "Are you sure?";
      Button yesButton = new JFXButton("Yes");
      Button noButton = new JFXButton("No");
      JFXDialog dialog = showAlert(title, null, yesButton, noButton);

      noButton.setOnAction((eevent) -> dialog.close());

      yesButton.setOnAction((eevent) -> {
        // Select spot
        STATUS status = getManager().doSelectSpot(id);

        switch (status) {
        case SUCCESS:
          // on success go to main menu
          loadPage(Pages.MainMenuPage);
          break;

        default:
          throw new IllegalStateException("Impossible status: " + status);
        }

        dialog.close();
      });

    };
  }
}
