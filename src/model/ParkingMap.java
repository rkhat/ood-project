package model;

/**
 * The Parking Map class.
 * Represents a parking map with a list of spots.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMap {
    private Spot[] spots;
    private boolean isFull;
    
    /**
     * Creates a ParkingMap object with the given spots.
     * 
     * @param spots	The list of spots in the parking map.
     */
    public ParkingMap(Spot[] spots) {
    	this.spots = spots;
    	this.isFull = false;
    }
    
    /**
     * Sets the list of spots for the parking map
     * 
     * @param spots	The list of spots.
     */
    public void setSpots(Spot[] spots) {
    	this.spots = spots;
    }
    
    /**
     * Accessor for the list of spots.
     * 
     * @return	The list of spots.
     */
    public Spot[] getSpots() {
    	return this.spots;
    }
    
    /**
     * Check if there are spots available.
     * 
     * @return	true if there are spots available, false otherwise.
     */
    public boolean spotsAvailable() {
    	return (!this.isFull);
    }
    
    /**
     * Attempt to lock the given spot.
     * 
     * @param spot	The spot to be locked.
     * @return	true if successful, false otherwise.
     */
    public boolean lockSpot(Spot spot) {
    	// TODO: if we get a reference to the spot from parking map, we may not need to iterate.
    	// May be able to simply lock the spot.
    	for (Spot s : this.spots) {
    		if (s.equals(spot)) {
    			return s.lock();
    		}
    	}
    	return false;
    }
    
    /**
     * Attempt to reserve the given spot.
     * 
     * @param spot	The spot to be reserved.
     * @return	true if successful, false otherwise.
     */
    public boolean reserveSpot(Spot spot) {
    	// TODO: if we get a reference to the spot from parking map, we may not need to iterate.
    	// May be able to simply reserve the spot.
    	for (Spot s : this.spots) {
    		if (s.equals(spot)) {
    			return s.reserve();
    		}
    	}
    	return false;
    }
    
    /**
     * Free the given spot.
     * 
     * @param spot	The spot to be freed.
     */
    public void freeSpot(Spot spot) {
    	// TODO: if we get a reference to the spot from parking map, we may not need to iterate.
    	// May be able to simply free the spot.
    	for (Spot s : this.spots) {
    		if (s.equals(spot)) {
    			s.free();
    			return;
    		}
    	}
    }
}
