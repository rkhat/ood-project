package controllers;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import model.enums.STATUS;
import util.StringHelper;

/**
 * FXML Controller class
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class SignupController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField1;
  @FXML PasswordField passwordField2;
  @FXML Button signupButton;

  @FXML
  public void initialize() {
    // username field validation listener
    BooleanBinding usernameFieldValid = Bindings.createBooleanBinding(() -> {
      // username must be alphanumeric with at least one character.
      return StringHelper.checkAlphaNumeric(usernameField.getText(), 1);
    }, usernameField.textProperty());

    // password field 1 validation listener
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      // password must be alphanumeric with at least six characters.
      return StringHelper.checkAlphaNumeric(passwordField1.getText(), 6);
    }, passwordField1.textProperty());

    // password field validation listener
    BooleanBinding passwordFieldValid2 = Bindings.createBooleanBinding(() -> {
      // user name must be alphanumeric with at least six characters.
      return passwordField2.getText().contentEquals(passwordField1.getText());
    }, passwordField2.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = usernameFieldValid.and(passwordFieldValid)
        .and(passwordFieldValid2);
    signupButton.disableProperty().bind(valid.not());
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }

  /**
   * Click Signup button
   */
  public void clickSignup() {
    String username = usernameField.getText();
    String password1 = passwordField1.getText();

    // create account
    STATUS status = getManager().doCreateAccount(username, password1);

    switch (status) {

    case SUCCESS:
      //on success go to main menu
      setPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // TODO: Pop-up dialog invalid username or password
      System.out.println("Username taken");
      break;
      
    default:
      throw new IllegalStateException("Impossible status");
    }

  }

}
