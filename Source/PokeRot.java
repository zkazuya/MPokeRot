import java.util.ArrayList;

public class PokeRot {
    private String name;
    private int level;
    private int maxHP;
    private int currentHP;
    private int attack;
    private ArrayList<Move> moves;
    private double drawnHP;
    private double drawnExp = 0;
    private int exp;
    private int expNeeded;

    public PokeRot(String name, int maxHP, int attack) {
        this.name = name;
        this.level = 1; // always start at level 1
        this.maxHP = maxHP;
        this.currentHP = maxHP; // always start at full health
        this.attack = attack;
        this.moves = new ArrayList<>();
        this.drawnHP = maxHP;
        this.exp = 0;
        this.expNeeded = 100;
    }

    public void update () {
        if (drawnHP > currentHP) {
            drawnHP -= 0.3; // bawas bawasan kada frame para smooth it healthbar diri instant
            if (drawnHP < currentHP) drawnHP = currentHP; // para diri lumapos it healthbar na actual
        }

        if (drawnExp < exp) {
            drawnExp += 1.0;
            if (drawnExp > exp) {
                drawnExp = exp;
            } else if (drawnExp > exp) {
                drawnExp = 0; // means we leveled up so reset the drawing back to 0
            }
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

    public int getBaseExpYield () {
        return this.level * 35; // level 1 gives 35 xp, level 2 gives 70 xp etc
    } 

    public void levelUp () {
        this.level++;
        this.exp -= this.expNeeded; // keep left over exp
        this.expNeeded = (int) (this.expNeeded * 1.5); // next lvl requires 50% more xp
        // stat boost below
        this.maxHP += 5; // increase maxhp
        this.attack += 2; // increase atk
    }

    public boolean gainExp (int amount) { // give exp to this pokerot, return true if nag level up (important)
        this.exp += amount;
        if (this.exp >= this.expNeeded) {
            levelUp();
            return true;
        }
        return false;
    }

    public String getName () { return this.name; }
    public int getLevel () { return this.level;}
    public int getMaxHP () { return this.maxHP; }
    public int getCurrentHP () { return this.currentHP; }
    public int getAttack () { return this.attack; }
    public double getDrawnHP () { return this.drawnHP; }
    public int getExp () { return this.exp; }
    public int getExpNeeded () { return this.expNeeded; }
    public double getDrawnExp () { return this.drawnExp; }
}