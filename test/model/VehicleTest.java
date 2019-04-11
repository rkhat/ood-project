package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.Vehicle;

class VehicleTest {

	// Testing Vehicle Constructor
	@Test
	void testVehicle() {
		String plate = "ABC123";
		Vehicle vehicle = new Vehicle(plate);
		Assert.assertTrue(vehicle.getPlate().equals(plate));
	}

}
