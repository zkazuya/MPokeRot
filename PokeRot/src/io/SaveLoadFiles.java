package io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import main.GamePanel;

public class SaveLoadFiles {

    public static File getSaveDirectory(){

        String appDataPath = System.getenv("APPDATA");

        File saveDir = new File(appDataPath + File.separator + "PokeRot");

        if (!saveDir.exists()){
            saveDir.mkdirs();
        }

        return saveDir;
    }

    public static File getFile(String slotName){
        return new File(getSaveDirectory(), slotName + ".sav");
    }


    public static void startSaving(SaveData savedData, String slotName){
        new Thread(() -> {
            saveGame(savedData, slotName);
        }).start();
    }

    public static void saveGame(SaveData savedData, String slotName){
        
        try {
            
            File saveFile = getFile(slotName);

            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(savedData);
            os.close();
            System.out.println("Saved Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData loadGame(String slotName){
        try{

            File loadingFile = getFile(slotName);

            FileInputStream fis = new FileInputStream(loadingFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            SaveData data = (SaveData) ois.readObject();
            ois.close();
            System.out.println("Game Loaded!");
            return data;
        }catch (IOException | ClassNotFoundException e ){
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteSave(String slotName){

        File deleteFile = getFile(slotName);

        if(deleteFile.exists()){
            if(deleteFile.delete()){
                System.out.println("tinapon ko na ang bola !");
            }else {
                System.out.println("hindi talaga na delete dol" + slotName);
            }
        }else {
            System.out.println("Wala naman");
        }
    }

    public static boolean fileExists(String slotName){
        File file = getFile(slotName);
        return file.exists();
    }

    public static void saveRightNow(GamePanel gp, int slotNumber){
        SaveData data = new SaveData();
         data.setPlayerName(gp.titlePanel.getPlayerName());
         data.setPlayerParty(gp.getPlayer().getPlayerParty());
         data.setPlayerX(gp.getPlayer().getX());
         data.setPlayerY(gp.getPlayer().getY());
         startSaving(data, "slot" + slotNumber);
    }
}