package controllers;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.Pages;
import controllers.Controller;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class TopController {
  @FXML ToolBar toolbar;
  @FXML Pane content;
  
  Pages backPage;
  private int userType;
  
  
  @FXML
  public void initialize() {
    Pages page = Pages.InitialPage;
    loadPage(page);
  }
  
  public void showToolbar(boolean show) {
    System.out.println(show);
    toolbar.setVisible(show);
  }
  
  public void loadPage(Pages page) {
    try {
      FXMLLoader loader = page.getLoader();
      System.out.println("Loader:" + loader);
      Parent root = loader.load();
      System.out.println("PageRoot:" + root);
      Controller controller = loader.getController();
      controller.setMainViewController(this);
      
      content.getChildren().clear();
      content.getChildren().add(root);
    } catch (IOException ex) {
      Logger.getLogger("MainViewController.loadPage").log(Level.SEVERE, null, ex);
      System.exit(1);
    }
  }
  
  
}
