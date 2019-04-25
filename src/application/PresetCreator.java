package application;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Manager;
import model.ParkingMap;
import model.ParkingSystem;
import model.Spot;

public class PresetCreator {
  public static void main(String[] args) {
    createOodpPreset();
    //createIbmPreset();
  }

  public static void createIbmPreset() {
    String fileName = "ibm.ser";
    
    List<Spot> spotList = new ArrayList<Spot>();
    addSpotsHorizontal(spotList, 0, 0, 3);
    addSpotsVertical(spotList, 1, 1, 3);
    addSpotsHorizontal(spotList, 0, 4, 3);
    addSpotsVertical(spotList, 4, 0, 5);
    spotList.add(new Spot(5, 0));
    spotList.add(new Spot(5, 2));
    spotList.add(new Spot(5, 4));
    spotList.add(new Spot(6, 1));
    spotList.add(new Spot(6, 3));
    addSpotsVertical(spotList, 8, 0, 5);
    spotList.add(new Spot(9, 1));
    spotList.add(new Spot(10, 2));
    spotList.add(new Spot(11, 1));
    addSpotsVertical(spotList, 12, 0, 5);

    ParkingMap pm = new ParkingMap(spotList);
    ParkingSystem ps = ParkingSystem.getInstance();
    ps.setMap(pm);

    Manager.createInstance(ps);
    Manager app = Manager.getInstance();

    addMember(app, "Alec", "Alec123", 25, "ABC123", 3);
    addMember(app, "Mac", "Mac123", 5, "123ABC", 5);
    addMember(app, "Matias", "Matias123", 22, "HPKM16", 10);
    addMember(app, "Jorge", "Jorge123", 17.4, "M16HPK", 12);
    addMember(app, "Larry", "Larry123", 15, "GHJ253", 17);
    addMember(app, "Sara", "Sara123", 21, "193KFH", 18);
    addMember(app, "Allie", "Allie123", 39, "194LDR", 20);
    addMember(app, "Will", "Will123", 53, "FERREL", 22);
    addMember(app, "John", "John123", 12.5, "CENA12", 24);
    addMember(app, "Rami", "Rami123", 13.7, "104KD7", 26);
    addMember(app, "Rafa", "Rafa123", 5.1, "573KD7", 28);
    addMember(app, "Carolina", "Carolina123", 6.3, "LSKD73", 30);

    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(ps);
      oos.close();
      fos.close();
    } catch (Exception e) {
      System.err.println(e.toString());
    }
    
  }
  
  public static void createOodpPreset() {
    String fileName = "oodp.ser";
    
    List<Spot> spotList = new ArrayList<Spot>();
    
    addSpotsVertical(spotList, 0, 0, 5);
    spotList.add(new Spot(1,0));
    spotList.add(new Spot(1,4));
    addSpotsVertical(spotList, 2, 0, 5);
    
    addSpotsVertical(spotList, 4, 0, 5);
    spotList.add(new Spot(5,0));
    spotList.add(new Spot(5,4));
    addSpotsVertical(spotList, 6, 0, 5);
    
    addSpotsVertical(spotList, 8, 0, 5);
    spotList.add(new Spot(9,0));
    spotList.add(new Spot(9,4));
    addSpotsVertical(spotList, 10, 1, 3);

    addSpotsVertical(spotList, 12, 0, 5);
    spotList.add(new Spot(13,0));
    spotList.add(new Spot(13,2));
    
    addSpotsVertical(spotList, 14, 0, 3);

    ParkingMap pm = new ParkingMap(spotList);
    ParkingSystem ps = ParkingSystem.getInstance();
    ps.setMap(pm);

    Manager.createInstance(ps);
    Manager app = Manager.getInstance();
    
    addMember(app, "Alec", "Alec123", 25, "ABC123", 2);
    addMember(app, "Mac", "Mac123", 5, "123ABC", 9);
    addMember(app, "Matias", "Matias123", 22, "HPKM16", 14);
    addMember(app, "Jorge", "Jorge123", 17.4, "M16HPK", 21);
    addMember(app, "Larry", "Larry123", 15, "GHJ253", 26);
    addMember(app, "Sara", "Sara123", 21, "193KFH", 32);
    addMember(app, "Allie", "Allie123", 39, "194LDR", 36);
    addMember(app, "Will", "Will123", 53, "046DK3", 40);
    addMember(app, "John", "John123", 12.5, "AK3LF8", 43);

    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(ps);
      oos.close();
      fos.close();
    } catch (Exception e) {
      System.err.println(e.toString());
    }
    
  }

  public static ParkingSystem createAppSystem() {
      // create list of spots
      List<Spot> spotList = new ArrayList<Spot>(10);
      addSpotsHorizontal(spotList,0,0,10);
      addSpotsHorizontal(spotList,0,2,10);
      addSpotsHorizontal(spotList,0,3,10);
      addSpotsHorizontal(spotList,0,5,10);
      addSpotsHorizontal(spotList,0,6,10);
      addSpotsHorizontal(spotList,0,8,4);
      addSpotsHorizontal(spotList,6,8,4);

      // create map
      ParkingMap map = new ParkingMap(spotList);

      // Set up parking system
      ParkingSystem ps = ParkingSystem.getInstance();
      ps.setMap(map);
      return ps;
  }
  
  // create member with credits and parked vehicle.
  public static void addMember(Manager m, String un, String psw, double credits,
      String plate, int spotID) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doAddVehicle(plate);
    m.doSelectVehicle(m.getVehiclesAsList().get(0).getID());
    m.doSelectSpot(spotID);
    m.doLogOut();
  }

  // create member with credits and unparked vehicle.
  public static void addMember(Manager m, String un, String psw, double credits,
      String plate) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doAddVehicle(plate);
    m.doLogOut();
  }

  // create member with credits and no vehicles.
  public static void addMember(Manager m, String un, String psw, double credits) {
    m.doCreateAccount(un, psw);
    m.doAddCredits(credits);
    m.doLogOut();
  }

  /**
   * Add spots in a vertical line starting at position x, yBegin and ending at
   * position x, yBegin+amt-1
   * 
   * @param spotList The list to add spots to.
   * @param x        The X value of the column to add the spots in.
   * @param yBegin   The beginning Y value.
   * @param amt      The number of spots to add.
   */
  public static void addSpotsVertical(List<Spot> spotList, int x, int yBegin,
      int amt) {
    for (int i = 0; i < amt; i++) {
      spotList.add(new Spot(x, yBegin + i));
    }
  }

  /**
   * Add spots in a horizontal line starting at position xBegin, y and ending at
   * position xBegin+amt-1, y
   * 
   * @param spotList The list to add spots to.
   * @param xBegin   The beginning X value.
   * @param y        The Y value of the row to add the spots in.
   * @param amt      The number of spots to add.
   */
  public static void addSpotsHorizontal(List<Spot> spotList, int xBegin, int y,
      int amt) {
    for (int i = 0; i < amt; i++) {
      spotList.add(new Spot(xBegin + i, y));
    }
  }
}
