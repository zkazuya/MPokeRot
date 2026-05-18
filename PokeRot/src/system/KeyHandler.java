package system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import main.GameState;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean enterPressed; // BATTLE CHANGE PHASE KEY
    private boolean escPressed; // CANCEL/GOING BACK KEY
    private boolean fPressed; // DIALOGUE INTERACTION KEY
    private boolean spacePressed; 
    private boolean shiftPressed; 
    private boolean backSpacePressed;
    private char typedChar;
    private boolean charTyped;
    private boolean ifTyping = false;
    private GamePanel gamePanel;

    public KeyHandler (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped (KeyEvent input) {
        if (ifTyping){
        typedChar = input.getKeyChar();
        charTyped = true;
        }
    } // NOT USED BUT MUST BE IMPORTED

    @Override
    public void keyPressed (KeyEvent input) { // THIS METHOD IS TRIGGERED WHEN A KEY IS HELD
        int code = input.getKeyCode(); 
        switch (code) {
            // DIRECTIONAL UI NAVIGATION SOUNDS
            case KeyEvent.VK_W -> { 
                if (!upPressed) playMenuSound("Assets/Music/buttSelect.wav", true);
                upPressed = true; 
            }
            case KeyEvent.VK_A -> { 
                if (!leftPressed) playMenuSound("Assets/Music/buttSelect.wav", true);
                leftPressed = true; 
            }
            case KeyEvent.VK_S -> { 
                if (!downPressed) playMenuSound("Assets/Music/buttSelect.wav", true);
                downPressed = true; 
            }
            case KeyEvent.VK_D -> { 
                if (!rightPressed) playMenuSound("Assets/Music/buttSelect.wav", true);
                rightPressed = true; 
            }
            
            // GENERAL INTERACTION AND ACTION SOUNDS
            case KeyEvent.VK_F -> { 
                if (!fPressed) playMenuSound("Assets/Music/buttSelect.wav", false);
                fPressed = true; 
            }
            case KeyEvent.VK_ENTER -> { 
                if (!enterPressed) playMenuSound("Assets/Music/buttSelect.wav", false);
                enterPressed = true; 
            }
            case KeyEvent.VK_SPACE -> { 
                if (!spacePressed) playMenuSound("Assets/Music/buttSelect.wav", false);
                spacePressed = true; 
            }
            case KeyEvent.VK_ESCAPE -> { 
                if (!escPressed) playMenuSound("Assets/Music/buttSelect.wav", false);
                escPressed = true; 
            }
            case KeyEvent.VK_BACK_SPACE -> { 
                if (!backSpacePressed) playMenuSound("Assets/Music/buttSelect.wav", false);
                backSpacePressed = true; 
            }
        }
    }

    // NEW HELPER METHOD TO ROUTE CRISP AUDIO MENU CLICKS SAFELY WITHOUT BACKGROUND NPISE LOOP OVERLAP
    private void playMenuSound(String soundPath, boolean isDirectionalKey) {
        if (gamePanel == null) return;
        if (isDirectionalKey && gamePanel.gameState == GameState.ROAMSTATE) return; 
        // DIRECT EXECUTION ACROSS ANY BACKGORUND LAYOUT MENU LAYERS
       // SoundHelper.playSound(soundPath);
    }

    @Override
    public void keyReleased (KeyEvent input) { // TRIGGERS AFTER PRESSING A KEY
        int code = input.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_F -> fPressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
            case KeyEvent.VK_ESCAPE -> escPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_SHIFT -> shiftPressed = false;
            case KeyEvent.VK_BACK_SPACE -> backSpacePressed = false;
        }
    }
    
    public boolean getUpPressed () { return this.upPressed; }
    public boolean getDownPressed () { return this.downPressed; }
    public boolean getLeftPressed () { return this.leftPressed; }
    public boolean getRightPressed () { return this.rightPressed; }
    public boolean getEnterPressed () { return this.enterPressed; }
    public boolean getEscPressed () { return this.escPressed; }
    public boolean getFPressed () { return this.fPressed; }
    public boolean getSpacePressed () { return this.spacePressed; }
    public boolean getShiftPressed () { return this.shiftPressed; }
    public boolean getBackSpacePressed () { return this.backSpacePressed; }
    public void IfTypingTrue (boolean isTyping) {  ifTyping = isTyping; }
    public boolean isCharTyped() { return charTyped; }
    public char getTypedChar() { return typedChar; }
    public void resetTypedChar() { charTyped = false; }

    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }
    public void setEnterPressed(boolean enterPressed) { this.enterPressed = enterPressed; }
    public void setEscPressed(boolean escPressed) { this.escPressed = escPressed; }
    public void setFPressed(boolean fPressed) { this.fPressed = fPressed; }
    public void setSpacePressed(boolean spacePressed) { this.spacePressed = spacePressed; }
    public void setShiftPressed(boolean shiftPressed) { this.shiftPressed = shiftPressed; }
}
