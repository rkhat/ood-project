package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import util.RandomGenerator;

/**
 * Member class. Represents a member of a parking system.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Member implements Serializable {

  private static final long serialVersionUID = 4513445765193189811L;
  
  private Reservation reservation;
  private Map<Integer, Vehicle> vehicles;
  private String code;
  private String userName;
  private String password;
  private double credits;

  /**
   * Constructs a Member object with a unique user name and password and no
   * credits.
   * 
   * @param un  The member's user name
   * @param psw The member's password
   */
  public Member(String un, String psw) {
    this.userName = un;
    this.password = psw;
    this.credits = 0;
    this.code = RandomGenerator.generateRandomString(10);
    this.vehicles = new HashMap<Integer, Vehicle>();
  }

  /**
   * Sets the password.
   * 
   * @param psw Password to set.
   */
  public void setPassword(String psw) {
    this.password = psw;
  }

  /**
   * Sets the current reservation.
   * 
   * @param res The current reservation.
   */
  public void setReservation(Reservation res) {
    this.reservation = res;
  }

  /**
   * Sets the current parked vehicle.
   * 
   * @param veh The parked vehicle.
   */
  public void setVehicle(Vehicle veh) {
    // TODO: is this function necessary?
  }

  /**
   * Sets the member's unique checkout code.
   * 
   * @param code The code.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Accessor for the reservation.
   * 
   * @return the reservation.
   */
  public Reservation getReservation() {
    return this.reservation;
  }

  /**
   * Access the member's vehicles.
   * 
   * @return The list of vehicles.
   */
  public Map<Integer, Vehicle> getVehicles() {
    return this.vehicles;
  }

  /**
   * Access the member's code.
   * 
   * @return The code.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Access the member's user name.
   * 
   * @return The user name
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * Access the member's password.
   * 
   * @return The password.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Get the amount of credits on the account.
   * 
   * @return The number of credits.
   */
  public double getCredits() {
    return this.credits;
  }

  /**
   * Add credits to the account.
   * 
   * @param n Number of credits to add.
   */
  public void addCredits(double n) {
    this.credits += n;
  }

  /**
   * Remove credits from the account.
   * 
   * @param n Number of credits to remove.
   */
  public void removeCredits(double n) {
    // Make sure credits is not negative.
    if (n > this.credits) {
      this.credits = 0;
      return;
    }
    this.credits -= n;
  }

  /**
   * Check if the member has enough credits for a transaction.
   * 
   * @param amt The amount of credits to check for.
   * @return true if member has enough credits, false otherwise.
   */
  public boolean hasSufficientCredits(double amt) {
    return (this.credits >= amt);
  }

  /**
   * Remove the current reservation.
   */
  public void removeReservation() {
    reservation = null;
  }

  /**
   * Add a new vehicle to the account.
   * 
   * @param vehicle The vehicle to add.
   * @return true if successful, false otherwise.
   */
  public boolean addVehicle(Vehicle vehicle) {
    try {
      vehicles.put(vehicle.getID(), vehicle);
    } catch (Exception e) {
      System.err.println(e.toString());
      return false;
    }
    return true;
  }

  /**
   * Remove the specified vehicle from the account.
   * 
   * @param id The ID of the vehicle to remove.
   * @return true if successful, false otherwise.
   */
  public boolean removeVehicle(int id) {
    if (vehicles.remove(id) != null) {
      return true;
    }
    return false;
  }

  /**
   * Check whether the members are equal (same user name)
   * 
   * @param other The Member to compare this to.
   * @return true if equal, false otherwise.
   */
  public boolean equals(Member other) {
    if (this == other)
      return true;
    if (other == null)
      return false;
    return (this.userName.equals(other.userName));
  }
}
