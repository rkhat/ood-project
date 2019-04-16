package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Parking Map class.
 * Represents a parking map with a list of spots.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMap {
    private List<Spot> spots;
    private int height;
    private int width;
    
    /**
     * Creates a ParkingMap object with no spots.
     */
    public ParkingMap() {
    	spots = new ArrayList<Spot>();
    	height = 0;
    	width = 0;
    }
    
    /**
     * Creates a ParkingMap object with the given spots.
     * 
     * @param spots	The list of spots in the parking map.
     */
    public ParkingMap(List<Spot> spots) {
    	this.spots = new ArrayList<Spot>(spots);
    	int x_min = spots.get(0).getX(), x_max = spots.get(0).getX();
    	int y_min = spots.get(0).getY(), y_max = spots.get(0).getY();
    	for (Spot s : spots) {
    		x_min = s.getX() < x_min ? s.getX() : x_min;
    		x_max = s.getX() > x_max ? s.getX() : x_max;
    		y_min = s.getY() < y_min ? s.getY() : y_min;
    		y_max = s.getY() > y_max ? s.getY() : y_max;
    	}
    	width = x_max - x_min;
    	height = y_max - y_min;
    }
    
    /**
     * Sets the list of spots for the parking map
     * 
     * @param spots	The list of spots.
     */
    public void setSpots(List<Spot> spots) {
    	this.spots = new ArrayList<Spot>(spots);
    	int x_min = spots.get(0).getX(), x_max = spots.get(0).getX();
    	int y_min = spots.get(0).getY(), y_max = spots.get(0).getY();
    	for (Spot s : spots) {
    		x_min = s.getX() < x_min ? s.getX() : x_min;
    		x_max = s.getX() > x_max ? s.getX() : x_max;
    		y_min = s.getY() < y_min ? s.getY() : y_min;
    		y_max = s.getY() > y_max ? s.getY() : y_max;
    	}
    	width = x_max - x_min;
    	height = y_max - y_min;    
    }
    
    /**
     * Accessor for the list of spots.
     * 
     * @return	The list of spots.
     */
    public List<Spot> getSpots() {
    	return this.spots;
    }
    
    /**
     * Get the width.
     * 
     * @return	The width.
     */
    public int getWidth() {
    	return width;
    }
    
    /**
     * Get the Height.
     * 
     * @return	The height.
     */
    public int getHeight() {
    	return height;
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
