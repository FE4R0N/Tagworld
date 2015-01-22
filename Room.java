import java.io.Serializable;
import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class Room  implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
	private String description;
    public HashMap<String, Room> exits;
    private Encounter encounter;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, int cr) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        encounter = new Encounter(cr);
    }
    public void setExit(String exit, Room toRoom) 
    {
        exits.put(exit, toRoom);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
    	return  description + ".";
    }
    public String getExits(){
    	String availableExits = "     ";
    	if(exits.containsKey("north")){
    		availableExits = availableExits + "[north]  ";
    	}
    	if(exits.containsKey("east")){
    		availableExits = availableExits + "[east]  ";
    	}
    	if(exits.containsKey("south")){
    		availableExits = availableExits + "[south]  ";
    	}	
    	if(exits.containsKey("west")){
    		availableExits = availableExits + "[west]  ";
    	}
    	return availableExits;		
    }
    public void setRoomsEncounter(Encounter newEncounter){
    	encounter = newEncounter;
    }
    public Encounter getRoomsEncounter(){
    	return encounter;
    }
    public Room getExit(String direction)
    {
    	if(exits.containsKey(direction)){
    		return exits.get(direction);
    	}
    	return null;
    }
    public Room getLastRoom(String direction){
    	String inversedDirection = null;
    	if(direction.equals("north")){
    		inversedDirection = "south";
    	}
    	if(direction.equals("south")){
    		inversedDirection = "north";
    	}
    	if(direction.equals("west")){
    		inversedDirection = "east";
    	}
    	if(direction.equals("east")){
    		inversedDirection = "west";
    	}
    	return exits.get(inversedDirection);
    }
    public void setFinalRoom(){
    	encounter.setStairCase();
    }
    	
}
