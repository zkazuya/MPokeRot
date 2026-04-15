import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed; // input switches for aswd ENCAPSULATED
    private boolean fPressed; // this is for dialogue interaction

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
        if (code == KeyEvent.VK_F) fPressed = true; //VK_F = 70
        
    }

    @Override // override another KeyEvent abstract method
    public void keyReleased (KeyEvent input) { // this method disables the switches when they are not pressed or hold
        int code = input.getKeyCode(); // translates the keys like "awsd" into number
        if (code == KeyEvent.VK_W) upPressed = false; // VK_W = 87
        if (code == KeyEvent.VK_S) downPressed = false; // VK_W = 83
        if (code == KeyEvent.VK_A) leftPressed = false; // VK_A = 65
        if (code == KeyEvent.VK_D) rightPressed = false; // VK_D = 68
        if (code == KeyEvent.VK_F) fPressed = false; //VK_F = 70
    }

    public boolean getUpPressed () {
        return this.upPressed; // returns true or false for w key
    }

    public boolean getDownPressed () {
        return this.downPressed; // returns true or false for s key
    }

    public boolean getLeftPressed () {
        return this.leftPressed; // returns true or false for a key
    }

    public boolean getRightPressed () {
        return this.rightPressed; // returns true or false for d key
    }

    public boolean getFPressed(){
        return this.fPressed; //returns true/false for pressing f key
    }

}
