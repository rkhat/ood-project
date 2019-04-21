package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.enums.STATUS;

public class ManagerTest {
  private ParkingSystem ps;
  private Manager app;
  private STATUS status;
  
  private final String defaultUsername = "Alec";
  private final String defaultPassword = "Alec123";
  private final String defaultPlate = "ABC123";
  
  public void init() {
    ps = ParkingSystem.getInstance();
    int numSpots = 2;
    List<Spot> spots = new ArrayList<Spot>(numSpots);
    for (int i = 0; i < numSpots; i++) {
      spots.add(new Spot(1,i));
    }
    ParkingMap pm = new ParkingMap(spots);
    ps.setMap(pm);
    app = Manager.getInstance();
  }
  
  public void reset() {
    ps.reset();
  }
  
  public void initWithMember(boolean loggedIn, boolean vehicle, boolean reservation) {
    init();
    ps.createAccount(defaultUsername, defaultPassword);
    if (vehicle) {
      app.doLogIn(defaultUsername, defaultPassword);
      app.doAddVehicle(defaultPlate);
      if (reservation) {
        app.doSelectVehicle(0);
        app.doSelectSpot(0);
      }
      if (!loggedIn) {
        app.doLogOut();
      }
    }
  }
  
  // Testing doCreateAccount
  @Test
  void testDoCreateAccount() {
    init();
    System.out.println("Run test doCreateAccount");
    String userName = "Alec";
    String password = "abc123";
    status = app.doCreateAccount(userName, password);
    Assert.assertTrue(status == STATUS.SUCCESS);
    reset();
  }
  
  // Testing doLogIn
  @Test
  void testDoLogIn() {
    initWithMember(false, false, false);
    System.out.println("Run test doLogIn");
    // log in with correct un and psw
    status = app.doLogIn(defaultUsername, defaultPassword);
    Assert.assertTrue(status == STATUS.SUCCESS);
    // log in with incorrect psw
    status = app.doLogIn(defaultUsername,"wrongpsw");
    Assert.assertTrue(status == STATUS.FAILED);
    // log in with incorrect un
    status = app.doLogIn("wrongun",defaultPassword);
    Assert.assertTrue(status == STATUS.FAILED);
    reset();
  }
  
  // Testing doSelectVehicle
  @Test
  void testDoSelectVehicle() {
    initWithMember(true, true, false);
    System.out.println("Run test doSelectVehicle");
    status = app.doSelectVehicle(0);
    Assert.assertTrue(status == STATUS.SUCCESS);
    reset();
  }
  
  // Testing doChangePassword 
  @Test
  void testDoChangePassword() {
    initWithMember(true, false, false);
    System.out.println("Run test doChangePassword");
    String newPassword = "
    status = app.doChangePassword(defaultPassword, )
  }

}
