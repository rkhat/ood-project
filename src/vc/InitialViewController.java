package vc;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class InitialViewController extends AbstractViewController{
  
  @FXML
  public void initialize() {
  }

  @Override
  public void updateMainViewController() {
    showToolbar(false);
  }
  
  public void guest() {
  }
  
  public void login() {
    setPage(Pages.LoginPage);
  }
  
  public void signup() {
    setPage(Pages.SignupPage);
  }
}
