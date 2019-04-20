package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

<<<<<<< HEAD
import javafx.util.Pair;
import model.enums.STATUS;
=======
import util.StringHelper;
>>>>>>> 1d290bb6af736a3f3c32a60bdd8264614fc37597

/**
 * The Parking System class.
 * Manages reservations, members, and the parking map.
 * ParkingSystem is a singleton class. 
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingSystem {
	
    private Map<String, Reservation> reservations;
    private Map<String,Vehicle> vehicles;
    private Map<String, Member> members;
    private ParkingMap map;
    
    /**
     * Creates a ParkingSystem object.
     * 
     * @param map	The parking map.
     */
    private ParkingSystem() {
    	reservations = new HashMap<String, Reservation>();
    	vehicles = new HashMap<String,Vehicle>();
    	members = new HashMap<String, Member>();
    }
    
    /**
     * Set the parking map.
     * 
     * @param map	The parking map.
     */
    public void setMap(ParkingMap map) {
    	this.map = map;
    }
    
    /**
     * Access the singleton ParkingSystem instance.
     * 
     * @return	The ParkingSystem instance.
     */
    public static ParkingSystem getInstance() {
    	return instance;
    }
    
    /**
     * Check if there are spots available.
     * 
     * @return	true if spots available, false otherwise.
     */
    public boolean spotsAvailable() {
    	return this.map.spotsAvailable();
    }
    
    /**
     * Look up a reservation with the given code.
     * 
     * @param code	The code.
     * @return		  The Reservation object if found, null otherwise.
     */
    public Reservation lookUp(String code) {
    	return this.reservations.get(code);
    }
    
    /**
     * Adds a reservation to the system.
     * 
     * @param res	The reservation to add.
     * @return    SUCCESS if successful, FAILED otherwise.
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
     * @return    true if successful, false otherwise.
     */
    public STATUS addVehicle(Vehicle veh) {
      // make sure the vehicle is not already in the system
      if (!vehicles.containsKey(veh.getPlate())) {
        // not in system, add
        vehicles.put(veh.getPlate(),veh);
        return STATUS.SUCCESS;
      }
      // vehicle already in system
      return STATUS.PLATE_DUPLICATE;
    }
    
    /**
     * Removes a reservation from the system.
     * 
     * @param code	The code of the reservation to remove.
     * @return		  true if successful, false otherwise.
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
     * @param un	The user name.
     * @param psw	The password.
     * @return		A Pair containing the status of the login and the member object (or null if failed).
     * 
     *            Possible status enums:
     *            FAILED, SUCCESS
     */
    public Pair<STATUS,Member> verifyLoginInfo(String un, String psw) {
    	Member mem = members.get(un);
    	// if the username is not found, return null
    	if (mem == null) return new Pair<>(STATUS.FAILED,mem);
    	// if the password is correct, return the member object.
    	if (mem.getPassword().contentEquals(psw)) return new Pair<>(STATUS.SUCCESS, mem);
    	// password incorrect, return null.
    	return new Pair<>(STATUS.FAILED,null);
    }
    
    /**
     * Create a new member account.
     * 
     * @param un	The user name.
     * @param psw	The Password.
     * @return		Pair<STATUS,Member> containing the status of the account creation
     *            and the member object (or null if failed).
     *            
     *            Possible status:
     *            SUCCESS, USERNAME_INVALID, USERNAME_IN_USE, PASSWORD_INVALID
     */
    public Pair<STATUS,Member> createAccount(String un, String psw) {
    	// user name must be alphanumeric with at least one character.
<<<<<<< HEAD
    	if (!checkAlphaNumeric(un, 1)) return new Pair<>(STATUS.USERNAME_INVALID,null);
    	// username already in use
    	if (this.members.containsKey(un)) return new Pair<>(STATUS.USERNAME_IN_USE,null);
    	// password must be alphanumeric with at least six characters.
    	if (!checkAlphaNumeric(psw, 6)) return new Pair<>(STATUS.PASSWORD_INVALID,null);
=======
    	if (!StringHelper.checkAlphaNumeric(un, 1)) return null; //throw new IllegalArgumentException("Username must be alphanumeric with at least one character");
    	// if the username is already in use, return null to indicate failure.
    	if (this.members.containsKey(un)) return null;
    	// password must be alphanumeric with at least six characters.
    	if (!StringHelper.checkAlphaNumeric(psw, 6)) return null; //throw new IllegalArgumentException("Password must be alphanumeric with at least six characters");
>>>>>>> 1d290bb6af736a3f3c32a60bdd8264614fc37597
    	// credentials accepted, create member
    	Member newMem = new Member(un, psw);
    	this.members.put(un, newMem);
    	
    	return new Pair<>(STATUS.SUCCESS,newMem);
    }
    
    /**
     * Get the parking map.
     * 
     * @return  The parking map.
     */
    public ParkingMap getMap() {
      return map;
    }
<<<<<<< HEAD
  
    /**
     * Checks if string is alphanumeric with minimum of minLength characters.
     * 
     * @param str		The string.
     * @param minLength	The minimum length of the string.
     * @return			true if valid, false otherwise.
     */
    public static boolean checkAlphaNumeric(String str, int minLength) {
    	if (str.length() < minLength) return false;
    	// check if alphanumeric
    	String regex = "^[a-zA-Z0-9]+$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher strMatcher = pattern.matcher(str);
    	if (!strMatcher.matches()) return false;
    	
    	return true;
=======
    
    /**
     * Check whether a vehicle with the given plate number is parked.
     * 
     * @param plate The plate number.
     * @return      true if the plate number is in the list of parked plates,
     *              false otherwise.
     */
    public boolean plateIsParked(String plate) {
      for (String s : parkedPlates) {
        if (s.contentEquals(plate)) {
          return true;
        }
      }
      return false;
>>>>>>> 1d290bb6af736a3f3c32a60bdd8264614fc37597
    }
    
    private static ParkingSystem instance = new ParkingSystem();

}
