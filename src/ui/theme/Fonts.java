package ui.theme;

import java.awt.*;
import java.io.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Fonts {
  public static Font getFont(String fileName, float fontSize) {
    try (InputStream stream = Fonts.class.
            getResourceAsStream("/resources/fonts/" + fileName)) {
      
      return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(fontSize);
      
    } catch (IOException | FontFormatException e) {
      System.out.println (fileName + " = null");
      return null;
    }
  }
  
  public static final Font BOLD = Fonts.getFont("Roboto-Bold.ttf", 14f);
  public static final Font MEDIUM = Fonts.getFont("Roboto-Medium.ttf", 14f);
  public static final Font REGULAR = Fonts.getFont("Roboto-Regular.ttf", 14f);
}
