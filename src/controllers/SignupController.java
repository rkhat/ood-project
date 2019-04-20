package controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

/**
 * FXML Controller class
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class SignupController extends AbstractController {

  Manager manager = Manager.getInstance();
  @FXML TextField usernameField;
  @FXML PasswordField passwordField1;
  @FXML PasswordField passwordField2;
  
  @FXML
  public void initialize() {
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
    String password2 = passwordField2.getText();
    
    //Don't continue if password fields are not the same.
    if(!password1.contentEquals(password2)) {
      //TODO: Please enter same password dialog
      System.out.println("Please enter same password in password fields");
      return;
    }
    
    
    boolean success = manager.doCreateAccount(username, password1);
    if (!success) {
      // TODO: Popup dialog invalid username or password
      System.out.println("Invalid Username or password");
      return;
    }
    
    setPage(Pages.MainMenuPage);

  }

}
