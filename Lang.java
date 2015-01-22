import java.util.ArrayList;

public class Lang {

	/**
	 * This is the array where all text is stored, 
	 */
	private static ArrayList<String> allText = new ArrayList<String>();

	public static void setLanguage(String language){
		if(language == "deutsch"){
			allText.add("Du bist in");										//0
			allText.add("Du kannst keine Ausgänge erreichen, da ein/e");	//1
			allText.add("den Weg versperrt");								//2
			allText.add("Du siehst gerade so einen Ausgang im");
			allText.add("Du siehst einen Ausgang im");
			allText.add("SPIEL VORBEI");									//5
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("einfach");
			allText.add("mittel");											//10
			allText.add("schwer");											//11
			allText.add("");		//12
			allText.add("");		//13
			allText.add("");		//14
			allText.add("Krieger");		//15
			allText.add("Magier");		//16
			allText.add("Schurke");
			allText.add("Kleriker");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");								
		}
		else if(language == "english")
			allText.add("You are in");										//0
			allText.add("You can not reach any Exits since the");			//1
			allText.add("is blocking the way");								//2
			allText.add("You can barely make out an Exit in the");			//3
			allText.add("You can see an Exit in the");						//4
			allText.add("GAME OVER");										//5
			allText.add("Score");	
			allText.add("Thank you for playing.  Good bye");
			allText.add("Please Choose your difficulty!");
			allText.add("easy");
			allText.add("normal");											//10
			allText.add("hard");
			allText.add("What is your Name?");
			allText.add("The Nameless Hero");
			allText.add("As what class do you want to play?");
			allText.add("warrior");											//15
			allText.add("mage");
			allText.add("rogue");
			allText.add("clerik");
			allText.add("is not an availabele class");
			allText.add("Please select one of the available classes!");
			allText.add("Reroll ability Scores?");
			allText.add("yes");
			allText.add("no");
			allText.add("Welcome to TAGWORLD, your own private little adventure!");
			allText.add("Feel free to type [help] if you dont know what to do.");
			allText.add("Oh, and remember parameter and command words from");
			allText.add("the text are always written in [brackets]. Combine ");
			allText.add("them and see what happens!");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
			allText.add("");
	}
	public static String get(int index){
		return allText.get(index);
	}
}
