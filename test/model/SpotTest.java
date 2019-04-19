package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SpotTest {

	// Testing reserve
	@Test
	void testReserve() {
		System.out.println("Testing reserve");
		Spot spot = new Spot(1,1);
		// spot is not yet reserved, should succeed.
		Assert.assertTrue(spot.reserve() == true);
		Assert.assertTrue(spot.isReserved() == true);
		
		// spot is already reserved, should fail
		Assert.assertTrue(spot.reserve() == false);
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

