package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Manager;
import util.Debugger;
import views.ToolbarView;
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
  public void updateParentController() {
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = false;
    toolbar(toolbarView);
  }
  
  public void guest() {
  }
  
  /**
   * Member button action
   */
  public void memberAction() {
    Manager.createInstance();
    loadPage(Pages.LoginPage);
  }
  
  /**
   * Register button action
   */
  public void registerAction() {
    Manager.createInstance();
    loadPage(Pages.SignupPage);
  }
}
