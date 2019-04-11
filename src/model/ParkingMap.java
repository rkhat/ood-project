package model;

import java.util.ArrayList;

/**
 * The Parking Map class.
 * Represents a parking map with a list of spots.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMap {
    private ArrayList<Spot> spots;
    
    /**
     * Creates a ParkingMap object with no spots.
     */
    public ParkingMap() {
    	spots = new ArrayList<Spot>(10);
    }
    
    /**
     * Creates a ParkingMap object with the given spots.
     * 
     * @param spots	The list of spots in the parking map.
     */
    public ParkingMap(ArrayList<Spot> spots) {
    	this.spots = spots;
    }
    
    /**
     * Sets the list of spots for the parking map
     * 
     * @param spots	The list of spots.
     */
    public void setSpots(ArrayList<Spot> spots) {
    	this.spots = spots;
    }
    
    /**
     * Accessor for the list of spots.
     * 
     * @return	The list of spots.
     */
    public ArrayList<Spot> getSpots() {
    	return this.spots;
    }
    
    /**
     * Accessor for number of available spots.
     * 
     * @return	The number of available spots.
     */
    public int getNumAvailable() {
    	int numAvailable = 0;
    	// count the number of available spots
    	for (Spot s : spots) {
    		if (!(s.isReserved() || s.isLocked()) ) numAvailable++;
    	}
    	return numAvailable;
    }
    
    /**
     * Check if there are spots available.
     * 
     * @return	true if there are spots available, false otherwise.
     */
    public boolean spotsAvailable() {
    	return (getNumAvailable() > 0);
    }
    
    /**
     * Attempt to lock the given spot.
     * 
     * @param spot	The spot to be locked.
     * @return		true if successful, false otherwise.
     */
    public boolean lockSpot(Spot spot) {
    	if (spots.contains(spot)) {
    		return spot.lock();
    	}
    	return false;
    }
    
    /**
     * Attempt to reserve the given spot.
     * 
     * @param spot	The spot to be reserved.
     * @return		true if successful, false otherwise.
     */
    public boolean reserveSpot(Spot spot) {
    	if (spots.contains(spot)) {
    		return spot.reserve();
    	}
    	return false;
    }
    
    /**
     * Free the given spot.
     * 
     * @param spot	The spot to be freed.
     */
    public void freeSpot(Spot spot) {
    	if (spots.contains(spot)) {
    		spot.free();
    	}
    }
}
