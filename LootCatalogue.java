import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class LootCatalogue implements Serializable

{
	private static final long serialVersionUID = -5966618586666474164L;
	private ArrayList<Item> garbageLoot;
	private ArrayList<Item> normalLoot;
	private ArrayList<Item> rareLoot;
	private ArrayList<Item> epicLoot;
	private ArrayList<Item> spells;
	private ArrayList<Item> allItems;
	
	public LootCatalogue(){
		garbageLoot = new ArrayList<Item>();
		normalLoot = new ArrayList<Item>();
		rareLoot = new ArrayList<Item>();
		epicLoot = new ArrayList<Item>();
		spells = new ArrayList<Item>();
		allItems = new ArrayList<Item>();
		
    	createItems();
	}
	
	/**
	 * Create a bunch of Items with different stats and dsecriptions.
	 * The default Format in which Items are created is:
	 * prefix, name, type, stat, description, weight, prize (in silver pieces)
	 * you can also create items with advanced stats like armor with dexterity penalties
	 * prefix, name, type, base stat, additional stat, advanced stat, description, weight, prize (in silver pieces)
	 */
	private void createItems(){
		
		//add some garbage
		
		Item dagger = new Item("","dagger","weapon",4,1,19,"a short nearly broken [dagger]",1,20);
    	garbageLoot.add(dagger);
    	Item punchingdagger = new Item("","punching-dagger","weapon",4,"a [punching-dagger] for deadly thrust attacks",1,20);
    	garbageLoot.add(punchingdagger);
    	Item gauntlet = new Item("","gauntlet","weapon",3,1,19,"a single [gauntlet] for even better punches",1,20);
    	garbageLoot.add(gauntlet);
    	Item trash = new Item("","trash","garbage",0,"a bunch of [trash]",4,1);
    	garbageLoot.add(trash);
    	Item robe = new Item("","robe","armor",0,"a worn and shabby looking [robe]",6,3);
    	garbageLoot.add(robe);
		Item garbage = new Item("","garbage","garbage",0,"a small pile of dirt and old unidentifiable [garbage]",1,0);
		garbageLoot.add(garbage);
		Item junk = new Item("","junk","garbage",0,"some [junk]",2,0);
		garbageLoot.add(junk);
    	
    	//add some potions

    	Item potionOfCureLightWounds = new Item("heal","potion-of-cure-light-wounds","potion",8,2,80,"a bootle with a [potion-of-cure-light-wounds]",4,150);
    	garbageLoot.add(potionOfCureLightWounds);
		
		//add some decent weapons
		
		Item sickle = new Item("","sickle","weapon",6,"a [sickle] that cuts wheat and throats",4,150);
    	normalLoot.add(sickle);
    	Item flail = new Item("","flail","weapon",8,"a [flail] with eight deadly spikes",5,80);
    	normalLoot.add(flail);
    	Item longsword = new Item("","longsword","weapon",8,1,19,"a standart [longsword] used by many adventurers",4,150);
    	normalLoot.add(longsword);
    	Item rapier = new Item("","rapier","weapon",6,1,18,"a [rapier] prefered by nobles and duelists",2,200);
    	normalLoot.add(rapier);
    	Item spear = new Item("","spear","weapon",8,"a [spear] perfectly suited to stab something",9,20);
    	normalLoot.add(spear);
    	Item falchion = new Item("","falchion","weapon",4,2,18,"a heavy looking [falchion]",8,750);
    	normalLoot.add(falchion);
    	Item shortsword = new Item("","shortsword","weapon",6,"a [shortsword], the adventurers first choice",8,100);
    	normalLoot.add(shortsword);
    	Item scimitar = new Item("","scimitar","weapon",6,1,18,"a curved [scimitar] that looks kind of exotic",4,150);
    	normalLoot.add(scimitar);

    	//add some decent armors
    	
    	Item hide = new Item("","hide","armor", 4,3,0,"a set of [hide] armor",13,25);
    	normalLoot.add(hide);
    	
    	//add some decent potions

    	//add some rare weapons
    	
    	Item mwkdagger = new Item("mwk","dagger+1","weapon",4,"a short masterfully worked [dagger+1]",1,320);
    	rareLoot.add(mwkdagger);
    	Item mwkpunchingdagger = new Item("mwk","punching-dagger+1","weapon",4,"a masterfully worked [punching-dagger+1]",1,320);
    	rareLoot.add(mwkpunchingdagger);
    	Item mwkgauntlet = new Item("mwk","gauntlet+1","weapon",3,"a single masterfully worked [gauntlet+1]",1,320);
    	rareLoot.add(mwkgauntlet);
    	
    	//add some rare armors

    	Item fullplate = new Item("","full-plate","armor",9,0,0,"a some heavy [full-plate] armor",50,15000);
    	rareLoot.add(fullplate);
    	Item halfplate = new Item("","half-plate","armor",8,1,0,"a heavy set of [half-plate] armor",50,6000);
    	rareLoot.add(halfplate);
    	
    	//add some rare potions

    	Item potionOfCureMediumWounds = new Item("heal","potion-of-cure-medium-wounds","potion",8,2,76,"a bootle with a [potion-of-cure-medium-wounds]",4,150);
    	normalLoot.add(potionOfCureMediumWounds);

    	//add some epic armors
    	
    	Item mwklongsword = new Item("mwk","longsword+1","weapon",8,"a masterfully worked [longsword+1]",4,450);
    	epicLoot.add(mwklongsword);
    	Item mwkspear = new Item("mwk","spear+1","weapon",8,"a masterfully worked [spear+1]",9,320);
    	epicLoot.add(mwkspear);
    	
    	//add some epic armors
    	
    	
    	//add loot that isn't  normally available
    	
    	Item unarmedattack = new Item("","unarmed-attack","weapon",3,"an [unarmed-attack] with you bare hands",0,0);
    	allItems.add(unarmedattack);
    	Item slam = new Item("","slam","weapon",6,"a [slam] with your whole body weight",0,0);
    	allItems.add(slam);
    	Item bite = new Item("","bite","weapon",3,"a [bite] with your fangs",0,0);
    	allItems.add(bite);
    	Item none = new Item("","none","armor",0,"nothing",0,0);
    	allItems.add(none);
		Item map = new Item("","map","other",0,0,0,"a [map] given to you by the storyteller who send you here",0,0);
    	allItems.add(map);
    	
    	//add spells

    	Item cureLightWounds = new Item("restoration","cure-light-wounds","spell",8,1,8,"You heal yourself and small wounds begin to close",2,0);
    	spells.add(cureLightWounds);
    	Item cureMediumWounds = new Item("restoration","cure-medium-wounds","spell",8,1,11,"You heal yourself and even moderate wounds close",2,0);
    	spells.add(cureMediumWounds);
    	Item burningHands = new Item("destruction","burning-hands","spell",6,1,15,"A stream of fire emerges from your hands, burning the enemy alive",1,0);
    	spells.add(burningHands);
    	Item greaterBurningHands = new Item("destruction","greater-burning-hands","spell",6,2,23,"A giant pillar of fire emerges from your hands , burning the enemy alive",1,0);
    	spells.add(greaterBurningHands);
    	Item acidDart = new Item("summoning","acid-dart","spell",4,2,18,"You shot a tiny stream of acid into your opponents direction",0,1);
    	spells.add(acidDart);
    	
    	for(Item item : garbageLoot){
    		allItems.add(item);
    	}
    	for(Item item : normalLoot){
    		allItems.add(item);
    	}
    	for(Item item : rareLoot){
    		allItems.add(item);
    	}
    	for(Item item : epicLoot){
    		allItems.add(item);
    	}
    	for(Item item : spells){
    		allItems.add(item);
    	}
    }
	public Item getLoot(int rarity){
    	Random rand = new Random();
    	Item loot;
    	loot = garbageLoot.get(rand.nextInt(garbageLoot.size()));
    	if(rarity == 1){
    		loot = normalLoot.get(rand.nextInt(normalLoot.size()));
    	}
    	else if(rarity == 2){
			loot = rareLoot.get(rand.nextInt(rareLoot.size()));
		}
		else if(rarity == 3){
			loot = epicLoot.get(rand.nextInt(epicLoot.size()));
		}
    	return loot;
    }
	
	public Item getItem(String itemToGet){
		for(Item item : allItems){
    		if(item.getEquipmentName().equals(itemToGet))
    			return item;
    	}
		return null;
	}
	
	public ArrayList<Item> getAllItems(){
		return allItems;
	}
}
