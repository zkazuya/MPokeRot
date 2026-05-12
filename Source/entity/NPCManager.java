package entity;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;

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
        npc.add(new NPC(gamePanel, sTile * 137, sTile * 71, 5, "Scoobaa Scoob"));
        npc.add(new NPC(gamePanel, sTile * 68, sTile * 53, 6, "Numero Uno"));
        npc.add(new NPC(gamePanel, sTile * 12, sTile * 47, 7, "Mark"));
        npc.add(new NPC(gamePanel, sTile * 57, sTile * 67, 8, "Martino Croco"));
        npc.add(new NPC(gamePanel, sTile * 66, sTile * 93, 9, "Skrill buddy"));
        npc.add(new NPC(gamePanel, sTile * 140, sTile * 62, 10, "Annie M. Pito"));
        npc.add(new NPC(gamePanel, sTile * 130, sTile * 95, 11, "Pitou A-nim"));

        npc.get(2).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Skibidi Toilet"));
        npc.get(3).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Frulli Frulla"));
        npc.get(4).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Tung Tung Sahur"));


        npc.get(5).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("67"));
        npc.get(6).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Bobrini Cocosini"));
        npc.get(7).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Brr Brr Patapim"));
        npc.get(8).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Cappuccino Assassino"));
        npc.get(9).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Tung Tung Sahur"));
        npc.get(10).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("La Vaca Saturno Saturnita"));
        npc.get(11).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Udin Din Din Dun"));
        // npc.get(4).addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Brr Brr
        // Patapim"));

        npc.get(0).setDialogues(
                new String[] { "Ah, I see you've chosen your PokeRot!", "Let's test its strength right now!" },
                new String[] { "Excellent!", "You have great potential. Good luck out there!" });
        npc.get(1).setDialogues(new String[] { "Welcome to UP Clinic!", "Let me take your PokeRots for a second!", "*Da-da-da-da-da!*", "There you go they are fully healed!", "Come back anytime!" },
                new String[] { "damn you beat me bro" });
        npc.get(2).setDialogues(new String[] { "hello there", "I am Kuya Shhh", "And I will be your opponent!" },
                new String[] { "Damn you !", "You are very strong!" });
        npc.get(3).setDialogues(new String[] { "Oh! Hello there", "I am Queen Y, I work here!", "Want to fight?" },
                new String[] { "Awww dangg it" });
        npc.get(4).setDialogues(
                new String[] { "Hello there", "I am Sammy one of the Strongest!", "Fight me if you dare!" },
                new String[] { "I never though you could beat me!", "You are worthy!" });
        npc.get(5).setDialogues(
                new String[] { "Hey there, young trainer!", "Your PokeRot looks full of energy!",
                        "Care for a quick battle?" },
                new String[] { "Whoa... your bond with your PokeRot is incredible!",
                        "Keep training and you'll go far!" });
        npc.get(5).getNPCParty().get(0).jumpToLevel(4);
        npc.get(6).setDialogues(
                new String[] { "The winds today feel perfect for battling!", "Show me what your team can do!" },
                new String[] { "Hah! That was an exciting match!", "Your strategy caught me off guard!" });
        npc.get(7).setDialogues(
                new String[] { "I train every single day beside the tall grass.", "Strong trainers never back down!",
                        "Battle me now!" },
                new String[] { "Looks like my training still isn't enough...", "You're seriously talented!" });
        npc.get(8).getNPCParty().get(0).jumpToLevel(4);
        npc.get(8).setDialogues(
                new String[] { "Shhh... listen carefully.", "Wild PokeRot gather around powerful trainers.",
                        "Let's see if the rumors are true!" },
                new String[] { "Amazing...", "Your PokeRot fought with pure heart!" });
        npc.get(9).setDialogues(
                new String[] { "Yo! I've been waiting for a worthy challenger!",
                        "Think you can defeat my favorite PokeRot?" },
                new String[] { "No way!", "Your team is stronger than I expected!" });
        npc.get(9).getNPCParty().get(0).jumpToLevel(3);
        npc.get(10).setDialogues(
                new String[] { "Greetings, traveler.", "Many trainers pass through this route.",
                        "Few can defeat me though!" },
                new String[] { "Impressive victory.", "You battle just like a true champion!" });
        //npc.get(1).getNPCParty().get(0).jumpToLevel(2);
        npc.get(11).setDialogues(
                new String[] { "Bwahaha!", "My PokeRot and I are unbeatable!", "Prepare yourself!" },
                new String[] { "What?! Impossible!", "I guess even legends can lose sometimes..." });
        npc.get(11).getNPCParty().get(0).jumpToLevel(4);
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < npc.size(); i++) {
            if (npc.get(i) != null) {
                npc.get(i).draw(g2);
            }
        }
    }
}
