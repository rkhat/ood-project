package application;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import vc.Pages;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Main extends Application {
  public static void main(String[] args) {
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
