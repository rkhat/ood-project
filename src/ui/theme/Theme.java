package ui.theme;
import java.awt.*;
import javax.swing.*;
import mdlaf.MaterialLookAndFeel;
import mdlaf.utils.MaterialColors;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Theme {
  
  private Theme(
    LookAndFeel lookAndFeel,
    Font buttonFont,
    Color buttonBackground,
    Color buttonForeground
  ) {
    this.lookAndFeel = lookAndFeel;
    
    this.buttonFont = buttonFont;
    this.buttonBackground = buttonBackground;
    this.buttonForeground = buttonForeground;
  }
  
  public LookAndFeel getLookAndFeel() {
    return lookAndFeel;
  }
  
  public Font getButtonFont() {
    return buttonFont;
  }
  
  public Color getButtonBackground(){
    return buttonBackground;
  }
  
  public Color getButtonForeground() {
    return buttonForeground;
  }
  
  //LookAndFeel
  private final LookAndFeel lookAndFeel;
  
  //Button
  private final Font buttonFont;
	private final Color buttonBackground, buttonForeground;
  
  
  //Themes
  public static final Theme LightTheme = new Theme(
    new MaterialLookAndFeel(),
    Fonts.MEDIUM, //Font
    new Color (245, 0, 87), //Buttons
    MaterialColors.WHITE
  );

}
