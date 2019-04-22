package controllers;

import javafx.fxml.FXML;
import model.Manager;
import views.ToolbarView;

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
