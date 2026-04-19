import java.util.ArrayList;

public class PokeRot {
    private String name;
    private int level;
    private int maxHP;
    private int currentHP;
    private int attack;
    private ArrayList<Move> moves;
    private double drawnHP;

    public PokeRot(String name, int maxHP, int attack) {
        this.name = name;
        this.level = 1; // always start at level 1
        this.maxHP = maxHP;
        this.currentHP = maxHP; // always start at full health
        this.attack = attack;
        this.moves = new ArrayList<>();
        this.drawnHP = maxHP;
    }

    public void update () {
        if (drawnHP > currentHP) {
            drawnHP -= 0.3; // bawas bawasan kada frame para smooth it healthbar diri instant
            if (drawnHP < currentHP) drawnHP = currentHP; // para diri lumapos it healthbar na actual
        }
    }

    public void addMove(Move move) {
        if (this.moves.size() < 4)
            this.moves.add(move);
    }

    public Move getMove(int index) {
        if (index >= 0 && index < this.moves.size())
            return this.moves.get(index);
        return null;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0)
            this.currentHP = 0;
    }

    public String getName () { return this.name; }
    public int getLevel () { return this.level;}
    public int getMaxHP () { return this.maxHP; }
    public int getCurrentHP () { return this.currentHP; }
    public int getAttack () { return this.attack; }
    public double getDrawnHP () { return this.drawnHP; }
}