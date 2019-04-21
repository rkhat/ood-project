package controllers;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.*;
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
public class LoginController extends AbstractController {
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Button loginButton;
  
  public LoginController() {
    setBackPage(Pages.InitialPage);
  }

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
  public void updateParentController() {
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
  public void loginAction() {
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
      //Pop-up dialog invalid username or password
      String title = "Invalid username or Password";
      Button button = new JFXButton("Okay");
      JFXDialog dialog = showAlert(title, null, button);
      button.setOnAction((event) -> dialog.close());
      break;
      
    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }

}
