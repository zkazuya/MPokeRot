import java.util.ArrayList;

public class PokeRotRegistry {
    private ArrayList<PokeRot> pokeRotList;
    private ArrayList<Move> pokeRotMoves;
    
    public PokeRotRegistry () {
        pokeRotList = new ArrayList<>(); 
        pokeRotMoves = new ArrayList<>();
        registerPokeRot();
        registerPokeRotMoves();
        setPokeRotMovesOnEach();
    }

    public PokeRot generateRandomPokeRot () {
        int randomIndex = (int) (Math.random() * 14);
        return pokeRotList.get(randomIndex);
    }

    public void registerPokeRot () {
        pokeRotList.add(new PokeRot("67", 105, 95));
        pokeRotList.add(new PokeRot("Ballerina Capuccina", 65, 125));
        pokeRotList.add(new PokeRot("Bobrini Cocosini", 75, 100));
        pokeRotList.add(new PokeRot("Boneca Ambalabu", 130, 85));
        pokeRotList.add(new PokeRot("Brr Brr Patapim", 80, 105));
        pokeRotList.add(new PokeRot("Cappuccino Assassino", 90, 85));
        pokeRotList.add(new PokeRot("Chimpanzini Bananini", 90, 90));
        pokeRotList.add(new PokeRot("Frulli Frulla", 90, 90));
        pokeRotList.add(new PokeRot("holeee", 106, 110));
        pokeRotList.add(new PokeRot("La Vaca Saturno Saturnita", 80, 125));
        pokeRotList.add(new PokeRot("Lirili Larila", 65, 80));
        pokeRotList.add(new PokeRot("Tung Tung Sahur", 95, 80));
        pokeRotList.add(new PokeRot("Skibidi Toilet", 80, 85));
        pokeRotList.add(new PokeRot("Tralalelo Tralala", 73, 100));
        pokeRotList.add(new PokeRot("Udin din din dun", 99, 68));
    }

    public void registerPokeRotMoves () {
        pokeRotMoves.add(new Move("Explosive Diarrhea", 22));
        pokeRotMoves.add(new Move("Jawline Cutter", 19));
        pokeRotMoves.add(new Move("Fanum Tax", 21));
        pokeRotMoves.add(new Move("Maximum Mog", 20));
        pokeRotMoves.add(new Move("Toilet Flush", 18));
        pokeRotMoves.add(new Move("Gyatt Punch", 21));
        pokeRotMoves.add(new Move("Brain Damage", 21));
        pokeRotMoves.add(new Move("Nitro Flex", 20));
        pokeRotMoves.add(new Move("Mewing Streak", 18));
        pokeRotMoves.add(new Move("Sigma Stare", 22));
        pokeRotMoves.add(new Move("Rizzification", 21));
        pokeRotMoves.add(new Move("Eyes Scream", 18));
        pokeRotMoves.add(new Move("Womp Womp", 21));
        pokeRotMoves.add(new Move("Goon Swing", 19));
    }

    public void setPokeRotMovesOnEach () {
        for (PokeRot eachPokeRot : pokeRotList) {
            for (int i = 1; i <= 4; i++) {
                int randomMove = (int) (Math.random() * 14);
                eachPokeRot.addMove(pokeRotMoves.get(randomMove));
            }
        }
    }
}
