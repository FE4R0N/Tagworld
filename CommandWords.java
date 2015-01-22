import java.util.ArrayList;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class CommandWords
{
	
    // a constant array that holds all valid command words
    private ArrayList<String> validCommands =  new ArrayList<>();

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
    	validCommands.add("go"); 
    	validCommands.add("quit"); 
    	validCommands.add("help"); 
    	validCommands.add("fight"); 
    	validCommands.add("run"); 
    	validCommands.add("search");
    	validCommands.add("equip"); 
    	validCommands.add("show"); 
    	validCommands.add("rest" );
    	validCommands.add("discard");
    	validCommands.add("look");
    	validCommands.add("say");
    	validCommands.add("buy");
    	validCommands.add("drink");
    	validCommands.add("perform");
    	validCommands.add("cast");
    	validCommands.add("focus");
    	validCommands.add("sell");
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(String thisString : validCommands) {
            if(thisString.equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
}
