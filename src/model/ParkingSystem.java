package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Parking System class.
 * Manages reservations, members, and the parking map.
 * ParkingSystem is a singleton class. 
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingSystem {
	
    private Map<String, Reservation> reservations;
    private List<String> parkedPlates;
    private Map<String, Member> members;
    private ParkingMap map;
    
    /**
     * Creates a ParkingSystem object.
     * 
     * @param map	The parking map.
     */
    private ParkingSystem() {
    	reservations = new HashMap<String, Reservation>();
    	parkedPlates = new ArrayList<String>();
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
     * @return    true if successful, false otherwise.
     */
    public boolean addReservation(Reservation res) {
      String plate = res.getVehicle().getPlate();
      // if the plate is already parked, return false.
      if (plateIsParked(plate)) return false;
      // otherwise, create the reservation and return true.
      res.setStartTime(new Date());
      if (map.reserveSpot(res.getSpot().getID())) {
        parkedPlates.add(res.getVehicle().getPlate());
        reservations.put(res.getCode(), res);
        return true;
      }
      return false;
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
        parkedPlates.remove(res.getVehicle().getPlate());
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
     * @return		The member object if credentials are correct, null otherwise.
     */
    public Member verifyLoginInfo(String un, String psw) {
    	Member mem = this.members.get(un);
    	// if the username is not found, return null
    	if (mem == null) return null;
    	// if the password is correct, return the member object.
    	if (mem.getPassword().contentEquals(psw)) return mem;
    	// password incorrect, return null.
    	return null;
    }
    
    /**
     * Create a new member account.
     * 
     * @param un	The user name.
     * @param psw	The Password.
     * @return		true if successful, false otherwise.
     * @throws 		IllegalArgumentException if username or password is incorrect format, or username exists.
     */
    public Member createAccount(String un, String psw) throws IllegalArgumentException {
    	// user name must be alphanumeric with at least one character.
    	if (!checkAlphaNumeric(un, 1)) return null; //throw new IllegalArgumentException("Username must be alphanumeric with at least one character");
    	// if the username is already in use, return null to indicate failure.
    	if (this.members.containsKey(un)) return null;
    	// password must be alphanumeric with at least six characters.
    	if (!checkAlphaNumeric(psw, 6)) return null; //throw new IllegalArgumentException("Password must be alphanumeric with at least six characters");
    	// credentials accepted, create member
    	Member newMem = new Member(un, psw);
    	this.members.put(un, newMem);
    	
    	return newMem;
    }
    
    /**
     * Get the parking map.
     * 
     * @return  The parking map.
     */
    public ParkingMap getMap() {
      return map;
    }
    
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
    }
  
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
    }
    
    private static ParkingSystem instance = new ParkingSystem();

}
