package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import util.StringHelper;
import views.ToolbarView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ChangePasswordController extends AbstractController {
  @FXML PasswordField currentPasswordField;
  @FXML PasswordField newPasswordField;
  @FXML PasswordField newPasswordField2;
  @FXML Button ChangePasswordButton;

  public ChangePasswordController() {
    setBackPage(Pages.InitialPage);
  }

  @FXML
  public void initialize() {
    // current password field validation listener
    BooleanBinding currentPasswordFieldValid = Bindings
        .createBooleanBinding(() -> {
          // Current password field must be alphanumeric with at least six
          // characters.
          return StringHelper.checkAlphaNumeric(currentPasswordField.getText(),
              6);
        }, currentPasswordField.textProperty());

    // New password fields validation listener
    BooleanBinding newPasswordFieldValid = Bindings.createBooleanBinding(() -> {
      // New password must be alphanumeric with at least six characters
      // and both new password fields are equal
      return StringHelper.checkAlphaNumeric(newPasswordField.getText(), 6) &&
          newPasswordField2.getText().contentEquals(newPasswordField.getText());

    }, newPasswordField.textProperty(), newPasswordField2.textProperty());

    // enable login button when all fields are valid
    BooleanBinding valid = currentPasswordFieldValid.and(newPasswordFieldValid);
    ChangePasswordButton.disableProperty().bind(valid.not());
  }

  @Override
  public void updateParentController() {
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
  public void changePasswordAction() {
    String currentPassword = currentPasswordField.getText();
    String newPassword = newPasswordField.getText();

//    // Perform login
////    STATUS status = getManager().(username, password);
//
//    switch (status) {
//    case SUCCESS:
//      // Password changed successfully
//      String title = "Password Changed Successfully!";
//      Button button = new JFXButton("Okay");
//      JFXDialog dialog = showAlert(title, null, button);
//      button.setOnAction((event) -> dialog.close());
//      loadPage(Pages.MainMenuPage);
//      break;
//
//    case FAILED:
//      // Current Password is incorrect
//      String title = "Current password is incorrect.";
//      String body = "Please fix current pasword and try again";
//      Button button = new JFXButton("Okay");
//      JFXDialog dialog = showAlert(title, body, button);
//      button.setOnAction((event) -> dialog.close());
//      break;
//
//    default:
//      throw new IllegalStateException("Impossible status: " + status);
//    }
  }

}