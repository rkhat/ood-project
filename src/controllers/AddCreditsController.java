package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.enums.STATUS;
import views.ToolbarView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class AddCreditsController extends AbstractController {
  
  @FXML Label creditsAmountLabel;
  
  public AddCreditsController() {
    setBackPage(Pages.SettingsPage);
  }

  @FXML
  public void initialize() {
    String creditsAmount = Double.toString(getManager().getMember().getCredits());
    creditsAmountLabel.setText(creditsAmount);
  }

  @Override
  public void updateParentController() {
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Settings";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }
  
  private void addCredits(double amount) {
    // Perform add Credits
    STATUS status = getManager().doAddCredits(amount);
    
    switch (status) {
    case SUCCESS:
      // Success
      String title = "Credits added successfully!";
      Button button = new JFXButton("Okay");
      JFXDialog dialog = showAlert(title, null, button);
      button.setOnAction((event) -> dialog.close());
      loadPage(Pages.MainMenuPage);
      break;
    
    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }
  
  @FXML
  public void addCreditsAction5() {
    addCredits(5.0);
  }
  
  @FXML
  public void addCreditsAction10() {
    addCredits(10.0);
  }
  
  @FXML
  public void addCreditsAction15() {
    addCredits(15.0);
  }
  
  @FXML
  public void addCreditsAction20() {
   addCredits(20.0);
  }
  
}
