package ui.theme;

import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class GUITheme {
  private GUITheme() {
  }
  
  
  public void setTheme(Theme theme) throws UnsupportedLookAndFeelException{
    currentTheme = theme;
    UIManager.setLookAndFeel(theme.getLookAndFeel());
    UIManager.put("Button.font", theme.getButtonFont());
    UIManager.put("Button.background", theme.getButtonBackground());
    UIManager.put("Button.foreground", theme.getButtonForeground());
  }
  
  public static final GUITheme getInstance() {
    return instance;
  }
  
  private Theme currentTheme;
  
  private static final GUITheme instance = new GUITheme();
}
