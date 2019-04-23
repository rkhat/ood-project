package controllers;

import com.jfoenix.controls.JFXDialog;

import javafx.scene.Node;
import model.Manager;
import views.Pages;
import views.ToolbarView;
import views.Transition;

/**
 * Abstract controller for all the child pages.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractController implements Controller {
  private Manager manager = Manager.getInstance(); // manager
  private TopController parentController;
  private Pages backPage;
  private Node defaultNode;

  @Override
  public void setParentController(TopController parentController) {
    // set Parent controller
    this.parentController = parentController;
    // Use template pattern to update the parent controller
    updateParentController();
  }

  /**
   * Default action for back button
   */
  @Override
  public void back() {
    //back page loads from left to right
    loadPage(backPage, Transition.LTR);
  }

  /**
   * Change toolbar based on the toolbar view
   * 
   * @param toolbarView the toolbar view
   */
  protected void toolbar(ToolbarView toolbarView) {
    parentController.showToolbar(toolbarView);
  }

  /**
   * Set back page to run when back method is called.
   * 
   * @param backPage Back page of the current page
   */
  protected void setBackPage(Pages backPage) {
    this.backPage = backPage;
  }

  /**
   * Show an alert.
   * 
   * @param title   title of the alert.
   * @param body    body of the alert
   * @param actions
   * @return The jfxdialog that is displayed
   */
  protected JFXDialog showAlert(String title, String body, Node... actions) {
    // Call parent controller's show popup dialog
    return parentController.showPopupDialog(title, body, actions);
  }

  /**
   * Load a new page
   * 
   * @param page to load
   * @param transition direction the page should show from (None, LTR or RTL)
   */
  protected void loadPage(Pages page, Transition transition) {
    // Call parent controller's load new page
    parentController.loadPage(page, transition);
  }

  /**
   * Get manager.
   * 
   * @return manager
   */
  protected Manager getManager() {
    return manager;
  }

  /**
   * Set default node
   * 
   * @param node node to set as default
   */
  protected void setDefaultNode(Node node) {
    defaultNode = node;
  }

  @Override
  public void focus() {
    // request focus on default node;
    defaultNode.requestFocus();
  }
}
