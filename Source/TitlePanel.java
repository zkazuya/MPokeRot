import java.awt.*;

public class TitlePanel {
    private GamePanel gp;
    int commandNum  =0;//default at option 0:create new game

    // constructor
    public TitlePanel(GamePanel gp) {this.gp = gp;}

    public void update(KeyHandler keyHandler) {
        if(commandNum<3||commandNum>=0){
            if (keyHandler.getEnterPressed()) {
                switch(commandNum){
                    case 0:
                        gp.gameState = GameState.TALKINGSTATE; break;
                    case 1:
                        //LOAD SAVED Game
                        break;
                    case 2:
                        System.exit(0); break;
                }    
            }else if(keyHandler.getDownPressed()){
                if(commandNum<2){
                commandNum++;
                }//keyHandler.setDownPressed(false); //hehe
            }else if(keyHandler.getUpPressed()){
                if(commandNum>0){
                commandNum--;
            }//keyHandler.setUpPressed(false);
        }
    }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        String[] buttonNames = {"Start New Game", "Load Saved Game", "Exit"};

        //remove if done with pokerot title screen graphic
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "PokéRot®";
        int x = gp.getScreenWidth() / 2 - g2.getFontMetrics().stringWidth(title) / 2;
        int y = gp.getScreenHeight() / 2 - 50;
        g2.drawString(title, x, y);
        //////////////////////tutob dida



        int width = 250;
        int height = 50;
        int xB = (gp.getScreenWidth() / 2) - (width / 2);
        int yBase = (gp.getScreenHeight() / 2) + 70;

        for(int i = 0; i < 3; i++) {
            int yB = yBase + (i * 60);//spacing 60

            g2.setColor(new Color(0, 0, 0, 210));
            g2.fillRoundRect(xB, yB, width, height, 25, 25);

            if (commandNum == i) {
                g2.setColor(Color.YELLOW); 
            } else {
                g2.setColor(Color.WHITE);
            }

            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(xB, yB, width, height, 25, 25);

            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            int textX = xB + (width / 2) - (g2.getFontMetrics().stringWidth(buttonNames[i]) / 2);
            int textY = yB + (height / 2) + (g2.getFontMetrics().getAscent() / 2) - 5; //ascent is the distance between base of our rectangle and its top
            g2.drawString(buttonNames[i], textX, textY);
}


    }
}