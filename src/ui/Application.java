package ui;
import api.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Application extends JFrame {
  public Application(Manager manager) {
    super();
    this.manager = manager;
    
    setMinimumSize(MIN_DIMENSION);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public void swapPage(Page newPage) {
    remove(currentPage.getPanel());
    currentPage = newPage;
    add(currentPage.getPanel());
    repaint();
  }
  
  public void restart() {
    currentPage = new InitialPage(this, manager);
    add(currentPage.getPanel());
    repaint();
  }
  
  public void setCurrentSessionId(int sessionId) {
    this.sessionId = sessionId;
  }
  
  private int sessionId = 0;
  private Page currentPage;
  private final Manager manager;
  
  private static final Dimension MIN_DIMENSION = new Dimension(300, 300);
}
