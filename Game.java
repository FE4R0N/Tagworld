import java.util.ArrayList;
import java.io.*;

/**
 *  This class is the main class of the "Text Adventure" application.
 *  
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all rooms and it also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author Philipp Wolf
 * @version 15.01.2015
 */

public class Game implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
	private Parser parser;
	private Room currentRoom;
	private Encounter currentEncounter;
	private int currentRoomIndexInDungeonMap;
	private int opponentsAwareness;
	private int spellsTodayUsed;
	private int floorNumber;
	private GameData settings;
	private DungeonFloor dungeon;
	private boolean playerDead;
	private StoryLine storyLine;

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() 
	{
		spellsTodayUsed = 0;
		floorNumber = 1;
		playerDead = false;
		parser = new Parser();
	}

	/**
	 * Creates the current dungeon floor, setting up the rooms and their encounters
	 */
	private void createDungeon(){
		dungeon = new DungeonFloor(settings.player.getLvl());
		currentRoom = dungeon.roomsInDungeon.get(0);  // start game in testRoom
		currentEncounter = currentRoom.getRoomsEncounter(); // start game with the current Rooms Encounter
		opponentsAwareness = 0;
	}

	/**
	 *  Main play routine.  Loops until end of play.
	 */
	private void play() 
	{           
		if(load() == true){
			System.out.print("loading");
			DramaticalPause.shortPause();
			System.out.print(".");
			DramaticalPause.shortPause();
			System.out.print(".");
			DramaticalPause.shortPause();
			System.out.print(".");
			DramaticalPause.shortPause();
			System.out.print("Done!");
			System.out.println();
		}	
		else{
			getSettings();
			createDungeon();
			printWelcome();
			storyLine = new StoryLine(settings.player.getName());
			settings.score = 0;
			System.out.println(Lang.get(0) + " " + currentRoom.getDescription() + ".");
			System.out.println(currentEncounter.getEncounterDescription());
			if(currentRoom.getRoomsEncounter().getType().equals("battle")){
				System.out.println(Lang.get(1) + " [" + currentEncounter.getPrimaryEnemy().getName() +"] " + Lang.get(2) + ".");
				System.out.println(Lang.get(3));

			}
			else{
				System.out.println(Lang.get(4));
			}
			System.out.println(currentRoom.getExits());
			// Enter the main command loop.  Here we repeatedly read commands and
			// execute them until the game is over.
		}
		boolean finished = false;
		while (! finished) {
			Command command = parser.getCommand();
			String output = processCommand(command);
			finished = (null == output);
			if (!finished)
			{ 
				System.out.println(output);
			}
			if(playerDead == true){
				DramaticalPause.shortPause();
				System.out.print("....");
				DramaticalPause.shortPause();
				System.out.print("....");
				DramaticalPause.shortPause();
				System.out.print("....");
				DramaticalPause.shortPause();
				System.out.println();
				System.out.println();
				System.out.println("GAME OVER");
				System.out.println();
				System.out.println();
				System.out.println("Score:    " + settings.score);
				finished = true;
				File file = new File("save.sav");
				file.delete();
			}

		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 *  Asks the player choose the difficulty of the game and the player creates his character
	 */
	private void getSettings()
	{
		// Here we ask the player to set the game up for himself
		/*System.out.println();
		System.out.println("Please Choose your language!");
		System.out.println("Bitte Wähle deine Sprache!");
		System.out.println("  [deutsch]   [english]");
		String languageSetting = parser.getInput();
		if(languageSetting.equals("deutsch")){

			Lang.setLanguage("deutsch");
		}else{
		 */
		Lang.setLanguage("english");
		/*
		}
		 */
		System.out.println();
		System.out.println(Lang.get(8));
		System.out.println("  [" + Lang.get(9) + "]   [" + Lang.get(10) + "]   [" + Lang.get(11) + "]");
		String difficultySetting = parser.getInput();
		int difficulty = 1;
		if(difficultySetting.equals("easy") || difficultySetting.equals("einfach")){
			difficulty = 0;
		}
		if(difficultySetting.equals("hard") || difficultySetting.equals("schwer")){
			difficulty = 2;
		}
		System.out.println(Lang.get(12));
		String playerName = parser.getInput();
		if(playerName.equals("")){
			playerName = Lang.get(13);
		}
		System.out.println(Lang.get(14));
		System.out.println("[" + Lang.get(15) + "] [" + Lang.get(16) + "] [" + Lang.get(17) + "] [" + Lang.get(18) + "]");
		String playerClass = "";
		boolean classHasBeenSelected = false;
		while(classHasBeenSelected == false){
			playerClass = parser.getInput();
			playerClass = playerClass.toLowerCase();
			switch(playerClass){
			case "warrior" :
				classHasBeenSelected = true;
				break;
			case "krieger" :
				classHasBeenSelected = true;
				playerClass = "warrior";
				break;
			case "rogue" :
				classHasBeenSelected = true;
				break;
			case "schurke" :
				classHasBeenSelected = true;
				playerClass = "rogue";
				break;
			case "magier" :
				classHasBeenSelected = true;
				playerClass = "mage";
				break;
			case "mage" :
				classHasBeenSelected = true;
				break;
			case "kleriker" :
				classHasBeenSelected = true;
				playerClass = "clerik";
				break;
			case "clerik" :
				classHasBeenSelected = true;
				break;
			}
			if(classHasBeenSelected == false){
				System.out.println(playerClass + " " + Lang.get(19) + ".");
				System.out.println(Lang.get(20));
			}
		}
		this.settings = new GameData(difficulty, playerName, playerClass);
		boolean finished = false;
		String command = "yes";
		while(finished == false){
			settings.player.printAbilityScores();
			System.out.println(Lang.get(21));
			System.out.println("   [" + Lang.get(22) + "]     [" + Lang.get(23) + "]");
			command = parser.getInput();
			if(command.equals("yes") || command.equals("ja")){
				settings.player.reRollAbilityScores();
				finished = false;
			}else{
				finished = true;
			}
		}
		settings.player.addSpellsOnLevelUp();
	}


	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome()
	{
		System.out.println();
		System.out.println(Lang.get(24));
		System.out.println();
		System.out.println(Lang.get(25));
		System.out.println(Lang.get(26));
		System.out.println(Lang.get(27));
		System.out.println(Lang.get(28));
		System.out.println();
		System.out.println();
	}


	/**
	 * Given a command, process (that is: execute) the command.
	 * @param command The command to be processed.
	 * @return a text to be written in the play routine.
	 */
	private String processCommand(Command command) 
	{

		if(command.isUnknown()) {
			return "The Idea is good but your arms are to short...";
		}
		String commandWord = command.getCommandWord();
		String result = null;
		switch(commandWord){
		case "help" : 
			result = printHelp(command);
			break;
		case "go" : 
			result = goRoom(command);
			break;
		case "quit" :
			result = quit(command);
			break;
		case "fight" :
			result = fight(command);
			break;
		case "search" :
			result = search(command);
			break;
		case "equip" :
			result = equip(command);
			break;
		case "show" :
			result = show(command);
			break;
		case "rest" :
			result = rest();
			break;
		case "discard" :
			result = discard(command);
			break;
		case "look" :
			result = look();
			break;
		case "say" :
			result = say(command);
			break;
		case "buy" :
			result = buy(command);
			break;
		case "drink" :
			result = drink(command);
			break;
		case "perform" :
			result = maneuver(command);
			break;
		case "cast" :
			result = cast(command);
			break;
		case "focus" :
			result = focus(command);
			break;
		case "sell" :
			result = sell(command);
			break;
		}

		return result;

	}
	/**
	 * Implementation of all available commands
	 */

	// implementations of user commands:
	/**
	 * Print out some help information.
	 * Here we print some stupid, cryptic message and a list of the 
	 * command words.
	 * @param command The command to be processed.
	 * @return a text to be written in the play routine.
	 */
	private String printHelp(Command command) 
	{   String result = null;
	if(!command.hasSecondWord()) {
		// if there is no second word, print some helping text...

		result = "You are a brave little adventurer. Set into a little World\n";
		result += "and free to do what ever you like (that is if there is a command for it).\n";
		result += "\n";
		result += "All available command words are:\n";
		result += "   [help]  [quit]  [look]  [fight]  [buy]  [equip]  \n";
		result += "   [show]  [rest]  [discard]  [go]  [say]  [search] \n";
		result += "   [cast]  [focus] [perform] \n";
		result += "And remember parameter and command words from the text are always \n";
		result += "written in [brackets]. Combine them and see what happens! \n";
	}
	else{
		//if there is a second word we look for a command that matches the word
		//and if we find something we print out some helping text for the command

		String helpWord = command.getSecondWord();
		switch(helpWord){
		case "help" : 
			result = "Type help to get help. Duuuh...\n";
			break;
		case "go" : 
			result = "Go into a direction. If there is an exit you leave the room through that exit.\n";
			break;
		case "quit" :
			result = "Quit the Game. All progress is Lost...\n";
			break;
		case "fight" :
			result = "Fight an Enemy in the Room.\n";
			break;
		case "search" :
			result = "Search the room for items. If you find an Item, you automatically \n" + "try to pick it up and place it in your inventory.\n";
			break;
		case "show" :
			result = "[show]+[me] shows you your Status, [show]+[inventory] shows you your inventory, \n" + "[show]+[spellbook] shows you your spellbook and you can type \n" +"[show]+[shop] to show you the shops wares, assuming you have a merchant near by.\n";
			break;
		case "equip" :
			result = "Equip a specific item you have in your inventory";
			break;
		case "rest" :
			result = "Rest for some ours and heal yourself.\n";
			break;
		case "discard" :
			result = "Discard an item from your inventory.\n";
			break;
		case "look" :
			result = "Look around the room.\n";
			break;
		case "say" :
			result = "Say something.\n";
			break;
		case "drink" :
			result = "Drink a potion from you inventory\n";
			break;
		case "buy" :
			result = "Buy a specific item from the shops wares, again assuming you have a shopkeep near by.\n";
			break;
		case "perform" :
			result = "Execute a specific combat maneuver against an enemy, available maneuvers are\n";
			result += "  [feint] \n";
			break;
		case "cast" :
			result = "Cast a spell from your spellbook, type [show] + [spellbook] to show all your spells. \n";
			break;
		case "focus" :
			result = "Focus an opponent, revealing his HP. Your spells and maneuvers will now hit him instead. \n";
			break;
		}
	}
	return result;
	}

	/** 
	 * Try to go into one direction. If there is an exit, enter
	 * the new room, otherwise print an error message.
	 * @param command The command to be processed.
	 * @return a text to be written in the play routine.
	 */
	private String goRoom(Command command) 
	{

		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			return "Go where?";
		}

		String direction = command.getSecondWord();
		String result = "";
		Room nextRoom = null;
		nextRoom = currentRoom.getExit(direction);
		if(currentEncounter.getType().equals("staircase") && direction.equals("down")){
			result += "Eagerly trying to be carefull you slowly step down into the darkness of the\n";
			result += "staircase that reaches far into the depths below.\n";
			result += "Slowly pushing forward you notice a crack in the wall rising from beneath!\n";
			result += "As you instinctivly make a jump forward the stairway collapses behind you.\n";
			result += "You have no other choice than to move on from here.\n";
			System.out.println("saving progress...");
			System.out.println();
			settings.score += 1000*settings.player.getLvl();
			createDungeon();
			store();
			floorNumber ++;
			result += "You walk into " + currentRoom.getDescription() + "\n";
			result += detailedSorroundings();
		}
		else if (nextRoom == null) {
			return "There is no door!";
		}
		else if(currentEncounter.getType().equals("battle") ){
			if(SkillCheck.opposedAcrobaticsCheck(settings.player,currentEncounter.getPrimaryEnemy())){
				result += "You nimbely dodge the [" + currentEncounter.getPrimaryEnemy().getName() + "]s attack and leg it.\n";
				result += walkIntoNextRoom(nextRoom);
			}
			else{
				result += "You try to leave the room but get attacked by the [" + currentEncounter.getPrimaryEnemy().getName() + "].\n";
				result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
				result += printHpStatus(false);
			}
		}
		else if(currentEncounter.getType().equals("skirmish")){
			result += "You try to leave the room but get attacked by,\n" + 
					"the [" + currentEncounter.getPrimaryEnemy().getName() + "]" + currentEncounter.getNameOfAllAditionalEnemys(true) + ".\n";
			result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);
		}
		else {
			result += walkIntoNextRoom(nextRoom);

		}
		return result;
	}

	/** 
	 * "Quit" was entered. Check the rest of the command to see
	 * whether we really quit the game.
	 * @return null, if this command quits the game, something else to output otherwise.
	 */
	private String quit(Command command) 
	{
		if(command.hasSecondWord()) {
			return "Quit what?";
		}
		else {
			return null;  // signal that we want to quit
		}
	}

	/** 
	 * Try to rest. If there is an enemy in your room you get interrupted and attacked by him.
	 * If there is a warrior in the current room the player can rest for some hours.
	 * @return returns something to be printed out
	 */
	private String rest(){
		String result = "";
		if(settings.player.getCurrentHp()  == settings.player.getMaxHp() && spellsTodayUsed == 0){
			result = "You are already completly Healed and have not used any spells today!";
		}else if(currentEncounter.getType() == "battle"){
			result = "You try to rest but get attacked by the [" + currentEncounter.getPrimaryEnemy().getName() + "].\n";
			result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);
		}else if(currentEncounter.getType().equals("skirmish")){
			result += "You try to rest but get attacked by both,\n" + 
					"the [" + currentEncounter.getPrimaryEnemy().getName() + "]" + currentEncounter.getNameOfAllAditionalEnemys(true) + ".\n";
			result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);

		}else{
			if(currentEncounter.getType().equals("nsc")){
				if(currentEncounter.getNSC().getNscType().equals("warrior")){
					int healed = settings.player.getMaxHp() - settings.player.getCurrentHp();
					int restDuration = healed/settings.player.getLvl();
					if(restDuration <= 0)
						restDuration = 1;
					restDuration = restDuration * 8;
					result += "You feel relativley save in the presence of the warrior and you rest for a while.\n";
					result += "You rested for " + restDuration + "hours, healed " + healed + "HP and restored your spells per day.\n";
					spellsTodayUsed = 0;
					settings.player.setCurrentHp(settings.player.getMaxHp());
				}
				else{
					result += "You really dont feel like rest in this dark dungeon even \n" + "with someone like the " + currentEncounter.getNSC().getNscType() + " by your side.\n";
					result += "He really doens't seem like someone who could put up a good fight...\n";
				}
			}else{
				result += "You really dont want to rest in this dark dungeon all by\n" + "yourself, with no one guarding you while you are asleep.";
			}
		}
		return result;
	}

	/** 
	 * Try to fight something in the current room. If there is no enemy return an error message.
	 * @param command If the input is correct, hopefully the enemy to be fought against.
	 * @return a text to be written in the play routine.
	 */
	private String fight(Command command) 
	{
		String result = "";
		String enemyToAttack = "";
		if(command.hasSecondWord() == false){
			enemyToAttack = currentEncounter.getPrimaryEnemy().getName();
		}
		else{
			enemyToAttack = command.getSecondWord();
		}
		if (currentEncounter.getType().equals("battle") == false && currentEncounter.getType().equals("skirmish") == false) {
			result = "There is nothing alive to fight against!\n";
		}
		else if(enemyToAttack.equals(currentEncounter.getPrimaryEnemy().getName())){
			result = combat(false,false,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);
		}
		else if(currentEncounter.getType().equals("skirmish")){ 
			if(enemyToAttack.equals(currentEncounter.getPrimaryEnemy().getName())){
				result = combat(false,false,false,currentEncounter.getPrimaryEnemy());
			}
			else{
				for(CharacterSheet enemy : currentEncounter.additionalEnemys)
					if(enemyToAttack.equals(enemy.getName())){
						result = combat(false,false,false,enemy);
					}


			}
			result += printHpStatus(false);
		}
		else{
			result = "There is no[" + enemyToAttack + "].\n";
		}

		return result;
	}

	/** 
	 * Try to perform a combat maneuver against the currently focused enemy. 
	 * If there is no enemy return an error message.
	 * @param command If the input is correct, hopefully the enemy to be fought against.
	 * @return a text to be written in the play routine.
	 */
	private String maneuver(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to fight...
			result = "What maneuver do you want to execute?";
		}
		else{
			if(command.hasSecondWord()){
				String combatManeuver = command.getSecondWord();
				if (currentEncounter.getType().equals("battle") == false && currentEncounter.getType().equals("skirmish") == false) {
					result = "There is nothing to make a maneuver against!\n";
				}else{
					switch(combatManeuver){
					case "feint" :
						result += "You try to make a feint of hitting the opponents left leg.\n";
						if(SkillCheck.bluffCheck(settings.player, currentEncounter.getPrimaryEnemy().getWisMod() + 4 + currentEncounter.getPrimaryEnemy().getBab() + opponentsAwareness)){
							result += combat(true,false,false,currentEncounter.getPrimaryEnemy());
							result += "The [" + currentEncounter.getPrimaryEnemy().getName() + "] falls for your feint!\n";
							result += combat(false,false,true,currentEncounter.getPrimaryEnemy());
							opponentsAwareness += 4;
						}else{
							result += "But your opponent doesn't fall for it and catches you off guard instead.\n";
							opponentsAwareness += 2;
							result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
						}
						result += printHpStatus(false);
						break;
					}
				}

			}

		}
		return result;

	}


	/** 
	 * Searches for items in the room. Informations about the rooms items are
	 * being recorded in the encounter of the room.
	 * @return returns something to be printed out
	 */
	private String search(Command command) 
	{
		String result = "";
		if(command.hasSecondWord()){
			String winCommand = command.getSecondWord();
			if (winCommand.equals("destroy")){
				//some debugging stuff
				result += settings.player.earnExp(8000, settings.levelRanges);
				result += settings.player.earnExp(16000, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				result += settings.player.earnExp(32, settings.levelRanges);
				settings.player.equipment.addMoneyToWallet(30000);
			}
		}
		if (currentEncounter.getType().equals("treasure")) {
			result = "You search the treasure chest and find " + currentEncounter.getReward().getEquipmentDescription() + "\n";
			settings.player.equipment.addEquipment(currentEncounter.getReward());
			currentEncounter.setLootToGarbage();
		}else if (currentEncounter.getType().equals("battle")){
			result = "You try to search the room but get attacked by the [" + currentEncounter.getPrimaryEnemy().getName() + "].\n";
			result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);

		}else if (currentEncounter.getType().equals("skrimish")){
			result = "You try to search the room but get attacked by the [" + currentEncounter.getPrimaryEnemy().getName() + "] and\n" + 
					" the his group.\n";
			result += combat(true,true,false,currentEncounter.getPrimaryEnemy());
			result += printHpStatus(false);

		}else{
			String searchResult = giveRandomSearchAnswer();
			if(Roll.dice(20)+settings.player.getWisMod() <= 20){
				result = searchResult + " but your effort is futile and you don't find anything.\n";
			}else{

				result = searchResult + " and you find\n" + currentEncounter.getReward().getEquipmentDescription() +
						" and put it in your inventory\n";
				settings.player.equipment.addEquipment(currentEncounter.getReward());
				currentEncounter.setLootToGarbage();
			}
		}
		return result; 
	}

	/** 
	 * Try to equip an item from the players inventory.
	 * @return returns something to be printed out
	 */ 
	private String equip(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Equip what?";
		}else{

			String itemToEquip = command.getSecondWord();
			if(settings.player.equipment.containsEquipment(itemToEquip)){
				if(settings.player.equipment.getEquipment(itemToEquip).getEquipmentType().equals("armor")){
					settings.player.equipment.equipArmor(settings.player.equipment.getEquipment(itemToEquip));
					result = "You equip " + settings.player.equipment.getEquipedArmor().getEquipmentDescription() + "\n";
				}
				else if(settings.player.equipment.getEquipment(itemToEquip).getEquipmentType().equals("weapon")){
					settings.player.equipment.equipWeapon(settings.player.equipment.getEquipment(itemToEquip));
					result = "You equip " + settings.player.equipment.getEquipedWeapon().getEquipmentDescription() + "\n";
				}
				else{
					result = "You can not equip [" + itemToEquip + "].";
				}
			}
			else{
				result = "You dont have such a thing in your inventory.";
			}
		}
		return result;

	}

	/** 
	 * Try to discard an item from the players inventory.
	 * @return returns something to be printed out
	 */
	private String discard(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Discard what?";
		}
		else{
			String itemToDiscard = command.getSecondWord();
			if(settings.player.equipment.containsEquipment(itemToDiscard)){
				String description = settings.player.equipment.getEquipment(itemToDiscard).getEquipmentDescription();
				settings.player.equipment.discardItem(settings.player.equipment.getEquipment(itemToDiscard));
				result += "You discarded " + description + "\n";
			}else{
				result += "You dont have such an item.\n";
			}
		}
		return result;

	}

	private String sell(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to sell...
			result = "Sell what?";
		}
		else if(currentEncounter.getType().equals("nsc")){
			if(currentEncounter.getNSC().getNscType().equals("merchant")){
				String itemToSell = command.getSecondWord();
				if(settings.player.equipment.containsEquipment(itemToSell)){
					String description = settings.player.equipment.getEquipment(itemToSell).getEquipmentDescription();
					int refund = settings.player.equipment.sellItem(settings.player.equipment.getEquipment(itemToSell));
					refund = refund/2;
					result += "You sold " + description + " for " + refund + " silver coins\n";
					settings.player.getInventory().addMoneyToWallet(refund);

				}else{
					result += "You dont have such an item.\n";
				}
			}else{
				result += "The warrior does not seem like he wants to buy anything from you.\n";
			}
		}else{
			result += "There is no one to sell something to.\n";
		}
		return result;

	}

	/** 
	 * Shows the contains of the players or a shops inventory or the players status.
	 * @return returns something to be printed out
	 */
	private String show(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to show...
			result = "Show what?";
		}
		else{

			String whatToShow = command.getSecondWord();
			if(whatToShow.equals("me")||whatToShow.equals(settings.player.getName())){
				result = settings.player.getStatus();
			}
			else if(whatToShow.equals("spellbook")){
				result = settings.player.equipment.getSpellBook();
			}
			else if(whatToShow.equals("inventory")){
				result = settings.player.equipment.getContents(false);
			}
			else if(whatToShow.equals("map")){
				result = "You are on Floor Number" + "";
				result += "Score: " + "";
			}
			else if(whatToShow.equals("shop") && currentEncounter.getType().equals("nsc")){
				if(currentEncounter.getNSC().getNscType().equals("merchant"))
					result = currentEncounter.getNSC().getNscSheet().equipment.getContents(true) + "\n";
				else
					result = "The " + currentEncounter.getNSC().getNscType() + " doesn't exactly look like he wants to sell you stuff.\n" ;
			}
			else{
				result = "I don't know what you mean...";
			}
		}
		return result;

	}

	/** 
	 * Looks around the room and gives the player all available information about the room.
	 * It is planed to enhance this method to make it possible to look into another room.
	 * If there is a trap in this room the player may also get informtations abput the trap.
	 * @return returns something to be printed out
	 */
	private String look(){
		String result = "";
		result += "You look around the Room.\n" + "You see " + currentRoom.getDescription() + "\n";
		if(currentRoom.getRoomsEncounter().getType().equals("trap")){
			if(Roll.dice(20) + settings.player.getWisMod() >= 25){

				//since traps are not yet implemented there is nothing do do here...

				//result += "At closer Look, you notice " + currentEncounter.getCurrentTrap().getDescription() + "\n";
			}	
		}
		else{
			result += detailedSorroundings();
		}
		return result;
	}

	/** 
	 * Say something to an npc or enemy.
	 * @return returns something to be printed out
	 */
	private String say(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to say...
			result = "Say what?";
		}
		else{
			String whatToSay = command.getSecondWord();
			result += settings.player.getName() + ":  " + whatToSay + ".\n";
			if(currentEncounter.getType().equals("nsc"))
				result += currentEncounter.getNSC().getNscSheet().getName() + ":  " + currentEncounter.getNSC().getAnswer(whatToSay) + "\n";
		}
		return result;

	}

	/** 
	 * Try to buy something from the current rooms merchant. If there is no merchant return an error message.
	 * @return returns something to be printed out
	 */
	private String buy(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Buy what?";
		}
		else{
			String whatToBuy = command.getSecondWord();
			if(currentEncounter.getType().equals("nsc")){
				if(currentEncounter.getNSC().getNscType().equals("merchant")){
					if(currentEncounter.getNSC().getNscSheet().equipment.containsEquipment(whatToBuy)){
						Item itemToBuy = currentEncounter.getNSC().getNscSheet().equipment.getEquipment(whatToBuy);
						if(itemToBuy.getPrize() <=
								settings.player.equipment.getWallet()){
							settings.player.equipment.addEquipment(itemToBuy);
							result += "You give "+ currentEncounter.getNSC().getNscSheet().getName() + " " + itemToBuy.getPrize() +" silver coins.\n";
							result += "You bougth " + itemToBuy.getEquipmentDescription() + "\n";

						}else{
							result += "You dont have enough money in your wallet to buy " + itemToBuy.getEquipmentDescription() + "\n";
						}
					}else{
						result += "The merchant doesn't sell " + whatToBuy + "s.\n";
					}
				}else{
					result += currentEncounter.getNSC().getAnswer("?");
				}
			}
			else if(currentEncounter.getType().equals("battle") || currentEncounter.getType().equals("skirmish"))
				result += "The [" + currentEncounter.getPrimaryEnemy().getName() + "] doesn't seem like it wants to sell you a " + whatToBuy + ".\n";
			else{
				result += "There is no one to buy stuff from!";

			}
		}
		return result;

	}

	/**
	 * Changes the focused enemy to the one specified in the commands second word.
	 * @param command if this command does not include the enemy to be focused, return an error message. 
	 * @return something to be printed out in the main play routine.
	 */
	private String focus(Command command){
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Focus what?";
		}
		else if(currentEncounter.getType().equals("battle") && command.getSecondWord().equals(currentEncounter.getPrimaryEnemy().getName())){
			result = "You focus the [" + command.getSecondWord() + "].\n";
			result += printHpStatus(true);
		}
		else if(currentEncounter.getType().equals("skirmish") && command.getSecondWord().equals(currentEncounter.getPrimaryEnemy().getName())){
			result = "You focus the [" + command.getSecondWord() + "].\n";
			result += printHpStatus(true);
		}
		else if(currentEncounter.getType().equals("skirmish")){
			CharacterSheet enemyToFocus = currentEncounter.getPrimaryEnemy();
			boolean found = false;
			for(CharacterSheet additionalEnemy : currentEncounter.additionalEnemys){
				if(command.getSecondWord().equals(additionalEnemy.getName())){
					enemyToFocus = additionalEnemy;
					found = true;
				}
			}
			if(found == false){
				result = "There is no[" + command.getSecondWord() + "].\n";
			}else{
				currentEncounter.changePrimaryEnemy(enemyToFocus);
				result = "You focus the [" +  command.getSecondWord() + "].\n";
				result += printHpStatus(true);
			}
		}
		else{
			result = "There is no[" + command.getSecondWord() + "].\n";
		}

		return result;

	}

	/**
	 * Tries to drink a potion from the players inventory. If the player doesn't have the in the command
	 * specified potion, return an error message.
	 * @return something to be printed out in the main play routine
	 */
	private String drink(Command command){
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Drink what?";
		}
		else{
			String potionToDrink = command.getSecondWord();
			if(settings.player.equipment.containsEquipment(potionToDrink)){
				Item potion = settings.player.equipment.getEquipment(potionToDrink);
				String description = potion.getEquipmentDescription();
				result += "You drink " + description + ".\n";
				if(potion.getPrefix().equals("heal")){
					int heal = 0;
					for(int i = 0; i < potion.amountOfDiceToBeRolled(); i++){
						heal += Roll.dice(potion.getBaseStat());
					}
					settings.player.heal(heal);
					result += "The potion healed " + heal +" HP.\n";
					if(Roll.dice(100) < potion.getSecondaryStat()){
						settings.player.equipment.discardItem(potion);
					}
				}

			}else{
				result += "You dont have such an item.\n";
			}
		}
		return result;

	}

	/** 
	 * Try cast a spell from the players spellbook. If the player doesn't have the in the command
	 * specified spell, return an error message.
	 * @return returns something to be printed out
	 */ 
	private String cast(Command command) 
	{
		String result = "";
		if(!command.hasSecondWord()) {
			// if there is no second word, we don't know what to equip...
			result = "Cast what?";
		}else{
			boolean enemyExists = false;
			if(currentEncounter.getType().equals("battle")){
				enemyExists = true;
			}
			if(currentEncounter.getType().equals("skirmish")){
				enemyExists = true;
			}
			String spellToCast = command.getSecondWord();
			if(settings.player.equipment.containsEquipment(spellToCast)){
				Item spell = settings.player.equipment.getEquipment(spellToCast);
				if(spell.getEquipmentType().equals("spell") &&
						settings.player.getRawIn() >= spell.getSecondaryStat() &&
						settings.player.getLvl() > spellsTodayUsed - settings.player.getInMod()){
					result = "You cast [" + spell.getEquipmentName() + "].\n";
					spellsTodayUsed ++;
					int damageDealt = 0;
					switch(spell.getPrefix()){
					case "restoration" :
						result += spell.getEquipmentDescription() + ".\n";
						int restoredHP = Roll.dice(spell.getBaseStat(),spell.getPrimaryStat());
						settings.player.heal(restoredHP);
						result += "You restored " + restoredHP + "HP.\n";
						break;
					case "destruction" :
						if(enemyExists){
							result += spell.getEquipmentDescription() + ".\n";
							damageDealt = Roll.dice(spell.getBaseStat(),spell.getPrimaryStat());
							result += dealSpellDamage(damageDealt,false);
							if(currentEncounter.getPrimaryEnemy().getCurrentHp() > 0)
								result += combat(true,false,false,currentEncounter.getPrimaryEnemy());
							result += printHpStatus(false);
						}
						else{
							result += "There is no enemy to hit.";
						}
						break;
					case "summoning" :
						if(spell.getSpellTargetingType().equals("touch")){
							if(enemyExists){
								result += spell.getEquipmentDescription() + ".\n";
								damageDealt = Roll.dice(spell.getBaseStat(),spell.getPrimaryStat());
								result += dealSpellDamage(damageDealt,true);
								if(currentEncounter.getPrimaryEnemy().getCurrentHp() > 0)
									result += combat(true,false,false,currentEncounter.getPrimaryEnemy());
								result += printHpStatus(false);
							}
							else{
								result += "There is no enemy to hit.";
							}

						}
						break;
					}
				}
				else{
					result = "You can not cast [" + spellToCast + "] since you dont have any\n" + "spells per day left or are not intelligent enough.";
				}
			}
			else{
				result = "You dont have such a spell in your inventory.";
			}
		}
		return result;

	}


	/**
	 * The main method for combats happening during the play routine. Closely connected to the
	 * CombatEngine Class. Resolves a fight, updating the participants in the current room.
	 * @param surpriseRound if the player can't reacted to an enemy's attack, he can not attack
	 * during the following combat round.
	 * @param playerFlatFooded if the player is flat footed, he loses his dexterity bonus 
	 * during the following combat round.
	 * @param enemyFlatFooded if the enemy is flat footed, he loses his dexterity bonus 
	 * during the following combat round.
	 * @param enemyToAttack the enemy to be attacked by the player, if the enemy is not already focused,
	 * he now is.
	 * @return the result of this combat round
	 */
	private String combat(boolean surpriseRound, boolean playerFlatFooted,  boolean enemyFlatFooted, CharacterSheet enemyToAttack) 
	{
		//resolves a combat order...
		String result = "";
		if(enemyToAttack.equals(currentEncounter.getPrimaryEnemy()) == false){
			currentEncounter.changePrimaryEnemy(enemyToAttack);
		}
		if(currentEncounter.getType().equals("battle")){
			CombatEngine currentCombat = new CombatEngine(settings.player, currentEncounter.getPrimaryEnemy(), settings.getlevelRanges());
			if(surpriseRound==false && playerFlatFooted == false && enemyFlatFooted == false){
				result = "You fiercely strike out to hit the [" + currentCombat.getDefenderName() + "] with all your might!\n";
			}
			if(enemyFlatFooted == true){
				result += "You catch the [" + currentCombat.getDefenderName() + "] off guard!\n";
			}
			if(playerFlatFooted == true){
				result += "The [" + currentCombat.getDefenderName() + "] caught you off guard!\n";
			}
			if(surpriseRound == true){
				result += currentCombat.surpriseRound(true,playerFlatFooted,enemyFlatFooted);
			}
			else{
				result += currentCombat.battleRound(playerFlatFooted,enemyFlatFooted);
			}
			settings.replaceSheet(currentCombat.getAttacker());
			currentEncounter.replacePrimaryEnemy(currentCombat.getDefender());
			if(settings.player.getCurrentHp() <= 0){
				//the player is dead
				playerDead = true;
			}else if(currentEncounter.getPrimaryEnemy().getCurrentHp() <= 0){
				result += "The [" + currentCombat.getDefender().getName() + "] dropped something!\n" +  
						"You pick up " + currentEncounter.getReward().getEquipmentDescription() + " and a wallet"
						+ " containing " + currentEncounter.getPrimaryEnemy().equipment.getWallet() +" silver coins.\n";
				settings.player.equipment.addEquipment(currentEncounter.getReward());
				int xpReward = currentEncounter.getPrimaryEnemy().getExpScore();
				result += settings.player.earnExp(xpReward,settings.levelRanges);
				settings.player.equipment.addMoneyToWallet(currentEncounter.getPrimaryEnemy().equipment.getWallet());
				currentEncounter.setLootToGarbage();
				settings.score += xpReward;
				if(currentEncounter.getType().equals("battle"))
					currentEncounter.changeTypeToNothing();
			}
		}
		if(currentEncounter.getType().equals("skirmish")){
			CombatEngine currentCombat = new CombatEngine(settings.player, currentEncounter.getPrimaryEnemy(), currentEncounter.additionalEnemys, settings.getlevelRanges());
			if(surpriseRound==false && playerFlatFooted == false && enemyFlatFooted == false){
				result = "You fiercely strike out to hit the [" + currentCombat.getDefenderName() + "] with all your might!\n";
			}
			if(enemyFlatFooted == true){
				result += "You catch the [" + currentCombat.getDefenderName() + "] off guard!\n";
			}
			if(playerFlatFooted == true){
				result += "The [" + currentCombat.getDefenderName() + "] caught you off guard!\n";
			}
			result += "The other participants catch you off guard!\n";

			if(surpriseRound == true){
				result += currentCombat.surpriseRoundTwoOnOne(true,playerFlatFooted,enemyFlatFooted);
			}
			else{
				result += currentCombat.battleRoundTwoOnOne(playerFlatFooted,enemyFlatFooted);
			}
			settings.replaceSheet(currentCombat.getAttacker());
			currentEncounter.replacePrimaryEnemy(currentCombat.getDefender());
			if(settings.player.getCurrentHp() <= 0){
				//the player is dead
				playerDead = true;
			}else if(currentEncounter.getPrimaryEnemy().getCurrentHp() <= 0){
				result += "The [" + currentEncounter.getPrimaryEnemy().getName() + "] dropped something!\n" +  
						"You pick up " + currentEncounter.getReward().getEquipmentDescription() + " and a wallet"
						+ " containing " + currentEncounter.getPrimaryEnemy().equipment.getWallet() +" silver coins.\n";
				settings.player.equipment.addEquipment(currentEncounter.getReward());
				int xpReward = currentEncounter.getPrimaryEnemy().getExpScore();
				result += settings.player.earnExp(xpReward,settings.levelRanges);
				settings.player.equipment.addMoneyToWallet(currentEncounter.getPrimaryEnemy().equipment.getWallet());
				currentEncounter.setLootToGarbage();
				settings.score += xpReward;
				currentEncounter.changePrimaryEnemy();
				boolean allAdditionalEnemysDead = true;
				for(CharacterSheet enemy : currentEncounter.additionalEnemys){
					if(enemy.getCurrentHp() > 0){
						allAdditionalEnemysDead = false;
					}
				}
				if(allAdditionalEnemysDead == true){
					currentEncounter.changeTypeToBattle();
				}
			}
		}
		return result;
	}

	/**
	 * Damage dealt by a spell doesn't automatically cause a reaction by the enemy and uses a different
	 * system to determine a hit. If not specified in touch attack spell damage is automatically dealt.
	 * @param damageDealt the damage to be dealt to the currently focused enemy
	 * @param touchAttack if the attack doesn't hit automatically, the player must succeed a touch attempt
	 * to hit the target with his spell. 
	 * @return the result of the spells attempt to deal damage.
	 */
	private String dealSpellDamage(int damageDealt,boolean touchAttack){
		//resolves a combat order...
		String result = "";
		int touchAc = currentEncounter.getPrimaryEnemy().getAc() - 
				currentEncounter.getPrimaryEnemy().getInventory().getEquipedArmor().getArmorRating();
		int hit = Roll.dice(20) + settings.player.getBab() + settings.player.getDexMod();
		if(hit < touchAc && touchAttack){
			result += "Your spell missed the [" + currentEncounter.getPrimaryEnemy().getName() + "].\n";
		}
		else{
			CombatEngine currentCombat = new CombatEngine(settings.player, currentEncounter.getPrimaryEnemy(), settings.getlevelRanges());
			currentCombat.spellDamage(damageDealt);
			result += "You dealt " + damageDealt + " points of damage.\n";
			settings.replaceSheet(currentCombat.getAttacker());
			currentEncounter.replacePrimaryEnemy(currentCombat.getDefender());
			if(currentEncounter.getPrimaryEnemy().getCurrentHp() <= 0){
				result += "You killed the [" + currentCombat.getDefender().getName() + "].\n";
				result += "The [" + currentCombat.getDefender().getName() + "] dropped something!\n" +  
						"You pick up " + currentEncounter.getReward().getEquipmentDescription() + " and a wallet"
						+ " containing " + currentEncounter.getPrimaryEnemy().equipment.getWallet() +" silver coins.\n";
				settings.player.equipment.addEquipment(currentEncounter.getReward());
				int xpReward = currentEncounter.getPrimaryEnemy().getExpScore();
				result += settings.player.earnExp(xpReward,settings.levelRanges);
				settings.player.equipment.addMoneyToWallet(currentEncounter.getPrimaryEnemy().equipment.getWallet());
				currentEncounter.setLootToGarbage();
				settings.score += xpReward;
				if(currentEncounter.getType().equals("skirmish"))
					currentEncounter.changeTypeToBattle();
				else if(currentEncounter.getType().equals("battle"))
					currentEncounter.changeTypeToNothing();
			}
		}
		return result;
	}

	private String detailedSorroundings(){

		//just getting some data about the location we are in, note that this can also be used
		//even when the player isn't moving
		String result = "";
		if(currentEncounter.getType().equals("battle")){
			if(currentEncounter.getPrimaryEnemy().getCurrentHp() > 0){
				result += currentEncounter.getEncounterDescription() + "\n";
				result += "You can not reach any exits since the [" + currentEncounter.getPrimaryEnemy().getName() +"] is blocking the way.\n";
				result += "You can barely make out an exit in the \n";
				result += currentRoom.getExits() + "\n";
			}
			else{
				result += "The corpse of a dead [" + currentEncounter.getPrimaryEnemy().getName() + "] is lying before you.\n";
				result += "You can see an Exit in the \n";
				result += currentRoom.getExits() + "\n";
			}

		}
		else if(currentEncounter.getType().equals("skirmish")){
			result += currentEncounter.getEncounterDescription() + "\n";
			result += "The exits are guarded by the [" + currentEncounter.getPrimaryEnemy().getName() +"].\n";
			result += "You can barely make out an exit in the \n";
			result += currentRoom.getExits() + "\n";
		}
		else{
			result += currentEncounter.getEncounterDescription() + "\n";
			result += "You can see an exit in the \n";
			result += currentRoom.getExits() + "\n";
		}
		return result;
	}

	private String walkIntoNextRoom(Room nextRoom){
		String result = "";
		currentRoom.setRoomsEncounter(currentEncounter);
		dungeon.roomsInDungeon.set(currentRoomIndexInDungeonMap, currentRoom);
		currentRoom = nextRoom;
		currentEncounter = currentRoom.getRoomsEncounter();
		result += "You walk into " + currentRoom.getDescription() + "\n";
		result += detailedSorroundings();
		opponentsAwareness = 0;
		settings.score += 100;
		return result;
	}

	private String printHpStatus(boolean onlyPrimaryEnemy){

		String result = "";
		if(onlyPrimaryEnemy == false){
			result += "[" + settings.player.getName() + "]s HP: " + settings.player.getCurrentHp() + "/" + settings.player.getMaxHp()  +
					"      [" + currentEncounter.getPrimaryEnemy().getName() + "]s HP: " + currentEncounter.getPrimaryEnemy().getCurrentHp()
					+ "/" + currentEncounter.getPrimaryEnemy().getMaxHp();
			if(currentEncounter.getType().equals("skirmish"))
				for(CharacterSheet additinalEnemy : currentEncounter.additionalEnemys){
					result += "      [" + additinalEnemy.getName() + "]s HP: " + additinalEnemy.getCurrentHp()
							+ "/" + additinalEnemy.getMaxHp();
				}
		}
		else{
			result += "         [" + currentEncounter.getPrimaryEnemy().getName() + "]s HP: " + currentEncounter.getPrimaryEnemy().getCurrentHp();
		}
		result += "\n";
		return result;
	}

	private String giveRandomSearchAnswer(){
		//give a random answer for the search method when used in an empty room...
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("You search a dead adventurers corpse");
		answers.add("You look into a cupboard in the corner");
		answers.add("You let your gaze wander around the room \n"+"and a suspicious looking pile of trash meets your eye.\n"+"You rummage through it");
		return answers.get(Roll.dice(3)-1);
	}

	public static void main(String[] args){
		Game game = new Game();
		game.play();
	}
	
	public boolean store() {
		try {
			FileOutputStream fos = new FileOutputStream("save.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(currentRoom);
			oos.writeObject(currentEncounter);
			oos.writeObject(currentRoomIndexInDungeonMap);
			oos.writeObject(opponentsAwareness);
			oos.writeObject(spellsTodayUsed);
			oos.writeObject(floorNumber);
			oos.writeObject(settings);
			oos.writeObject(dungeon);
			oos.writeObject(storyLine);
			oos.close();
			fos.close();
		} catch (IOException ex) {
			return false;
		}
		return true;
	}
	
	public boolean load() {
		try {
			FileInputStream fis = new FileInputStream("save.sav");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.currentRoom = (Room)ois.readObject();
			this.currentEncounter = (Encounter)ois.readObject();
			this.currentRoomIndexInDungeonMap = (Integer)ois.readObject();
			this.opponentsAwareness = (Integer)ois.readObject();
			this.spellsTodayUsed = (Integer)ois.readObject();
			this.floorNumber = (Integer)ois.readObject();
			this.settings = (GameData)ois.readObject();
			this.dungeon = (DungeonFloor)ois.readObject();
			this.storyLine = (StoryLine)ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
}