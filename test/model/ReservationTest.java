package model;

import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReservationTest {

	// Testing getTotal
	@Test
	void testGetTotal() {
		System.out.println("Testing getTotal");
		Reservation res = new Reservation();
		// set start time to 35 hrs before now
		Date startTime = new Date(new Date().getTime() - 35*(1000*60*60));
		res.setStartTime(startTime);
		// 35 hours, hourly = 35*2.50
		res.setTimeType("HOURLY");
		Assert.assertTrue(res.getTotal() == 35*2.50);
		// 35 hours, daily = 2*20
		res.setTimeType("DAILY");
		Assert.assertTrue(res.getTotal() == 2*20);
	}
	
}
