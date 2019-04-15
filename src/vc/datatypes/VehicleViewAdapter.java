package vc.datatypes;

import model.*;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class VehicleViewAdapter implements VehicleView {
  private final String plate;
  private final boolean memberParked;
  private final boolean vehicleParked;
//  private final Vehicle vehicle;
  
  public VehicleViewAdapter(Vehicle vehicle, Vehicle parkedVehicle) {
    this.plate = vehicle.getPlate();
    if(parkedVehicle == null) {
      memberParked = false;
      vehicleParked = false;
    } else {
      memberParked = true;
      if(vehicle.equals(parkedVehicle)) {
        vehicleParked = true;
      } else {
        vehicleParked = false;
      }
    }
    
  }

  @Override
  public String getPlate() {
    return plate;
  }

  @Override
  public boolean isMemberParked() {
    return memberParked;
  }

  @Override
  public boolean isVehicleParked() {
    return vehicleParked;
  }
  
  
}
