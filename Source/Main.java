import javax.swing.JFrame;

public class Main {
    public static void main (String[] args) {
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
    }
}