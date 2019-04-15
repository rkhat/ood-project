package vc;

import java.util.List;
import javafx.fxml.FXML;
import model.*;

/**
 * FXML Controller class
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class LoginViewController extends AbstractViewController{
  
  @FXML
  public void initialize() {    
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }
  
  public void login() {
    setPage(Pages.MainMenuPage);
  }
  
}
