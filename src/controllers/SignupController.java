package controllers;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import model.enums.STATUS;
import util.StringHelper;
import views.ToolbarView;

/**
 * FXML Controller class
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class SignupController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML PasswordField passwordField2;
  @FXML Button signupButton;
  
  public SignupController() {
    super();
    setBackPage(Pages.InitialPage);
  }

  @FXML
  public void initialize() {
    // username field validation listener
    BooleanBinding usernameFieldValid = Bindings.createBooleanBinding(() -> {
      // username must be alphanumeric with at least one character.
      return StringHelper.checkAlphaNumeric(usernameField.getText(), 1);
    }, usernameField.textProperty());

    // password fields validation listener
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      // password must be alphanumeric with at least six characters
      // and both password fields are equal
      return StringHelper.checkAlphaNumeric(passwordField.getText(), 6) &&
          passwordField2.getText().contentEquals(passwordField.getText());

    }, passwordField.textProperty(), passwordField2.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = usernameFieldValid.and(passwordFieldValid);
    signupButton.disableProperty().bind(valid.not());
  }

  @Override
  public void updateParentController() {
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
  public void signupAction() {
    String username = usernameField.getText();
    String password1 = passwordField.getText();

    // create account
    STATUS status = getManager().doCreateAccount(username, password1);

    switch (status) {
    case SUCCESS:
      // on success go to main menu
      loadPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // Pop-up dialog invalid username or password
      String title = "Username taken";
      Button button = new JFXButton("Okay");
      JFXDialog dialog = showAlert(title, null, button);
      button.setOnAction((event) -> dialog.close());
      break;

    default:
      throw new IllegalStateException("Impossible status: " + status);
    }

  }

}
