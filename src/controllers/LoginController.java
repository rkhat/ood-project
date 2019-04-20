package controllers;

import java.util.List;

import javafx.beans.binding.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import util.StringHelper;

/**
 * FXML Controller class
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class LoginController extends AbstractController {

  Manager manager = Manager.getInstance();
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Button loginButton;

  @FXML
  public void initialize() {
    BooleanBinding usernameFieldValid = Bindings.createBooleanBinding(() -> {
      System.out.println(usernameField.getText());
      System.out.println(StringHelper.checkAlphaNumeric(usernameField.getText(), 1));
      // user name must be alphanumeric with at least one character.
      return StringHelper.checkAlphaNumeric(usernameField.getText(), 1);
    }, usernameField.textProperty());
    
    BooleanBinding passwordFieldValid = Bindings.createBooleanBinding(() -> {
      System.out.println(passwordField.getText());
      System.out.println(StringHelper.checkAlphaNumeric(passwordField.getText(), 1));
      // user name must be alphanumeric with at least six characters.
      return StringHelper.checkAlphaNumeric(passwordField.getText(), 6);
    }, passwordField.textProperty());
    
    
    //enable login button when all fields are valid
    loginButton.disableProperty().bind(usernameFieldValid.not().or(passwordFieldValid.not()));

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
    boolean success = manager.doEnterLoginInfo(username, password);

    // Failed login
    if (!success) {
      // TODO: Popup dialog invalid username or password
      System.out.println("Invalid Username and password");
    }

    // Successful login
    setPage(Pages.MainMenuPage);
  }

}
