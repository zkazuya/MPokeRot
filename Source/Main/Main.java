package Main;

import javax.swing.JFrame;

public class Main {
    public static void main (String[] args) {
        JFrame gameWindow = new JFrame(); // only one JFrame object
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // terminate app on exit
        gameWindow.setResizable(false); // not allow window to be resizable
        gameWindow.setTitle("PokeRot"); // sets window title
        GamePanel gamePanel = new GamePanel(); // only one game panel object
        gameWindow.add(gamePanel); // JFrame's add method put there gamePanel
        gameWindow.pack(); // force the window to resize itself to fit our canvas
        gameWindow.setLocationRelativeTo(null); // centralize app on launch THIS SHOULD BE AFTER .pack()
        gameWindow.setVisible(true); // makes frame visible THIS MUST BE AT THE LAST
        gamePanel.startGameThread(); // starts the game thread (runs update and repaint)
    }
}