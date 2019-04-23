package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.enums.STATUS;
import util.StringHelper;
import views.ToolbarView;

/**
 * Change password page
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ChangePasswordController extends AbstractController {
  @FXML PasswordField oldPasswordField;
  @FXML PasswordField newPasswordField;
  @FXML PasswordField newPasswordField2;
  @FXML Button changePasswordButton;

  /**
   * Constructor
   */
  public ChangePasswordController() {
    super();
    // set back page
    setBackPage(Pages.SettingsPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // current password field validation listener
    BooleanBinding oldPasswordFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyOldPassword();
    }, oldPasswordField.textProperty());

    // New password fields validation listener
    BooleanBinding newPasswordFieldValid = Bindings.createBooleanBinding(() -> {
      return verifyNewPassword();
    }, newPasswordField.textProperty(), newPasswordField2.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = oldPasswordFieldValid.and(newPasswordFieldValid);
    changePasswordButton.disableProperty().bind(valid.not());

    setDefaultNode(oldPasswordField);
  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Change Password";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  /**
   * Change Password button action
   */
  @FXML
  public void changePasswordAction() {
    // Do nothing if fields invalid
    if (!verifyOldPassword() || !verifyNewPassword()) return;

    String oldPassword = oldPasswordField.getText();
    String newPassword = newPasswordField.getText();

    // Perform login
    STATUS status = getManager().doChangePassword(oldPassword, newPassword);

    String title;
    String body;
    Button button;
    JFXDialog dialog;

    switch (status) {
    case SUCCESS:
      // Password changed successfully
      title = "Password Changed Successfully!";
      button = new JFXButton("OKAY");
      dialog = showAlert(title, null, button);
      button.setOnAction((event) -> dialog.close());
      loadPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // Current Password is incorrect
      title = "Current password is incorrect.";
      body = "Please fix current pasword and try again";
      button = new JFXButton("OKAY");
      dialog = showAlert(title, body, button);
      button.setOnAction((event) -> dialog.close());
      break;

    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }

  /**
   * Old password field valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyOldPassword() {
    // Old password field must be alphanumeric with at least six characters.
    return StringHelper.checkAlphaNumeric(oldPasswordField.getText(), 6);
  }

  /**
   * New password field valid
   * 
   * @return true if valid, else false
   */
  private boolean verifyNewPassword() {
    // New password must be alphanumeric with at least six characters
    // and both new password fields are equal
    return StringHelper.checkAlphaNumeric(newPasswordField.getText(), 6) &&
        newPasswordField2.getText().contentEquals(newPasswordField.getText());
  }

}