

import java.io.Serializable;

public class Move implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private int damage;

    public Move (String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName () { return this.name; }
    public int getDamage () { return this.damage; }
    public void setName(String name) { this.name = name; }
    public void setDamage (int damage) { this.damage = damage; }
}
