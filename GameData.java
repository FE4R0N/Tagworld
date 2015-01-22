import java.io.Serializable;

public class GameData implements Serializable{
	private static final long serialVersionUID = -5966618586666474164L;
    public int[] levelRanges;
    public CharacterSheet player;
	public int score;
    
    public GameData(int difficulty,String playerName,String playersClass){
    	score = 0;
    	levelRanges = new int[21];
    	player = new CharacterSheet(playerName,1,10,playersClass);
    	if(difficulty == 0){
    		setLevelProgressionToFast();
    	}
    	if(difficulty == 1){
    		setLevelProgressionToNormal();
    	}
    	if(difficulty == 2){
    		setLevelProgressionToSlow();
    	}
    }
    public void replaceSheet(CharacterSheet replaceSheet){
    	this.player = replaceSheet;
    }
    private void setLevelProgressionToFast(){
    	this.levelRanges[0] = 0;
    	this.levelRanges[1] = 1000;
    	this.levelRanges[2] = 3300;
    	this.levelRanges[3] = 6000;
    	this.levelRanges[4] = 10000;
    	this.levelRanges[5] = 15000;
    	this.levelRanges[6] = 23000;
    	this.levelRanges[7] = 34000;
    	this.levelRanges[8] = 50000;
    	this.levelRanges[9] = 71000;
    	this.levelRanges[10] = 105000;
    	this.levelRanges[12] = 145000;
    	this.levelRanges[13] = 210000;
    	for(int i=14; i<=20; i++){
    		levelRanges[i] = levelRanges[i-1] + levelRanges[i-4];
    	}
    }
    private void setLevelProgressionToNormal(){
    	this.levelRanges[0] = 0;
    	this.levelRanges[1] = 2000;
    	this.levelRanges[2] = 5000;
    	this.levelRanges[3] = 9000;
    	this.levelRanges[4] = 15000;
    	this.levelRanges[5] = 23000;
    	this.levelRanges[6] = 35000;
    	this.levelRanges[7] = 51000;
    	this.levelRanges[8] = 75000;
    	this.levelRanges[9] = 105000;
    	this.levelRanges[10] = 155000;
    	this.levelRanges[12] = 220000;
    	this.levelRanges[13] = 315000;
    	for(int i=14; i<=20; i++){
    		levelRanges[i] = levelRanges[i-1] + levelRanges[i-3] - levelRanges[i-8];
    	}
    }
    private void setLevelProgressionToSlow(){
    	this.levelRanges[0] = 0;
    	this.levelRanges[1] = 3000;
    	this.levelRanges[2] = 7500;
    	this.levelRanges[3] = 14000;
    	this.levelRanges[4] = 23000;
    	this.levelRanges[5] = 35000;
    	this.levelRanges[6] = 53000;
    	this.levelRanges[7] = 77000;
    	this.levelRanges[8] = 115000;
    	this.levelRanges[9] = 160000;
    	this.levelRanges[10] = 235000;
    	this.levelRanges[12] = 330000;
    	this.levelRanges[13] = 475000;
    	for(int i=14; i<=20; i++){
    		levelRanges[i] = levelRanges[i-1] + levelRanges[i-2] - levelRanges[i-8];
    	}
    }
    public int[] getlevelRanges(){
    	return levelRanges;
    }
}
