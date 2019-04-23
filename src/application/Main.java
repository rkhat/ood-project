package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import model.Manager;
import model.Member;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;
import model.Vehicle;
import javafx.fxml.FXMLLoader;

/**
 * Main GUI application
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class Main extends Application {

  private static final String defaultFile = "app.ser";
  private static String fileName;
  private static boolean save;
  private static ParkingSystem ps;

  private TopController topController;
  private Stage window;

  /**
   * Entry Point for the application
   * 
   * @param args not used
   */
  public static void main(String args[]) {

    try {
      fileName = args[0];
    } catch (IndexOutOfBoundsException e) {
      System.out.println("No file specified to load from, default "
          + defaultFile + " selected\n");
      fileName = defaultFile;
    }

    System.out.println("Loaded from file " + fileName + "\n");

    // only save if it is the actual app, not one of the presets.
    save = fileName.contentEquals(defaultFile) ? true : false;

    // Set up ParkingSystem
    ps = initParkingSystem(fileName);
    
    // Set up Manager
    Manager.createInstance(ps);
    Manager.getInstance();

    launch(args);
  }

  public static ParkingSystem initParkingSystem(String fileName) {
    ParkingMap map = null;
    ParkingSystem ps = null;

    // read parking system from file
    try {
      FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      ps = (ParkingSystem) ois.readObject();
      ois.close();
      fis.close();
      return ps;
    } catch (FileNotFoundException e) {
      System.out.println("Setting up app...\n");
      // create list of spots
      List<Spot> spotList = new ArrayList<Spot>(1);
      // spotList.add(new Spot(0, 0));
      for (int i = 1; i <= 5; i++) {
        for (int j = 1; j <= 2; j++) {
          spotList.add(new Spot(i, j));
        }
      }

      // create map
      map = new ParkingMap(spotList);

      // Set up parking system
      ps = ParkingSystem.getInstance();
      ps.setMap(map);
      return ps;

    } catch (IOException e) {
      System.err.println(e.toString());
    } catch (ClassNotFoundException e) {
      System.err.println(e.toString());
    }
    return null;
  }

  // create member with credits and parked vehicle.
  public static void addMember(Manager m, String un, String psw, double credits,
      String plate, int spotID) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doAddVehicle(plate);
    m.doSelectVehicle(m.getVehiclesAsList().get(0).getID());
    m.doSelectSpot(spotID);
    m.doLogOut();
  }

  // create member with credits and unparked vehicle.
  public static void addMember(Manager m, String un, String psw, double credits,
      String plate) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doAddVehicle(plate);
    m.doLogOut();
  }

  // create member with credits and no vehicles.
  public static void addMember(Manager m, String un, String psw,
      double credits) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doLogOut();
  }

  /**
   * Add spots in a vertical line starting at position x, yBegin and ending at
   * position x, yBegin+amt-1
   * 
   * @param spotList The list to add spots to.
   * @param x        The X value of the column to add the spots in.
   * @param yBegin   The beginning Y value.
   * @param amt      The number of spots to add.
   */
  public static void addSpotsVertical(List<Spot> spotList, int x, int yBegin,
      int amt) {
    for (int i = 0; i < amt; i++) {
      spotList.add(new Spot(x, yBegin + i));
    }
  }

  /**
   * Add spots in a horizontal line starting at position xBegin, y and ending at
   * position xBegin+amt-1, y
   * 
   * @param spotList The list to add spots to.
   * @param xBegin   The beginning X value.
   * @param y        The Y value of the row to add the spots in.
   * @param amt      The number of spots to add.
   */
  public static void addSpotsHorizontal(List<Spot> spotList, int xBegin, int y,
      int amt) {
    for (int i = 0; i < amt; i++) {
      spotList.add(new Spot(xBegin + i, y));
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    // Get the FXML loader of the top page
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/pages/top.fxml"));
    // Load top page as root
    Parent root = loader.load();
    // Get controller
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
    window.setOnCloseRequest(e -> {
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
      if (save) {
        try {
          FileOutputStream fos = new FileOutputStream(fileName);
          ObjectOutputStream oos = new ObjectOutputStream(fos);
          oos.writeObject(ps);
          oos.close();
          fos.close();
        } catch (Exception e) {
          System.err.println(e.toString());
        }
      }

      // close dialog
      window.close();
    });
  }
}