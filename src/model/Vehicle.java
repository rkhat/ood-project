package model;

/**
 * The Vehicle class.
 * Represents a vehicle with given license plate.
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public class Vehicle {
    private String plate;
    
    public Vehicle(String plate) {
    	this.plate = plate;
    }
    
    public String getPlate() {
    	return this.plate;
    }
    
    /**
     * Check whether two vehicles are equal (same plate)
     * 
     * @param other	The vehicle to compare this to.
     * @return		true if equal, false otherwise.
     */
    public boolean equals(Vehicle other) {
    	return (this.plate.equals(other.plate));
    }
}
