package views;

import java.net.URL;
import javafx.fxml.FXMLLoader;

/**
 * Child pages enumeration
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

  /**
   * Constructor that stores the filename in a URL
   * 
   * @param filename filename
   */
  private Pages(String filename) {
    this.url = getClass().getResource("/pages/" + filename + ".fxml");
  }

  /**
   * Get the FXMLLoader of the url
   * 
   * @return FXMLLoader of the url
   */
  public FXMLLoader getLoader() {
    return new FXMLLoader(url);
  }

  private final URL url; // URL
}
