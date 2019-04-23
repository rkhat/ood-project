package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.enums.STATUS;
import views.Pages;
import views.ToolbarView;
import views.Transition;

/**
 * Settings page controller
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class SettingsController extends AbstractController {
  @FXML Button addCreditsButton;

  /**
   * Constructor
   */
  public SettingsController() {
    super();
    // set back page
    setBackPage(Pages.MainMenuPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // Set default node
    setDefaultNode(addCreditsButton);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
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
    //go to add credits page
    loadPage(Pages.AddCreditsPage, Transition.RTL);
  }

  /**
   * Change password button action
   */
  @FXML
  public void changePasswordAction() {
    //go to change password page
    loadPage(Pages.ChangePasswordPage, Transition.RTL);
  }

  /**
   * Logout button action
   */
  @FXML
  public void logoutAction() {
    // Perform logout
    STATUS status = getManager().doLogOut();

    switch (status) {
    case SUCCESS:
      // on success go to initial page
      loadPage(Pages.InitialPage, Transition.LTR);
      break;

    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }

}
