import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class DungeonFloor implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;

	public ArrayList<Room> roomsInDungeon;
	private Random rand;


	/**
	 * Create all the rooms and link their exits together.
	 */
	public DungeonFloor(int cr){

		roomsInDungeon = new ArrayList<Room>();
		rand = new Random();
		createDungeonFloorFromPresets(cr);
	}

	private void createDungeonFloorFromPresets(int cr){
		int dungeonNumber = rand.nextInt(1);
		switch(dungeonNumber){
		case 0 :
			
			//this is just a layout for testing purposes, add a dungeon layout 
			//with some atmospheric description here.
			
			Room entranceRoom;
			Room room1;
			Room room2;
			Room deadEnd;
			Room room3;
			Room room4;
			Room finalRoom;

			entranceRoom = new Room("a room for test purposes", cr);
			room1 = new Room("another room for test purposes", cr);
			room2 = new Room("a room for test purposes. Again", cr);
			deadEnd = new Room("a room for test purposes. But this time, it's a dead end", cr);
			room3 = new Room("a room for test purposes. Yes. Definetly. You know them by now", cr);
			room4 = new Room("another room for test purposes. Its like being in a nightmare full of testing rooms", cr);
			finalRoom = new Room("a room for... aaah goddamit, there ARE only rooms for test purposes..", cr);
			
			//sets the finalRoom up to have a staircase instead of a normal encounter
			finalRoom.setFinalRoom();

			entranceRoom.setExit("south", room1);
			room1.setExit("south", room2);
			room2.setExit("south", room3);
			room3.setExit("south", room4);
			room4.setExit("south", finalRoom);
			room1.setExit("north", entranceRoom);
			room2.setExit("north", room1);
			room3.setExit("north", room2);
			room4.setExit("north", room3);
			finalRoom.setExit("north", room4);
			room2.setExit("east", deadEnd);
			deadEnd.setExit("west", room2);

			roomsInDungeon.add(entranceRoom);
			roomsInDungeon.add(room1);
			roomsInDungeon.add(room2);
			roomsInDungeon.add(deadEnd);
			roomsInDungeon.add(room3);
			roomsInDungeon.add(room4);
			roomsInDungeon.add(finalRoom);
			
			
			break;
		case 1 :
			
			//Add a Dungeon Layout here, don't forget to increase the bounds
			//of the random number accordingly
			
			break;
		}
	}
}
