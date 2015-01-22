import java.io.Serializable;


/**
 * Write a description of class Weapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
    // instance variables - replace the example below with your own
	private int baseStat;
    private int primaryStat;
    private int secondaryStat;
    private int equipmentWeight;
    private int equipmentPrize;
    private String equipmentPrefix;
    private String equipmentName;
    private String equipmentType;
    private String equipmentDescription;
    
    /**
     * Constructor for objects of class Weapon
     */
    public Item(String prefix, String name, String type, int stat, String description, int weight, int prize)
    {
    	equipmentPrefix = prefix;
    	equipmentName = name;
    	equipmentWeight = weight;
        equipmentType = type;
        equipmentPrize = prize;
        equipmentDescription = description;
        primaryStat = 0;
        if(type.equals("weapon")){
        	baseStat = stat; //The weapons damage dice
        	primaryStat = 1; //The number of dice rolled
        	secondaryStat = 20; //The weapons cirtical threat range
        }
        if(type.equals("armor")){
        	baseStat = stat; //The armors armor value (the ac bonus)
        	secondaryStat = 100;//The armors maximal Dexterity modifier
        }
        if(type.equals("spell")){
        	baseStat = stat; //The spells effect damage/heal/something dice
        	primaryStat = 1; //The number of dice rolled
        	secondaryStat = 20; //The caster level requiered to cast this spell
        }
        if(type.equals("potion")){
        	baseStat = stat; //The potions dice (depending on the effect specifid in the prefix)
        	primaryStat = 1; //The number of dice rolled
        	secondaryStat = 100;//The potions chance to be used up
        }
    }
    public Item(String prefix, String name, String type, int stat,int additionalStat, int advancedStat, String description, int weight, int prize)
    {
    	equipmentPrefix = prefix;
    	equipmentName = name;
    	equipmentWeight = weight;
        equipmentType = type;
        equipmentPrize = prize;
        equipmentDescription = description;
        baseStat = stat;
        primaryStat = additionalStat;
        secondaryStat = advancedStat;
    
    }

    public int getArmorRating()
    {
        return baseStat;
    }

    public int getBaseStat()
    {
        return baseStat;
    }
    public int getWeaponDamage()
    {
        return baseStat;
    }
    public int getEquipmentWeight()
    {
        return equipmentWeight;
    }
    public int getPrize()
    {
        return equipmentPrize;
    }
    public String getEquipmentName(){
    	return equipmentName;
    }
    public String getEquipmentDescription(){
    	return equipmentDescription;
    }
    public String getEquipmentType(){
    	return equipmentType;
    }
    public String getPrefix(){
    	return equipmentPrefix;
    }
    public int amountOfDiceToBeRolled(){
    	return primaryStat;
    }
    public int getPrimaryStat(){
    	return primaryStat;
    }
    public String getCritRateBonus(){
    	String result = "";
    	int bonus = 20 - secondaryStat;
    	if(bonus > 0)
    		result = "+" + bonus;
    	if(bonus == 0)
    		result = "0";
    	if(bonus < 0)
    		result = "" + bonus;
    	return result;
    }
    public int getSecondaryStat(){
    	return secondaryStat;
    }
    public int getArmorsMaxDexValue(){
    	return secondaryStat;
    }
    public int getWeaponsCriticalHitRange(){
    	return secondaryStat;
    }
    public String getSpellTargetingType(){
    	String result = "";
    	switch(equipmentPrize){
    	case 1 :
    		result = "touch";
    		break;
    	case 2 :
    		result = "self";
    		break;
    	case 3 :
    		result = "burst";
    		break;
    	}
    	return result;
    }
}
