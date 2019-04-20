package controllers;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;

import controllers.adapters.VehicleView;
import controllers.adapters.VehicleViewAdapter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import model.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class MainMenuController extends AbstractController {

  @FXML ListView<VehicleView> listView;

  @FXML
  public void initialize() {
    // Observable list of Vehicle for listView
    ObservableList<VehicleView> list = FXCollections.observableArrayList();
    // get Manager
    Manager manager = getManager();
    // parked Vehicle ID
    Integer parkedVehicleID = manager.getVehicleID();
    // get logged in member
    Member member = getManager().getMember();
    // get vehicle list
    List<Vehicle> vehicles = manager.getVehiclesAsList();

    // add vehicles to Observable list
    for (Vehicle vehicle : vehicles) {
      // convert vehicle to appropriate adapter
      VehicleView vehicleView = new VehicleViewAdapter(vehicle,
          parkedVehicleID);
      list.add(vehicleView);
    }

    // set list view items to observable list
    listView.setItems(list);

    // use custom cell type for list view
    listView.setCellFactory((ListView<VehicleView> param) -> new VehicleViewCell(memberParked));
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }

  /**
   * Custom cell for list view
   *
   */
  private class VehicleViewCell extends JFXListCell<VehicleView> {
    final HBox hbox = new HBox();
    final Label plateLabel = new Label();
    final Button removeButton = new JFXButton();
    final Button parkButton = new JFXButton(); // this button is also checkout
    private final boolean memberParked;

    /**
     * Constructor for a custom cell. The cell displays a remove button, Vehicle
     * plate, and add/checkout button horizontally.
     * 
     * @param memberParked Pass true if member has a parked vehicle, else false
     */
    public VehicleViewCell(boolean memberParked) {
      super();
      this.memberParked = memberParked;

      // add remove button then plateLabel then parkButton to hbox
      hbox.getChildren().addAll(removeButton, plateLabel, parkButton);

      hbox.setPrefHeight(50.0);

      HBox.setHgrow(plateLabel, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(VehicleView item, boolean empty) {
      super.updateItem(item, empty);

      setText(null); // No text in label of super class
      
      //if item is empty
      if (empty) {
        setGraphic(null);
      } else {
        //set vehicle plate label
        plateLabel.setText(item.getPlate());

        //remove button
        removeButton.setText("Remove");
        removeButton.setOnAction(remove(item));
        
        //park button
        parkButton.setText("Park");
        
        if(item.isVehicleParked()) {
          
        } else {
          
        }
        
        if(memberParked) {
          parkButton.setText("Park");
          park
        }
        
        
        
        if (item.isVehicleParked()) {
          //park button
          parkButton.setText("Checkout");
          button1.setOnAction(checkout(item));
          button2.setDisable(true);
        } else {
          button1.setText("Park");
          button1.setOnAction(park(item));

          if (vehicleView.isMemberParked()) {
            button1.setDisable(true);
          }
        }

        setGraphic(hbox);
      }
    }

  }

  /**
   * 
   * @param vehicleView
   * @return
   */
  private EventHandler<ActionEvent> park(final VehicleView vehicleView) {
    return (ActionEvent event) -> {
      System.out.println("parked");
      setPage(Pages.ParkingMapPage);
    };

  }

  private EventHandler<ActionEvent> remove(final VehicleView vehicleView) {
    return (ActionEvent event) -> {
      System.out.println("remove");
    };

  }

  private EventHandler<ActionEvent> checkout(final VehicleView vehicleView) {
    return (ActionEvent event) -> {
      System.out.println("checkout");
    };

  }

  /**
   * add Vehicle
   */
  public add() {
    return (ActionEvent event) -> {
      System.out.println("checkout");
    };
    
  }

}