
public class SaveUtil {

    public static final int maxSlots = 3;

    public static boolean slotExists (int slot){
        return SaveLoadFiles.fileExists("slot" + slot);  
    }

    public static int findFree(){
        for (int i = 1; i <= 3; i++){
            if(!slotExists(i)){
                return i;
            }
        }return -1;

    }


}
