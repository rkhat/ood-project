package ui;
import api.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class VehicleFormPage extends AbstractPage {
  public VehicleFormPage(Application app, Manager manager, boolean member) {
    super(app, manager);
    this.member = member;
    
  }
  
  
  private final boolean member;
}
