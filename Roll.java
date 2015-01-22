import java.util.*;

/**
 * A basic dice rolling class.
 * 
 * @author Philipp Wolf 
 * @version 1.0
 */
public class Roll
{
	static Random rand = new Random();

    /**
     * Roll a dice. 
     * 
     * @param  dice specifies what kind of dice should be rolled. You can only roll d100 d20, d12, d10, d8, d6, d4, d3 and d2s.
     * @return     returns the result of the thrown dice.
     */
    public static int dice(int dice)
    {
        int result = 0;
        if(dice == 100)
            result = rand.nextInt(dice) + 1;
        if(dice == 20)
            result = rand.nextInt(dice) + 1;
        if(dice == 12)
            result = rand.nextInt(dice) + 1;
        if(dice == 10)
            result = rand.nextInt(dice) + 1;
        if(dice == 8)
            result = rand.nextInt(dice) + 1;
        if(dice == 6)
            result = rand.nextInt(dice) + 1;
        if(dice == 4)
            result = rand.nextInt(dice) + 1;
        if(dice == 3)
            result = rand.nextInt(dice) + 1;
        if(dice == 2)
            result = rand.nextInt(dice) + 1;
        return result;
        
    }
    
    /**
     * Rolling many dice. 
     * 
     * @param  dice specifies what kind of dice should be rolled. You can only roll d100 d20, d12, d10, d8, d6, d4, d3 and d2s.
     * @param	many specifies how many dice should be thrown.
     * @return     returns the result of all the thrown dice added together.
     */
    public static int dice(int dice, int many)
    {
        int finalResult = 0;
        if(many != 0){
	        for(int i = 1; i <= many; i++){
	            
	            finalResult = finalResult + dice(dice);
	            }
        }
        return finalResult;
    }
    
    /**
     * Rolling exactly 4 or 2 dice, removing the worst result. 
     * 
     * @param dice specifies what kind of dice should be rolled. You can only roll d100 d20, d12, d10, d8, d6, d4, d3 and d2s.
     * @param forCharacterSheet when used for rolling the characterSheets ability scores, 4 dice are rolled otherwise 2 dice are rolled
     * @return returns the result of all the thrown dice added together minus the worst result.
     */
    public static int dice(int dice, boolean forCharacterSheet)
    {
    	int result = 0;
    	if(forCharacterSheet==true){
	        int dice1 = dice(dice);
	        int dice2 = dice(dice);
	        int dice3 = dice(dice);
	        int dice4 = dice(dice);
	        if(dice1<=dice2 && dice1<=dice3 && dice1<=dice4)
	        	dice1 = 0;
	        else if(dice2<=dice1 && dice2<=dice3 && dice2<=dice4)
	        	dice2 = 0;
	        else if(dice3<=dice1 && dice3<=dice2 && dice3<=dice4)
	        	dice3 = 0;
	        else
	        	dice4 = 0;
	        result = dice1 + dice2 + dice3 + dice4;
    	}else
    	if(forCharacterSheet==false){
	        int dice1 = dice(dice);
	        int dice2 = dice(dice);
	        if(dice1<=dice2)
	        	dice1 = 0;
	        else
	        	dice2 = 0;
	        result = dice1 + dice2;
    	}
    	return result;
    }
}
