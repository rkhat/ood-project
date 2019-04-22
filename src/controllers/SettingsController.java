package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.enums.STATUS;
import views.ToolbarView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class SettingsController extends AbstractController {
  @FXML Button addCreditsButton;
  @FXML Button changePasswordButton;
  @FXML Button logoutButton;
  
  public SettingsController() {
    setBackPage(Pages.MainMenuPage);
  }

  @FXML
  public void initialize() {
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
  
  public void addCreditsAction() {
    loadPage(Pages.AddCreditsPage);
  }
  
  public void changePasswordAction() {
    loadPage(Pages.ChangePasswordPage);
  }
  
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
