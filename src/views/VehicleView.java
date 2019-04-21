package views;

/**
 * This interface is used for the list view main menu
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public interface VehicleView {

  /**
   * Gets ID of this vehicle
   * 
   * @return This vehicle's ID
   */
  public int getID();

  /**
   * Gets the plate number of this vehicle
   * 
   * @return This vehicle's plate number
   */
  public String getPlate();

  /**
   * Check if this vehicle is parked
   * 
   * @return true if this vehicle is parked, else false
   */
  public boolean isVehicleParked();

  /**
   * Check if the member has a parked vehicle
   * 
   * @return true if the member has a parked vehicle, else false
   */
  public boolean isMemberParked();
}
