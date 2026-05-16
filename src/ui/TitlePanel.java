package ui;

import io.SaveData;
import io.SaveLoadFiles;
import io.SaveUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GameState;
import pokerot.PokeRot;
import system.KeyHandler;

public class TitlePanel {
    private GamePanel gp;
    private int commandNum = 0; // DEFAULT AT OPTION 0: CREATE A GAME
    private boolean isOnSave = false;
    private boolean isOnLoad = false;
    private boolean isTyping = false;
    private String playerName = "";
    private int slotNumber;
    private boolean[] slotExists = new boolean[3];
    private SaveData[] slotData = new SaveData[3];
    private boolean pickingSlot = false;
    private String buttonSound = "Assets/Music/buttSelect.wav";
    private ArrayList<BufferedImage> Ui = new ArrayList<>();
    private int keyCooldown;
    
    public TitlePanel(GamePanel gp) {
        this.gp = gp;
        loadUi();
    }

    public void update(KeyHandler keyHandler) {
        if (keyCooldown > 0){
            keyCooldown--;
        } else {
            if((commandNum<3 ||commandNum>=0) && isOnLoad == false && isOnSave == false){
                if (keyHandler.getEnterPressed()) {
                    switch(commandNum){
                        case 0:
                            isOnSave = true;
                            commandNum = 1;
                            keyCooldown = 8;
                            break;
                        case 1:
                            isOnLoad = true;
                            refreshSlots(); 
                            commandNum = 1;
                            keyCooldown = 8;
                            break;
                        case 2:
                            System.exit(0); 
                            break;
                    }   
                } else if(keyHandler.getDownPressed()){
                    if(commandNum<2){
                        commandNum++;
                        keyCooldown = 8;
                    }
                } else if(keyHandler.getUpPressed()){
                    if(commandNum>0){
                        commandNum--;
                        keyCooldown = 8;
                    }
                }
            }

            // THIS IS WHEN YOU PICK THE CREATE NEW SAVE AND WHERE YOU HAVE TO ENTER YOUR NAME
            else if(isOnSave == true){
                if (commandNum == 0){
                    isTyping = true;
                    keyHandler.IfTypingTrue(isTyping);
                    
                    if (keyHandler.isCharTyped() && keyCooldown == 0){
                        char c = keyHandler.getTypedChar();
                        keyCooldown = 5;

                        if (c == '\b'){
                            if(playerName.length() > 0){
                                playerName = playerName.substring(0, playerName.length() - 1);
                            }
                        }
                        if (Character.isLetterOrDigit(c)){
                            if(playerName.length() < 12){
                                playerName += c;
                            }
                        }
                        keyHandler.resetTypedChar();
                    }
                    if (keyHandler.getEnterPressed()){
                        keyCooldown = 8;
                        isTyping = false;
                        keyHandler.IfTypingTrue(isTyping);
                        commandNum++;
                    }
                }
                else if(keyHandler.getEnterPressed() && isTyping == false){
                    switch(commandNum){    
                        case 1: 
                            if(pickingSlot != true){
                                int freeSlot = SaveUtil.findFree(); 
                                if(freeSlot != -1){
                                    SaveData data = new SaveData();
                                    data.setPlayerName(playerName);
                                    data.setPlayerParty(gp.getPlayer().getPlayerParty());
                                    data.setPlayerX(gp.getPlayer().getX());
                                    data.setPlayerY(gp.getPlayer().getY());

                                    SaveLoadFiles.startSaving(data, "slot" + (freeSlot));
                                    slotNumber = freeSlot;
                                    
                                    keyHandler.setEnterPressed(false);
                                    gp.gameState = GameState.STARTERSTATE;
                                } else {
                                    System.out.println("No slots");
                                }
                            } else {
                                SaveData data = new SaveData();
                                data.setPlayerName(playerName);
                                data.setPlayerParty(gp.getPlayer().getPlayerParty());
                                data.setPlayerX(gp.getPlayer().getX());
                                data.setPlayerY(gp.getPlayer().getY());

                                SaveLoadFiles.startSaving(data, "slot" + slotNumber);
                                
                                keyHandler.setEnterPressed(false);
                                gp.gameState = GameState.STARTERSTATE;
                            }
                            break;
                        case 2: 
                            isOnSave = false;
                            commandNum = 0;
                            keyCooldown = 8;
                            pickingSlot = false;
                            break;
                    }
                } else if (keyHandler.getDownPressed() && isTyping == false){
                    if (commandNum < 2){
                        commandNum++;
                        keyCooldown = 8;
                    }
                } else if (keyHandler.getUpPressed() && isTyping == false){
                    if (commandNum > 0){
                        commandNum--;
                        keyCooldown = 8;
                    }
                }
            }
            
            // LOADING MENU
            else if((commandNum > 0 || commandNum < 4) && isOnLoad == true){
                if(keyHandler.getEnterPressed()){
                    switch(commandNum){
                        case 0: 
                            if(!slotExists[commandNum]){
                                keyCooldown = 8;
                                pickingSlot = true;
                                slotNumber = 1;
                                isOnLoad = false;
                                isOnSave = true;
                                commandNum = 1;
                            } else {
                                keyCooldown = 8;
                                playerName = slotData[commandNum].getPlayerName();
                                gp.getPlayer().setPlayerParty(slotData[commandNum].getPlayerParty());
                                gp.getPlayer().setX(slotData[commandNum].getPlayerX());
                                gp.getPlayer().setY(slotData[commandNum].getPlayerY());

                                for (PokeRot p : gp.getPlayer().getPlayerParty()){
                                    p.initAfterLoad();
                                }
                                slotNumber = 1;
                                gp.gameState = GameState.ROAMSTATE;
                            }
                            break;
                        case 1:
                            if(!slotExists[commandNum]){
                                keyCooldown = 8;
                                pickingSlot = true;
                                slotNumber = 2;
                                isOnLoad = false;
                                isOnSave = true;
                                commandNum = 1;
                            } else {
                                keyCooldown = 8;
                                playerName = slotData[commandNum].getPlayerName();
                                gp.getPlayer().setPlayerParty(slotData[commandNum].getPlayerParty());
                                gp.getPlayer().setX(slotData[commandNum].getPlayerX());
                                gp.getPlayer().setY(slotData[commandNum].getPlayerY());

                                for (PokeRot p : gp.getPlayer().getPlayerParty()){
                                    p.initAfterLoad();
                                }
                                slotNumber = 2;
                                gp.gameState = GameState.ROAMSTATE;
                            }
                            break;
                        case 2:
                            if(!slotExists[commandNum]){
                                keyCooldown = 8;
                                pickingSlot = true;
                                slotNumber = 3;
                                isOnLoad = false;
                                isOnSave = true;
                                commandNum = 1;
                            } else {
                                keyCooldown = 8;
                                playerName = slotData[commandNum].getPlayerName();
                                gp.getPlayer().setPlayerParty(slotData[commandNum].getPlayerParty());
                                gp.getPlayer().setX(slotData[commandNum].getPlayerX());
                                gp.getPlayer().setY(slotData[commandNum].getPlayerY());

                                for (PokeRot p : gp.getPlayer().getPlayerParty()){
                                    p.initAfterLoad();
                                }
                                slotNumber = 3;
                                gp.gameState = GameState.ROAMSTATE;
                            }
                            break;
                        case 3: 
                            isOnLoad = false;
                            keyCooldown = 8;
                            commandNum = 0;
                            break;
                    }
                } else if (keyHandler.getBackSpacePressed()){    
                    switch(commandNum){
                        case 0: 
                            if (slotExists[commandNum]){
                                keyCooldown = 8;
                                SaveLoadFiles.deleteSave("slot1");
                                refreshSlots();
                            }
                            break;
                        case 1: 
                            if (slotExists[commandNum]){
                                keyCooldown = 8;
                                SaveLoadFiles.deleteSave("slot2");
                                refreshSlots();
                            }
                            break;
                        case 2: 
                            if (slotExists[commandNum]){
                                keyCooldown = 8;
                                SaveLoadFiles.deleteSave("slot3");
                                refreshSlots();
                            }
                            break;
                        case 3: 
                            isOnLoad = false;
                            keyCooldown = 8;
                            commandNum = 0;
                            break;
                    }
                } else if (keyHandler.getDownPressed()){
                    if (commandNum < 3){
                        commandNum++;
                        keyCooldown = 8;
                    }
                } else if (keyHandler.getUpPressed()){
                    if (commandNum > 0){
                        commandNum--;
                        keyCooldown = 8;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(18, 50, 77));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        int width = 250;
        int height = 150;
        int xB = (gp.getScreenWidth() / 2) - (width / 2);
        int yBase = (gp.getScreenHeight() / 2) + 70;

        if (isOnLoad != true && isOnSave != true){
            g2.drawImage(Ui.get(6), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
       
            for(int i = 0; i < 3; i++) {
                int yB = yBase + (i * 80);
                if (commandNum == i) {
                    g2.drawImage(Ui.get(i+3), xB, yB, width, height, null);
                } else {
                    g2.drawImage(Ui.get(i), xB, yB, width, height, null);
                } 
            }
        } else if (isOnSave == true){
            g2.drawImage(Ui.get(14), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
            g2.drawImage(Ui.get(8), gp.getScreenWidth() / 2 - 290, gp.getScreenHeight() / 2 - 64, width + 350, height + 20, null);
            
            g2.setColor(Color.BLACK);
              g2.drawString(playerName, gp.getScreenWidth() / 2 - 180, gp.getScreenHeight() / 2 + 30);

            for(int i = 1; i <= 2; i++){
                int yB = yBase + (i * 80);
                if (commandNum == i){
                    g2.drawImage(Ui.get(i + 11), xB, yB, width, height, null);
                } else {
                    g2.drawImage(Ui.get(i + 9), xB, yB, width, height, null);
                }
            }
        } else if (isOnLoad == true){
            g2.drawImage(Ui.get(7), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
            for (int i = 0; i < 4; i++){
                int yB = (yBase + (i * 90) - 250); 
                if (commandNum == i){
                    if(commandNum == 3){
                        g2.drawImage(Ui.get(13), xB, yB + 50, width, height, null);
                    } else {
                        g2.drawImage(Ui.get(9), xB - 150, yB, width + 300, height + 50, null);
                    }
                } else {
                    if (i == 3){
                        g2.drawImage(Ui.get(11), xB, yB + 50, width, height, null);
                    } else {
                        g2.drawImage(Ui.get(8), xB - 150, yB, width + 300, height + 50, null);
                    }
                }

                if(i < 3){
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));   
                    if(slotExists[i]){
                        g2.drawString(slotData[i].getPlayerName(), xB - 50, yB + 100);
                    } else {
                        g2.drawString("Create New Save", xB - 50, yB + 100);
                    }
                }
            }
        }
    }

    public void loadUi(){
        for(int i = 1; i <= 15; i++){ 
            try {
                BufferedImage uiPic = ImageIO.read(getClass().getResourceAsStream(("Assets/Ui/" + i + ".png")));
                Ui.add(uiPic);
            } catch (IOException e) {
                System.out.println("MISSING UI FILE: Assets/Ui/" + i + ".png");
                Ui.add(null); // Add null to preserve the index order so Ui.get(6) doesn't crash!
            }
        }
    }

    public void setTitleState(){
        gp.gameState = GameState.TITLESCREEN;
        isOnSave = false;
        isOnLoad = false;
        isTyping = false;
        pickingSlot = false;
        playerName = "";
        commandNum = 0;
        keyCooldown = 8;
        slotNumber = 1;
        refreshSlots();
    }

    public int getSlotNumber(){ return slotNumber; }
    public String getPlayerName(){ return playerName; }

    public void refreshSlots(){
        for(int i = 0; i < 3; i++){
            int slotNumber = i + 1;
            slotExists[i] = SaveLoadFiles.fileExists("slot" + slotNumber);
            if(slotExists[i]){
                slotData[i] = SaveLoadFiles.loadGame("slot" + slotNumber);
            } else {
                slotData[i] = null;
            }
        }
    }  
}