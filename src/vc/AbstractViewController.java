package vc;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import vc.datatypes.VehicleView;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractViewController implements ViewController{
  private TopViewController mainVC;
  
  @Override
  public void setMainViewController(TopViewController mainVC) {
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
