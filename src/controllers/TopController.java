package controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import views.Pages;
import views.ToolbarView;
import views.Transition;

import java.io.IOException;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

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

  private Controller controller; // child controller

  private Parent currentRoot; // current child root

  /**
   * Initialize the page
   */
  @FXML
  public void initialize() {
    // load initial page
    Pages page = Pages.InitialPage;
    loadPage(page, Transition.NONE);
    
    // Escape button goes to the previous page
    mainPane.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
      if (ev.getCode() == KeyCode.ESCAPE) {
        backAction();
        ev.consume();
      }
    });
  }

  /**
   * Load a new page. This uses the strategy pattern.
   * 
   * @param page       new page to load
   * @param transition direction the page should show from (None, LTR or RTL)
   */
  public void loadPage(Pages page, Transition transition) {
    try {
      // Old Root
      final Parent oldRoot = currentRoot;

      // get the FXMLLoader of the page
      FXMLLoader loader = page.getLoader();
      // load the FXMLLoader
      currentRoot = loader.load();
      // get the loader's controller
      controller = loader.getController();
      // set this controller as main controller for the child
      controller.setParentController(this);

      // add new page
      content.getChildren().add(currentRoot);

      // new page animation
      TranslateTransition newNav = new TranslateTransition(new Duration(350),
          currentRoot);
      // old page animation
      TranslateTransition oldNav = new TranslateTransition(new Duration(150),
          oldRoot);

      oldNav.setOnFinished(event -> content.getChildren().remove(oldRoot));

      if (oldRoot != null) {
        // transition animation
        switch (transition) {
        case NONE:
          // just remove current root
          content.getChildren().remove(oldRoot);
          break;
        case RTL:
          // right to left animation
          newNav.setFromX(400.0);
          newNav.setToX(0.0);
          oldNav.setFromX(0.0);
          oldNav.setToX(-400.0);
          newNav.play();
          oldNav.play();
          break;
        case LTR:
          // right to left animation
          newNav.setFromX(-400.0);
          newNav.setToX(0.0);
          oldNav.setFromX(0.0);
          oldNav.setToX(400.0);
          newNav.play();
          oldNav.play();
          break;
        }
      }

      // focus on child page
      controller.focus();
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
    loadPage(Pages.SettingsPage, Transition.RTL);
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
    // "NO" button) or "OKAY" if only one button exist
    final Button escapeButton = actions.length > 1 ? (Button) actions[1]
        : (Button) actions[0];

    dialog.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
      if (ev.getCode() == KeyCode.ESCAPE) {
        escapeButton.fire();
        ev.consume();
      }
    });

    // Show dialog
    dialog.show();
    return dialog;
  }

}
