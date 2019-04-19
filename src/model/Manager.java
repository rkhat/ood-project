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
	 * Take care of logging in.
	 * 
	 * @param un   The username entered
	 * @param psw  The password entered
	 * @return     True if log in succeeded, false otherwise.
	 */
	public boolean doLogIn(String un, String psw) {
	  member = parkingSystem.verifyLoginInfo(un, psw);
	  // if login fails, return false
	  if (member == null) {
	    return false;
	  }
	  // if login succeeds, get the members vehicles, reservation, etc.
	  reservation = member.getReservation();
	  vehicles = member.getVehicles();
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
	  // if the vehicle ID is valid, attempt to create the reservation.
	  if (vehicle != null) {
	    // if a vehicle with the same plate is already parked, return false.
	    if (parkingSystem.plateIsParked(vehicle.getPlate())) return false;
	    // create the reservation and return true.
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
	 * @precondition   spotID must be a valid spot ID.
	 */
	public boolean doSelectSpot(int spotID) {
	  try {
	    // try to select the spot and add it to the reservation.
	    spot = spots.get(spotID);
	    // make sure the spot is not reserved, and add it to the reservation.
	    if (!spot.isReserved()) {
	      reservation.setSpot(spot);
	      member.setReservation(reservation);
	      return parkingSystem.addReservation(reservation);
	    }
	    return false;
	  } catch(IndexOutOfBoundsException e) {
	    // if there was an error selecting the spot, set spot null and return false.
	    spot = null;
	    return false;
	  }
	}
	
	/**
	 * Take care of adding credits to account.
	 * 
	 * @param amt  The amount of credits to add.
	 * @return     true if successful, false otherwise.
	 */
	public boolean doAddCredits(double amt) {
	  if (amt < 0) {
	    System.err.println("Amount must be positive");
	    System.out.println();
	    return false;
	  }
	  member.addCredits(amt);
	  return true;
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
	 * @return       true if successful, false otherwise.
	 */
	public boolean doAddVehicle(String plate) {
	  if (ParkingSystem.checkAlphaNumeric(plate, 6)) {
	    Vehicle veh = new Vehicle(plate);
	    return member.addVehicle(veh);
	  }
	  // not alphanumeric
	  return false;
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
   * Get the list of spots in the map.
   * 
   * @return  The list of spots.
   */
  public List<Spot> getSpots() {
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
   * @return  true if checkout succeeded, false otherwise.
   */
  public boolean doCheckout() {
    double total = reservation.getTotal();
    if (member.hasSufficientCredits(total)) {
      member.removeCredits(total);
      member.removeReservation();
      parkingSystem.removeReservation(reservation.getCode());
      reservation = null;
      vehicle = null;
      spot = null;
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
  
	private static Manager instance;
	
}
