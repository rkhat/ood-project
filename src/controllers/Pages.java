package controllers;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
  PasswordPage("password"),
  CreditsPage("credits");
  
  private Pages(String filename) {
    this.url = getClass().getResource("/pages/" + filename + ".fxml");
  }
  
  public FXMLLoader getLoader() {
    return new FXMLLoader(url);
  }
  
  private final URL url;
}
