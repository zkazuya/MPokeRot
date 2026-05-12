import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
            // --- DIRECTIONAL UI NAVIGATION SOUNDS (Marked true to block overworld walk sound buzzes) ---
            case KeyEvent.VK_W -> { upPressed = true; playMenuSound("Assets/Music/move.wav", true); }
            case KeyEvent.VK_A -> { leftPressed = true; playMenuSound("Assets/Music/move.wav", true); }
            case KeyEvent.VK_S -> { downPressed = true; playMenuSound("Assets/Music/move.wav", true); }
            case KeyEvent.VK_D -> { rightPressed = true; playMenuSound("Assets/Music/move.wav", true); }
            
            // --- GENERAL INTERACTION AND ACTION SOUNDS (Marked false to always play across screens) ---
            case KeyEvent.VK_F -> { fPressed = true; playMenuSound("Assets/Music/interact.wav", false); }
            case KeyEvent.VK_ENTER -> { enterPressed = true; playMenuSound("Assets/Music/confirm.wav", false); }
            case KeyEvent.VK_SPACE -> { spacePressed = true; playMenuSound("Assets/Music/confirm.wav", false); }
            case KeyEvent.VK_ESCAPE -> { escPressed = true; playMenuSound("Assets/Music/cancel.wav", false); }
            case KeyEvent.VK_BACK_SPACE -> { backSpacePressed = true; playMenuSound("Assets/Music/cancel.wav", false); }
        }
    }

    // New helper method to route crisp audio menu clicks safely without background noise loop overlap
    private void playMenuSound(String soundPath, boolean isDirectionalKey) {
        if (gamePanel == null) return;
        
        // Block directional movement indicators from playing clips during exploration ticks
        if (isDirectionalKey && gamePanel.gameState == GameState.ROAMSTATE) {
            return; 
        }
        
        // Direct execution across any background layout menu layers
        SoundHelper.playSound(soundPath);
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

    //setters pleek pleek
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
