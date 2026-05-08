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
        this.level = 1; // ALWAYS START AT LVL 1
        this.maxHP = maxHP;
        this.currentHP = maxHP; // ALWAYS START AT FULL HEALTHH
        this.attack = attack;
        this.moves = new ArrayList<>();
        this.drawnHP = maxHP;
        this.exp = 0;
        this.expNeeded = 100;
    }

    public PokeRot (PokeRot other) {
        this.name = other.name;
        this.level = other.level;
        this.maxHP = other.maxHP;
        this.currentHP = other.maxHP;
        this.attack = other.attack;
        this.moves = new ArrayList<>(other.moves);
        this.drawnHP = other.maxHP;
        this.exp = other.exp;
        this.expNeeded = other.expNeeded;
    }

    public void update () {
        if (drawnHP > currentHP) { // FOR HP VISUAL BAR
            drawnHP -= 0.3; // KEEP REDUCING VISUAL HP BAR
            if (drawnHP < currentHP) drawnHP = currentHP; // PREVENT VISUAL BAR FROM OVERSHOOTING HP
        }

        if (drawnExp < exp) { // FOR EXP VISUAL BAR
            drawnExp += 1.0; // KEEP INCREASING EXP BAR
            if (drawnExp > exp) { // PREVENT VISUAL BAR FROM OVERSHOOTING ACTUAL XP
                drawnExp = exp;
            }
        } else if (drawnExp > exp) {
            drawnExp = 0; // MEANS WE LEVELED UP SO RESET BACK TO 0
        }
    }

    public void addMove(Move move) {
        if (this.moves.size() < 4)
            this.moves.add(move);
    }

    public int getHowManyMoves () { return this.moves.size(); }

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
        return this.level * 35; // LEVEL 1 GIVES 35 XP, LEVEL 2 GIVES 70 XP ETC
    } 

    public void levelUp () {
        this.level++;
        this.exp -= this.expNeeded; // KEEP LEFTOVER XP
        this.expNeeded = (int) (this.expNeeded * 1.5); // NEXT LVL REQUIRES 50% MORE XP
        // STAT BOOST EACH LVL UP BELOW
        this.maxHP += 5; // PERMANENT INCREASE MAX HP
        this.attack += 2; // PERMANENT INCREASE ATK
    }

    public boolean gainExp (int amount) { // GIVE XP TO POKEROT & RETURN TRUE IF IT LVLED UP
        this.exp += amount;
        if (this.exp >= this.expNeeded) { // IF CURRENT XP HIT XP TRESHOLD
            levelUp(); // CALL LVLUP METHOD
            return true; // RETURNS TRUE, IMPORTANT FOR LVL TRIGGER EVENTS
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