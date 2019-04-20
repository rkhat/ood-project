package controllers.adapters;

/**
 * This interface displays
 * @author Alec Agnese, Rami El Khatib
 */
public interface VehicleView {
  
  /**
   * Gets ID of this vehicle
   * @return This vehicle's Id
   */
  public int getID();
  
  /**
   * Gets the plate number of this vehicle
   * @return This vehicle's plate number
   */
  public String getPlate();
  
  /**
   * Check if this vehicle is parked
   * @return true if this vehicle is parked
   */
  public boolean isVehicleParked();
}
