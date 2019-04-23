package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.enums.STATUS;
import util.StringHelper;
import views.ToolbarView;

/**
 * Login page controller
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class LoginController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Button loginButton;

  /**
   * Constructor
   */
  public LoginController() {
    super();
    // Back page is initial page
    setBackPage(Pages.InitialPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // username field validation listener
    BooleanBinding usernameFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyUsername();
    }, usernameField.textProperty());

    // password field validation listener
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyPassword();
    }, passwordField.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = usernameFieldValid.and(passwordFieldValid);
    loginButton.disableProperty().bind(valid.not());

    // Set default node
    setDefaultNode(usernameField);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Log in";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  /**
   * Login button action
   */
  @FXML
  public void loginAction() {
    // Do nothing if fields invalid
    if (!verifyUsername() || !verifyPassword()) return;

    String username = usernameField.getText();
    String password = passwordField.getText();

    // Perform login
    STATUS status = getManager().doLogIn(username, password);

    switch (status) {
    case SUCCESS:
      // on success go to main menu
      loadPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // Pop-up dialog invalid username or password
      String title = "Invalid username or password";
      Button button = new JFXButton("OKAY");
      JFXDialog dialog = showAlert(title, null, button);
      button.setOnAction((event) -> dialog.close());
      break;

    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }

  /**
   * Username field valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyUsername() {
    // user name must be alphanumeric with at least one character.
    return StringHelper.checkAlphaNumeric(usernameField.getText(), 1);
  }

  /**
   * Password field valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyPassword() {
    // password must be alphanumeric with at least six characters.
    return StringHelper.checkAlphaNumeric(passwordField.getText(), 6);
  }

}
