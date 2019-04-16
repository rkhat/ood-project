package vc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
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
import model.Vehicle;
import vc.datatypes.VehicleView;
import vc.datatypes.VehicleViewAdapter;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class MainMenuViewController extends AbstractViewController{
  @FXML ListView<VehicleView> listView;
  
  @FXML
  public void initialize() {
    ObservableList<VehicleView> list = FXCollections.observableArrayList();
    Vehicle vehicle1 = new Vehicle("X123456");
    Vehicle vehicle2 = new Vehicle("X223456");
    Vehicle vehicle3 = new Vehicle("X323456");
    Vehicle vehicle4 = new Vehicle("X423456");
    Vehicle vehicle5 = new Vehicle("X523456");
    
    VehicleView vehicleView1 = new VehicleViewAdapter(vehicle1, vehicle1);
    VehicleView vehicleView2 = new VehicleViewAdapter(vehicle5, vehicle1);
    VehicleView vehicleView3 = new VehicleViewAdapter(vehicle3, vehicle1);
    VehicleView vehicleView4 = new VehicleViewAdapter(vehicle4, vehicle1);
    
    
    list.add(vehicleView1);
    list.add(vehicleView2);
    list.add(vehicleView3);
    list.add(vehicleView4);
    
    System.out.println(vehicleView2.getPlate());
    System.out.println(listView);
    
    listView.setItems(list);
    listView.setCellFactory((ListView<VehicleView> param) -> new XCell());
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }
  
  private class XCell extends JFXListCell<VehicleView>{
    final HBox hbox = new HBox();
    final Label label = new Label();
    final Button button1 = new JFXButton();
    final Button button2 = new JFXButton();
    private VehicleView vehicleView;
    
    public XCell() {
      super();
      hbox.getChildren().addAll(label, button1, button2);
      
      hbox.setPrefHeight(50.0);
      
      HBox.setHgrow(label, Priority.ALWAYS);
    }
    
    @Override
    protected void updateItem(VehicleView item, boolean empty) {
      super.updateItem(item, empty);
      setText(null);  // No text in label of super class
      if (empty) {
        vehicleView = null;
        setGraphic(null);
      } else {
        vehicleView = item;
        label.setText(vehicleView.getPlate());
        
        button2.setText("Remove");
                 button2.setOnAction(remove(item));
          
        if(vehicleView.isVehicleParked()){
          button1.setText("Checkout");
          button1.setOnAction(checkout(item));
          button2.setDisable(true);
        } else {
          button1.setText("Park");
          button1.setOnAction(park(item));
 
          if(vehicleView.isMemberParked()) {
            button1.setDisable(true);
          }
        }
        
        setGraphic(hbox);
      }
    }
    
  }
  
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
  
  
}