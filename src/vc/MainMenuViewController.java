package vc;

import javafx.fxml.FXML;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class MainMenuViewController extends AbstractViewController{
  
  
  @FXML
  public void initialize() {
  }

  @Override
  public void updateMainViewController() {
    showToolbar(true);
  }
  
  
  
}