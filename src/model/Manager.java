package model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
	
	private ParkingSystem parkingSystem;
	private ParkingMap parkingMap;
	private Member member;
	private Reservation reservation;
	private List<Spot> spots;
	private Spot spot;
	private Map<String,Vehicle> vehicles;
	private Vehicle vehicle;
	
	/**
	 * Manager constructor.
	 * 
	 * @param ps	The parking system instance.
	 */
	private Manager(ParkingSystem ps) {
		parkingSystem = ps;
		parkingMap = ps.getMap();
		spots = parkingMap.getSpots();
	}
	
	/**
	 * Create a new instance of Manager.
	 * 
	 * @param ps	The parking system instance.
	 */
	public static void createInstance(ParkingSystem ps) {
		instance = new Manager(ps);
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
	public boolean doEnterLoginInfo(String un, String psw) {
		member = parkingSystem.verifyLoginInfo(un, psw);
		// if verification fails, return false.
		if (member == null) {
			return false;
		}
		// if verification succeeds, get the reservation (if any) and return true.
		reservation = member.getReservation();
		return true;
	}
	
	/**
	 * Takes care of creating an account.
	 * 
	 * @param un   The username entered.
	 * @param psw  The password entered.
	 * @return     true if successful, false otherwise.
	 */
	public boolean doCreateAccount(String un, String psw) {
	  member = parkingSystem.createAccount(un, psw);
	  // if account creation fails, return false.
	  if (member == null) {
	    return false;
	  }
	  // if account creation succeeds, return true.
	  return true;
	}
	
	/**
	 * Attempt to create a reservation with the vehicle corresponding to the given ID.
	 * 
	 * @param vehicleID  The ID of the vehicle.
	 * @return           true if successful, false otherwise.
	 */
	public boolean doSelectVehicle(int vehicleID) {
	  // if there is no member signed in, return false.
	  if (member == null) return false;
	  // if there is already a reservation, return false.
	  if (reservation != null) return false;
	  // if there are no spots, return false.
	  if (!parkingSystem.spotsAvailable()) return false;
	  // attempt to set the vehicle.
	  vehicle = member.getVehicles().get(vehicleID);
	  // if the vehicle ID is valid, create the reservation and return true.
	  if (vehicle != null) {
	    reservation = new Reservation();
	    reservation.setVehicle(vehicle);
	    reservation.setCode(member.getCode());
	    return true;
	  }
	  // vehicle ID was invalid, return false.
	  return false;
	}
	
	/**
	 * Take care of selecting a spot.
	 * 
	 * @param spotID   The ID of the spot to select.
	 * @return         true if successful, false otherwise.
	 */
	public boolean doSelectSpot(int spotID) {
	  try {
	    // try to select the spot and lock it.
	    spot = spots.get(spotID);
	    if (spot.lock()) return true;
	    // if spot cannot be locked, deselect it and return false.
	    spot = null;
	    return false;
	  } catch(IndexOutOfBoundsException e) {
	    // if there was an error selecting the spot, set spot null and return false.
	    spot = null;
	    return false;
	  }
	}
	
	/**
	 * Take care of confirming a spot selection. 
	 * 
	 * @param confirm  The state of confirmation (true means confirm, false means cancel)
	 * @return         true if successful, false otherwise. If confirm == true,
	 *                 success means the spot was reserved and added to the reservation.
	 *                 If confirm == false, success means the spot was freed and deselected.
	 */
	public boolean doConfirmSpot(boolean confirm) {
	  // if there is no spot selected, return false.
	  if (spot == null) return false;
	  // if spot was confirmed, reserve it and return true.
	  if (confirm) {
	    if (spot.reserve()) {
	      reservation.setSpot(spot);
	      return true;
	    }
	    return false;
	  }
	  // spot was not confirmed, deselect it and return true.
	  spot.free();
	  spot = null;
	  return true;
	}
	
	private static Manager instance;
	
}
