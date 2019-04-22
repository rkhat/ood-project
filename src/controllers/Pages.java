package controllers;

import java.net.URL;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public enum Pages {
  InitialPage("initial"),
  LoginPage("login"),
  SignupPage("signup"),
  MainMenuPage("mainmenu"),
  VehiclePage("vehicle"),
  ParkingMapPage("parking_map"),
  CheckoutPage("checkout"),
  SettingsPage("settings"),
  ChangePasswordPage("change_password"),
  AddCreditsPage("add_credits");
  
  private Pages(String filename) {
    this.url = getClass().getResource("/pages/" + filename + ".fxml");
  }
  
  public FXMLLoader getLoader() {
    return new FXMLLoader(url);
  }
  
  private final URL url;
}
