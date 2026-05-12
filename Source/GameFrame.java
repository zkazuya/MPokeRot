

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

// CREATED GAME FRAME SO THAT WE CAN HAVE THE DRIVER CLASS, THIS IS WHERE WE SWAP OUR PANELS

public class GameFrame extends JFrame {
    GamePanel gamePanel;
    JPanel switcherPanel;
    GameIntro gameIntro;
    CardLayout switcher;

    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("PokeRot");
        gamePanel = new GamePanel(this);
        gameIntro = new GameIntro(this);
        switcher = new CardLayout();
        switcherPanel = new JPanel(switcher);
        switcherPanel.add(gameIntro, "Intro");
        switcherPanel.add(gamePanel, "Game");
        add(switcherPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.startGameThread();
    }

    public void switchPanel(String name) {
        if ("Game".equals(name)) {
            switcher.show(switcherPanel, name);
            gamePanel.requestFocusInWindow(); 
        }
        switcherPanel.revalidate();
        switcherPanel.repaint();
    }
}