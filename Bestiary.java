import java.util.*;

/**
 * 
 * This is a class for the games bestiary, here is were
 * all available enemys are stored. To add enemys visit
 * http://paizo.com/pathfinderRPG/prd/monsters/monsterIndex.html
 * and add them with the format under creatMosters() with
 * the appropriate creation format
 * @author Philipp Wolf
 * @version 10.01.2015
 *
 */
public class Bestiary {
    private ArrayList<CharacterSheet> cr0;
    private ArrayList<CharacterSheet> cr1;
    private ArrayList<CharacterSheet> cr2;
    private ArrayList<CharacterSheet> cr3;
    private ArrayList<CharacterSheet> cr4;
    private ArrayList<CharacterSheet> cr5;
    private ArrayList<CharacterSheet> cr6;
    private ArrayList<CharacterSheet> cr7;
    
    public Bestiary(){
    	cr0 = new ArrayList<CharacterSheet>();
    	cr1 = new ArrayList<CharacterSheet>();
    	cr2 = new ArrayList<CharacterSheet>();
    	cr3 = new ArrayList<CharacterSheet>();
    	cr4 = new ArrayList<CharacterSheet>();
    	cr5 = new ArrayList<CharacterSheet>();
    	cr6 = new ArrayList<CharacterSheet>();
    	cr7 = new ArrayList<CharacterSheet>();
    	createMonsters();
    }
    /**
     * Create some Monsters for the encounters.
     * The format in which monsters should be created is 
     * name, hitPoints, Str, Dex, Con, Wis, In, Cha, natural aromr, the monsters weapon, the monsters armor, exp Reward
     * 
     */
    private void createMonsters(){
    	

    	CharacterSheet rat = new CharacterSheet("rat",4,2,15,11,2,13,2,2,"bite","none",100);
    	this.cr0.add(rat);
    	CharacterSheet grimplegremlin = new CharacterSheet("grimple-gremlin",4,3,13,12,10,11,6,2,"bite","none",100);
    	this.cr0.add(grimplegremlin);
    	
    	
    	//add some cr1 opponents (they are cr 1/4 opponents in the orriginal pathfinder bestiary, but since 
    	//the player is alone the cr is adjusted to better represent the challenge for the player)
    	
    	CharacterSheet mite = new CharacterSheet("mite",3,8,13,11,8,13,8,1,"dagger","robe",100);
    	this.cr1.add(mite);
    	CharacterSheet alphaRat = new CharacterSheet("alpha-rat",4,2,16,12,3,14,3,2,"bite","none",135);
    	this.cr1.add(alphaRat);
    	
    	//add some cr2 opponents again they are not actually cr2 but are some original cr1/3 and 1/2 opponents

    	CharacterSheet duergar = new CharacterSheet("duergar",6,12,9,15,10,13,4,0,"warhammer","chainmail",135);
    	this.cr2.add(duergar);
    	CharacterSheet kobold = new CharacterSheet("kobold",4,9,13,10,10,9,8,1,"spear","leatherarmor", 100);
    	this.cr2.add(kobold);
    	CharacterSheet skeleton = new CharacterSheet("skeleton",4,15,14,10,10,10,10,2,"scimitar","chainshirt",135);
    	this.cr2.add(skeleton);
    	CharacterSheet goblin = new CharacterSheet("goblin",6,11,15,12,10,9,6,1,"shortsword","leatherarmor",135);
    	this.cr2.add(goblin);
    	CharacterSheet ratfolk = new CharacterSheet("ratfolk",8,6,15,11,14,10,9,1,"dagger","leatherarmor",135);
    	this.cr2.add(ratfolk);
    	
    	//add some cr3 opponents they now are cr 1 and 2 opponents form the orignal game

    	CharacterSheet zombie = new CharacterSheet("zombie",12,17,10,10,10,10,10,2,"slam","robe", 100);
    	this.cr3.add(zombie);
    	
    	//add some even stronger opponents the cr should always be adjusted
    	CharacterSheet cr4dummy = new CharacterSheet("cr4dummy",12,17,10,10,10,10,10,2,"slam","robe", 100);
    	this.cr4.add(cr4dummy);
    	CharacterSheet cr5dummy = new CharacterSheet("cr5dummy",12,17,10,10,10,10,10,2,"slam","robe", 100);
    	this.cr5.add(cr5dummy);
    	CharacterSheet cr6dummy = new CharacterSheet("cr6dummy",12,17,10,10,10,10,10,2,"slam","robe", 100);
    	this.cr6.add(cr6dummy);
    	CharacterSheet cr7dummy = new CharacterSheet("cr7dummy",12,17,10,10,10,10,10,2,"slam","robe", 100);
    	this.cr7.add(cr7dummy);
    	
    	
    	
    }
    public CharacterSheet getEnemy(int cr){
    	Random rand = new Random();
    	CharacterSheet Enemy;
    	Enemy = cr0.get(rand.nextInt(cr0.size()));
		if(cr == 1){
			if(Roll.dice(2) == 1){
				Enemy = cr0.get(rand.nextInt(cr1.size()));
			}else{
				Enemy = cr1.get(rand.nextInt(cr1.size()));
			}
		}
		if(cr == 2){
			Enemy = cr2.get(rand.nextInt(cr2.size()));
		}
		if(cr == 3){
			Enemy = cr3.get(rand.nextInt(cr3.size()));
		}
		if(cr == 4){
			Enemy = cr4.get(rand.nextInt(cr4.size()));
		}
		if(cr == 5){
			Enemy = cr5.get(rand.nextInt(cr5.size()));
		}
		if(cr == 6){
			Enemy = cr6.get(rand.nextInt(cr6.size()));
		}
		if(cr == 7){
			Enemy = cr7.get(rand.nextInt(cr7.size()));
		}
		return Enemy;
    }
}
