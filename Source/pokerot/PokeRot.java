package pokerot;
import java.io.Serializable;
import java.util.ArrayList;

public class PokeRot implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name; // NAME OF EACH POKEROT
    private int level; // LEVEL OF EACH POKEROT
    private int maxHP; // THIS INCREASES
    private int currentHP; // IF DAMAGED, KEEP AS THIS HP
    private int attack; // THIS INCREASES
    private ArrayList<Move> moves; // STORE MOVES UNIQUE TO THE POKEROT
    transient private double drawnHP; // THE VISUAL BAR'S NUMBER FOR HP
    transient private double drawnExp = 0; // THE VISUAL BAR'S NUMBER FOR EXP
    private int exp; // THIS IS THE ACTUAL XP
    private int expNeeded; // THIS IS USED FOR CONDITIONING/LEVELING
    private int baseMaxHP; // LEVEL 1 BASE STATS HEALTH (GOES BACK TO THIS AFTER DEATH)
    private int baseAttack; // LEVEL 1 BASE STATS ATTACK (GOES BACK TO THIS AFTER DEATH)

    public PokeRot(String name, int maxHP, int attack) {
        this.name = name;
        this.level = 1; // ALWAYS START AT LVL 1
        this.maxHP = maxHP; // MAX HP IS THE STARTING HP AT FIRST
        this.currentHP = maxHP; // ALWAYS START AT FULL HEALTHH
        this.attack = attack; // MAX ATK IS THE STARTING ATK AT FIRST
        this.moves = new ArrayList<>(); // INITIALIZE AND ADD MOVES LATER
        this.drawnHP = maxHP; // THE DRAWN HP STARTS AT MAX HP AT FIRST (BECUZ THIS DRAINS AND GOES DOWN TO 0)
        this.exp = 0; // EXP IS 0 BY DEFAULT FOR EVERY POKEROT
        this.expNeeded = 100; // EXP NEEDED IS 100 THIS IS SCALED BY 35
        this.baseMaxHP = maxHP; // THE LEVEL 1 STAT IS OFC THE VERY FIRST STATE
        this.baseAttack = attack; // SAME THING HERE BUT THESE DON'T CHANGE
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
        if (drawnHP > currentHP) { // FOR HP VISUAL BAR TAKING DMG
            drawnHP -= 0.3; // KEEP REDUCING VISUAL HP BAR
            if (drawnHP < currentHP) drawnHP = currentHP; // PREVENT VISUAL BAR FROM OVERSHOOTING HP
        } else if (drawnHP < currentHP) { // FOR HP VISUAL BAR HEALING
            drawnHP += 0.3; // KEEP INCREASING VISUAL HP BAR
            if (drawnHP > currentHP) drawnHP = currentHP; // PREVENT OVERSHOOTING
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

    public Move getMove(int index) {
        if (index >= 0 && index < this.moves.size())
            return this.moves.get(index);
        return null;
    }

    public void jumpToLevel (int targetLevel) {
        if (targetLevel <= this.level) return;
        int levelsToGain = targetLevel - this.level;
        for (int i = 1; i <= levelsToGain; i++) {
            this.level++;
            this.maxHP += 10;
            this.currentHP += 10;
            this.attack += 4;
            this.expNeeded = (int) (expNeeded * 1.5);
        }
        this.drawnHP = this.currentHP;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0)
            this.currentHP = 0;
    }

    public void heal (int amount) {
        this.currentHP += amount;
        if (this.currentHP > this.maxHP) this.currentHP = this.maxHP;
    }

    public int getBaseExpYield () {
        return this.level * 60; // LEVEL 1 GIVES 60 XP, LEVEL 2 GIVES 120 XP ETC
    } 

    public void levelUp () {
        this.level++;
        this.exp -= this.expNeeded; // KEEP LEFTOVER XP
        this.expNeeded = (int) (this.expNeeded * 1.5); // NEXT LVL REQUIRES 50% MORE XP
        // STAT BOOST EACH LVL UP BELOW
        this.maxHP += 10; // PERMANENT INCREASE MAX HP
        this.attack += 4; // PERMANENT INCREASE ATK
    }

    public boolean gainExp (int amount) { // THIS METHOD HAS THREE PURPOSES, ONE IS GIVING EXP
        this.exp += amount; // SECOND IS RETURNING TRUE IF LEVELED UP 1 OR MORE TIMES
        boolean didWeLevelUp = false; // KEEP TRACK IF WE LEVELED UP AT LEAST ONCE
        while (this.exp >= this.expNeeded) { // THE THIRD PURPOSE IS WHEN WE GAIN SO MUCH EXP
            levelUp(); // WE CAN LEVEL UP MORE THAN ONCE
            didWeLevelUp = true;
        }
        return didWeLevelUp; //RETURN TRUE IF WE LEVLED UP 1 OR MORE TIMES
    }

    public void resetToLevelOne () { // THIS METHOD IS USED WHENEVER THE POKEROT DIES FOR RESETTING
        this.level = 1; // LEVEL GOES BACK TO 1
        this.maxHP = this.baseMaxHP; // THEIR MAX HP DEVOLVES DOWN TO WHAT IT WAS INITIALLY
        this.attack = this.baseAttack; // SAME HERE BEFORE THEY WERE INCREASED BY LVLING UP
        this.currentHP = this.maxHP; // CURRENT HP ALSO GOES BACK DOWN TO MAX HP (THIS SIMULATES FULL HEAL)
        this.drawnHP = this.maxHP; // ALSO THE DRAWING IS RESET BACK TO WHATEVER IS THE FULL HEALTH INITIALLY
        this.exp = 0; // WIPE LEFTOVER EXP FROM PREVIOUS FIGHTS
        this.drawnExp = 0; // RESET VISUAL EXP BAR (RMEMBER THEY START AT 0)
        this.expNeeded = 100; // RESET LEVELING THRESHOLD
    }

    public String getName () { return this.name; }
    public int getLevel () { return this.level;}
    public int getMaxHP () { return this.maxHP; }
    public int getCurrentHP () { return this.currentHP; }
    public int getAttack () { return this.attack; }
    public double getDrawnHP () { return this.drawnHP; }
    public int getExp () { return this.exp; }
    public int getExpNeeded () { return this.expNeeded; }
    public int getHowManyMoves () { return this.moves.size(); }
    public double getDrawnExp () { return this.drawnExp; }

    public void setName (String name) {  this.name = name; }
    public void setLevel (int level) {  this.level = level;}
    public void setMaxHP (int maxHP) { this.maxHP = maxHP; }
    public void setCurrentHP (int currentHP) {  this.currentHP = currentHP; }
    public void setAttack (int attack) {  this.attack = attack; }
    public void setDrawnHP (int drawnHP) {  this.drawnHP = drawnHP; }
    public void setExp (int exp) {  this.exp = exp; }
    public void setExpNeeded (int expNeeded) {  this.expNeeded = expNeeded; }
    public void setDrawnExp (int drawnExp) {  this.drawnExp = drawnExp; }
    public PokeRot getPokeRot () { return this; }
    
    public void initAfterLoad(){
        this.drawnHP = this.currentHP;
        this.drawnExp = this.exp;
    }
}