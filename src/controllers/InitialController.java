package controllers;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import views.ToolbarView;

/**
 * Initial page controller
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class InitialController extends AbstractController {
  @FXML Button memberButton;

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // Set default node
    setDefaultNode(memberButton);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = false;
    toolbar(toolbarView);
  }

  /**
   * Member button action
   */
  @FXML
  public void memberAction() {
    // goto login page
    loadPage(Pages.LoginPage);
  }

  /**
   * Register button action
   */
  @FXML
  public void registerAction() {
    // goto signup page
    loadPage(Pages.SignupPage);
  }
}
