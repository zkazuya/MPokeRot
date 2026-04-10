public enum GameState {
    ROAMSTATE, // allows walking on the map
    BATTLESTATE, // for being locked in battle
    PAUSESTATE, // stop everything
    TITLESCREEN, // main menu, disable everything
    TALKINGSTATE; // behaviors for when interacting with NPC
}
