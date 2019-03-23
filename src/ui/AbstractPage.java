package ui;
import api.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public abstract class AbstractPage implements Page {
  
  AbstractPage(Application app, Manager manager) {
    this.app = app;
    this.manager = manager;
    panel = new JPanel();
  }
  
  @Override
  public Component getPanel() {
    return panel;
  }
  
  @Override
  public void update() {
    
  }
  
  @Override
  public void repaint() {
    update();
    panel.repaint();
  }
  
  @Override
  public void backPage() {
    
  }
  
  protected final Application app;
  protected final Manager manager;
  protected final JPanel panel;
}
