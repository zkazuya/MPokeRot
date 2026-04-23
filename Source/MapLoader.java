import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {

    public static String[][] loadMap(String filepath) {
        ArrayList<String[]> rows = new ArrayList<>();

        try (BufferedReader BR = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = BR.readLine()) != null) {
                String[] values = line.split(","); // splitting the csv
                rows.add(values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rows.toArray(new String[0][]);
    }
}
