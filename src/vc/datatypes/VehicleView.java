package vc.datatypes;

import model.*;

/**
 * This interface displays
 * @author Alec Agnese, Rami El Khatib
 */
public interface VehicleView {
  /**
   * Gets the plate number of this vehicle
   * @return This vehicle's plate number
   */
  public String getPlate();

  /**
   * Checks if Member has one of his vehicles parked
   * @return true if Member's vehicle is parked
   */
  public boolean isMemberParked();
  
  /**
   * Check if this vehicle is parked
   * @return true if this vehicle is parked
   */
  public boolean isVehicleParked();
}
