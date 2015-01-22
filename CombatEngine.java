import java.util.ArrayList;
/**
 * This is the combatEngine of the game.
 * The basics of a fight happen here.
 * 
 * @author Philipp Wolf 
 * @version 01.01.2015
 */
public class CombatEngine
{
	// instance variables - replace the example below with your own
	private CharacterSheet attacker;
	private CharacterSheet defender;
	private CharacterSheet currentlySupporting;
	private ArrayList<CharacterSheet> support;


	public CombatEngine(CharacterSheet player,CharacterSheet enemy, int[] levelRanges)
	{
		attacker = player;
		defender = enemy;
	}
	public CombatEngine(CharacterSheet player,CharacterSheet enemy,ArrayList<CharacterSheet> addintionalEnemys, int[] levelRanges)
	{
		attacker = player;
		defender = enemy;
		support = addintionalEnemys;
	}

	public void spellDamageTwoOnOne(int spellDamage){
		defender.setCurrentHp(defender.getCurrentHp() - spellDamage);
	}

	public void spellDamage(int spellDamage){
		defender.setCurrentHp(defender.getCurrentHp() - spellDamage);
	}

	public String battleRoundTwoOnOne(boolean playerFlatFooded, boolean enemyFlatFooded)
	{
		String result = "";
		result += attackerAttacks(enemyFlatFooded);
		for(CharacterSheet supporting : support){
			currentlySupporting = supporting;
			result += supportAttacks(true);

		}
		if(defender.getCurrentHp() > 0){
			result += defenderAttacks(playerFlatFooded);
		}
		return result;
	}

	public String battleRound(boolean playerFlatFooded, boolean enemyFlatFooded)
	{
		String result = "";
		result += attackerAttacks(enemyFlatFooded);

		if(defender.getCurrentHp() > 0){
			result += defenderAttacks(playerFlatFooded);
		}
		return result;
	}

	public String surpriseRoundTwoOnOne(boolean enemySurpriseRound, boolean playerFlatFooded, boolean enemyFlatFooded)
	{
		String result = "";
		if(enemySurpriseRound == false){
			result += attackerAttacks(enemyFlatFooded);
		}
		if(enemySurpriseRound == true){
			for(CharacterSheet supporting : support){
				currentlySupporting = supporting;
				result += supportAttacks(true);
			}
			result += defenderAttacks(playerFlatFooded);
		}
		return result;
	}

	public String surpriseRound(boolean enemySurpriseRound, boolean playerFlatFooded, boolean enemyFlatFooded)
	{
		String result = "";
		if(enemySurpriseRound == false){
			result += attackerAttacks(enemyFlatFooded);
		}
		if(enemySurpriseRound == true){
			result += defenderAttacks(playerFlatFooded);
		}
		return result;
	}

	private String attackerAttacks(boolean flatFooded){
		String result = "";
		int attackerAb = attacker.getBab() + attacker.getStrMod();
		if(attacker.equipment.getEquipedWeapon().getPrefix().equals("mwk"))
			attackerAb ++;
		int defenderAc = defender.getAc() + defender.equipment.getEquipedArmor().getArmorRating();
		if(flatFooded == true){
			defenderAc -= defender.getDexMod();
		}
		int attackerResult = 0;
		for(int i = 0; i < attacker.equipment.getEquipedWeapon().amountOfDiceToBeRolled(); i++){
			attackerResult += Roll.dice(20);
		}
		if(attackerResult >= attacker.equipment.getEquipedWeapon().getWeaponsCriticalHitRange()
				&& attackerResult + attackerAb - defenderAc >= 0){
			result += "You scored a critical Hit!!!\n";
			result += criticalHit();
		}
		else if(attackerResult + attackerAb - defenderAc >= 0){
			result += hit();
		}
		else{
			result += miss();
		}
		return result;


	}

	private String defenderAttacks(boolean flatFooded){
		String result = "";
		int defenderAb = defender.getBab() + defender.getStrMod();
		if(defender.equipment.getEquipedWeapon().getPrefix().equals("mwk"))
			defenderAb ++;
		int attackerAc = attacker.getAc() + attacker.getInventory().getEquipedArmor().getArmorRating();
		if(flatFooded == true){
			attackerAc -= attacker.getDexMod();
		}
		int defenderResult = 0;
		for(int i = 0; i < defender.equipment.getEquipedWeapon().amountOfDiceToBeRolled(); i++){
			defenderResult += Roll.dice(20);
		}
		if(defenderResult >= defender.equipment.getEquipedWeapon().getWeaponsCriticalHitRange() &&
				defenderResult + defenderAb - attackerAc >= 0 && defender.getCurrentHp() >= 0){
			result += "The [" + defender.getName() + "] scored a critical Hit!!!\n";
			result += defenderCriticalHit();
		}
		else if(defenderResult + defenderAb - attackerAc >= 0){
			result += defenderHit();
		}else{
			result += defenderMiss();
		}

		return result;

	}

	private String supportAttacks(boolean flatFooded){
		String result = "";
		int supportAb = currentlySupporting.getBab() + currentlySupporting.getStrMod();
		if(currentlySupporting.equipment.getEquipedWeapon().getPrefix().equals("mwk"))
			supportAb ++;
		int attackerAc = attacker.getAc() + attacker.getInventory().getEquipedArmor().getArmorRating();
		if(flatFooded == true){
			attackerAc -= attacker.getDexMod();
		}
		int supportResult = 0;
		for(int i = 0; i < currentlySupporting.equipment.getEquipedWeapon().amountOfDiceToBeRolled(); i++){
			supportResult += Roll.dice(20);
		}
		if(supportResult >= currentlySupporting.equipment.getEquipedWeapon().getWeaponsCriticalHitRange() &&
				supportResult + supportAb - attackerAc >= 0){
			result += "The [" + currentlySupporting.getName() + "] scored a critical Hit!!!\n";
			result += supportCriticalHit();
		}
		else if(supportResult + supportAb - attackerAc >= 0){
			result += supportHit();
		}else{
			result += supportMiss();
		}

		return result;

	}

	private String criticalHit(){
		String result = "";
		int attackerWeaponDamage = Roll.dice(attacker.getInventory().getEquipedWeapon().getWeaponDamage(),attacker.getInventory().getEquipedWeapon().getPrimaryStat());
		int attackerDamage = attacker.getStrMod() + attackerWeaponDamage;
		if(attackerDamage <= 0){
			attackerDamage = 1;
		}attackerWeaponDamage = Roll.dice(attacker.getInventory().getEquipedWeapon().getWeaponDamage(),attacker.getInventory().getEquipedWeapon().getPrimaryStat());
		int attackerDamage2 = attacker.getStrMod() + attackerWeaponDamage;
		if(attackerDamage2 <= 0){
			attackerDamage2 = 1;
		}
		attackerDamage += attackerDamage2;
		defender.setCurrentHp(defender.getCurrentHp() - attackerDamage);
		result = "You hit the [" + defender.getName() + "] with your [" + attacker.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + attackerDamage + " Damage.\n";
		if(defender.getCurrentHp() <= 0){
			result += "You killed the [" + defender.getName() + "].\n";
		}
		return result;

	}
	private String hit(){
		String result = "";
		int attackerWeaponDamage = Roll.dice(attacker.getInventory().getEquipedWeapon().getWeaponDamage(),attacker.getInventory().getEquipedWeapon().getPrimaryStat());
		int attackerDamage = attacker.getStrMod() + attackerWeaponDamage;
		if(attackerDamage <= 0){
			attackerDamage = 1;
		}
		defender.setCurrentHp(defender.getCurrentHp() - attackerDamage);
		result = "You hit the [" + defender.getName() + "] with your [" + attacker.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + attackerDamage + " Damage.\n";
		if(defender.getCurrentHp() <= 0){
			result += "You killed the [" + defender.getName() + "].\n";
		}
		return result;

	}
	private String defenderCriticalHit(){
		//funny sitenote: the defender can score a citical hit and deal
		//negative damage thus healing the player. Don't as me why, it should be impossible...
		//Update: fixed it!
		String result = "";
		int defenderWeaponDamage = Roll.dice(defender.getInventory().getEquipedWeapon().getWeaponDamage(),defender.getInventory().getEquipedWeapon().getPrimaryStat());
		int defenderDamage = defender.getStrMod() + defenderWeaponDamage;
		if(defenderDamage <= 0){
			defenderDamage = 1;
		}
		defenderWeaponDamage = Roll.dice(defender.getInventory().getEquipedWeapon().getWeaponDamage(),defender.getInventory().getEquipedWeapon().getPrimaryStat());
		int defenderDamage2 = defender.getStrMod() + defenderWeaponDamage;
		if(defenderDamage2 <= 0){
			defenderDamage2 = 1;
		}
		defenderDamage += defenderDamage2;
		attacker.setCurrentHp(attacker.getCurrentHp() - defenderDamage);
		result = "The [" + defender.getName() + "] hits you with its [" + defender.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + defenderDamage + " Damage.\n";
		if(attacker.getCurrentHp() <= 0){
			result += "You got killed by the [" + defender.getName() + "].\n";
		}
		return result;
	}
	private String defenderHit(){
		String result = "";
		int defenderWeaponDamage = Roll.dice(defender.getInventory().getEquipedWeapon().getWeaponDamage(),defender.getInventory().getEquipedWeapon().getPrimaryStat());
		int defenderDamage = defender.getStrMod() + defenderWeaponDamage;
		if(defenderDamage <= 0){
			defenderDamage = 1;
		}
		attacker.setCurrentHp(attacker.getCurrentHp() - defenderDamage);
		result = "The [" + defender.getName() + "] hits you with its [" + defender.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + defenderDamage + " Damage.\n";
		if(attacker.getCurrentHp() <= 0){
			result += "You got killed by the [" + defender.getName() + "].\n";
		}
		return result;
	}
	private String supportCriticalHit(){
		String result = "";
		int supportWeaponDamage = Roll.dice(currentlySupporting.getInventory().getEquipedWeapon().getWeaponDamage(),currentlySupporting.getInventory().getEquipedWeapon().getPrimaryStat());
		int supportDamage = currentlySupporting.getStrMod() + supportWeaponDamage;
		if(supportDamage <= 0){
			supportDamage = 1;
		}
		supportWeaponDamage = Roll.dice(currentlySupporting.getInventory().getEquipedWeapon().getWeaponDamage(),currentlySupporting.getInventory().getEquipedWeapon().getPrimaryStat());
		int supportDamage2 = currentlySupporting.getStrMod() + supportWeaponDamage;
		if(supportDamage2 <= 0){
			supportDamage2 = 1;
		}
		supportDamage += supportDamage2;
		attacker.setCurrentHp(attacker.getCurrentHp() - supportDamage);
		result = "The [" + currentlySupporting.getName() + "] hits you with its [" + currentlySupporting.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + supportDamage + " Damage.\n";
		if(attacker.getCurrentHp() <= 0){
			result += "You got killed by the [" + currentlySupporting.getName() + "].\n";
		}
		return result;
	}
	private String supportHit(){
		String result = "";
		int supportWeaponDamage = Roll.dice(currentlySupporting.getInventory().getEquipedWeapon().getWeaponDamage(),currentlySupporting.getInventory().getEquipedWeapon().getPrimaryStat());
		int supportDamage = currentlySupporting.getStrMod() + supportWeaponDamage;
		if(supportDamage <= 0){
			supportDamage = 1;
		}
		attacker.setCurrentHp(attacker.getCurrentHp() - supportDamage);
		result = "The [" + currentlySupporting.getName() + "] hits you with its [" + currentlySupporting.getInventory().getEquipedWeapon().getEquipmentName()
				+ "] for " + supportDamage + " Damage.\n";
		if(attacker.getCurrentHp() <= 0){
			result += "You got killed by the [" + currentlySupporting.getName() + "].\n";
		}
		return result;
	}
	private String miss(){
		return "You missed the [" + defender.getName() + "].\n";
	}
	private String defenderMiss(){
		return "The [" + defender.getName() + "] missed you.\n";
	}
	private String supportMiss(){
		return "The [" + currentlySupporting.getName() + "] missed you.\n";
	}
	public String getDefenderName(){
		return defender.getName();
	}
	public CharacterSheet getAttacker(){
		return attacker;
	}
	public CharacterSheet getDefender(){
		return defender;
	}
	public ArrayList<CharacterSheet> getAdditionalEnemys(){
		return support;
	}
}
