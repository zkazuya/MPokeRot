import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {

    private static final String filepath = "Assets/Maps/Map-save-updated-final.txt";

    public static int[][] loadMap() {

        ArrayList<int[]> rows = new ArrayList<>();

        try (BufferedReader BR = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = BR.readLine()) != null) {
                String[] values = line.split(",");

                int[] row = new int[values.length];

                for (int i = 0; i < values.length; i++) {
                    row[i] = Integer.parseInt(values[i]);
                }
                rows.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rows.toArray(new int[0][]);
    }
}
