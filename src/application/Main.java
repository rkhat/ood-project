package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controllers.Pages;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Manager;
import model.Member;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import model.enums.STATUS;
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
    
    Member m1 = ps.createAccount("test1", "123456").getValue();
    Member m2 = ps.createAccount("test2", "123456").getValue();
    Member m3 = ps.createAccount("test3", "123456").getValue();
    Member m4 = ps.createAccount("test4", "123456").getValue();
    Member m5 = ps.createAccount("test5", "123456").getValue();
    Member m6 = ps.createAccount("test6", "123456").getValue();
    
    //create vehicles
    Vehicle v1 = new Vehicle("AAAAAA");
    Vehicle v2 = new Vehicle("BBBBBB");
    Vehicle v3 = new Vehicle("CCCCCC");
    Vehicle v4 = new Vehicle("DDDDDD");
    Vehicle v5 = new Vehicle("EEEEEE");
    Vehicle v6 = new Vehicle("FFFFFF");
    Vehicle v7 = new Vehicle("GGGGGG");
    Vehicle v8 = new Vehicle("HHHHHH");
    Vehicle v9 = new Vehicle("IIIIII");
    Vehicle v10 = new Vehicle("JJJJJJ");
    Vehicle v11 = new Vehicle("KKKKKK");
    
    //add vehicles to members
    ps.addVehicle(v1);
    ps.addVehicle(v2);
    ps.addVehicle(v3);
    ps.addVehicle(v4);
    ps.addVehicle(v5);
    ps.addVehicle(v6);
    ps.addVehicle(v7);
    ps.addVehicle(v8);
    ps.addVehicle(v9);
    ps.addVehicle(v10);
    ps.addVehicle(v11);
    
    m1.addVehicle(v1);
    m1.addVehicle(v2);
    m1.addVehicle(v3);
    m1.addVehicle(v4);
    m1.addVehicle(v5);
    m1.addVehicle(v6);
    m2.addVehicle(v7);
    m3.addVehicle(v8);
    m4.addVehicle(v9);
    m5.addVehicle(v10);
    m5.addVehicle(v11);
    
    
    
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