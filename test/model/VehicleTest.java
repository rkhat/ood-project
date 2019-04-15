package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.Vehicle;

class VehicleTest {

	// Testing Vehicle Constructor
	@Test
	void testVehicle() {
		System.out.println("Testing Vehicle constructor");
		String plate = "ABC123";
		Vehicle vehicle = new Vehicle(plate);
		Assert.assertTrue(vehicle.getPlate().equals(plate));
	}
	
	// Testing equals
	@Test
	void testEquals() {
		System.out.println("Testing equals");
		String plate1 = new String("ABC123");
		String plate2 = new String("ABC123");
		String plate3 = new String("123ABC");
		Vehicle vehicle1 = new Vehicle(plate1);
		Vehicle vehicle2 = new Vehicle(plate2);
		Vehicle vehicle3 = new Vehicle(plate3);
		Assert.assertTrue(vehicle1.equals(vehicle2));
		Assert.assertTrue(!vehicle1.equals(vehicle3));
	}

}
