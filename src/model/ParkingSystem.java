package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;
import model.enums.STATUS;
import util.StringHelper;

/**
 * The Parking System class. Manages reservations, members, and the parking map.
 * ParkingSystem is a singleton class.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingSystem {

  private Map<String, Reservation> reservations;
  private Map<String, Vehicle> vehicles;
  private Map<String, Member> members;
  private ParkingMap map;

  private static int nextVehicleID = 0;

  /**
   * Creates a ParkingSystem object.
   * 
   * @param map The parking map.
   */
  private ParkingSystem() {
    reservations = new HashMap<String, Reservation>();
    vehicles = new HashMap<String, Vehicle>();
    members = new HashMap<String, Member>();
  }

  /**
   * Set the parking map.
   * 
   * @param map The parking map.
   */
  public void setMap(ParkingMap map) {
    this.map = map;
  }

  /**
   * Access the singleton ParkingSystem instance.
   * 
   * @return The ParkingSystem instance.
   */
  public static ParkingSystem getInstance() {
    return instance;
  }

  /**
   * Check if there are spots available.
   * 
   * @return true if spots available, false otherwise.
   */
  public boolean spotsAvailable() {
    return this.map.spotsAvailable();
  }

  /**
   * Look up a reservation with the given code.
   * 
   * @param code The code.
   * @return The Reservation object if found, null otherwise.
   */
  public Reservation lookUp(String code) {
    return this.reservations.get(code);
  }

  /**
   * Adds a reservation to the system.
   * 
   * @param res The reservation to add.
   * @return SUCCESS if successful, FAILED otherwise.
   */
  public STATUS addReservation(Reservation res) {
    // set the start time of the reservation
    res.setStartTime(new Date());
    // make sure the spot is available
    if (map.reserveSpot(res.getSpot().getID())) {
      reservations.put(res.getCode(), res);
      return STATUS.SUCCESS;
    }
    // failed to reserve spot
    return STATUS.FAILED;
  }

  /**
   * Attempt to add a vehicle to the system.
   * 
   * @param veh The vehicle.
   * @return true if successful, false otherwise.
   */
  public STATUS addVehicle(Vehicle veh) {
    // make sure the vehicle is not already in the system
    if (!vehicles.containsKey(veh.getPlate())) {
      // not in system, add
      veh.setID(nextVehicleID++);
      vehicles.put(veh.getPlate(), veh);
      return STATUS.SUCCESS;
    }
    // vehicle already in system
    return STATUS.PLATE_DUPLICATE;
  }

  /**
   * Remove a vehicle from the system.
   * 
   * @param plate Plate of the vehicle to remove.
   * @return SUCCESS or PLATE_NOT_FOUND
   */
  public STATUS removeVehicle(String plate) {
    // try to remove the vehicle
    Vehicle v = vehicles.remove(plate);
    // vehicle was removed
    if (v != null)
      return STATUS.SUCCESS;
    // vehicle was not found
    return STATUS.PLATE_NOT_FOUND;

  }

  /**
   * Removes a reservation from the system.
   * 
   * @param code The code of the reservation to remove.
   * @return true if successful, false otherwise.
   */
  public boolean removeReservation(String code) {
    // remove the reservation and free the spot
    Reservation res = reservations.remove(code);
    if (res != null) {
      map.freeSpot(res.getSpot().getID());
      return true;
    }
    // return false to indicate the reservation was not found.
    return false;
  }

  /**
   * Verify the login info provided.
   * 
   * @param un  The user name.
   * @param psw The password.
   * @return A Pair containing the status of the login and the member object (or
   *         null if failed).
   * 
   *         Possible status enums: FAILED, SUCCESS
   */
  public Pair<STATUS, Member> verifyLoginInfo(String un, String psw) {
    Member mem = members.get(un);
    // if the username is not found, return null
    if (mem == null)
      return new Pair<>(STATUS.FAILED, mem);
    // if the password is correct, return the member object.
    if (mem.getPassword().contentEquals(psw))
      return new Pair<>(STATUS.SUCCESS, mem);
    // password incorrect, return null.
    return new Pair<>(STATUS.FAILED, null);
  }

  /**
   * Create a new member account.
   * 
   * @param un  The user name.
   * @param psw The Password.
   * @return Pair<STATUS,Member> containing the status of the account creation
   *         and the member object (or null if failed).
   * 
   *         Possible status: SUCCESS, USERNAME_INVALID, USERNAME_IN_USE,
   *         PASSWORD_INVALID
   */
  public Pair<STATUS, Member> createAccount(String un, String psw) {
    // user name must be alphanumeric with at least one character.
    if (!StringHelper.checkAlphaNumeric(un, 1))
      return new Pair<>(STATUS.USERNAME_INVALID, null);
    // username already in use
    if (this.members.containsKey(un))
      return new Pair<>(STATUS.USERNAME_IN_USE, null);
    // password must be alphanumeric with at least six characters.
    if (!StringHelper.checkAlphaNumeric(psw, 6))
      return new Pair<>(STATUS.PASSWORD_INVALID, null);
    // credentials accepted, create member
    Member newMem = new Member(un, psw);
    this.members.put(un, newMem);

    return new Pair<>(STATUS.SUCCESS, newMem);
  }

  /**
   * Get the parking map.
   * 
   * @return The parking map.
   */
  public ParkingMap getMap() {
    return map;
  }

  /**
   * Reset the parking system.
   */
  public static void reset() {
    instance = new ParkingSystem();
  }
  
  private static ParkingSystem instance = new ParkingSystem();

}
