import java.io.Serializable;


public class StoryLine implements Serializable{
	//private String name;
	//private int positionInStory;
	private static final long serialVersionUID = -5966618586666474164L;

	public StoryLine(String playerName){
		//name = playerName;
		//positionInStory = 0;
		printOpeningIntroduction();
	}

	private void printOpeningIntroduction(){
		System.out.println("You arrive at the entrance of the underground labyrinth as");
		System.out.println("the sun is starting to set.");
		System.out.println();
		printDots();
		System.out.println("The description of the place the old storyteller gave you");
		System.out.println("has been fairly accurate until now and you haven't had any");
		System.out.println("problems venturing here.");
		System.out.println();
		printDots();
		System.out.println("He even gave you a map that should lead you right through the");
		System.out.println("dungeon ahead of you and, with a bit of luck, to the legendary");
		System.out.println("treasure rumored to be burried here.");
		System.out.println();
		printDots();
		System.out.println("You take a deep breath and begin your descent.");
		System.out.println();
		printDots();
		System.out.println("It doesn't take long till you stand before a massive door,");
		System.out.println("standing wiedly open, inviting you to walk in.");
		System.out.println();
		printDots();
		System.out.println("As you make your way through the door a stream of air emerges");
		System.out.println("from inside pulling at your clothes.");
		System.out.println();
		printDots();
		System.out.println("You clinge to your map not letting loose of it and the wind");
		System.out.println("harmlessly passes by, leaving e stench of rotten flesh behind.");
		System.out.println();
		printDots();
		System.out.println("Slowly making progress forward, you instantly freeze as the massive");
		System.out.println("door slam shuts behind you!");
		System.out.println();
		printDots();
		System.out.println("Now trapped, you have no other choice then to move forward...");
		System.out.println();
		printDots();
	}

	private void printDots(){
		for(int i = 0; i <= 40; i++){
			DramaticalPause.miniPause();
			System.out.print(". ");
		}
		System.out.println("}->");
		System.out.println();
	}
}
