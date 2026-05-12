import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Created GameFrame so that we can have the driver class thingy, this is also where we swap our panels

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
        gameIntro = new GameIntro(this);// has "this" so that the Panels will have access to changing panels  "switchPanel()"

        switcher = new CardLayout();

        switcherPanel = new JPanel(switcher);

        switcherPanel.add(gameIntro, "Intro"); // assigning them names, so that we can easily switch with them later on
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
            gamePanel.requestFocusInWindow(); // this is so that the gamePanel will be able to use their keyboard
                                              // because the gui stops when you dont have focus on that component
        }
        switcherPanel.revalidate();
        switcherPanel.repaint();
    }

}