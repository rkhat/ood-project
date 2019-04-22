package controllers;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  @FXML StackPane rootPane;

  @FXML ToolBar toolbar;
  @FXML Button backButton;
  @FXML Label titleToolbar;
  @FXML Button settingsButton;

  @FXML Pane content;

  Controller controller; // child controller

  @FXML
  public void initialize() {
    // load initial page
    Pages page = Pages.InitialPage;
    loadPage(page);
  }

  /**
   * Load a new page
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
    controller.back();
  }

  /**
   * Settings button action
   * 
   */
  @FXML
  public void settingsAction() {
    loadPage(Pages.SettingsPage);
  }

  /**
   * Show a popup dialog
   * 
   * @param title   title of dialog
   * @param body    body of dialog
   * @param actions buttons of dialog
   */
  public JFXDialog showPopupDialog(String title, String body, Node... actions) {
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setHeading(new Text(title));
    if (body != null) {
      dialogLayout.setBody(new Text(body));
    }
    dialogLayout.setActions(actions);

    JFXDialog dialog = new JFXDialog(rootPane, dialogLayout,
        JFXDialog.DialogTransition.CENTER);
    dialog.setOverlayClose(false);
    dialog.show();
    return dialog;
  }

}
