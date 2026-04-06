package entity;

import main.KeyHandler;
import main.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues () {
        x = 100;
        y = 150;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage () {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walknorth1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walknorth2.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walksouth1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walksouth2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walkwest1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walkwest2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walkeast1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walkeast2.png"));

            idleup1 = ImageIO.read(getClass().getResourceAsStream("/res/player/northidle1.png"));
            idleup2 = ImageIO.read(getClass().getResourceAsStream("/res/player/northidle2.png"));

            idledown1 = ImageIO.read(getClass().getResourceAsStream("/res/player/southidle1.png"));
            idledown2 = ImageIO.read(getClass().getResourceAsStream("/res/player/southidle2.png"));

            idleleft1 = ImageIO.read(getClass().getResourceAsStream("/res/player/westidle1.png"));
            idleleft2 = ImageIO.read(getClass().getResourceAsStream("/res/player/westidle2.png"));

            idleright1 = ImageIO.read(getClass().getResourceAsStream("/res/player/eastidle1.png"));
            idleright2 = ImageIO.read(getClass().getResourceAsStream("/res/player/eastidle2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update () {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            }
        }
        spriteCounter++;
        if (spriteCounter > 20) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw (Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        boolean isMoving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        switch (direction) {
            case "up":
                if (isMoving) image = (spriteNum == 1) ? up1 : up2;
                else image = (spriteNum == 1) ? idleup1 : idleup2;
                break;
            case "down":
                if (isMoving) image = (spriteNum == 1) ? down1 : down2;
                else image = (spriteNum == 1) ? idledown1 : idledown2;
                break;
            case "left":
                if (isMoving) image = (spriteNum == 1) ? left1 : left2;
                else image = (spriteNum == 1) ? idleleft1 : idleleft2;
                break;
            case "right":
                if (isMoving) image = (spriteNum == 1) ? right1 : right2;
                else image = (spriteNum == 1) ? idleright1 : idleright2;
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
