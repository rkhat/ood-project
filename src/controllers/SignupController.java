package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.enums.STATUS;
import util.StringHelper;
import views.Pages;
import views.ToolbarView;
import views.Transition;

/**
 * Sign up page controller
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class SignupController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML PasswordField passwordField2;
  @FXML Button signupButton;

  /**
   * Constructor
   */
  public SignupController() {
    super();
    // set back page
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

    // password fields validation listener
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyPassword();
    }, passwordField.textProperty(), passwordField2.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = usernameFieldValid.and(passwordFieldValid);
    signupButton.disableProperty().bind(valid.not());

    // Set default node
    setDefaultNode(usernameField);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Sign up";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  /**
   * Signup button action
   */
  @FXML
  public void signupAction() {
    // Do nothing if fields invalid
    if (!verifyUsername() || !verifyPassword()) return;

    String username = usernameField.getText();
    String password = passwordField.getText();

    // create account
    STATUS status = getManager().doCreateAccount(username, password);

    switch (status) {
    case SUCCESS:
      // on success go to main menu
      loadPage(Pages.MainMenuPage, Transition.RTL);
      break;

    case USERNAME_IN_USE:
      // Pop-up dialog invalid username or password
      String title = "Username taken!";
      String body = "Please try a different username";
      Button button = new JFXButton("OKAY");
      JFXDialog dialog = showAlert(title, body, button);
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
   * Password fields valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyPassword() {
    // password must be alphanumeric with at least six characters
    // and both password fields are equal
    return StringHelper.checkAlphaNumeric(passwordField.getText(), 6) &&
        passwordField2.getText().contentEquals(passwordField.getText());
  }

}
