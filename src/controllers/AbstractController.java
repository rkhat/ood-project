package controllers;

import controllers.adapters.VehicleView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Manager;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractController implements Controller{
  private Manager manager = Manager.getInstance();
  private TopController mainVC;
  
  @Override
  public void setMainViewController(TopController mainVC) {
    this.mainVC = mainVC;
    updateMainViewController();
  }
  
  protected void showToolbar(boolean show) {
    mainVC.showToolbar(show);
  }
  
  protected void setPage(Pages page) {
    mainVC.loadPage(page);
  }
  
  protected Manager getManager() {
    return manager;
  }
}
