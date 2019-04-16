package vc;

import com.jfoenix.controls.JFXButton;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.*;
import vc.datatypes.VehicleView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMapViewController extends AbstractViewController{
  @FXML GridPane parkingGrid;
  
  @FXML
  public void initialize() {
    List<Spot> spots = new ArrayList<>();
    spots.add(new Spot(0,0,0));
    spots.add(new Spot(0,5,1));
    spots.add(new Spot(2,2,2));
    spots.add(new Spot(5,0,3));
    spots.add(new Spot(3,4,4));
    spots.add(new Spot(2,6,5));
    spots.add(new Spot(7,8,6));
    ParkingMap parkingMap = new ParkingMap(spots);
    spots = parkingMap.getSpots();
    
    List<Button> cars = new ArrayList<>(); 
    
    
    for (Spot spot : spots) {
      Button car = new JFXButton("car");
      if(spot.isLocked() || spot.isReserved()) {
        car.setDisable(true);
      } else {
        
      }
      GridPane.setConstraints(car, spot.getX(), spot.getY());
      car.setOnAction(selectSpot(spot.getID()));
      
      cars.add(car);
    }
    System.out.println(parkingGrid);
    System.out.println(cars);
    parkingGrid.getChildren().addAll(cars);
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }
  
  private EventHandler<ActionEvent> selectSpot(final int id) {
    return (ActionEvent event) -> {
      System.out.println("car=" + id);
    };
  }
}
