import java.io.*;

public class SaveLoadFiles {

    public static void startSaving(SaveData savedData, String slotName){
        new Thread(() -> {
            saveGame(savedData, slotName);
        }).start();
    }


    public static void saveGame(SaveData savedData, String slotName){
        try {
            File folder = new File("saves");
            if (!folder.exists()){
                folder.mkdir();
            }
            FileOutputStream fos = new FileOutputStream("saves/" + slotName + ".sav");
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
            FileInputStream fis = new FileInputStream("saves/" + slotName + ".sav");
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

        File file = new File("saves/" + slotName + ".sav");

        if(fileExists(slotName)){

            if(file.delete()){
                System.out.println("tinapon ko na ang bola !");
            }else {
                System.out.println("hindi talaga na delete dol" + slotName);
            }

        }else {
            System.out.println("Wala naman");
        }
    }

    public static boolean fileExists(String slotName){

        File file = new File("saves/" +slotName +".sav");
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
