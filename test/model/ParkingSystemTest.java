package model;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ParkingSystemTest {

	// Testing spotsAvailable
	@Test
	void testSpotsAvailable() {
		System.out.println("Testing spotsAvailable");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		ParkingSystem ps = ParkingSystem.getInstance();
		
		// Test with spots available
		ps.setMap(map);
		Assert.assertTrue(ps.spotsAvailable());
		
		// Test with no spots available
		for (Spot s : spots) {
			s.lock();
			s.reserve();
		}
		Assert.assertTrue(!ps.spotsAvailable());
	}
	
	// Testing lookUp
	// This test also implicitly tests addReservation
	@Test
	void testLookUp() {
		System.out.println("Testing lookUp");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		ParkingSystem ps = ParkingSystem.getInstance();
		ps.setMap(map);
		
		// add a reservation to the system
		Reservation res = new Reservation();
		String code = "asldkfjahs192348as2ldfj";
		res.setCode(code);
		ps.addReservation(res);
		
		Assert.assertTrue(ps.lookUp(code).equals(res));
		Assert.assertTrue(ps.lookUp("blah") == null);
	}
	
	
	
}
