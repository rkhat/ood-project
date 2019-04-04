package ui;
import api.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class InitialPage extends AbstractPage {
  public InitialPage(Application app, Manager manager) {
    super(app, manager);
    panel.add(guest);
    panel.add(member);
    panel.add(signup);
    
    guest.addActionListener((ActionEvent e) -> {
      manager.createGuestSession();
      Page page = new VehicleFormPage(app, manager, false);
      app.swapPage(page);
    });
    
    member.addActionListener((ActionEvent e) -> {
      Page page = new LoginFormPage(app, manager);
      app.swapPage(page);
    });
    
    signup.addActionListener((ActionEvent e) -> {
      Page page = new SignupFormPage(app, manager);
      app.swapPage(page);
    });
  }
  
  JButton guest = new JButton("GUEST");
  JButton member = new JButton("MEMBER");
  JButton signup = new JButton("SIGN UP");
}
