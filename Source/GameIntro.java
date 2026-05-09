import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//The Intro for the game (I will explain this code better in f2f meetings)

public class GameIntro extends JPanel {

    private ArrayList<BufferedImage> frames = new ArrayList<>();
    private ArrayList<BufferedImage> gifFrames = new ArrayList<>();
    private Timer timer, gifTimer;

    private int currFrame = 0;
    private int gifCurrFrame = 0;
    private boolean intro = true;
    private boolean showText = false; // dictates whether the text will be showing on the screen or not
    private int screenWidth = 32 * 16;
    private int screenHeight = 32 * 12;

    KeyHandler keyHandler = new KeyHandler();
    GameFrame frame;

    public GameIntro(GameFrame frame) {
        this.frame = frame;
        setFocusable(true);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        addKeyListener(keyHandler);

        for (int i = 1; i <= 564; i++) { // Iterates through each and every single frame and loads them up
                                         // (MultiThreadable sha)
            try {
                if (i < 10) {
                    BufferedImage pic = ImageIO.read(new File("Assets/IntroFrames/00" + i + ".png"));
                    frames.add(pic);
                } else if (i < 100 && i > 9) {
                    BufferedImage pic = ImageIO.read(new File("Assets/IntroFrames/0" + i + ".png"));
                    frames.add(pic);
                } else {
                    BufferedImage pic = ImageIO.read(new File("Assets/IntroFrames/" + i + ".png"));
                    frames.add(pic);
                }
            } catch (IOException e) {
            }
        }

        for (int i = 1; i <= 150; i++) { // Iterates through each and every single frame to load them up
            try {
                if (i < 10) {
                    BufferedImage gifPic = ImageIO.read(new File("Assets/IntroFrames/GifFrames/00" + i + ".png"));
                    gifFrames.add(gifPic);
                } else if (i < 100 && i > 9) {
                    BufferedImage gifPic = ImageIO.read(new File("Assets/IntroFrames/GifFrames/0" + i + ".png"));
                    gifFrames.add(gifPic);
                } else {
                    BufferedImage gifPic = ImageIO.read(new File("Assets/IntroFrames/GifFrames/" + i + ".png"));
                    gifFrames.add(gifPic);
                }
            } catch (IOException e) {
            }
        }

        timer = new Timer(33, e -> { // a timer of 33 miliseconds (30 frames per second)
            currFrame++;
            if (currFrame >= frames.size() || keyHandler.getShiftPressed()) { // if ever the current frame of the intro
                                                                              // animation equals to the amount maximum
                                                                              // frames, it will stop and then switch to
                                                                              // the GIF animation so it can loop
                timer.stop(); // if Shift is pressed then it will immediately go to the GIF
                gifStart(); // method down below
                intro = false; // boolean used to dictate that the intro animation will not be introing
            }
            repaint();
        });
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (!frames.isEmpty() && intro == true) {
            g2d.drawImage(frames.get(currFrame), 0, 0, getWidth(), getHeight(), null);
        } else if (!gifFrames.isEmpty() && intro == false) {
            g2d.drawImage(gifFrames.get(gifCurrFrame), 0, 0, getWidth(), getHeight(), null);
            if (showText) {

                Font font = new Font("Arial", Font.BOLD, 48);
                g2d.setFont(font);
                String title = "Press SPACE to start!";
                int x = ((screenWidth / 2 - g2d.getFontMetrics().stringWidth(title) / 2) + 250);
                int y = screenHeight / 2 + 350;

                // Creating outline for text
                FontMetrics fm = g2d.getFontMetrics(); // gets the measurements of the font
                GlyphVector transformer = font.createGlyphVector(fm.getFontRenderContext(), title); // transforms the
                                                                                                    // text that you
                                                                                                    // have, into a
                                                                                                    // shape so that you
                                                                                                    // can add an
                                                                                                    // outline to it
                Shape textShape = transformer.getOutline(x, y); // adds the outline of the newly transformed text, by
                                                                // casting it as a shape (kind of)

                // colors the outline
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5f));
                g2d.draw(textShape);
                // colors the text
                g2d.setColor(Color.WHITE);
                g2d.fill(textShape);

            }
        }

    }

    public void gifStart() {
        gifTimer = new Timer(33, e -> {
            gifCurrFrame++;
            if (gifCurrFrame >= gifFrames.size()) {
                gifCurrFrame = 0; // whenever the current frame reaches the same amount the frames in the GIF will
                                  // reset back to 0 to make a loop
            }
            if (keyHandler.getSpacePressed()) {
                frame.switchPanel("Game"); // if Space is pressed, then it will go to game
                gifTimer.stop();
            }
            if ((gifCurrFrame >= 10 && gifCurrFrame <= 40) || (gifCurrFrame >= 90 && gifCurrFrame <= 120)) { // this if
                                                                                                             // statement
                                                                                                             // makes it
                                                                                                             // so that
                                                                                                             // the text
                                                                                                             // will
                                                                                                             // only
                                                                                                             // show up
                                                                                                             // every 30
                                                                                                             // frames
                showText = true;
            } else {
                showText = false;
            }

            repaint();
        });
        gifTimer.start();
    }

    public void setIntro() { // makes it so that the intro animation will repeat
        intro = true;
    }
}
