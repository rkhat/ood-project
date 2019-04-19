package model;

/**
 * The Vehicle class.
 * Represents a vehicle with given license plate.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class Vehicle {
    private String plate;
    private int id;
    
    /**
     * Create a Vehicle object with given plate.
     * 
     * @param plate The plate.
     */
    public Vehicle(String plate) {
    	this.plate = plate;
    	this.id = -1;
    }
    
    /**
     * Get the plate number.
     * 
     * @return  The plate number (String)
     */
    public String getPlate() {
    	return this.plate;
    }
    
    /**
     * Get the ID.
     * 
     * @return  The ID.
     */
    public int getID() {
      return id;
    }
    
    /**
     * Check whether two vehicles are equal (same plate)
     * 
     * @param other	The vehicle to compare this to.
     * @return		true if equal, false otherwise.
     */
    public boolean equals(Vehicle other) {
    	if (this == other) return true;
    	if (other == null) return false;
    	return (this.plate.equals(other.plate));
    }
    
    /**
     * Set the Vehicle ID.
     * 
     * @param id  The ID.
     */
    public void setID(int id) {
      this.id = id;
    }
    
    /**
     * Return a string representation of the vehicle.
     */
    public String toString() {
      String s = "ID: " + id + "\tPlate: " + plate;
      return s;
    }
}
