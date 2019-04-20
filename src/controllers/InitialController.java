package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Manager;
import util.Debugger;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class InitialController extends AbstractController{
  
  @FXML
  public void initialize() {
  }

  @Override
  public void updateMainViewController() {
    showToolbar(false);
  }
  
  public void guest() {
  }
  
  /**
   * Click Member button
   */
  public void clickMember() {
    Manager.createInstance();
    setPage(Pages.LoginPage);
  }
  
  /**
   * Click Register button
   */
  public void clickRegister() {
    Manager.createInstance();
    setPage(Pages.SignupPage);
  }
}
