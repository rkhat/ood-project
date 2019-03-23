package ui;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public interface Page {
  Component getPanel();
  
  void update();
  
  void repaint();
  
  void backPage();
}
