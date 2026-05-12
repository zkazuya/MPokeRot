package data;

import java.io.Serializable;
import java.util.ArrayList;

import pokerot.PokeRot;

public class SaveData implements Serializable {
    private static long serialVersionUID = 1L;
    private String playerName = "";
    private int playerX;
    private int playerY;
    private ArrayList<PokeRot> party = new ArrayList<>();

   // METHODS TO USE FOR LOADING
    public String getPlayerName() { return playerName; }
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public ArrayList<PokeRot> getPlayerParty() { return party; }

    // METHODS TO USE FOR SAVING
    public void setPlayerName(String playerName ) { this.playerName = playerName; }
    public void setPlayerX(int playerX) { this.playerX = playerX; }
    public void setPlayerY(int playerY) { this.playerY = playerY; }
    public void setPlayerParty(ArrayList<PokeRot> party) { this.party = party; }
}


