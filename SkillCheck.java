
public class SkillCheck {
	
	/**
	 * Make a bluff check
	 * 
	 * @param user the character for whom the bluff check should be made
	 * @param target the character the user tries to fool
	 * @param difficultyClass the minimally required result to pass the check aka the difficultyClass of the check 
	 * @return returns whether or not the check was passed
	 */
	public static boolean opposedBluffCheck(CharacterSheet user,CharacterSheet target){
		boolean passedCheck = false;
		if(senseMotiveCheck(target, Roll.dice(20) + user.getChaMod()) == false){
			passedCheck = true;
		}
		return passedCheck;
			
	}
	
	/**
	 * Make a sense motive check
	 * 
	 * @param character the character for whom the acrobatics check should be made
	 * @param difficultyClass the minimally required result to pass the check aka. the difficultyClass of the check 
	 * @return returns whether or not the check was passed
	 */
	public static boolean senseMotiveCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getWisMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	
	/**
	 * Make an opposed acrobatics check
	 * 
	 * @param character the character for whom the acrobatics check should be made
	 * @param difficultyClass the minimally required result to pass the check aka. the difficultyClass of the check 
	 * @return returns whether or not the check was passed
	 */
	public static boolean opposedAcrobaticsCheck(CharacterSheet user,CharacterSheet target){
		boolean passedCheck = false;
		if(acrobaticsCheck(target,user.getKMV()) == false){
			passedCheck = true;
		}
		return passedCheck;
			
	}
	public static boolean bluffCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getChaMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean acrobaticsCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getDexMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean demploacyCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getChaMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean perceptionCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getChaMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean spellcraftCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getChaMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}
	public static boolean intimidateCheck(CharacterSheet character,int difficultyClass){
		if(Roll.dice(20) + character.getChaMod() >= difficultyClass){
			return true;
		}else{
			return false;
		}
			
	}

}
