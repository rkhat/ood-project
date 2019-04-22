package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import model.enums.STATUS;

public class ManagerTest {
  private ParkingSystem ps;
  private Manager app;
  private STATUS status;

  private final String defaultUsername = "Alec";
  private final String defaultPassword = "Alec123";
  private final String defaultPlate = "ABC123";

  /**
   * initialize a Manager with a Parking System and Parking Map with preset
   * spots
   */
  public void init() {
    ps = ParkingSystem.getInstance();
    int numSpots = 2;
    List<Spot> spots = new ArrayList<Spot>(numSpots);
    for (int i = 0; i < numSpots; i++) {
      spots.add(new Spot(1, i));
    }
    ParkingMap pm = new ParkingMap(spots);
    ps.setMap(pm);
    app = Manager.getInstance();
  }

  @AfterEach
  public void reset() {
    ps.reset();
  }

  /**
   * initialize a Manager with a Parking System and Parking Map with preset
   * spots, and a preset member.
   * 
   * @param loggedIn    if true, member remains logged in after function call.
   * @param vehicle     if true, member is initialized with a default vehicle.
   * @param reservation if true (and vehicle is true), member is initialized
   *                    with a reservation.
   */
  // initialize a Manager with a Parking System and Parking Map with preset
  // spots, and a preset member.
  // if loggedIn == true, member will be logged in after function call.
  // if
  public void initWithMember(boolean loggedIn, boolean vehicle,
      boolean reservation) {
    init();
    app.doCreateAccount(defaultUsername, defaultPassword);
    if (vehicle) {
      app.doAddVehicle(defaultPlate);
      if (reservation) {
        app.doSelectVehicle(0);
        app.doSelectSpot(0);
      }
    }
    if (!loggedIn) {
      app.doLogOut();
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
    status = app.doLogIn(defaultUsername, "wrongpsw");
    Assert.assertTrue(status == STATUS.FAILED);
    // log in with incorrect un
    status = app.doLogIn("wrongun", defaultPassword);
    Assert.assertTrue(status == STATUS.FAILED);
  }

  // Testing doLogOut
  @Test
  void testDoLogOut() {
    initWithMember(true, false, false);
    System.out.println("Run test doLogOut");
    // try to log out with member logged in (should succeed)
    status = app.doLogOut();
    Assert.assertTrue(status == STATUS.SUCCESS);
    Assert.assertTrue(app.getMember() == null);
    Assert.assertTrue(app.getVehiclesAsList() == null);
    Assert.assertTrue(app.getReservation() == null);
    // try to log out with no member logged in (should fail)
    status = app.doLogOut();
    Assert.assertTrue(status == STATUS.FAILED);
  }

  // Testing doSelectVehicle
  @Test
  void testDoSelectVehicle() {
    initWithMember(true, true, false);
    System.out.println("Run test doSelectVehicle");
    status = app.doSelectVehicle(0);
    Assert.assertTrue(status == STATUS.SUCCESS);
  }

  // Testing doChangePassword
  @Test
  void testDoChangePassword() {
    initWithMember(true, false, false);
    System.out.println("Run test doChangePassword");
    String newPassword = "new123";
    status = app.doChangePassword(defaultPassword, newPassword);
    Assert.assertTrue(status == STATUS.SUCCESS);
    status = ps.verifyLoginInfo(defaultUsername, newPassword).getKey();
    Assert.assertTrue(status == STATUS.SUCCESS);
  }

  // Testing doSelectSpot
  @Test
  void testDoSelectSpot() {
    initWithMember(true, true, false);
    System.out.println("Run test doSelectSpot");
    app.doSelectVehicle(app.getVehiclesAsList().get(0).getID());
    status = app.doSelectSpot(app.getSpotsAsList().get(0).getID());
    Assert.assertTrue(status == STATUS.SUCCESS);
    status = app.doSelectSpot(5);
    Assert.assertTrue(status == STATUS.SPOT_NOT_FOUND);
  }

  // Testing doAddCredits
  @Test
  void testDoAddCredits() {
    initWithMember(true, false, false);
    System.out.println("Run test doAddCredits");
    status = app.doAddCredits(10);
    Assert.assertTrue(status == STATUS.SUCCESS);
    Assert.assertTrue(app.getCredits() == 10);
  }

  // Testing doRemoveCredits
  @Test
  void testDoRemoveCredits() {
    initWithMember(true, false, false);
    System.out.println("Run test doRemoveCredits");
    double addAmt = 10, subAmt = 4.7;
    app.doAddCredits(addAmt);
    status = app.doRemoveCredits(subAmt);
    Assert.assertTrue(status == STATUS.SUCCESS);
    Assert.assertTrue(app.getCredits() == addAmt - subAmt);
  }

  // Testing doAddVehicle
  @Test
  void testDoAddVehicle() {
    initWithMember(true, true, false);
    System.out.println("Run test doAddVehicle");
    // try to add existing vehicle, should fail
    status = app.doAddVehicle(defaultPlate);
    Assert.assertTrue(status == STATUS.PLATE_DUPLICATE);
    // try to add a new vehicle with valid plate, should succeed
    status = app.doAddVehicle("NEW123");
    Assert.assertTrue(status == STATUS.SUCCESS);
    // try to add a new vehicle with invalid plate, should fail
    status = app.doAddVehicle("toomanycharacters");
    Assert.assertTrue(status == STATUS.PLATE_INVALID);
  }

  // Testing doRemoveVehicle
  @Test
  void testDoRemoveVehicle() {
    initWithMember(true, true, false);
    System.out.println("Run test doRemoveVehicle");
    // try to remove the existing vehicle, should succeed
    status = app.doRemoveVehicle(app.getVehiclesAsList().get(0).getID());
    Assert.assertTrue(status == STATUS.SUCCESS);
    // try to remove a vehicle now that there aren't any
    status = app.doRemoveVehicle(0);
    Assert.assertTrue(status == STATUS.INVALID_ID);
  }

  // Testing doCheckOut
  @Test
  void testDoCheckOut() {
    initWithMember(true, true, true);
    System.out.println("Run test doCheckOut");
    // try to check out with no credits, should fail
    status = app.doCheckout();
    Assert.assertTrue(status == STATUS.FAILED);
    // add credits and try to check out again
    app.doAddCredits(10);
    status = app.doCheckout();
    Assert.assertTrue(status == STATUS.SUCCESS);
    // make sure remaining credits is 10-2.5 = 7.5
    Assert.assertTrue(app.getCredits() == 7.5);
  }

}
