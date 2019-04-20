package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import javafx.util.Pair;
import model.enums.STATUS;
=======
import util.StringHelper;
>>>>>>> 1d290bb6af736a3f3c32a60bdd8264614fc37597

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
	
	private ParkingSystem parkingSystem;
	private ParkingMap parkingMap;
	private Member member;
	private Reservation reservation;
	private Map<Integer,Spot> spots;
	private Spot spot;
	private Map<Integer,Vehicle> vehicles;
	private Vehicle vehicle;
	
	/**
	 * Manager constructor.
	 * 
	 * @param ps	The parking system instance.
	 */
	private Manager(ParkingSystem ps) {
		parkingSystem = ps;
		parkingMap = ps.getMap();
		spots = parkingMap.getSpotsAsMap();
	}
	
	/**
	 * Create a new instance of Manager.
	 * 
	 */
	public static void createInstance() {
		instance = new Manager(ParkingSystem.getInstance());
	}
	
	/**
	 * Return the last Manager instance created
	 * 
	 * @return	The instance.
	 */
	public static Manager getInstance() {
		return instance;
	}
	
	/**
	 * Takes care of entering log in info.
	 * 
	 * @param un	The username entered.
	 * @param psw	The password entered.
	 * @return		true if info was correct, false otherwise.
	 */
	public STATUS doLogIn(String un, String psw) {
	  Pair<STATUS,Member> p = parkingSystem.verifyLoginInfo(un, psw);
	  STATUS status = p.getKey();
		if (status == STATUS.SUCCESS) {
      // success, log the member in
	    member = p.getValue();
		  reservation = member.getReservation();
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
	 * @param un   The username entered.
	 * @param psw  The password entered.
	 * @return     SUCCESS, USERNAME_INVALID, USERNAME_IN_USE, PASSWORD_INVALID
	 */
	public STATUS doCreateAccount(String un, String psw) {
	  Pair<STATUS,Member> p = parkingSystem.createAccount(un, psw);
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
	 * Attempt to create a reservation with the vehicle corresponding to the given ID.
	 * 
	 * @param vehicleID  The ID of the vehicle.
	 * @return           SUCCESS, NO_SPOTS_AVAILABLE, MEMBER_HAS_RESERVATION, 
	 *                   INVALID_ID, or NOT_LOGGED_IN
	 */
	public STATUS doSelectVehicle(int vehicleID) {
	  // no member logged in
	  if (member == null) return STATUS.NOT_LOGGED_IN;
	  // member already has a reservation
	  if (reservation != null) return STATUS.MEMBER_HAS_RESERVATION;
	  // no spots available
	  if (!parkingSystem.spotsAvailable()) return STATUS.NO_SPOTS_AVAILABLE;
    vehicle = vehicles.get(vehicleID);
    // invalid ID
    if (vehicle == null) return STATUS.INVALID_ID;
    
    // vehicle is valid, create the reservation
    reservation = new Reservation();
    reservation.setVehicle(vehicle);
    reservation.setCode(member.getCode());
    return STATUS.SUCCESS;
	}
	
	/**
	 * Take care of selecting a spot.
	 * 
	 * @param spotID   The ID of the spot to select.
	 * @return         SUCCESS, SPOT_NOT_FOUND, SPOT_RESERVED, or FAILED
	 */
	public STATUS doSelectSpot(int spotID) {
	    // try to select the spot and add it to the reservation.
	    spot = spots.get(spotID);
	    // spot not found
	    if (spot == null) return STATUS.SPOT_NOT_FOUND;
	    // spot reserved
	    if (spot.isReserved()) return STATUS.SPOT_RESERVED;
	    // spot available, add to reservation.
      reservation.setSpot(spot);
      member.setReservation(reservation);
      return parkingSystem.addReservation(reservation);
	}
	
	/**
	 * Take care of adding credits to account.
	 * 
	 * @param amt  The amount of credits to add.
	 * @return     true if successful, false otherwise.
	 */
	public STATUS doAddCredits(double amt) {
	  if (amt < 0) {
	    return STATUS.FAILED;
	  }
	  member.addCredits(amt);
	  return STATUS.SUCCESS;
	}
	
	/**
	 * Take care of logging out.
	 * 
	 * @return true if a member was logged in, false otherwise.
	 */
	public boolean doLogOut() {
	  // if a member is logged in, log out and return true.
	  if (member != null) {
	    resetReservation();
	    member = null;
	    return true;
	  }
	  // no member logged in, return false.
	  return false;
	}
	
	/**
	 * Take care of adding a vehicle.
	 * 
	 * @param plate  String representing the plate number.
	 * @return       SUCCESS if vehicle added, PLATE_INVALID if plate is invalid format,
	 *               FAILED otherwise.
	 */
<<<<<<< HEAD
	public STATUS doAddVehicle(String plate) {
	  // must be alphanumeric with 6 characters
	  if ( !(ParkingSystem.checkAlphaNumeric(plate, 6) && (plate.length() == 6)) ) {
	    return STATUS.PLATE_INVALID;
=======
	public boolean doAddVehicle(String plate) {
	  if (StringHelper.checkAlphaNumeric(plate, 6)) {
	    Vehicle veh = new Vehicle(plate);
	    return member.addVehicle(veh);
>>>>>>> 1d290bb6af736a3f3c32a60bdd8264614fc37597
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
   * @return  The list of spots as a List.
   */
  public List<Spot> getSpotsAsList() {
    return new ArrayList<Spot>(spots.values());
  }
  
  /**
   * Get the list of spots as a Map.
   * 
   * @return  The list of spots as a Map.
   */
  public Map<Integer,Spot> getSpotsAsMap() {
    return spots;
  }
  
  /**
   * Get the reservation.
   * 
   * @return  The reservation.
   */
  public Reservation getReservation() {
    return reservation;
  }
  
  /**
   * Handle check out
   * 
   * @return  true if member had enough credits, false otherwise.
   */
  public boolean doCheckout() {
    double total = reservation.getTotal();
    if (member.hasSufficientCredits(total)) {
      member.removeCredits(total);
      member.removeReservation();
      parkingSystem.removeReservation(reservation.getCode());
      resetReservation();
      return true;
    }
    return false;
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
   * @return  true if spots available, false otherwise.
   */
  public boolean spotsAvailable() {
    return parkingMap.spotsAvailable();
  }
  
	private static Manager instance;
	
}
