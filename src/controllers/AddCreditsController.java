package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.enums.STATUS;
import views.CurrencyTextField;
import views.Pages;
import views.ToolbarView;
import views.Transition;

/**
 * Add credits page
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class AddCreditsController extends AbstractController {
  @FXML Label creditsAmountLabel; // credits amount label

  @FXML Button add10Button; // add $10 button

  @FXML CurrencyTextField addCreditsField; // add credits field
  @FXML Button addCreditsButton; // add credits button

  /**
   * Constructor
   */
  public AddCreditsController() {
    super();
    // set back page
    setBackPage(Pages.SettingsPage);
  }

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // Get credits from manager with $ sign and two decimal places
    String amount = "$" + String.format("%.2f", getManager().getCredits());
    // Set credits amount label
    creditsAmountLabel.setText(amount);

    // validate the addCredits Field
    BooleanBinding addCreditsFieldValid = Bindings.createBooleanBinding(() -> {
      try {
        // if addCredits can be parsed return true
        parseAddCredits();
        return true;
      } catch (NumberFormatException e) {
        // else false
        return false;
      }
    }, addCreditsField.textProperty());

    // enable add credits button if field is valid
    addCreditsButton.disableProperty().bind(addCreditsFieldValid.not());

    // Set default node
    setDefaultNode(add10Button);

  }

  @Override
  public void updateParentController() {
    // Set toolbar
    ToolbarView toolbarView = new ToolbarView();
    toolbarView.show = true;
    toolbarView.showBackButton = true;
    toolbarView.title = "Settings";
    toolbarView.showSettingsButton = false;
    toolbar(toolbarView);
  }

  /**
   * Add credits with any amount
   * 
   * @param amount amount to add
   */
  private void addCredits(final double amount) {
    // get balance
    double oldBalance = getManager().getCredits();
    
    // Create string to show details of adding credits
    StringBuilder builder = new StringBuilder();
    builder.append("Buying:\t\t\t$" + String.format("%.2f", amount) + "\n");
    builder.append("Current Balance:\t$" + String.format("%.2f", oldBalance) + "\n");
    builder.append("New Balance:\t$" + String.format("%.2f", oldBalance + amount));

    // confirm adding credits
    String title = "Confirm adding credits: ";
    String body = builder.toString();
    Button confirmButton = new JFXButton("CONFIRM");
    Button cancelButton = new JFXButton("CANCEL");
    JFXDialog dialog = showAlert(title, body, confirmButton, cancelButton);

    // For no do nothing and close dialog
    cancelButton.setOnAction((eevent) -> dialog.close());

    // For yes,
    confirmButton.setOnAction((eevent) -> {
      // Perform add Credits
      STATUS status = getManager().doAddCredits(amount);

      switch (status) {
      case SUCCESS:
        // Go to mainmenu
        loadPage(Pages.MainMenuPage, Transition.LTR);

        // close old dialog
        dialog.close();

        // new dialog to show success
        String ttitle = "Credits added successfully!";
        Button bbutton = new JFXButton("OKAY");
        JFXDialog ddialog = showAlert(ttitle, null, bbutton);
        bbutton.setOnAction((event) -> ddialog.close());
        break;

      default:
        throw new IllegalStateException("Impossible status: " + status);
      }

    });
  }

  /**
   * Add $5 button action
   */
  @FXML
  public void addCreditsAction5() {
    addCredits(5.0);
  }

  /**
   * Add $10 button action
   */
  @FXML
  public void addCreditsAction10() {
    addCredits(10.0);
  }

  /**
   * Add $15 button action
   */
  @FXML
  public void addCreditsAction15() {
    addCredits(15.0);
  }

  /**
   * Add $20 button action
   */
  @FXML
  public void addCreditsAction20() {
    addCredits(20.0);
  }

  /**
   * Add button action to add any credit amount.
   */
  @FXML
  public void addCreditsActionAny() {
    try {
      addCredits(parseAddCredits());
    } catch (NumberFormatException e) {
      // Do nothing
    }
  }

  /**
   * Parse the addCredits currency field.
   * 
   * @return the amount in the parsed string.
   * @throws NumberFormatException if text cannot be parsed or non-positive
   */
  private double parseAddCredits() throws NumberFormatException {
    // Get amount string without the $ symbol
    String amountStr = addCreditsField.getText().substring(1);
    // Parse the amount
    double amount = Double.parseDouble(amountStr);
    // Do not allow non-positive
    if (amount <= 0) throw new NumberFormatException("Number must be positive");
    // return amount
    return amount;
  }

}
