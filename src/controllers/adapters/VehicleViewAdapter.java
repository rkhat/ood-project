package controllers.adapters;

import model.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class VehicleViewAdapter implements VehicleView {
  private final int id;
  private final String plate;
  private final boolean vehicleParked;

  public VehicleViewAdapter(Vehicle vehicle, Integer parkedVehicleID) {
    this.id = vehicle.getID();
    this.plate = vehicle.getPlate();

    // true if vehicle == parked vehicle
    if (parkedVehicleID == null)
      vehicleParked = false;
    else
      vehicleParked = (this.id == parkedVehicleID);
  }

  @Override
  public int getID() {
    return id;
  }

  @Override
  public String getPlate() {
    return plate;
  }

  @Override
  public boolean isVehicleParked() {
    return vehicleParked;
  }

}
