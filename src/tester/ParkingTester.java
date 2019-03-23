package tester;
import javax.swing.*;
import api.*;
import ui.*;
import ui.theme.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingTester {
  public static void main(String[] args) {
    GUITheme guiTheme = GUITheme.getInstance();
    try {
      guiTheme.setTheme(Theme.LightTheme);
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println(e);
    }
    Manager manager = Manager.getInstance();
    Application app = new Application(manager);
    app.restart();
    app.setSize(600, 600);
    app.setVisible(true);
  }
}
