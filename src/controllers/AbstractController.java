package controllers;

import com.jfoenix.controls.JFXDialog;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.Manager;
import views.ToolbarView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractController implements Controller{
  private Manager manager = Manager.getInstance();
  private TopController mainController;
  private Pages backPage;
  
  @Override
  public void setParentController(TopController mainController) {
    this.mainController = mainController;
    updateParentController();
  }
  
  public void back() {
    mainController.loadPage(backPage);
  }
  
  protected void toolbar(ToolbarView toolbarView) {
    mainController.showToolbar(toolbarView);
  }
  
  protected void setBackPage(Pages backPage) {
    this.backPage = backPage;
  }
  
  protected JFXDialog showAlert(String title, String body, Node... actions) {
    return mainController.showPopupDialog(title, body, actions);
  }
  
  protected void loadPage(Pages page) {
    mainController.loadPage(page);
  }
  
  protected Manager getManager() {
    return manager;
  }
}
