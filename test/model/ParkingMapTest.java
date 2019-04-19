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
		spots.add(new Spot(0,0));
		spots.get(0).reserve(); // reserve a spot to make sure numAvailable is set correctly
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
		ParkingMap map = new ParkingMap(spots);
		// check that number of spots available is correct
		Assert.assertTrue( map.getNumAvailable() == spots.size()-1 );
		// check that width and height are correct
		Assert.assertTrue(map.getWidth() == 3);
		Assert.assertTrue(map.getHeight() == 3);
		// check that spot ID's were set correctly
		int numSpots = map.getSpots().size();
		for (int i = 0; i < numSpots; i++) {
		  Assert.assertTrue(map.getSpots().get(i).getID() == i);
		}
	}
	
	// Testing addSpots
	@Test
	void testSetSpots() {
		System.out.println("Testing addSpots");
		List<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0));
		spots.get(0).reserve(); // reserve a spot to make sure numAvailable is set correctly
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
		// check that number of spots available is correct
		ParkingMap map = new ParkingMap(spots);
		Assert.assertTrue(map.getNumAvailable() == spots.size()-1);
		// check that width and height are correct
		Assert.assertTrue(map.getWidth() == 3);
		Assert.assertTrue(map.getHeight() == 3);
    // check that spot ID's were set correctly
    int numSpots = map.getSpots().size();
    for (int i = 0; i < numSpots; i++) {
      Assert.assertTrue(map.getSpots().get(i).getID() == i);
    }
	}
	
	// Testing spotsAvailable
	@Test
	void testSpotsAvailable() {
		System.out.println("Testing spotsAvailable");
		List<Spot> spots1 = new ArrayList<Spot>(10);
		spots1.add(new Spot(0,0));
		spots1.add(new Spot(1,1));
		spots1.add(new Spot(2,2));
		ParkingMap map1 = new ParkingMap(spots1);
		
		// set all spots reserved for second parking map
		List<Spot> spots2 = new ArrayList<Spot>(10);
		spots2.add(new Spot(0,0));
		spots2.add(new Spot(1,1));
		spots2.add(new Spot(2,2));
		for (Spot s : spots2) {
			s.reserve();
		}
		ParkingMap map2 = new ParkingMap(spots2);
		Assert.assertTrue(map1.spotsAvailable() && !map2.spotsAvailable());
	}

	// Testing reserveSpot
	@Test
	void testReserveSpot() {
		System.out.println("Testing reserveSpot");
		List<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0));
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
		ParkingMap map = new ParkingMap(spots);
		map.reserveSpot(spots.get(0).getID());
		Assert.assertTrue(map.getSpots().get(0).isReserved());
	}
	
	// Testing freeSpot
	@Test
	void testFreeSpot() {
		System.out.println("Testing freeSpot");
		List<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0));
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
		ParkingMap map = new ParkingMap(spots);
		map.reserveSpot(spots.get(0).getID());
		map.freeSpot(spots.get(0).getID());
		Assert.assertTrue(!map.getSpots().get(0).isReserved());
	}
	
	 // Testing getSpots
  @Test
  void testGetSpots() {
    System.out.println("Testing getSpots");
    List<Spot> spots = new ArrayList<Spot>(10);
    spots.add(new Spot(0,0));
    spots.add(new Spot(1,1));
    spots.add(new Spot(2,2));
    ParkingMap map = new ParkingMap(spots);
    Assert.assertTrue(map.getSpots().equals(spots));
  }
}
