public class PokeRot {
    private String name;
    private int level;
    private int maxHP;
    private int currentHP;
    private int attack;

    public PokeRot (String name, int maxHP, int attack) {
        this.name = name;
        this.level = 1; // always start at level 1
        this.maxHP = maxHP;
        this.currentHP = maxHP; // always start at full health
        this.attack = attack;
    }

    public String getName () {
        return this.name;
    }

    public int getLevel () {
        return this.level;
    }

    public int getMaxHP () {
        return this.maxHP;
    }

    public int getCurrentHP () {
        return this.currentHP;
    }

    public int getAttack () {
        return this.attack;
    }
}
