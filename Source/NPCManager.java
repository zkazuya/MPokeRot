import java.awt.Graphics2D;
import java.util.ArrayList;

public class NPCManager {
    GamePanel gamePanel;
    public ArrayList<NPC> npc;
    

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        npc = new ArrayList<>();
    }

    public void setUpNPC() {
        int sTile = gamePanel.getTileSize();
        npc.add(new NPC(gamePanel, sTile * 32, sTile * 9, 0, "Nagpatakas an ayam"));
        npc.add(new NPC(gamePanel, sTile * 15, sTile * 10, 1, "Nurse Yot"));
        npc.add(new NPC(gamePanel, sTile * 60, sTile * 31, 2, "Kuya Shan Shan"));
        npc.add(new NPC(gamePanel, sTile * 39, sTile * 79, 3, "Queen Y"));
        npc.add(new NPC(gamePanel, sTile * 11, sTile * 79, 4, "Sammy strong"));

        npc.get(1).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Holeee"));
        npc.get(1).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Boneca Ambalabu"));
        npc.get(2).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Skibidi Toilet"));
        npc.get(3).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Frulli Frulla"));
        npc.get(4).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Tung Tung Sahur"));

        npc.get(1).getNPCParty().get(0).jumpToLevel(4);
        //npc.get(4).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Brr Brr Patapim"));

        npc.get(0).setDialogues(
            new String[]{"Ah, I see you've chosen your PokeRot!", "Goodluck out there in the wild!"}, 
            new String[]{"Excellent!", "You have great potential. Good luck out there!"});

        npc.get(1).setDialogues(new String[]{"hello there", "yoou da real ART", "hey pookie"}, 
                                new String[]{"damn you beat me bro"});
        npc.get(2).setDialogues(new String[]{"hello there", "I am Kuya Shhh", "And I will be your opponent!"}, 
                                new String[]{"Damn you !", "You are very strong!"} );
        npc.get(3).setDialogues(new String[]{"Oh! Hello there", "I am Queen Y, I work here!", "Want to fight?"}, 
                                new String[]{"Awww dangg it"});
        npc.get(4).setDialogues(new String[]{"Hello there", "I am Sammy one of the Strongest!", "Fight me if you dare!"}, 
                                new String[]{"I never though you could beat me!", "You are worthy!"});
        
        

    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < npc.size(); i++) {
            if (npc.get(i) != null) {
                npc.get(i).draw(g2);
            }
        }
    }
}