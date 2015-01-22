import java.io.Serializable;

/**
 * Creates a character that has different statistics an inventory
 * and a name. Inspired by the pathfinder character sheet.
 * 
 * @author Philipp Wolf
 * @version 03.01.2015
 */


public class CharacterSheet implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
	private LootCatalogue availableItems;
	/**
	 * Base Statistics Every Character Has
	 * 
	 */
	private String name;
	private String characterClass;
	private int lvl;

	private int hd;
	private int hp;
	private int currentHp;
	private int ac;

	private int bab;

	/**
	 * Ability Scores
	 * 
	 */
	private int str;
	private int strMod;
	private int dex;
	private int dexMod;
	private int con;
	private int conMod;
	private int in;
	private int inMod;
	private int wis;
	private int wisMod;
	private int cha;
	private int chaMod;

	private int exp;
	private Room location;

	public Inventory equipment;

	/**
	 * Constructor for objects of class CharacterSheet. This is a more rudamental
	 * version of the constructor for more or less random characters
	 */
	public CharacterSheet(String characterName, int level, int hitDice, String playerClass){
		name = characterName;
		characterClass = playerClass;
		lvl = level;
		hd = hitDice;
		equipment = new Inventory(10);
		availableItems = new LootCatalogue();
		rollAbilityScores(lvl);
		applyCharacterClassOnAbilityScores();
		updateAbilityScores();
		hp = conMod * lvl + hd + Roll.dice(hd, lvl-1);
		currentHp = hp;
		ac = 10 + dexMod;
		equipment.updateStr(str);
	}
	/**
	 * Another Constructor for objects of class CharacterSheet that allows the
	 * Creation of specific characters without randomized stats.
	 * @param specificCharacterName give the Character a name
	 * @param hitpoints set the character up to have a specific amount of hitpoints
	 * @param naturalAc set the character up to have a specific amount of natural
	 * armor granted by size or scales
	 * @param expReward sets the characters exp value, this is the value a player gets
	 * fo slaying this character 
	 */
	public CharacterSheet(String specificCharacterName,
			int hitPoints,int newStr,int newDex,int newCon,int newWis,int newIn,int newCha,int naturalAc,
			String equipedWeapon, String equipedArmor, int expReward){
		name = specificCharacterName;
		lvl = 1;
		hp = hitPoints;
		str = newStr;
		con = newCon;
		dex = newDex;
		wis = newWis;
		in = newIn;
		cha = newCha;
		updateAbilityScores();
		hp = hitPoints;
		currentHp = hp;
		ac = 10 + naturalAc + dexMod;
		equipment = new Inventory(10);
		equipment.updateStr(str);
		setExpReward(expReward);
		availableItems = new LootCatalogue();
		for(Item item : availableItems.getAllItems()){
			if(item.getEquipmentName().equals(equipedWeapon)){
				equipment.addEquipment(item);
				equipment.equipWeapon(item);
			}
		}
		for(Item item : availableItems.getAllItems()){
			if(item.getEquipmentName().equals(equipedArmor)){
				equipment.addEquipment(item);
				equipment.equipArmor(item);
			}
		}
	}

	/**
	 * Rerolls the Ability Scores
	 */
	public void reRollAbilityScores(){

		this.hp = hd;
		rollAbilityScores(1);
		applyCharacterClassOnAbilityScores();
		updateAbilityScores();
		hp = conMod * lvl + hd + Roll.dice(hd, lvl-1);
		currentHp = hp;
	}
	private void applyCharacterClassOnAbilityScores(){
		switch(characterClass){
		case "warrior" :
			str += 3;
			con += 2;
			in -= 3;
			cha -= 2;
			break;
		case "mage" :
			in += 5;
			str -= 2;
			dex -= 2;
			break;
		case "rogue" :
			dex += 3;
			str += 1;
			break;
		case "clerik" :
			cha += 3;
			in += 1;
			break;
		}
	}
	public String addSpellsOnLevelUp(){
		Item learnedSpell = null;
		String result = "";
		if(in >= 18){
			for(Item item : availableItems.getAllItems()){
				if(item.getEquipmentName().equals("acid-dart")){
					learnedSpell = item;
					if(equipment.containsEquipment(learnedSpell.getEquipmentName()) == true){
						learnedSpell = null;
					}else{
						this.equipment.addEquipment(learnedSpell);
						result += "You learned [" + learnedSpell.getEquipmentName() + "].\n";
					}
				}
			}
		}
		if(in >= 15 && characterClass.equals("mage")){
			for(Item item : availableItems.getAllItems()){
				if(item.getEquipmentName().equals("burning-hands")){
					learnedSpell = item;
					if(equipment.containsEquipment(learnedSpell.getEquipmentName()) == true){
						learnedSpell = null;
					}else{
						this.equipment.addEquipment(learnedSpell);
						result += "You learned [" + learnedSpell.getEquipmentName() + "].\n";
					}
				}
			}
		}
		if(in >= 23 && characterClass.equals("mage")){
			for(Item item : availableItems.getAllItems()){
				if(item.getEquipmentName().equals("greater-burning-hands")){
					learnedSpell = item;
					if(equipment.containsEquipment(learnedSpell.getEquipmentName()) == true){
						learnedSpell = null;
					}else{
						this.equipment.addEquipment(learnedSpell);
						result += "You learned [" + learnedSpell.getEquipmentName() + "].\n";
					}
				}
			}
		}
		if(cha >= 15 && characterClass.equals("clerik")){
			for(Item item : availableItems.getAllItems()){
				if(item.getEquipmentName().equals("cure-light-wounds")){
					learnedSpell = item;
					if(equipment.containsEquipment(learnedSpell.getEquipmentName()) == true){
						learnedSpell = null;
					}else{
						this.equipment.addEquipment(learnedSpell);
						result += "You learned [" + learnedSpell.getEquipmentName() + "].\n";
					}
				}
			}
		}
		if(cha >= 23 && characterClass.equals("clerik")){
			for(Item item : availableItems.getAllItems()){
				if(item.getEquipmentName().equals("cure-medium-wounds")){
					learnedSpell = item;
					if(equipment.containsEquipment(learnedSpell.getEquipmentName()) == true){
						learnedSpell = null;
					}else{
						this.equipment.addEquipment(learnedSpell);
						result += "You learned [" + learnedSpell.getEquipmentName() + "].\n";
					}
				}
			}
		}
		return result;
	}
	private void rollAbilityScores(int lv){
		this.str = Roll.dice(6, true);
		this.dex = Roll.dice(6, true);
		this.con = Roll.dice(6, true);
		this.in = Roll.dice(6, true);
		this.wis = Roll.dice(6, true);
		this.cha = Roll.dice(6, true);
	}
	private void updateAbilityScores(){       
		this.strMod = baseStatToMod(str);        
		this.dexMod = baseStatToMod(dex);
		int hpBeforeUpdate = lvl * conMod;
		hpBeforeUpdate = hp - hpBeforeUpdate;
		this.conMod = baseStatToMod(con);
		this.hp = hpBeforeUpdate + conMod*lvl;
		this.inMod = baseStatToMod(in);
		this.wisMod = baseStatToMod(wis);
		this.chaMod = baseStatToMod(cha);
		this.bab = lvl - 1;
		currentHp = hp;
		this.ac = 10 + dexMod;
	}
	private int baseStatToMod(int baseStat){
		int mod = 0;
		baseStat = baseStat -10;
		if(baseStat < 0)
			baseStat += -1;
		mod =  baseStat/2;

		return mod;
	}
	public Inventory getInventory(){
		return equipment;
	}
	public int getBab(){
		return this.bab;
	}
	public int getAc(){
		int tempAc = ac;
		int maxDex = equipment.getEquipedArmor().getArmorsMaxDexValue();
		if(dexMod > maxDex){
			tempAc = ac - dexMod + maxDex;

		}
		return tempAc;
	}
	public int getCurrentHp(){
		return this.currentHp;
	}
	public int getMaxHp(){
		return this.hp;
	}
	public void setCurrentHp(int hpAfterLoss){
		this.currentHp = hpAfterLoss;
	}
	/**
	 * Heal this character a specific amount of hitpoints.
	 * @param amount the amount of hitpoints to be healed.
	 */
	public void heal(int amount){
		if(currentHp + amount > hp){
			this.currentHp = hp;
		}
		else{
			currentHp += amount;
		}
	}
	public int getStrMod(){
		return this.strMod;
	}
	public int getConMod(){
		return this.conMod;
	}
	public int getDexMod(){
		return this.dexMod;
	}
	public int getInMod(){
		return this.inMod;
	}
	public int getRawIn(){
		return this.in;
	}
	public int getRawWis(){
		return this.wis;
	}
	public int getWisMod(){
		return this.wisMod;
	}
	public int getChaMod(){
		return this.chaMod;
	}
	public String getName(){
		return this.name;
	}
	public void setLocation(Room newLocation){
		this.location = newLocation;
	}
	public Room getLocation(){
		return location;
	}
	public String earnExp(int earnedExp, int[] levelRanges){
		this.exp += earnedExp;
		String result = "You earned " + earnedExp + " exp.\n";
		if(exp>=levelRanges[lvl])
			result +=	lvlUp();
		return result;
	}
	public void setExpReward(int expReward){
		this.exp = exp + expReward;
	}
	public int getExpScore(){
		return exp;
	}
	public int getLvl(){
		return lvl;
	}
	public int getKMV(){
		return 10 + strMod + dexMod;
	}
	/**
	 * Increases the Level of this character by 1. Adds points in each ability score at random.
	 * @return returns a text to be printed out.
	 */
	public String lvlUp(){
		String result = "\n" + "You have Leveled up!\n";
		this.lvl += 1;
		result += "You are now Lvl " + lvl + ".\n \n";
		int diceResult1 = Roll.dice(6) - 5;
		if(diceResult1 <= 0 && characterClass.equals("warrior")){
			diceResult1 = Roll.dice(4,false) - 3;
		}
		if(diceResult1 <= 0 && characterClass.equals("rogue")){
			diceResult1 = Roll.dice(6) - 5;
		}
		if(diceResult1 > 0){
			result += "Your Str has increased by  +" + diceResult1 + "\n";
			this.str += diceResult1;
		}
		int diceResult2 = Roll.dice(6) - 5;
		if(diceResult2 <= 0 && characterClass.equals("rogue")){
			diceResult2 = Roll.dice(4,false) - 3;
		}
		if(diceResult2 <= 0 && characterClass.equals("warrior")){
			diceResult2 = Roll.dice(6) - 5;
		}
		if(diceResult2 > 0){
			result += "Your Dex has increased by  +" + diceResult2 + "\n";
			this.dex += diceResult2;
		}
		int diceResult3 = Roll.dice(6) - 5;
		if(diceResult3 <= 0 && characterClass.equals("rogue")){
			diceResult3 = Roll.dice(4) - 3;
		}
		if(diceResult1 <= 0 && characterClass.equals("warrior")){
			diceResult1 = Roll.dice(6) - 5;
		}
		if(diceResult3 > 0){
			result += "Your Con has increased by  +" + diceResult3 + "\n";
			this.con += diceResult3;
		}
		int diceResult4 = Roll.dice(6) - 5;
		if(diceResult4 <= 0 && characterClass.equals("mage")){
			diceResult4 = Roll.dice(4,false) - 3;
		}
		if(diceResult4 > 0){
			result += "Your Wis has increased by  +" + diceResult4 + "\n";
			this.wis += diceResult4;
		}
		int diceResult5 = Roll.dice(6) - 5;
		if(diceResult5 <= 0 && characterClass.equals("mage")){
			diceResult5 = Roll.dice(2,false) - 1;
		}
		if(diceResult5 <= 0 && characterClass.equals("clerik")){
			diceResult5 = Roll.dice(4) - 3;
		}
		if(diceResult5 > 0){
			result += "Your In has increased by  +" + diceResult5 + "\n";
			this.in += diceResult5;
		}
		int diceResult6 = Roll.dice(6) - 5;
		if(diceResult6 >= 0 && characterClass.equals("clerik")){
			diceResult6 = Roll.dice(3) - 2;
		}
		if(diceResult6 > 0){
			result += "Your Cha has increased by  +" + diceResult6 + "\n";
			this.cha += diceResult6;
		}
		updateAbilityScores();
		int diceResult7 = Roll.dice(hd,false);
		result += "Your maximal HP increased by    +" + diceResult7 + "\n \n";
		result += addSpellsOnLevelUp();
		this.hp += diceResult7;
		this.currentHp = hp;
		this.equipment.updateStr(str);

		return result;

	}
	/**
	 * prints this characters ability scores directly into the chat, ignoring the main play routine.
	 * this is only used when initially rolling out the players start ability scores.
	 */
	public void printAbilityScores(){
		String result = "Str   " + str + "\n";
		result += "Dex   " + dex + "\n";
		result += "Con   " + con + "\n";
		result += "Wis   " + wis + "\n";
		result += "In    " + in + "\n";
		result += "Cha   " + cha + "\n";
		System.out.println(result);
	}
	/**
	 * get this characters status to print it in the main play routine
	 * @return returns the current status of this character with all his ability scores and modifiers.
	 * This method does not return the contents of this characters inventory
	 */
	public String getStatus(){
		String result = "";
		result = "Name: " + name +"    "+"Lvl: " + lvl + "    " + "HP: " + currentHp + " / "+ hp +"\n";
		result += "Exp:   " + exp + "       " + "AC: " + ac + " + " + equipment.getEquipedArmor().getArmorRating() +"\n \n";
		result += "Weapon:  [" + equipment.getEquipedWeapon().getEquipmentName() + "]   Damage:  1d" + equipment.getEquipedWeapon().getWeaponDamage() + "\n";
		result += "Str   " + str + "(+" + strMod + ")" + "    ";
		result += "Dex   " + dex + "(+" + dexMod + ")"  + "    ";
		result += "Con   " + con + "(+" + conMod + ")" + "    \n";
		result += "Wis   " + wis + "(+" + wisMod +")" + "    ";
		result += "In    " + in + "(+" + inMod + ")" + "    ";
		result += "Cha   " + cha + "(+" + chaMod + ")" + "\n";
		return result;

	}
}
