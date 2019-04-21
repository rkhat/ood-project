package controllers;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import views.ToolbarView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
  private Pages backPage;
  Controller controller;

  @FXML StackPane topPane;
  @FXML ToolBar toolbar;
  @FXML Pane content;

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
      System.out.println("Loader:" + loader);
      // load the FXMLLoader
      Parent root = loader.load();
      System.out.println("PageRoot:" + root);
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
    toolbar.setVisible(toolbarView.show);
  }

  /**
   * Back button action
   * 
   */
  public void backAction() {
    controller.back();
  }

  /**
   * Set back page
   * 
   * @param backPage Back page to set
   */
  public void setBackPageAction(Pages backPage) {
    this.backPage = backPage;
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

    JFXDialog dialog = new JFXDialog(topPane, dialogLayout,
        JFXDialog.DialogTransition.CENTER);

    dialog.show();
    return dialog;
  }

}
