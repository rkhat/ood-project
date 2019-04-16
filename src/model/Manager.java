package model;


/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Manager {
	
	/**
	 * Manager Constructor
	 */
	private Manager() {}
	
	/**
	 * Create a new instance of Manager
	 */
	public static void createInstance() {
		instance = new Manager();
	}
	
	/**
	 * Return the last Manager instance created
	 * 
	 * @return	The instance.
	 */
	public static Manager getInstance() {
		return instance;
	}
	
	
	
	private static Manager instance;
	
	private int currentSession;
}
