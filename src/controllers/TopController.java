package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import views.ToolbarView;

import java.io.IOException;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import controllers.Pages;
import controllers.Controller;
import javafx.fxml.FXMLLoader;

/**
 * Top Controller to handle every page. It also handles the toolbar and pop-up
 * dialogs.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class TopController {
  @FXML StackPane rootPane; // root pane
  @FXML Pane mainPane; // pane contains content+toolbar but not dialog

  @FXML ToolBar toolbar; // toolbar
  @FXML Button backButton; // back button
  @FXML Label titleToolbar; // title in toolbar
  @FXML Button settingsButton; // settings button

  @FXML Pane content; // child content

  Controller controller; // child controller

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // load initial page
    Pages page = Pages.InitialPage;
    loadPage(page);
  }

  /**
   * Load a new page. This uses the strategy pattern.
   * 
   * @param page new page to load
   */
  public void loadPage(Pages page) {
    try {
      // get the FXMLLoader of the page
      FXMLLoader loader = page.getLoader();
      // load the FXMLLoader
      Parent root = loader.load();
      // get the loader's controller
      controller = loader.getController();
      // set this controller as main controller for the child
      controller.setParentController(this);

      // clear old page
      content.getChildren().clear();
      // add new page
      content.getChildren().add(root);

      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          controller.focus();
        }
      });

    } catch (IOException ex) {
      System.err.println("Page " + page + " does not exist!");
      System.exit(1);
    }
  }

  /**
   * Display the toolbar based on the toolbar view.
   * 
   * @param toolbarView The toolbar view
   */
  public void showToolbar(ToolbarView toolbarView) {
    // show toolbar
    toolbar.setVisible(toolbarView.show);
    // show back button
    backButton.setVisible(toolbarView.showBackButton);
    backButton.setManaged(toolbarView.showBackButton);
    // show title label
    titleToolbar.setText(toolbarView.title);
    // show settings button
    settingsButton.setVisible(toolbarView.showSettingsButton);
  }

  /**
   * Back button action
   * 
   */
  @FXML
  public void backAction() {
    // call child controller's back button
    controller.back();
  }

  /**
   * Settings button action
   * 
   */
  @FXML
  public void settingsAction() {
    // load settings page
    loadPage(Pages.SettingsPage);
  }

  /**
   * Show a popup dialog
   * 
   * @param title   title of dialog
   * @param body    body of dialog
   * @param actions buttons of dialog
   * @return dialog
   */
  public JFXDialog showPopupDialog(String title, String body, Node... actions) {
    // Create dialog layout
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    // Add title
    dialogLayout.setHeading(new Text(title));
    // Add body if not null
    if (body != null) {
      dialogLayout.setBody(new Text(body));
    }
    // Add buttons
    dialogLayout.setActions(actions);

    // Create dialog from dialog layout and attach it to the
    // root Pane
    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout,
        JFXDialog.DialogTransition.CENTER);

    // Prevent closing dialog anywhere
    dialog.setOverlayClose(false);

    // disable main pane to prevent tabbing to buttons in it
    mainPane.setDisable(true);

    dialog.setOnDialogOpened((event) -> {
      // Put focus on first button (usually "YES" or "OKAY" button)
      actions[0].requestFocus();
    });

    dialog.setOnDialogClosed((event) -> {
      // re-enable main pane to when dialog closed
      mainPane.setDisable(false);
      // re-focus on the default node of child
      controller.focus();
    });

    // Program escape button to click on second button if available (usually
    // "NO" button)
    if (actions.length > 1) {
      dialog.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
        if (ev.getCode() == KeyCode.ESCAPE) {
          ((Button) actions[1]).fire();
          ev.consume();
        }
      });
    }

    // Show dialog
    dialog.show();
    return dialog;
  }

}
