import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, escPressed; // input switches for aswd, esc, enter
    private boolean fPressed; // dialogue interaction key

    @Override // override KeyEvent abstract method
    public void keyTyped (KeyEvent input) {} // this method is never used but has to be overridden

    @Override // override another KeyEvent abstract method
    public void keyPressed (KeyEvent input) { // this method enables the switches when pressed or hold
        int code = input.getKeyCode(); // translates the keys like "awsd" into number
        // below are just setting up booleans if they match code, VK means virtual key and it's a final int
        if (code == KeyEvent.VK_W) upPressed = true; // VK_W = 87
        if (code == KeyEvent.VK_A) leftPressed = true; // VK_A = 65
        if (code == KeyEvent.VK_S) downPressed = true; // VK_S = 83
        if (code == KeyEvent.VK_D) rightPressed = true; // VK_D = 68
        if (code == KeyEvent.VK_F) fPressed = true; // VK_F = 70
        if (code == KeyEvent.VK_ENTER) enterPressed = true; // VK_ENTER = 10
        if (code == KeyEvent.VK_ESCAPE) escPressed = true; // VK_ESCAPE = 27
    }

    @Override // override another KeyEvent abstract method
    public void keyReleased (KeyEvent input) { // this method disables the switches when they are not pressed or hold
        int code = input.getKeyCode(); // translates the keys like "awsd" into number
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false; 
        if (code == KeyEvent.VK_F) fPressed = false;
        if (code == KeyEvent.VK_ENTER) enterPressed = false; //VK_ENTER = 10
        if (code == KeyEvent.VK_ESCAPE) escPressed = false;
    }
    
    public boolean getUpPressed () { return this.upPressed; }
    public boolean getDownPressed () { return this.downPressed; }
    public boolean getLeftPressed () { return this.leftPressed; }
    public boolean getRightPressed () { return this.rightPressed; }
    public boolean getEnterPressed () { return this.enterPressed; }
    public boolean getEscPressed() { return this.escPressed; }
    public boolean getFPressed() { return this.fPressed; }
}
