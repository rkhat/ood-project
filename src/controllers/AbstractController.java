package controllers;

import controllers.datatypes.VehicleView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractController implements Controller{
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
  
  
}
