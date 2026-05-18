# Team Basta MP CMSC 12

CMSC 12 Machine Problem Game

## Folder Structure
-  `src/battle` - Handles PokeRot battling system 
- `src/entity` - player, and npc's that can be battled or interactable.
- `src/io` - file handling (mostly for saving)
- `src/main` - includes the main swing components needed for the game (JFrame, JPanel)
- `src/pokerot` - handles everything related to the PokeRot (sprites, abilities, stats, levels)
- `src/system` - handling keyboard inputs
- `src/ui` - classes that mainly focuses on the foreground activities of the game
- `Assets` - images, map files, sprites, etc.
- `bin` - compiled `.class` output.

## Compiling
Type this in the terminal respectively, this will move you into the PokeRot folder where src and bin live: CMD: cd PokeRot

SECOND STEP (COMPILING IN TERMINAL):
CMD: javac -d bin (Get-ChildItem -Recurse src/*.java | Select-Object -ExpandProperty FullName)

LAST STEP(RUNNING IN TERMINAL):
To run the code just type this in the terminal
CMD: java -cp "bin;." Main

