package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controllers.Pages;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import model.Manager;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Main extends Application {
  public static void main(String args[]) {
    // create list of spots
    List<Spot> spotList = new ArrayList<Spot>(10);
    for (int i = 1; i <= 5; i++) {
      for (int j = 1; j <= 2; j++) {
        spotList.add(new Spot(i,j));
      }
    }
   
    // create map
    ParkingMap map = new ParkingMap(spotList);
   
    // Set up parking system
    ParkingSystem ps = ParkingSystem.getInstance();
    ps.setMap(map);
    
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/pages/top.fxml"));
    primaryStage.setTitle("Parking System");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}