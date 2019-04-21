package controllers;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Spot;
import model.enums.STATUS;
import views.ToolbarView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class CheckoutController extends AbstractController {

  @FXML Label creditsAmountLabel;

  @FXML
  public void initialize() {
    double creditsAmount = getManager().getMember().getCredits();
    creditsAmountLabel.setText(Double.toString(creditsAmount));
  }

  @Override
  public void updateParentController() {
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Checkout";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  /**
   * Pay button action
   */
  public void payAction() {
    // perform checkout
    STATUS status = getManager().doCheckout();

    switch (status) {
    case SUCCESS:
      // on success go to main menu
      loadPage(Pages.MainMenuPage);
      break;

    case FAILED:
      // Pop-up dialog insufficient credits
      String title = "Insufficient Credits";
      String body = "Please add credits before checking out";
      Button button = new JFXButton("Okay");
      JFXDialog dialog = showAlert(title, body, button);
      button.setOnAction((event) -> dialog.close());
      break;

    default:
      throw new IllegalStateException("Impossible status: " + status);
    }
  }

}
