package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SpotTest {

	// Testing lock
	@Test
	void testLock() {
		System.out.println("Testing lock");
		Spot spot = new Spot(1,1);
		// spot is not locked, so should return true
		Assert.assertTrue(spot.lock() == true);
		Assert.assertTrue(spot.isLocked() == true);
		// spot is locked, should return false
		Assert.assertTrue(spot.lock() == false);
	}
	
	// Testing unlock
	@Test
	void testUnlock() {
		System.out.println("Testing unlock");
		Spot spot = new Spot(1,1);
		spot.lock();
		spot.unlock();
		Assert.assertTrue(spot.isLocked() == false);
	}

	// Testing reserve
	@Test
	void testReserve() {
		System.out.println("Testing reserve");
		Spot spot = new Spot(1,1);
		// spot is not yet locked, should fail
		Assert.assertTrue(spot.reserve() == false);
		Assert.assertTrue(spot.isReserved() == false);
		
		// lock, then reserve should succeed
		spot.lock();
		Assert.assertTrue(spot.reserve() == true);
		Assert.assertTrue(spot.isReserved() == true);
	}
	
	// Testing equals
	@Test
	void testEquals() {
		System.out.println("Testing equals");
		Spot s1 = new Spot(1,1);
		Spot s2 = new Spot(1,2);
		s1.setID(1);
		s2.setID(2);
		Assert.assertTrue(!s1.equals(s2));
		s2.setID(1);
		Assert.assertTrue(s1.equals(s2));
	}
}

