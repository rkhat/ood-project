  package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Parking Map class.
 * Represents a parking map with a list of spots.
 *
 * @author Alec Agnese, Rami El Khatib
 */
public class ParkingMap {
    private Map<Integer,Spot> spots;
    private int nextID;
    private int minX, minY, maxX, maxY;
    private int freeSpots, reservedSpots;
    
    /**
     * Creates a ParkingMap object with the given spots and minimum width and height.
     * 
     * @param spots	The list of spots in the parking map.
     */
    public ParkingMap(List<Spot> spots) {
    	this.spots = new HashMap<Integer,Spot>(spots.size());
    	minY = Integer.MAX_VALUE;
      minX = Integer.MAX_VALUE;
      maxY = 0;
      maxX = 0;
    	nextID = 0;
    	freeSpots = 0;
    	reservedSpots = 0;
    	for (Spot s : spots) {
    		minX = s.getX() < minX ? s.getX() : minX;
    		maxX = s.getX() > maxX ? s.getX() : maxX;
    		minY = s.getY() < minY ? s.getY() : minY;
    		maxY = s.getY() > maxY ? s.getY() : maxY;
    		s.setID(nextID++);
    		this.spots.put(s.getID(),s);
    		if (s.isReserved()) reservedSpots++;
    		else freeSpots++;
    	}
    }
    
    /**
     * Adds a list of spots to the parking map
     * 
     * @param spots	The list of spots.
     * @precondition X and Y coordinates of new spots must not overlap with any existing spots.
     * @precondition spots cannot be an empty list.
     */
    public void addSpots(List<Spot> spots) {
      for (Spot s : spots) {
        minX = s.getX() < minX ? s.getX() : minX;
        maxX = s.getX() > maxX ? s.getX() : maxX;
        minY = s.getY() < minY ? s.getY() : minY;
        maxY = s.getY() > maxY ? s.getY() : maxY;
        s.setID(nextID++);
        this.spots.put(s.getID(),s);
        if (s.isReserved()) reservedSpots++;
        else freeSpots++;
      }
    }
    
    /**
     * Get the list of spots as a List.
     * 
     * @return	The list of spots as a List.
     */
    public List<Spot> getSpotsAsList() {
    	List<Spot> spotList = new ArrayList<Spot>(spots.values());
    	return spotList;
    }
    
    /**
     * Get the list of spots as a Map.
     * 
     * @return  The list of spots as a Map.
     */
    public Map<Integer,Spot> getSpotsAsMap() {
      return spots;
    }
    
    /**
     * Get the width.
     * 
     * @return	The width.
     */
    public int getWidth() {
    	return (maxX - minX + 1);
    }
    
    /**
     * Get the Height.
     * 
     * @return	The height.
     */
    public int getHeight() {
    	return (maxY - minY + 1);
    }
    
    /**
     * Get the maximum X value.
     * 
     * @return  the maximum X value.
     */
    public int getMaxX() {
      return maxX;
    }
    
    /**
     * Get the maximum Y value.
     * 
     * @return  the maximum Y value.
     */
    public int getMaxY() {
      return maxY;
    }
    
    /**
     * Accessor for number of available spots.
     * 
     * @return	The number of available spots.
     */
    public int getNumAvailable() {
    	return freeSpots;
    }
    
    /**
     * Accessor for number of available spots.
     * 
     * @return  The number of reserved spots.
     */
    public int getNumReserved() {
      return reservedSpots;
    }
    
    /**
     * Check if there are spots available.
     * 
     * @return	true if there are spots available, false otherwise.
     */
    public boolean spotsAvailable() {
    	return (freeSpots > 0);
    }
    
    /**
     * Attempt to reserve the given spot.
     * 
     * @param spotID  The ID of the spot to reserve
     * @return		    true if successful, false otherwise.
     * @precondition  spotID must correspond to an existing spot.
     */
    public boolean reserveSpot(int spotID) {
   		if (spots.get(spotID).reserve()) {
   		  reservedSpots++;
   		  freeSpots--;
   		  return true;
   		};
   		return false;
    }
    
    /**
     * Free the given spot.
     * 
     * @param spot	The spot to be freed.
     * @return      true if spot was reserved, 
     *              false if spotID was invalid or spot was not reserved.
     */
    public boolean freeSpot(int spotID) {
      Spot s = spots.get(spotID);
      if (s == null) {
        return false;
      }
      if (s.free()) {
        freeSpots++;
        reservedSpots--;
        return true;
      }
      return false;
    }
}
