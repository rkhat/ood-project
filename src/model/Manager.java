package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import model.enums.STATUS;
import util.StringHelper;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager implements Serializable {

  private static final long serialVersionUID = 3231326285507144700L;
  
  // non-changing fields
  private ParkingSystem parkingSystem;
  private ParkingMap parkingMap;
  private Map<Integer, Spot> spots;

  // reset after logout
  private Member member;
  private Map<Integer, Vehicle> vehicles;

  // current reservation details
  private Reservation reservation;
  private Spot spot;
  private Vehicle vehicle;

  /**
   * Manager constructor.
   * 
   * @param ps The parking system instance.
   */
  private Manager() {
    parkingSystem = ParkingSystem.getInstance();
    parkingMap = parkingSystem.getMap();
    if (parkingMap == null) {
      parkingMap = new ParkingMap(new ArrayList<Spot>());
    }
    spots = parkingMap.getSpotsAsMap();
  }
  
  /**
   * Manager constructor with specified ParkingSystem object.
   * 
   * @param ps  The ParkingSystem object.
   */
  private Manager(ParkingSystem ps) {
    parkingSystem = ps;
    parkingMap = parkingSystem.getMap();
    spots = parkingMap.getSpotsAsMap();
  }

  /**
   * Create a new instance of Manager.
   * 
   */
  public static void createInstance() {
    instance = new Manager();
  }
  
  public static void createInstance(ParkingSystem ps) {
    instance = new Manager(ps);
  }

  /**
   * Return the last Manager instance created
   * 
   * @return The instance.
   */
  public static Manager getInstance() {
    return instance;
  }

  /**
   * Takes care of entering log in info.
   * 
   * @param un  The username entered.
   * @param psw The password entered.
   * @return SUCCESS, FAILED
   */
  public STATUS doLogIn(String un, String psw) {
    Pair<STATUS, Member> p = parkingSystem.verifyLoginInfo(un, psw);
    STATUS status = p.getKey();
    if (status == STATUS.SUCCESS) {
      // success, log the member in
      member = p.getValue();
      reservation = member.getReservation();
      if (reservation != null)
        vehicle = reservation.getVehicle();
      vehicles = member.getVehicles();
      return STATUS.SUCCESS;
    }
    // failed, return status
    member = null;
    return status;
  }

  /**
   * Takes care of creating an account.
   * 
   * @param un  The username entered.
   * @param psw The password entered.
   * @return SUCCESS, USERNAME_INVALID, USERNAME_IN_USE, PASSWORD_INVALID
   */
  public STATUS doCreateAccount(String un, String psw) {
    Pair<STATUS, Member> p = parkingSystem.createAccount(un, psw);
    STATUS status = p.getKey();
    if (status == STATUS.SUCCESS) {
      // creation successful, log the member in
      member = p.getValue();
      vehicles = member.getVehicles();
      return STATUS.SUCCESS;
    }
    // if account creation fails, return status
    member = null;
    return status;
  }

  /**
   * Handle password change.
   * 
   * @param oldPsw The old password, for verification.
   * @param newPsw The new password.
   * @return SUCCESS if successful, PASSWORD_INVALID if new password is invalid
   *         format, FAILED if verification of old login info failed,
   *         PASSWORD_REUSE if the new password matches the old password.
   * @precondition Member must be logged in prior to function call.
   */
  public STATUS doChangePassword(String oldPsw, String newPsw) {
    return parkingSystem.changePassword(member.getUserName(), oldPsw, newPsw);
  }

  /**
   * Attempt to create a reservation with the vehicle corresponding to the given
   * ID.
   * 
   * @param vehicleID The ID of the vehicle.
   * @return SUCCESS, NO_SPOTS_AVAILABLE, MEMBER_HAS_RESERVATION, INVALID_ID, or
   *         NOT_LOGGED_IN
   */
  public STATUS doSelectVehicle(int vehicleID) {
    // no member logged in
    if (member == null)
      return STATUS.NOT_LOGGED_IN;
    // member already has a reservation
    if (reservation != null)
      return STATUS.MEMBER_HAS_RESERVATION;
    // no spots available
    if (!parkingSystem.spotsAvailable())
      return STATUS.NO_SPOTS_AVAILABLE;
    vehicle = vehicles.get(vehicleID);
    // invalid ID
    if (vehicle == null)
      return STATUS.INVALID_ID;

    // vehicle is valid, create the reservation
    reservation = new Reservation();
    reservation.setVehicle(vehicle);
    reservation.setCode(member.getCode());
    return STATUS.SUCCESS;
  }

  /**
   * Take care of selecting a spot.
   * 
   * @param spotID The ID of the spot to select.
   * @return SUCCESS, SPOT_NOT_FOUND, SPOT_RESERVED, or FAILED
   * @precondition vehicle must be selected using doSetVehicle prior to calling
   *               this function.
   */
  public STATUS doSelectSpot(int spotID) {
    // try to select the spot and add it to the reservation.
    spot = spots.get(spotID);
    // spot not found
    if (spot == null)
      return STATUS.SPOT_NOT_FOUND;
    // spot reserved
    if (spot.isReserved())
      return STATUS.SPOT_RESERVED;
    // spot available, add to reservation.
    reservation.setSpot(spot);
    member.setReservation(reservation);
    return parkingSystem.addReservation(reservation);
  }

  /**
   * Take care of adding credits to account.
   * 
   * @param amt The amount of credits to add.
   * @return SUCCESS or FAILED
   */
  public STATUS doAddCredits(double amt) {
    if (amt < 0) {
      return STATUS.FAILED;
    }
    member.addCredits(amt);
    return STATUS.SUCCESS;
  }

  /**
   * Take care of removing credits from account.
   * 
   * @param amt The amount of credits to remove.
   * @return SUCCESS, INSUFFICIENT_CREDITS, or INVALID_AMT
   */
  public STATUS doRemoveCredits(double amt) {
    if (amt < 0)
      return STATUS.INVALID_AMT;
    if (amt > member.getCredits())
      return STATUS.INSUFFICIENT_CREDITS;
    member.removeCredits(amt);
    return STATUS.SUCCESS;
  }

  /**
   * Take care of logging out.
   * 
   * @return SUCCESS or FAILED
   */
  public STATUS doLogOut() {
    // if a member is logged in, log out and return SUCCESS.
    if (member != null) {
      resetReservation();
      member = null;
      vehicles = null;
      return STATUS.SUCCESS;
    }
    // no member logged in, return false.
    return STATUS.FAILED;
  }

  /**
   * Take care of adding a vehicle.
   * 
   * @param plate String representing the plate number.
   * @return SUCCESS if vehicle added, PLATE_INVALID if plate is invalid format,
   *         PLATE_DUPLICATE if vehicle with same plate is in sytem.
   */
  public STATUS doAddVehicle(String plate) {
    // must be alphanumeric with 6 characters
    if (!(StringHelper.checkAlphaNumeric(plate, 6, true))) {
      return STATUS.PLATE_INVALID;
    }
    // alphanumeric with 6 characters, try to add to system
    Vehicle veh = new Vehicle(plate);
    STATUS status = parkingSystem.addVehicle(veh);
    if (status == STATUS.SUCCESS) {
      // added to system, now add to member
      member.addVehicle(veh);
      return STATUS.SUCCESS;
    }
    // failed, return status.
    return status;
  }

  /**
   * Remove a vehicle from the member's account.
   * 
   * @param id The ID of the vehicle to remove.
   * @return INVALID_ID, PLATE_NOT_FOUND, or SUCCESS
   */
  public STATUS doRemoveVehicle(int id) {
    Vehicle v = vehicles.get(id);
    // ID is invalid
    if (v == null)
      return STATUS.INVALID_ID;
    // vehicle removed, remove from system
    if (member.removeVehicle(id)) {
      return parkingSystem.removeVehicle(v.getPlate());
    }
    // vehicle was not in member account
    return STATUS.INVALID_ID;
  }

  /**
   * Get the logged in member.
   * 
   * @return The member.
   */
  public Member getMember() {
    return member;
  }

  /**
   * Get the selected vehicle.
   * 
   * @return The selected vehicle.
   */
  public Vehicle getVehicle() {
    return vehicle;
  }

  /**
   * Get the list of spots as a List.
   * 
   * @return The list of spots as a List.
   */
  public List<Spot> getSpotsAsList() {
    return new ArrayList<Spot>(spots.values());
  }

  /**
   * Get the list of spots as a Map.
   * 
   * @return The list of spots as a Map.
   */
  public Map<Integer, Spot> getSpotsAsMap() {
    return spots;
  }

  /**
   * Get the reservation.
   * 
   * @return The reservation.
   */
  public Reservation getReservation() {
    return reservation;
  }

  /**
   * Get the list of vehicles.
   * 
   * @return A Map<Integer,Vehicle> representing the list of vehicles.
   */
  public Map<Integer, Vehicle> getVehiclesAsMap() {
    return vehicles;
  }

  /**
   * Get the list of vehicles.
   * 
   * @return A List<Vehicle> representing the list of vehicles.
   */
  public List<Vehicle> getVehiclesAsList() {
    if (vehicles == null) return null;
    return new ArrayList<>(vehicles.values());
  }

  /**
   * Handle check out
   * 
   * @return SUCCESS or FAILED
   */
  public STATUS doCheckout() {
    double total = reservation.getTotal();
    if (member.hasSufficientCredits(total)) {
      member.removeCredits(total);
      member.removeReservation();
      parkingSystem.removeReservation(reservation.getCode());
      resetReservation();
      return STATUS.SUCCESS;
    }
    return STATUS.FAILED;
  }

  /**
   * Reset the reservation details.
   */
  public void resetReservation() {
    reservation = null;
    vehicle = null;
    spot = null;
  }

  /**
   * Check whether the map has spots available.
   * 
   * @return true if spots available, false otherwise.
   */
  public boolean spotsAvailable() {
    return parkingMap.spotsAvailable();
  }

  /**
   * Return a String representation of the vehicles
   */
  public String vehiclesToString() {
    StringBuilder s = new StringBuilder();
    for (Vehicle v : vehicles.values()) {
      s.append(v.toString() + "\n");
    }
    return s.toString();
  }

  /**
   * Get the ID of the vehicle on the reservation.
   * 
   * @return The ID if there is a reservation, null otherwise.
   */
  public Integer getVehicleID() {
    if (vehicle != null) {
      return vehicle.getID();
    }
    return null;
  }

  /**
   * Get the parking map.
   * 
   * @return the parking map.
   */
  public ParkingMap getParkingMap() {
    return parkingMap;
  }

  /**
   * Get the logged in member's credits.
   * 
   * @return The number of credits.
   * @precondition Member must be logged in.
   */
  public double getCredits() {
    return member.getCredits();
  }

  /**
   * Reset the Manager instance.
   */
  public void reset() {
    parkingSystem = ParkingSystem.getInstance();
    parkingMap = parkingSystem.getMap();
    spots = parkingMap.getSpotsAsMap();
    resetReservation();
    member = null;
    vehicles = null;
  }

  private static Manager instance = new Manager();

}
