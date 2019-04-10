package model.junit;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.Member;
import model.Reservation;
import model.Vehicle;

class MemberTest {

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
	
	// Testing setVehicle
	@Test
	void testSetVehicle() {
		System.out.println("Run test setVehicle");
		String userName = "Alec";
		String password = "abc123";
		Member member = new Member(userName, password);
		Vehicle vehicle = new Vehicle("ABC123");
		fail("not yet implemented");
		Assert.assertTrue(member.getUserName().equals(userName) && member.getPassword().equals(password));
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
	
	
}

