import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NSC implements Serializable
{

	private static final long serialVersionUID = -5966618586666474164L;
    private Random rand = new Random();
    private LootCatalogue availableItems = new LootCatalogue();
    
    
    private ArrayList<String> genericNscNames;
    private ArrayList<String> genericNscTypes;
    private HashMap<String, ArrayList<String>> dialogue;
    private CharacterSheet nscSheet;
    private String nscType;
    
    
    
    public NSC(int cr){
        dialogue = new HashMap<String, ArrayList<String>>();
        genericNscNames = new ArrayList<String>();
        genericNscNames.add("John");
        genericNscNames.add("Jim");
        genericNscTypes = new ArrayList<String>();
        genericNscTypes.add("merchant");
        genericNscTypes.add("warrior");
        genericNscTypes.add("warrior");
        nscType = genericNscTypes.get(rand.nextInt(genericNscTypes.size()));
        nscSheet = new CharacterSheet(genericNscNames.get(rand.nextInt(genericNscNames.size())),20,15,15,15,15,15,15,1,"shortsword","leatherarmor",135);
        createInventory(cr);
        addDialogue();
    }
    
    private void addDialogue(){
        String type = nscType;
        switch(type){
        case "merchant" : 
            addShopkeepDialogue();
            break;
        case "warrior" :
            addAdventurerDialogue();
            break;
        }
        
    }

    
    private void addShopkeepDialogue(){
        ArrayList<String> greetings = new ArrayList<String>();
        greetings.add("Hello there, want to buy anything at my wonderful little Shop?");
        greetings.add("I dont have much for you but if you find something you like tell me?");
        greetings.add("Oh my! You seem Like you could need some new equipment!");
        dialogue.put("greetings", greetings);
        dialogue.put("hello", greetings);
        dialogue.put("hi", greetings);
        ArrayList<String> bye = new ArrayList<String>();
        bye.add("Oh you are already going?");
        bye.add("Thank you very much.");
        bye.add("Feel free to visit my shop anytime!");
        dialogue.put("bye", bye);
        dialogue.put("later", bye);
        ArrayList<String> dontUnderstand = new ArrayList<String>();
        dontUnderstand.add("Could you please be a little bit more frank?");
        dontUnderstand.add("What are you saying?");
        dontUnderstand.add("Sorry, but I don`t understand you, sir.");
        dialogue.put("?", dontUnderstand);
        
    }
    
    private void addAdventurerDialogue(){
        ArrayList<String> greetings = new ArrayList<String>();
        greetings.add("What do ya want?");
        dialogue.put("greetings", greetings);
        dialogue.put("hello", greetings);
        dialogue.put("hi", greetings);
        ArrayList<String> bye = new ArrayList<String>();
        bye.add("Just leave me alone.");
        dialogue.put("bye", bye);
        dialogue.put("later", bye);
        ArrayList<String> dontUnderstand = new ArrayList<String>();
        dontUnderstand.add("Quit fooling around with me!");
        dontUnderstand.add("I dont know what ya mean.");
        dialogue.put("?", dontUnderstand);
        
    }
    
    private void createInventory(int cr){
        switch (this.nscType){
        case "merchant" :
        	Item unarmed = nscSheet.equipment.getEquipedWeapon();
            nscSheet.equipment.addEquipment(availableItems.getItem("shortsword"));
            nscSheet.equipment.equipWeapon(availableItems.getItem("shortsword"));
        	nscSheet.equipment.discardItem(unarmed);
            nscSheet.equipment.addEquipment(availableItems.getItem("robe"));
            nscSheet.equipment.equipArmor(availableItems.getItem("robe"));
            fillShopInventory(cr);
            break;
        case "warrior" :
            nscSheet.equipment.addEquipment(availableItems.getItem("longsword"));
            nscSheet.equipment.equipWeapon(availableItems.getItem("longsword"));
            nscSheet.equipment.addEquipment(availableItems.getItem("half-plate"));
            nscSheet.equipment.equipArmor(availableItems.getItem("half-plate"));
            break;
        }
    }
    public void fillShopInventory(int cr){
        int i = cr + 2;
        while(i > 0){
                Item itemToAdd = availableItems.getLoot(Roll.dice(3));
                this.nscSheet.equipment.addEquipment(itemToAdd);
                i--;
            }
        
       }
    public String getAnswer(String question){
        if(!dialogue.containsKey(question)){
            question = "?";
        }
        return dialogue.get(question).get(rand.nextInt(dialogue.get(question).size()));
    }
    
    public CharacterSheet getNscSheet(){
        return nscSheet;
    }
    public String getNscType(){
        return nscType;
    }
}
