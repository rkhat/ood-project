package api;

import api.datatype.*;
import api.adapter.*;
import java.util.*;
import model.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
  private Manager() {
  }
  
  
  public void createGuestSession() {
    guestSession = new GuestSession();
  }
  
  public void createMemberSession() {
    memberSession = new MemberSession();
  }
  
  public static Manager getInstance() {
    return instance;
  }
  
  private static final Manager instance = new Manager();
  
//  private static final ParkingSystem parkingSystem = ParkingSyst;
  private MemberSession memberSession;
  private GuestSession guestSession;
}
