package controllers;

import java.util.List;

import javafx.beans.binding.*;
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
public class LoginController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Button loginButton;

  @FXML
  public void initialize() {
    // username field validation listener
    BooleanBinding usernameFieldValid = Bindings.createBooleanBinding(() -> {
      // user name must be alphanumeric with at least one character.
      return StringHelper.checkAlphaNumeric(usernameField.getText(), 1);
    }, usernameField.textProperty());

    // password field validation listener
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      // user name must be alphanumeric with at least six characters.
      return StringHelper.checkAlphaNumeric(passwordField.getText(), 6);
    }, passwordField.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = usernameFieldValid.and(passwordFieldValid);
    loginButton.disableProperty().bind(valid.not());
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }

  /**
   * Click login button
   */
  public void clickLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Perform login
    STATUS status = getManager().doLogIn(username, password);

    switch (status) {

    case SUCCESS:
      // on success go to main menu
      setPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // TODO: Pop-up dialog invalid username or password
      System.out.println("Invalid username or password");
      break;
      
    default:
      throw new IllegalStateException("Impossible status");
    }
  }

}
