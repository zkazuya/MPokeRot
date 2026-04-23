import java.awt.*;

public class TitlePanel {
    private GamePanel gp;

    // constructor
    public TitlePanel(GamePanel gp) {
        this.gp = gp;
    }

    public void update(KeyHandler keyHandler) {
        if (keyHandler.getFPressed()) {
            gp.gameState = GameState.TALKINGSTATE;
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "PokéRot®";
        int x = gp.getScreenWidth() / 2 - g2.getFontMetrics().stringWidth(title) / 2;
        int y = gp.getScreenHeight() / 2 - 50;
        g2.drawString(title, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        String subtitle = "Press F to Start";
        x = gp.getScreenWidth() / 2 - g2.getFontMetrics().stringWidth(subtitle) / 2;
        y += 100;
        g2.drawString(subtitle, x, y);
    }
}