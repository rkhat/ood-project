package model;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  @AfterEach
  public void resetStatics() {
    try {
      Field field = ParkingSystem.class.getDeclaredField("nextVehicleID");
      field.setAccessible(true);
      field.set(null, 0);
    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }
  
	// Testing Member Constructor
	@Test
	void testMember() {
		System.out.println("Run test Member constructor");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Assert.assertTrue(member.getUserName().equals(userName) && member.getPassword().equals(password));
	}
	
	// Testing setPassword
	@Test
	void testSetPassword() {
		System.out.println("Run test setPassword");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		String newPassword = "123abc";
		member.setPassword(newPassword);
		Assert.assertTrue(member.getPassword().equals(newPassword));
	}

	// Testing setReservation
	@Test
	void testSetReservation() {
		System.out.println("Run test setReservation");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Reservation reservation = new Reservation();
		member.setReservation(reservation);
		Assert.assertTrue(member.getReservation().equals(reservation));
	}
	
	// Testing setCode
	@Test
	void testSetCode() {
		System.out.println("Run test setCode");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		String newCode = "1fgasdf154sDS";
		member.setCode(newCode);
		Assert.assertTrue(member.getCode().equals(newCode));
	}
	
	// Testing addCredits
	@Test
	void testAddCredits() {
		System.out.println("Run test addCredits");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		double credits1 = 15.50, credits2 = 12;
		member.addCredits(credits1);
		member.addCredits(credits2);
		Assert.assertTrue(member.getCredits() == (credits1+credits2));
	}
	
	// Testing removeCredits
	@Test
	void testRemoveCredits() {
		System.out.println("Run test removeCredits");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		double credits1 = 15.50, credits2 = 12, credits3 = 10.25, credits4 = 7;
		member.addCredits(credits1);
		member.addCredits(credits2);
		member.removeCredits(credits3);
		member.removeCredits(credits4);
		Assert.assertTrue(member.getCredits() == credits1+credits2-credits3-credits4);
	}
	
	// Testing hasSufficientCredits
	@Test
	void testHasSufficientCredits() {
		System.out.println("Run test hasSufficientCredits");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		double amt1 = 50.75, amt2 = 20, amt3 = 12.25;
		member.addCredits(20);
		Assert.assertTrue(!member.hasSufficientCredits(amt1) && member.hasSufficientCredits(amt2) && member.hasSufficientCredits(amt3));
	}
	
	// Testing removeReservation
	@Test
	void testRemoveReservation() {
		System.out.println("Run test removeReservation");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Reservation res = new Reservation();
		member.setReservation(res);
		member.removeReservation();
		Assert.assertTrue(member.getReservation() == null);
	}
	
	// Testing addVehicle
	@Test
	void testAddVehicle() {
		System.out.println("Run test addVehicle");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Vehicle v1 = new Vehicle("ABC123", 0);
		Vehicle v2 = new Vehicle("123ABC", 1);
		member.addVehicle(v1);
		member.addVehicle(v2);
		Assert.assertTrue(member.getVehicles().containsKey(0));
    Assert.assertTrue(member.getVehicles().containsKey(1));
	}
	
	// Testing removeVehicle
	@Test
	void testRemoveVehicle() {
	  System.out.println("Run test removeVehicle");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Vehicle vehicle = new Vehicle("ABC123");
		member.addVehicle(vehicle);
		member.removeVehicle(vehicle.getID());
		Assert.assertTrue(!member.getVehicles().containsKey(vehicle.getID()));
	}
	
	// Testing equals
	@Test
	void testEquals() {
		System.out.println("Run test equals");
		Member mem1 = new Member("Alec", "ABC123");
		Member mem2 = new Member("Alec", "q983u234");
		Member mem3 = new Member("Rami", "a2lkj23894");
		Assert.assertTrue(mem1.equals(mem2));
		Assert.assertTrue(!mem2.equals(mem3));
	}
}

