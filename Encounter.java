import java.io.Serializable;
import java.util.ArrayList;

public class Encounter implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
	private CharacterSheet primaryEnemy;
	public ArrayList<CharacterSheet> additionalEnemys;
	private NSC nsc;
	private Item loot;
	private String type;
	private String startType;
	private String encounterDescription;
	private LootCatalogue availableLoot = new LootCatalogue();

	public Encounter(int cr){
		int encounterType = Roll.dice(100);
		type = "nothing";
		startType = "nothing";
		encounterDescription = "The Room is Empty.";
		loot = availableLoot.getLoot(determineLootRarity(0));
		if(encounterType >= 10 && encounterType <= 25 ){
			setNSC(cr);
		}
		else if(encounterType >= 26 && encounterType <= 89 ){
			setBattle(cr);
		}
		else if(encounterType >= 90 && encounterType <= 96 ){
			setSkirmish(cr);
		}
		else if(encounterType >= 97){
			setTreasure(cr);
		}
	}
	private int determineLootRarity(int cr){
		int diceResult = cr + Roll.dice(100) ;
		int secondaryDiceResult = cr + Roll.dice(100) ;
		int rewardRarity = 0;
		if(diceResult >= 70 && diceResult <= 139){
			rewardRarity = 1;
			if(secondaryDiceResult <= 63){
				rewardRarity = 0;
			}
		}
		else if(diceResult >= 140 && diceResult <= 170){
			rewardRarity = 2;
			if(secondaryDiceResult <= 45){
				rewardRarity = 0;
			}
		}
		else if(diceResult >= 171){
			rewardRarity = 3;
			if(secondaryDiceResult <= 19){
				rewardRarity = 0;
			}
		}
		return rewardRarity;
	}
	public String getType(){
		return type;
	}
	public NSC getNSC(){
		return nsc;
	}
	public void setNSC(NSC nscReplacement){
		this.nsc = nscReplacement;
	}
	public CharacterSheet getPrimaryEnemy(){
		return primaryEnemy;
	}
	public void replacePrimaryEnemy(CharacterSheet replaceSheet){
		this.primaryEnemy = replaceSheet;
	}
	/*

	public CharacterSheet getSecondaryEnemy(){
		return secondaryEnemy;
	}
	public void replaceSecondaryEnemy(CharacterSheet replaceSheet){
    	this.secondaryEnemy = replaceSheet;
    }

	 */
	public Item getReward(){
		Item reward = loot;
		return reward;
	}
	public String getEncounterDescription(){
		return encounterDescription;
	}
	public void setLootToGarbage(){
		LootCatalogue availableItems = new LootCatalogue();
		this.loot = availableItems.getItem("garbage");
	}
	public void setStairCase(){
		this.type = "staircase";
		this.encounterDescription = "In the corner of the room a staircase reaches down into the depths of the dungeon.\n";
	}
	public void setSkirmish(int cr){
		type = "skirmish";
		startType = "skirmish";
		Bestiary bestiary = new Bestiary();
		primaryEnemy = bestiary.getEnemy(cr);
		additionalEnemys = new ArrayList<CharacterSheet>();
		if(cr-3 >= 0){
			switch(Roll.dice(3)){
			case 1:
				additionalEnemys.add(bestiary.getEnemy(cr-1));
				break;
			case 2:
				additionalEnemys.add(bestiary.getEnemy(cr-1));
				additionalEnemys.add(bestiary.getEnemy(cr-2));
				break;
			case 3:
				additionalEnemys.add(bestiary.getEnemy(cr-1));
				additionalEnemys.add(bestiary.getEnemy(cr-2));
				additionalEnemys.add(bestiary.getEnemy(cr-3));
				break;
			}
			primaryEnemy.equipment.addMoneyToWallet(cr*cr*10);
			for(CharacterSheet secondaryEnemy : additionalEnemys){
				secondaryEnemy.equipment.addMoneyToWallet(cr*cr*5);
			}
			loot = availableLoot.getLoot(determineLootRarity(cr*10));
			encounterDescription = "A [" + primaryEnemy.getName() + "] is standing before you.\n";
			for(CharacterSheet enemy : additionalEnemys){
				encounterDescription += "A [" + enemy.getName() + "] is standing before you.\n";
			}
			encounterDescription += "Together they are looking fairly intimidating.";
		}
		else if(cr-3 < 0){
			setBattle(cr);
		}
	}
	public void setBattle(int cr){
		type = "battle";
		startType = "battle";
		Bestiary bestiary = new Bestiary();
		primaryEnemy = bestiary.getEnemy(cr);
		primaryEnemy.equipment.addMoneyToWallet(cr*cr*10);
		loot = availableLoot.getLoot(determineLootRarity(cr*10));
		encounterDescription = "A [" + primaryEnemy.getName() + "] is standing before you.";
	}

	public void changeTypeToBattle(){
		type = "battle";
		encounterDescription = "A [" + primaryEnemy.getName() + "] is standing before you.\n " ;
		for(CharacterSheet secondaryEnemy : additionalEnemys){
			if(secondaryEnemy.getCurrentHp() <= 0)
			encounterDescription +=	"The carcas of a dead [" + secondaryEnemy.getName() + "] is lying before you.\n";;
		}
	}

	public void changeTypeToNothing(){
		type = "empty";
		if(startType.equals("skirmish")){
			encounterDescription = "The corpse of a dead [" + primaryEnemy.getName() + "] is lying before you.\n";
			for(CharacterSheet secondaryEnemy : additionalEnemys){
				encounterDescription +=	"The carcas of a dead [" + secondaryEnemy.getName() + "] is lying next to it.\n";;
			}
		}else{
			encounterDescription = "The corpse of a dead [" + primaryEnemy.getName() + "] is lying before you.\n";
		}
	}

	public void setTreasure(int cr){
		type = "treasure";
		loot = availableLoot.getLoot(determineLootRarity(cr*5+40));
		encounterDescription = "You look at a treasure Chest.";
	}
	public void setNSC(int cr){
		type = "nsc";
		nsc = new NSC(cr);
		loot = availableLoot.getLoot(determineLootRarity(cr-1));
		encounterDescription = "A " + nsc.getNscType() + " is resting in the middle of the room.\n"
				+ "The " + nsc.getNscType() + " is watching you as you approach him.";
	}
	public void changePrimaryEnemy(CharacterSheet toSwitch){
		CharacterSheet switcher = primaryEnemy;
		int indexToRemove = 0;
		for(CharacterSheet monster : additionalEnemys){
			if(monster.equals(toSwitch)){
				primaryEnemy = monster;
				indexToRemove = additionalEnemys.indexOf(monster);
			}
		}
		additionalEnemys.remove(indexToRemove);
		additionalEnemys.add(switcher);

	}
	
	public void changePrimaryEnemy(){
		CharacterSheet switcher = primaryEnemy;
		int indexToRemove = 0;
		for(CharacterSheet monster : additionalEnemys){
			if(monster.getCurrentHp() > 0){
				primaryEnemy = monster;
				indexToRemove = additionalEnemys.indexOf(monster);
			}
		}
		additionalEnemys.remove(indexToRemove);
		additionalEnemys.add(switcher);

	}
	public String getNameOfAllAditionalEnemys(boolean listed){
		String result = "";
		if(listed == true){
			for(CharacterSheet enemy : additionalEnemys){
				result += ", the [" + enemy.getName() + "] ";
			}
		}else{
			for(CharacterSheet enemy : additionalEnemys){
				result += "a [" + enemy.getName() + "], ";
			}
		}
		return result;
	}
}
