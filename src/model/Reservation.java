package model;

import java.util.Date;

/**
 * The Reservation class. Contains the details of a parking reservation.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Reservation {
  private Spot spot;
  private Vehicle vehicle;
  private String timeType;
  private Date startTime;
  private String code;
  private static final double hourlyRate = 2.50;
  private static final double dailyRate = 20;

  /**
   * Create a Reservation object.
   */
  public Reservation() {
    timeType = "HOURLY";
  }

  /**
   * Get the spot on the reservation.
   * 
   * @return The spot.
   */
  public Spot getSpot() {
    return this.spot;
  }

  /**
   * Get the vehicle on the reservation.
   * 
   * @return The vehicle.
   */
  public Vehicle getVehicle() {
    return this.vehicle;
  }

  /**
   * Get the checkout code.
   * 
   * @return The checkout code.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Get the time type.
   * 
   * @return The time type.
   */
  public String getTimeType() {
    return this.timeType;
  }

  /**
   * Set the spot on the reservation.
   * 
   * @param spot The spot.
   */
  public void setSpot(Spot spot) {
    this.spot = spot;

  }

  /**
   * Set the vehicle on the reservation.
   * 
   * @param vehicle The vehicle.
   */
  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  /**
   * Set the code on the reservation.
   * 
   * @param code The code.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Calculate the total price of the reservation.
   * 
   * @return The price in dollars.
   */
  public double getTotal() {
    Date endTime = new Date();

    // get the hours elapsed since the reservation was created.
    long millis = endTime.getTime() - startTime.getTime();
    int hours = (int) Math.ceil((double) millis / 3600000);
    // once reservation is made, member must pay minimum 1 hour
    if (hours == 0) hours = 1; 

    switch (timeType) {
    case "HOURLY":
      return (hours * hourlyRate);

    case "DAILY":
      int days = (int) Math.ceil((double) hours / 24);
      return (days * dailyRate);
    }

    return 0;
  }

  /**
   * Set the time type for the reservation.
   * 
   * @param timeType The time type.
   */
  public void setTimeType(String timeType) {
    this.timeType = timeType;
  }

  /**
   * Set the start time for the reservation.
   * 
   * @param startTime The start time (a Date object)
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public String toString() {
    String s = "Vehicle:\n" + vehicle.toString() + "\n\n" +
        "Spot:\n" + spot.toString() + "\n\n" +
        "Start time:\n" + startTime;
    return s;
  }
}
