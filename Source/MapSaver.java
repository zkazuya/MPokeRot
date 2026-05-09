import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MapSaver {

    private static final String FILE_PATH = "Assets/Maps/Map-save-updated-final.txt";

    public static void saveMap(int[][] map) {

        if (map == null || map.length == 0) {
            System.out.println("Map is empty!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            // LOOP ROWS
            for (int row = 0; row < map.length; row++) {

           
                for (int col = 0; col < map[row].length; col++) {

                    // WRITE TILE NUMBER
                    bw.write(String.valueOf(map[row][col]));

                    // ADD SPACE BETWEEN NUMBERS
                    // BUT NOT AFTER LAST NUMBER
                    if (col < map[row].length - 1) {
                        bw.write(" ");
                    }
                }

                // NEXT LINE AFTER EACH ROW
                bw.newLine();
            }

            System.out.println("Map saved successfully!");

        } catch (IOException e) {

            System.out.println("Error saving map!");
            e.printStackTrace();
        }
    }
}