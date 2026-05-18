# Team Basta MP CMSC 12

CMSC 12 Machine Problem Game
## Game Description
PokeRot is a game where you can fight, catch, and battle other people with their own PokeRots! These PokeRot's are entities that went viral on the internet, which are AI generated images 
that are composed of completely random things and making them humanoid at times, and then giving them Italian names. PokeRot is game that is combined from the classic Pokemon games, and the new
and viral Italian Brainrots.

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

Type this in the terminal respectively, this will move you into the PokeRot folder where src and bin live
```powershell
cd PokeRot
```

Second Step(Compiling in the Terminal):
```powershell
javac -d bin (Get-ChildItem -Recurse src/*.java | Select-Object -ExpandProperty FullName)
```

Third Step(Running in the Terminal):
```powershell
CMD: java -cp "bin;." Main
```
Fourth Step(Creating Jar):
Create `manifest.txt` first, the contents are:
```
Main-Class: Main
Class-Path: .

```
You must include the blank line at the end.

After creating the `manifest.txt`, You can now put this line in the terminal
```powershell
jar cfm PokeRot.jar manifest.txt -C bin .
```
Which will create the jar file.

You can run that jar file by doing:
```powershell
java -jar PokeRot.jar
```

