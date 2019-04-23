package controllers;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListCell;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.*;
import model.enums.STATUS;
import util.StringHelper;
import views.ToolbarView;
import views.VehicleView;
import views.VehicleViewAdapter;

/**
 * Main menu page controller
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class MainMenuController extends AbstractController {
  @FXML ListView<VehicleView> vehicleListView; // List view of vehicles

  @FXML Label creditsAmountLabel; // credits amount label

  @FXML TextField plateField; // enter vehicle plate field
  @FXML Button addVehicleButton; // add vehicle button

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // ObservableList
    ObservableList<VehicleView> list = FXCollections.observableArrayList();

    // get manager
    Manager manager = getManager();

    // Get credits from manager with $ sign and two decimal places
    String amount = "$" + String.format("%.2f", getManager().getCredits());
    // Set credits amount label
    creditsAmountLabel.setText(amount);

    // get vehicle list
    List<Vehicle> vehicles = manager.getVehiclesAsList();
    // parked Vehicle ID
    Integer parkedVehicleID = manager.getVehicleID();

    // add vehicles to Observable list
    for (Vehicle vehicle : vehicles) {
      // convert vehicle to appropriate adapter
      VehicleView vehicleView = new VehicleViewAdapter(vehicle,
          parkedVehicleID);
      list.add(vehicleView);
    }

    // plate field validation
    BooleanBinding plateFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyPlate();
    }, plateField.textProperty());

    // enable add button when plate field is valid
    addVehicleButton.disableProperty().bind(plateFieldValid.not());

    // set list view items to observable list
    vehicleListView.setItems(list);

    // use custom cell type for list view
    vehicleListView.setCellFactory((param) -> new VehicleViewCell());

    vehicleListView.setPlaceholder(new Label("Add vehicles to park"));

    // Set default node
    if (vehicles.size() > 0) {
      setDefaultNode(vehicleListView);
    } else {
      setDefaultNode(plateField);
    }
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = false;
    toolbarView.title = "Dashboard";
    toolbarView.showSettingsButton = true;
    toolbar(toolbarView);
  }

  /**
   * Add button action to add a vehicle
   */
  @FXML
  public void addAction() {
    // Do nothing if fields invalid
    if (!verifyPlate()) return;

    String plate = plateField.getText();

    // Add vehicle
    STATUS status = getManager().doAddVehicle(plate);

    switch (status) {
    case SUCCESS:
      // on success go to main menu
      loadPage(Pages.MainMenuPage);
      break;

    case PLATE_DUPLICATE:
      // Plate is already used
      String title = "License plate taken!";
      String body = "Remove the vehicle before adding this license plate\n"
          + "or try a different license plate.";
      Button button = new JFXButton("OKAY");
      JFXDialog dialog = showAlert(title, body, button);
      button.setOnAction((eevent) -> dialog.close());
      break;

    default:
      throw new IllegalStateException("Impossible STATUS");
    }
  }

  /**
   * Park button action to select a vehicle to park
   * 
   * @param vehicleID ID of vehicle to park
   * @return EventHandler to be added to the park button
   */
  private EventHandler<ActionEvent> getParkAction(final int vehicleID) {
    return (event) -> {
      // select vehicle
      STATUS status = getManager().doSelectVehicle(vehicleID);

      switch (status) {
      case SUCCESS:
        // on success go to parking map page
        loadPage(Pages.ParkingMapPage);
        break;

      case NO_SPOTS_AVAILABLE:
        // Pop-up dialog no spots available
        String title = "No Spots Available!";
        String body = "Please try again later.";
        Button button = new JFXButton("OKAY");
        JFXDialog dialog = showAlert(title, body, button);
        button.setOnAction((eevent) -> dialog.close());
        break;

      default:
        throw new IllegalStateException("Impossible status: " + status);
      }
    };

  }

  /**
   * Remove button action to remove a vehicle
   * 
   * @param vehicleID ID of vehicle to remove
   * @return EventHandler for the remove button
   */
  private EventHandler<ActionEvent> getRemoveAction(final int vehicleID) {
    return (event) -> {
      // remove vehicle
      STATUS status = getManager().doRemoveVehicle(vehicleID);

      switch (status) {
      case SUCCESS:
        // on success reload main menu page
        loadPage(Pages.MainMenuPage);
        break;

      default:
        throw new IllegalStateException("Impossible status: " + status);
      }
    };

  }

  /**
   * Checkout button action to checkout a parked vehicle
   * 
   * @return EventHandler for the checkout button
   */
  private EventHandler<ActionEvent> getCheckoutAction() {
    return (event) -> {

      // get balance
      double oldBalance = getManager().getCredits();
      double deduct = getManager().getTotal(); // get rate
      double newBalance = oldBalance - deduct;
      String oldBalanceStr = "$" + String.format("%.2f", oldBalance);
      String deductStr = "$" + String.format("%.2f", deduct);
      String newBalanceStr = (newBalance < 0 ? "-" : "") + "$"
          + String.format("%.2f", Math.abs(newBalance));

      // confirm adding credits
      String title = "Confirm adding credits: ";
      String body = "Pay:\t\t\t\t" + deductStr + "\n"
          + "Current Balance:\t" + oldBalanceStr + "\n"
          + "New Balance:\t" + newBalanceStr;
      Button yesButton = new JFXButton("YES");
      Button noButton = new JFXButton("NO");
      JFXDialog dialog = showAlert(title, body, yesButton, noButton);

      // For no do nothing and close dialog
      noButton.setOnAction((eevent) -> dialog.close());

      // For yes,
      yesButton.setOnAction((eevent) -> {
        // close old dialog
        dialog.close();
        // checkout vehicle
        STATUS status = getManager().doCheckout();

        String ttitle;
        String bbody;
        Button bbutton;
        JFXDialog ddialog;

        switch (status) {
        case SUCCESS:
          // reload page
          loadPage(Pages.MainMenuPage);
          // Pop-up success
          ttitle = "Checkout Successfull!";
          bbutton = new JFXButton("OKAY");
          ddialog = showAlert(ttitle, null, bbutton);
          bbutton.setOnAction((eeevent) -> ddialog.close());
          break;

        case FAILED:
          // Pop-up dialog insufficient credits
          ttitle = "Insufficient Credits!";
          bbody = "Please add credits before checking out.";
          bbutton = new JFXButton("OKAY");
          ddialog = showAlert(ttitle, bbody, bbutton);
          bbutton.setOnAction((eeevent) -> ddialog.close());
          break;

        default:
          throw new IllegalStateException("Impossible status: " + status);
        }

      });
    };
  }

  /**
   * Add plate field valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyPlate() {
    // Plate field must be alphanumeric with exactly six characters.
    return StringHelper.checkAlphaNumeric(plateField.getText(), 6, true);
  }

  /**
   * Custom cell for list view
   *
   */
  private class VehicleViewCell extends JFXListCell<VehicleView> {
    final HBox hbox = new HBox();
    final Pane space = new Pane();
    final Pane space2 = new Pane();
    final Label plateLabel = new Label();
    final Button removeButton = new JFXButton();
    final Button actionButton = new JFXButton(); // park or checkout

    /**
     * Constructor for a custom cell. The cell displays a remove button, Vehicle
     * plate, and add/checkout button horizontally.
     * 
     * @param memberParked Pass true if member has a parked vehicle, else false
     */
    public VehicleViewCell() {
      super();

      // add remove button then plateLabel then parkButton to hbox
      hbox.getChildren().addAll(removeButton, space, plateLabel, space2,
          actionButton);

      // Dummy horizontal space to make cell align perfectly
      HBox.setHgrow(space, Priority.ALWAYS);
      HBox.setHgrow(space2, Priority.ALWAYS);

      // add style classes to each node
      hbox.getStyleClass().add("vehicle-row");
      removeButton.getStyleClass().add("vehicle-remove");
      plateLabel.getStyleClass().add("vehicle-plate");
      actionButton.getStyleClass().add("vehicle-action");

      // "Hackish" fix for enter key to work on remove, park, and checkout
      // buttons. Global event handler doesn't work in cells.
      removeButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
        if (ev.getCode() == KeyCode.ENTER) {
          removeButton.fire();
          ev.consume();
        }
      });

      actionButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
        if (ev.getCode() == KeyCode.ENTER) {
          actionButton.fire();
          ev.consume();
        }
      });
    }

    @Override
    protected void updateItem(VehicleView item, boolean empty) {
      super.updateItem(item, empty);

      setText(null); // No text in label of super class

      // if item is empty
      if (empty) {
        setGraphic(null);
      } else {
        // set vehicle plate label
        plateLabel.setText(item.getPlate());

        // remove button
        removeButton.setOnAction(getRemoveAction(item.getID()));
        if (item.isVehicleParked()) {
          // if current vehicle is parked
          // action button becomes checkout button
          actionButton.setText("CHECKOUT");
          actionButton.setOnAction(getCheckoutAction());
          actionButton.setDisable(false);
          // disable remove button
          removeButton.setDisable(true);
        } else {
          // if current vehicle is not the parked vehicle
          // action button is a park button
          actionButton.setText("PARK");
          actionButton.setOnAction(getParkAction(item.getID()));

          // enable remove button
          removeButton.setDisable(false);

          if (item.isMemberParked()) {
            // if member has a parked vehicle,
            // disable park button
            actionButton.setDisable(true);
          } else {
            // else enable park button
            actionButton.setDisable(false);
          }
        }

        // Set the cell to display the hbox
        setGraphic(hbox);
      }
    }

  }

}