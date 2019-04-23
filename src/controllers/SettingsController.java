package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.enums.STATUS;
import views.ToolbarView;

/**
 * Settings page controller
 * @author Alec Agnese, Rami El Khatib
 */
public class SettingsController extends AbstractController {
  @FXML Button addCreditsButton;
  
  /**
   * Constructor
   */
  public SettingsController() {
    setBackPage(Pages.MainMenuPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    //Set default node
    setDefaultNode(addCreditsButton);
  }

  @Override
  public void updateParentController() {
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Settings";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }
  
  /**
   * Add credits button action
   */
  @FXML
  public void addCreditsAction() {
    loadPage(Pages.AddCreditsPage);
  }
  
  /**
   * Change password button action
   */
  @FXML
  public void changePasswordAction() {
    loadPage(Pages.ChangePasswordPage);
  }
  
  /**
   * Logout button action
   */
  @FXML
  public void logoutAction() {
    //Perform logout
    STATUS status = getManager().doLogOut();
    
    switch (status) {
    case SUCCESS:
      // on success go to initial page
      loadPage(Pages.InitialPage);
      break;
      
    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }
  
}
