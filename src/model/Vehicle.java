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
}
