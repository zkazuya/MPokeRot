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

    public PokeRot generateRandomPokeRot () { // THIS METHOD IS RESPONSIBLE FOR GRASS WILD ENCOUNTER
        int randomIndex = (int) (Math.random() * pokeRotList.size()); // RANDOMIZER
        PokeRot sendThisRot = new PokeRot(pokeRotList.get(randomIndex)); // NOTICE WE'RE CREATING A COPY, NOT SENDING THE ONE FROM ARRAYLIST
        return sendThisRot; // THIS IS BECAUSE WE WILL BATTLE IT AGAIN, AND WE DON'T WANT TO BATTLE A POKEROT WITH 0 HP
    }

    public PokeRot getSpecificPokeRot (String name) { // THIS METHOD IS RESPONSIBLE FOR GETTING A SPECIFIC POKEROT
        for (PokeRot eachPokeRot : pokeRotList) { // FOR EACH POKEROT FIND THE ONE THAT MATCHES THE NAME
            if (eachPokeRot.getName().equals(name)) { // AND THEN RETURN IT TO THE CALLER
                return new PokeRot(eachPokeRot); // ITS REAL USAGE IS FOR CREATING A CLONE POKEROT (LIKE SENDING IT TO PLAYER/ENEMY PARTY)
            } // AGAIN, WE DO NOT SEND THE POKEROT FROM THE ARRAYLIST
        }
        return null;
    }

    public void registerPokeRot () { // THIS METHOD JUST JUST MAKES ALL OF THE POKEROT IN THE GAME ALONG WITH THEIR STATS
        pokeRotList.add(new PokeRot("67", 105, 95));
        pokeRotList.add(new PokeRot("Ballerina Capuccina", 65, 125));
        pokeRotList.add(new PokeRot("Bobrini Cocosini", 75, 100));
        pokeRotList.add(new PokeRot("Boneca Ambalabu", 130, 85));
        pokeRotList.add(new PokeRot("Brr Brr Patapim", 80, 105));
        pokeRotList.add(new PokeRot("Cappuccino Assassino", 90, 85));
        pokeRotList.add(new PokeRot("Chimpanzini Bananini", 90, 90));
        pokeRotList.add(new PokeRot("Frulli Frulla", 90, 90));
        pokeRotList.add(new PokeRot("Holeee", 106, 110));
        pokeRotList.add(new PokeRot("La Vaca Saturno Saturnita", 80, 125));
        pokeRotList.add(new PokeRot("Lirili Larila", 65, 80));
        pokeRotList.add(new PokeRot("Tung Tung Sahur", 95, 80));
        pokeRotList.add(new PokeRot("Skibidi Toilet", 80, 85));
        pokeRotList.add(new PokeRot("Tralalelo Tralala", 73, 100));
        pokeRotList.add(new PokeRot("Udin Dinn Din Dun", 99, 68));
    }

    public void registerPokeRotMoves () { // THIS METHOD JUST CREATES ALL OF THE POKEROT MOVES WHICH WE WILL ADD TO POKEROTS
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

    public void setPokeRotMovesOnEach () { // THIS METHOD GIVES THE ENEMY AI MOVES TO USE
        for (PokeRot eachPokeRot : pokeRotList) {
            for (int i = 1; i <= 4; i++) { // GOES UP TO 4 ONLY SINCE ONLY 4 MOVES ARE AVAILABLE
                int randomMove = (int) (Math.random() * pokeRotMoves.size()); // RANDOMIZE THE INDEX
                eachPokeRot.addMove(pokeRotMoves.get(randomMove)); // ADD A RANDOM MOVE TO THE ENEMY AI
            }
        }
    }
}
