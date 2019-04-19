package model;

/**
 * The Spot class.
 * Represents a parking spot on a parking map.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class Spot {
	
	private boolean reserved;
	private int x;
	private int y;
	private int id;
	
	/**
	 * Create a spot object with given coordinates.
	 * 
	 * @param x	X coordinate.
	 * @param y	Y coordinate.
	 */
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
		reserved = false;
		this.id = -1;
	}
	
	/**
	 * Check whether or not the spot is reserved.
	 * 
	 * @return true if reserved, false otherwise.
	 */
	public boolean isReserved() {
		return this.reserved;
	}
	
	/**
	 * Get the X coordinate of the spot.
	 * 
	 * @return	The X coordinate.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the Y coordinate of the spot.
	 * 
	 * @return	The Y coordinate.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Get the spot's ID.
	 * 
	 * @return	The ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Attempt to reserve the spot.
	 * 
	 * @return	true if successful, false otherwise.
	 */
	public boolean reserve() {
		// if already reserved, return false to indicate failure.
		if (this.reserved) return false; 
		// otherwise, reserve and return true;
		this.reserved = true;
		return true;
	}
	
	/**
	 * Free the spot.
	 */
	public void free() {
		this.reserved = false;
	}
	
	/**
	 * Set the X coordinate of the spot.
	 * 
	 * @param x	The X coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y coordinate of the spot.
	 * 
	 * @param y	The Y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Set the spot's ID.
	 * 
	 * @param id The ID.
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Check if the two Spots are equal (same ID)
	 * 
	 * @param other	The spot to compare this to.
	 * @return		true if equal, false otherwise.
	 */
	public boolean equals(Spot other) {
    	if (this == other) return true;
    	if (other == null) return false;
    	return (this.id == other.id);
	}
	
	/**
	 * Return a string representation of the spot.
	 */
	public String toString() {
	  String s = "ID: " + id + "\tX: " + x + "\tY: " + y;
	  return s;
	}
}
