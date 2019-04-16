package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.ParkingMap;

class ParkingMapTest {

	// Testing ParkingMap constructor
	@Test
	void testParkingMap() {
		System.out.println("Testing ParkingMap");
		List<Spot> spots = new ArrayList<Spot>();
		spots.add(new Spot(0,0,0));
		spots.get(0).lock();
		spots.get(0).reserve(); // reserve a spot to make sure numAvailable is set correctly
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		// check that number of spots available is correct
		Assert.assertTrue( map.getSpots().equals(spots) );
		Assert.assertTrue( map.getNumAvailable() == spots.size()-1 );
		// check that width and height are correct
		Assert.assertTrue(map.getWidth() == 2);
		Assert.assertTrue(map.getHeight() == 2);
	}
	
	// Testing setSpots
	@Test
	void testSetSpots() {
		System.out.println("Testing setSpots");
		List<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.get(0).lock();
		spots.get(0).reserve(); // reserve a spot to make sure numAvailable is set correctly
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		// check that number of spots available is correct
		ParkingMap map = new ParkingMap(spots);
		Assert.assertTrue(map.getSpots().equals(spots));
		Assert.assertTrue(map.getNumAvailable() == spots.size()-1);
		// check that width and height are correct
		Assert.assertTrue(map.getWidth() == 2);
		Assert.assertTrue(map.getHeight() == 2);
	}
	
	// Testing spotsAvailable
	@Test
	void testSpotsAvailable() {
		System.out.println("Testing spotsAvailable");
		List<Spot> spots1 = new ArrayList<Spot>(10);
		spots1.add(new Spot(0,0,0));
		spots1.add(new Spot(1,1,1));
		spots1.add(new Spot(2,2,2));
		ParkingMap map1 = new ParkingMap(spots1);
		
		// set all spots reserved for second parking map
		List<Spot> spots2 = new ArrayList<Spot>(10);
		spots2.add(new Spot(0,0,0));
		spots2.add(new Spot(1,1,1));
		spots2.add(new Spot(2,2,2));
		for (Spot s : spots2) {
			s.lock();
			s.reserve();
		}
		ParkingMap map2 = new ParkingMap(spots2);
		Assert.assertTrue(map1.spotsAvailable() && !map2.spotsAvailable());
	}
	
	// Testing lockSpot
	@Test
	void testLockSpot() {
		System.out.println("Testing lockSpot");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		map.lockSpot(spots.get(0));
		Assert.assertTrue(map.getSpots().equals(spots));
	}

	// Testing reserveSpot
	@Test
	void testReserveSpot() {
		System.out.println("Testing reserveSpot");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		map.reserveSpot(spots.get(0));
		Assert.assertTrue(map.getSpots().equals(spots));
	}
	
	// Testing freeSpot
	@Test
	void testFreeSpot() {
		System.out.println("Testing freeSpot");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0,0));
		spots.add(new Spot(1,1,1));
		spots.add(new Spot(2,2,2));
		ParkingMap map = new ParkingMap(spots);
		map.lockSpot(spots.get(0));
		map.reserveSpot(spots.get(0));
		map.freeSpot(spots.get(0));
		Assert.assertTrue(!map.getSpots().get(0).isLocked() && !map.getSpots().get(0).isReserved());
	}
}
