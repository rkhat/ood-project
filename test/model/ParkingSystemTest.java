package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ParkingSystemTest {
  
	// Testing spotsAvailable
	@Test
	void testSpotsAvailable() {
		System.out.println("Testing spotsAvailable");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0));
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
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
	// and removeReservation
	@Test
	void testLookUp() {
		System.out.println("Testing lookUp");
		ArrayList<Spot> spots = new ArrayList<Spot>(10);
		spots.add(new Spot(0,0));
		spots.add(new Spot(1,1));
		spots.add(new Spot(2,2));
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
		
		// remove the reservation
		ps.removeReservation(code);
		
		Assert.assertTrue(ps.lookUp(code) == null);
	}
	
	// Testing createAccount
	@Test
	void testCreateAccount() {
		System.out.println("Testing createAccount");
		
		ParkingSystem ps = ParkingSystem.getInstance();
		
		// add a new account to the system with valid un/psw
		String un = "Alec";
		String psw = "123ABC";
		Member mem1 = ps.createAccount(un, psw);
		Assert.assertTrue(mem1.getUserName() == un && mem1.getPassword() == psw);
		
		// add a new account to the system with the same un/psw (should fail)
		Member mem2 = ps.createAccount(un, psw);
		Assert.assertTrue(mem2 == null);
		
		// add a new account to the system with an invalid username
		Member mem3 = ps.createAccount("f17&&%", psw);
		Assert.assertTrue(mem3 == null);
		
		// add a new account to the system with an invalid password
		Member mem4 = ps.createAccount("alec", "123");
		Assert.assertTrue(mem4 == null);
	}
	
	// Testing verifyLoginInfo
	@Test
	void testVerifyLoginInfo() {
		System.out.println("Testing verifyLoginInfo");
		
		ParkingSystem ps = ParkingSystem.getInstance();
		
		// add a new account to the system
		String un = "Alec1";
		String psw = "123ABC";
		Member mem = ps.createAccount(un, psw);
		// should return true when username and password match an account
		Assert.assertTrue(ps.verifyLoginInfo(un, psw).equals(mem));
		// false if either the username, password, or both are incorrect
		Assert.assertTrue(ps.verifyLoginInfo(un, "wrongPassword") == null);
		Assert.assertTrue(ps.verifyLoginInfo("wrongUsername", psw) == null);
		Assert.assertTrue(ps.verifyLoginInfo("wrongUsername", "wrongPassword") == null);
	}
	
	
	
}
