package model;

import java.util.HashMap;

/**
 * The Parking System class.
 * Manages reservations, members, and the parking map.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingSystem {
    private HashMap<String, Reservation> reservations;
    private HashMap<String, Member> members;
    private ParkingMap map;
    
    /**
     * Creates a ParkingSystem object with a given parking map.
     * 
     * @param map	The parking map.
     */
    public ParkingSystem(ParkingMap map) {
    	this.map = map;
    	reservations = new HashMap<>();
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
     * @return		The Reservation object if found, null otherwise.
     */
    public Reservation lookUp(String code) {
    	return this.reservations.get(code);
    }
    
    /**
     * Adds a reservation to the system.
     * 
     * @param res	The reservation to add.
     */
    public void addReservation(Reservation res) {
    	this.reservations.put(res.getCode(), res);
    }
    
    /**
     * Removes a reservation from the system.
     * 
     * @param res	The reservation to remove.
     * @return		true if successful, false otherwise.
     */
    public boolean removeReservation(Reservation res) {
    	String code = res.getCode();
    	// if the reservation was found, return true to indicate success.
    	if (this.reservations.remove(code) != null) return true;
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
    	if (mem.getPassword() == psw) return mem;
    	// password incorrect, return null.
    	return null;
    	
    }
    
    /**
     * Create a new member account.
     * 
     * @param un	The user name.
     * @param psw	The Password.
     * @return		The new member object if credentials are accepted, null otherwise.
     */
//    public Member createAccount(String un, String psw) {
//    	// if the username is already in use, return null to indicate failure
//    	if (this.reservations.containsKey(un)) return null;
//    	
//    	
//    	
//    }
    
//    public static boolean checkPassword(String psw) {
//    	if (psw.length() < 6) return false;
////    	if (psw.con)
//    }

}
