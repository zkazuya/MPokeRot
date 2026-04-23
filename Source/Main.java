import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame(); // only one JFrame object
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("PokeRot");
        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gamePanel.startGameThread();

        String[][] data = MapLoader.loadMap("Assets/Maps/FinalMap.csv");

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}