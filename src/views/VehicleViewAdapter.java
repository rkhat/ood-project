package views;

import model.*;

/**
 * Adapter converts from Vehicle to VehicleView
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class VehicleViewAdapter implements VehicleView {
  private final Vehicle vehicle;
  private final boolean vehicleParked;
  private final boolean memberParked;

  /**
   * This constructor stores the vehicle information and whether or not it is
   * parked. It also stores whether or not the member has a parked vehicle.
   * 
   * @param vehicle         A vehicle
   * @param parkedVehicleID ID of member's parked Vehicle. Null if no member has
   *                        no parked vehicles
   */
  public VehicleViewAdapter(Vehicle vehicle, Integer parkedVehicleID) {
    this.vehicle = vehicle;

    if (parkedVehicleID == null) {
      memberParked = false;
      vehicleParked = false;
    } else {
      // true if parked vehicle id exists
      memberParked = true;
      // true if vehicle id and parked vehicle id are equal
      vehicleParked = (vehicle.getID() == parkedVehicleID);
    }
  }

  @Override
  public int getID() {
    return vehicle.getID();
  }

  @Override
  public String getPlate() {
    return vehicle.getPlate();
  }

  @Override
  public boolean isVehicleParked() {
    return vehicleParked;
  }

  @Override
  public boolean isMemberParked() {
    return memberParked;
  }

}
