package model;


/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
  private Manager() {
  }
  
  public static void createInstance() {
    instance = new Manager();
  }
  
  public static Manager getInstance() {
    return instance;
  }
  
  
  
  private static Manager instance;
  
  private int currentSession;
}
