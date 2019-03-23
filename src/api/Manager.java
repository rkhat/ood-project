package api;

import api.datatype.*;
import api.adapter.*;
import model.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
  private Manager() {
  }
  
  
  public void createGuestSession() {
    GuestSession session = new GuestSession();
  }
  
  public void createMemberSession() {
    MemberSession session = new MemberSession();
  }
  
  public static Manager getInstance() {
    return instance;
  }
  
  private static final Manager instance = new Manager(); 
  
//  private static final ParkingSystem parkingSystem = ParkingSyst;
}
