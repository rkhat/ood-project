package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.enums.STATUS;

public class ManagerTest {
  ParkingSystem ps;
  Manager app;
  STATUS status;
  
  @BeforeEach
  public void init() {
    ps = ParkingSystem.getInstance();
    List<Spot> spots = new ArrayList<Spot>(2);
    for (int i = 0; i < spots.size(); i++) {
      spots.add(new Spot(1,i));
    }
    ParkingMap pm = new ParkingMap(spots);
    ps.setMap(pm);
    app = Manager.getInstance();
  }
  
  @AfterEach
  public void reset() {
    ParkingSystem.reset();
  }
  
  // Testing doCreateAccount
  @Test
  void testDoCreateAccount() {
    System.out.println("Run test doCreateAccount");
    String userName = "Alec";
    String password = "abc123";
    status = app.doCreateAccount(userName, password);
    Assert.assertTrue(status == STATUS.USERNAME_IN_USE);
  }
  
  // Testing doLogIn
  @Test
  void testDoLogIn() {
    System.out.println("Run test doLogIn");
    String userName = "Alec";
    String password = "abc123";
    app.doCreateAccount(userName, password);
    // log in with correct un and psw
    status = app.doLogIn(userName, password);
    Assert.assertTrue(status == STATUS.SUCCESS);
    // log in with incorrect psw
    status = app.doLogIn(userName,"wrongpsw");
    Assert.assertTrue(status == STATUS.FAILED);
    // log in with incorrect un
    status = app.doLogIn("wrongun",password);
    Assert.assertTrue(status == STATUS.FAILED);
  }
  

}
