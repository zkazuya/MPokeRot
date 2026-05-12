package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.GameFrame;

public class GameIntro extends JPanel {

    private BufferedImage introFrame, gifFrame, currIntroFrame, currGifFrame;
    private Timer timer, gifTimer;
    private int currFrame = 0;
    private int gifCurrFrame = 0;
    private boolean intro = true;
    private boolean showText = false; // DICTATES WHETHER THE TEXT WILL BE SHOWING IN THE SCREEN OR NOT
    private int screenWidth = 32 * 16;
    private int screenHeight = 32 * 12;
    private int zeru = 0;
    private int gifFrameSize = 150;
    private int introFrameSize = 560;
    private boolean spacePressed = false;
    private boolean shiftPressed = false;
    GameFrame frame;
    Clip gifLoop;
    Clip titleLoop;

    public GameIntro(GameFrame frame) {
        this.frame = frame;
        setFocusable(true);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = true;
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shiftPressed = true;
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = false;
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shiftPressed = false;
            }
        });
        Clip eyeSound = loadSound("Assets/Music/OpenEyeSound_(2).wav");
        Clip introSound = loadSound("Assets/Music/TitleScreen(no_melody).wav");
        Clip whoosh = loadSound("Assets/Music/woosh.wav");
        
        if (eyeSound != null) eyeSound.start();

        timer = new Timer(33, e -> { // A TIMER OF 33 MILISECOND (30 FPS)
            currFrame++;
            if (currFrame == 162){
                if (eyeSound != null){
                eyeSound.close();
                }if (introSound != null){
                introSound.start();
                }
            }if (currFrame == 509){
                if (whoosh != null){
                whoosh.setFramePosition(0);
                whoosh.start();
                }
            }
            currIntroFrame = loadIntroFrames(currFrame);
            if (currFrame >= introFrameSize || shiftPressed) { // IF CURRENT FRAME OF THE INTRO ANIMATION EQUALS AMOUNT OF MAXIMUM FRAMES THEN STOP AND SWITCH TO
                if (introSound != null){
                    introSound.stop();
                    introSound.close();
                }if (eyeSound != null){
                    eyeSound.stop();
                    eyeSound.close();
                }   
                gifLoop = loadSound("Assets/Music/Titlescreen(1).wav");
                if(gifLoop != null){
                    gifLoop.loop(Clip.LOOP_CONTINUOUSLY); // THE GIF ANIMATION SO IT CAN LOOP
                }
                timer.stop(); // IF SHIFT IS PRESSED THEN IT WILL IMMEDIATELY GO TO GIF
                gifStart(); // METHOD DOWN BELOW
                intro = false; // BOOLEAN USED TO DICTATE WHETHER WILL INTRO OR NOT
            }
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (currIntroFrame != null && intro == true) {
            g2d.drawImage(currIntroFrame, zeru, zeru, getWidth(), getHeight(), null);
        } else if (currGifFrame != null && intro == false) {
            g2d.drawImage(currGifFrame, zeru, zeru, getWidth(), getHeight(), null);
            if (showText) {
                Font font = new Font("Arial", Font.BOLD, 48);
                g2d.setFont(font);
                String title = "Press SPACE to start!";
                int x = ((screenWidth / 2 - g2d.getFontMetrics().stringWidth(title) / 2) + 250);
                int y = screenHeight / 2 + 350;
                // CREATING OUTLINE FOR TEXT
                FontMetrics fm = g2d.getFontMetrics(); // GETS THE MEASURES OF THE FONTS
                // TRANSFORMS THE TEXT THAT YOU HAVE, INTO SHAPE SO THAT YOU CAN ADD AN OUTLINE TO IT
                GlyphVector transformer = font.createGlyphVector(fm.getFontRenderContext(), title); 
                // ADDS THE OUTLINE OF THE NEWLY TRANSFORMED TEXT, BY CASTING IT AS A SHAPE (KIND OF)
                Shape textShape = transformer.getOutline(x, y);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5f));
                g2d.draw(textShape);
                g2d.setColor(Color.WHITE);
                g2d.fill(textShape);
                g2d.dispose();
            }
        }
    }

    public void gifStart() {
        gifTimer = new Timer(33, e -> {
            gifCurrFrame++;
            currGifFrame = loadGifFrames(gifCurrFrame); 
            if (gifCurrFrame >= gifFrameSize) { // WHENEVER THE CURRENT FRAME REACHES THE SAME AMOUNT OF FRAMES IN THE GIF WILL RESET BACK TO 0 TO MAKE LOOP
                gifCurrFrame = 0;        
            }
            if (spacePressed) {
                if (gifLoop != null){
                    gifLoop.stop();
                    gifLoop.close();
                }
                titleLoop = loadSound("Assets/Music/TitleScreenMenu.wav");
                FloatControl gainControl = (FloatControl) titleLoop.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15f);
                if(titleLoop != null){
                    titleLoop.loop(Clip.LOOP_CONTINUOUSLY);
                }
                frame.switchPanel("Game"); // IF SHIFT IS PRESSED THEN IT WILL GO TO GAME
                gifTimer.stop();
            }
            // THIS IF STATEMENT MAKES IT SO THAT THE TEXT WILL ONLY SHOW UP EVERY 30
            if ((gifCurrFrame >= 10 && gifCurrFrame <= 40) || (gifCurrFrame >= 90 && gifCurrFrame <= 120)) {                                                                                         // frames
                showText = true;
            } else {
                showText = false;
            }
            repaint();
        });
        gifTimer.start();
    }

    public BufferedImage loadIntroFrames(int i){
        try {
            if (i < 10) return ImageIO.read(new File("Assets/IntroFrames/00" + i + ".png"));
            else if (i < 100 && i > 9) return ImageIO.read(new File("Assets/IntroFrames/0" + i + ".png"));
            else return ImageIO.read(new File("Assets/IntroFrames/" + i + ".png"));   
        } catch (IOException e) { }
        return null;
    }

    public BufferedImage loadGifFrames(int i){
        try {
             if (i < 10) {
                    return ImageIO.read(new File("Assets/IntroFrames/GifFrames/00" + i + ".png"));
                    
                } else if (i < 100 && i > 9) {
                    return ImageIO.read(new File("Assets/IntroFrames/GifFrames/0" + i + ".png"));
                    
                } else {
                    return ImageIO.read(new File("Assets/IntroFrames/GifFrames/" + i + ".png"));
                    
                }
        } catch (IOException e) { }
        return null;
    }

    public void setIntro() { // MAKES IT SO INTRO ANIMATION CAN REPEAT
        intro = true;
    }

    public Clip loadSound(String path){
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}