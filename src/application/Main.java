package application;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import controllers.TopController;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Member;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import javafx.fxml.FXMLLoader;

/**
 * Main GUI application
 * @author Alec Agnese, Rami El Khatib
 */
public class Main extends Application {

  private TopController topController;
  private Stage window;

  /**
   * Entry Point for the application
   * @param args not used
   */
  public static void main(String args[]) {

    // create list of spots
    List<Spot> spotList = new ArrayList<Spot>(10);
    for (int j = 0; j <= 8; j++) {
      if (j == 1 || j == 4 || j == 7) continue;
      for (int i = 0; i <= 11; i++) {
        if (j == 8 && (i == 5 || i == 6)) continue;
        spotList.add(new Spot(i, j));
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

    // create vehicles
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

    // add vehicles to members
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
    window = primaryStage;
    
    //Get the FXML loader of the top page
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/top.fxml"));
    // Load top page as root
    Parent root = loader.load();
    //Get controller
    topController = loader.getController();
    
    // Create main scene from root
    Scene mainScene = new Scene(root);

    // Make Enter key fire focused buttons
    root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
      if (ev.getCode() == KeyCode.ENTER) {
        Node focusedNode = mainScene.focusOwnerProperty().get();
        if (focusedNode instanceof Button) {
          ((Button) focusedNode).fire();
          ev.consume();
        }
      }
    });
    
    // Change default exit button
    window.setOnCloseRequest(e ->{
      e.consume();
      closeProgram();
    });
    // Change windows icon
    window.getIcons().add(new Image("/resources/icons/parking.png"));
    // Set window title
    window.setTitle("Parking System");
    // Set scene for window
    window.setScene(mainScene);
    // Disable window resizing
    window.setResizable(false);
    // Show window
    window.show();
  }

  /**
   * Exit button action
   */
  private void closeProgram() {
    String title = "Are you sure?";
    Button yesButton = new JFXButton("YES");
    Button noButton = new JFXButton("NO");
    JFXDialog dialog = topController.showPopupDialog(title, null, yesButton,
        noButton);

    // For no do nothing and close dialog
    noButton.setOnAction((event) -> dialog.close());

    // For yes,
    yesButton.setOnAction((event) -> {
      // Save data

      // close dialog
      window.close();
    });
  }
}