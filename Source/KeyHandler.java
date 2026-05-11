import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean enterPressed; // BATTLE CHANGE PHASE KEY
    private boolean escPressed; // CANCEL/GOING BACK KEY
    private boolean fPressed; // DIALOGUE INTERACTION KEY
    private boolean spacePressed; 
    private boolean shiftPressed; 
    private char typedChar;
    private boolean charTyped;
    private boolean ifTyping = false;

    @Override
    public void keyTyped (KeyEvent input) {
        if (ifTyping){
        typedChar = input.getKeyChar();
        charTyped = true;
        }
    } // NOT USED BUT MUST BE IMPORTED

    @Override
    public void keyPressed (KeyEvent input) { // THIS METHOD IS TRIGGERED WHEN A KEY IS HOLD
        int code = input.getKeyCode(); // TRANSLATE KEYS "AWSD" TO ASCII VALUE
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_F -> fPressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_ESCAPE -> escPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_SHIFT -> shiftPressed = true;
        }
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
