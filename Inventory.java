import java.io.Serializable;
import java.util.*;
/**
 * Write a description of class Inventory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inventory implements Serializable
{
	private static final long serialVersionUID = -5966618586666474164L;
    // instance variables - replace the example below with your own
    private ArrayList<Item> equipment;
    private Item equipedWeapon;
    private Item equipedArmor;
    private int maxItemLoad;
    private int currentItemLoad;
    private int wallet;
    
    /**
     * Constructor for objects of class Inventory
     */
    public Inventory(int str)
    {
    	LootCatalogue availableItems = new LootCatalogue();
        equipment = new ArrayList<Item>();
        wallet = 0;
        equipedWeapon = availableItems.getItem("unarmed-attack");
        equipedArmor = availableItems.getItem("robe");
        maxItemLoad = str*20;
        currentItemLoad = 6;
        addEquipment(availableItems.getItem("map"));
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public boolean addEquipment(Item someEquipment)
    {
    	if(this.maxItemLoad >= currentItemLoad + someEquipment.getEquipmentWeight()){
    		equipment.add(someEquipment);
    		currentItemLoad += someEquipment.getEquipmentWeight();
    		return true;
    	}else{
    		System.out.println("You are carrying to much stuff!");
    		System.out.println("You have to throw something away to carry more items!");
    		return false;
    	}
        
    }
    public void updateStr(int str){
    	this.maxItemLoad = str*20;
    }
    public void equipWeapon(Item weaponToEquip)
    {
    	addEquipment(equipedWeapon);
        this.currentItemLoad -= equipedWeapon.getEquipmentWeight();
        this.equipedWeapon = weaponToEquip;
        discardItem(weaponToEquip);
        this.currentItemLoad += weaponToEquip.getEquipmentWeight();
    }
    public Item getEquipedWeapon()
    {
        return this.equipedWeapon;
    }
    public void equipArmor(Item armorToEquip)
    {
    	addEquipment(equipedArmor);
        this.currentItemLoad -= equipedArmor.getEquipmentWeight();
        this.equipedArmor = armorToEquip;
        discardItem(armorToEquip);
        this.currentItemLoad += armorToEquip.getEquipmentWeight();
    }
    public Item getEquipedArmor()
    {
        return this.equipedArmor;
    }
    public boolean containsEquipment(String someEquipment){
    	boolean contains = false;
    	for(Item item : equipment){
    		if(item.getEquipmentName().equals(someEquipment))
    			contains = true;
    	}
    	return contains;
    }
    public Item getEquipment(String someEquipment){
    	if(containsEquipment(someEquipment)){
	    	for(Item item : equipment){
	    		if(item.getEquipmentName().equals(someEquipment))
	    			return item;
	    	}
    	}
    	return null;
    }
    public String getSpellBook(){
    	String result = "   Your Spells: \n \n";
    	result += printType("spell");
    	return result;
    }
    public String getContents(boolean isShop){
    	//Prints out all Equipment held in this inventory, sorted by type
    	
    	String result = "   Weapons: \n \n";
    	if(!isShop){
			result += "Equiped Weapon:\n \n";
			
			result += "[" + equipedWeapon.getEquipmentName() + "]    Weapon damage: " + 
			equipedWeapon.getPrimaryStat() + "w" + equipedWeapon.getWeaponDamage() + "     Crit rate:   " + equipedWeapon.getCritRateBonus() + "\n";
			result += equipedWeapon.getEquipmentDescription() +".\n";
			result += "Prize:  " + equipedWeapon.getPrize() + " silver coins    Weight:  " + equipedWeapon.getEquipmentWeight() + " lbs\n \n";
			result += "-    -    -    -    -    -    -    -    -    -\n \n";
    	}
    	
		result += printType("weapon");
    	
    	result += "\n------------------------------------------------\n   Armors: \n \n";
    	if(!isShop){
			result += "Equiped Armor:\n \n";
			
			result += "[" + equipedArmor.getEquipmentName() + "]    Armor Rating: " + equipedArmor.getWeaponDamage() + "\n";
			result += equipedArmor.getEquipmentDescription() +".\n";
			result += "Prize:  " + equipedArmor.getPrize() + " silver coins    Weight:  " + equipedArmor.getEquipmentWeight() + " lbs\n \n";
			result += "-    -    -    -    -    -    -    -    -    -\n \n";
    	}
    	
    	result += printType("armor");
    	
    	result += "\n------------------------------------------------\n   Other: \n \n";
    	
    	result += printType("other");
    	return result;
    }
    
    public String getWalletandWeight(){
    	return "Wallet: " + wallet + " silver coins" + "    Total weight: " + currentItemLoad + "/" + maxItemLoad + "\n";
    }
    public int sellItem(Item itemToDiscard){
    	int itemNumber = -1;
    	int itemValue = 0;
    	for(Item item : equipment){
    		if (item.equals(itemToDiscard)){
    			itemNumber = equipment.indexOf(item);
    			this.currentItemLoad -= item.getEquipmentWeight();
    			itemValue = item.getPrize();
    		}
    	} 
    	equipment.remove(itemNumber);
    	return itemValue;
    }
    
    public void discardItem(Item itemToDiscard){
    	int itemNumber = -1;
    	for(Item item : equipment){
    		if (item.equals(itemToDiscard)){
    			itemNumber = equipment.indexOf(item);
    			this.currentItemLoad -= item.getEquipmentWeight();
    		}
    	}
    	equipment.remove(itemNumber);
    }
    private String printType(String type){
    	String result = "";
    	if(type.equals("weapon")){
        	for(Item item : equipment){
        		if (item.getEquipmentType().equals("weapon")){
        			result += "[" + item.getEquipmentName() + "]    Weapon damage: " + 
        					item.getPrimaryStat() + "w" + item.getWeaponDamage() + 
        					"     Crit rate:   " + item.getCritRateBonus() + "\n";
        			result += item.getEquipmentDescription() +".\n";
        			result += "Prize:  " + item.getPrize() + " silver coins    Weight:  " + item.getEquipmentWeight() + " lbs\n \n";
        		}
        	}
    	}
    	else if(type.equals("armor")){
        	for(Item item : equipment){
        		if (item.getEquipmentType().equals("armor")){
        			result += "[" + item.getEquipmentName() + "]    Armor Rating: " + item.getArmorRating() + "\n";
        			result += item.getEquipmentDescription() +".\n";
        			result += "Prize:  " + item.getPrize() + " silver coins    Weight:  " + item.getEquipmentWeight() + " lbs\n \n";
        		}
        	}
    	}
    	else if(type.equals("spell")){
        	for(Item item : equipment){
        		if (item.getEquipmentType().equals("spell")){
        			result += "[" + item.getEquipmentName() + "]    Dice: " + 
        					item.getPrimaryStat() + "w" + item.getBaseStat() + 
        					"     Required In:   " + item.getSecondaryStat() + "\n";
        			result += item.getEquipmentDescription() +".\n \n";
        		}
        	}
    	}
    	else{
        	for(Item item : equipment){
        		if (item.getEquipmentType().equals("armor") == false && item.getEquipmentType().equals("weapon") == false && item.getEquipmentType().equals("spell") == false){
        			result += "[" + item.getEquipmentName() + "]\n";
        			result += item.getEquipmentDescription() +".\n";
        			result += "Prize:  " + item.getPrize() + " silver coins    Weight:  " + item.getEquipmentWeight() + " lbs\n \n";
        		}
        	}
    	}
    	return result;
    }
    public int getWallet(){
    	return wallet;
    }
    public void addMoneyToWallet(int money){
    	this.wallet += money;
    }
    public void pay(int money){
    	this.wallet -= money;
    }
}
