# Team Basta MP CMSC 12 (GitHub Version)

CMSC 12 Machine Problem Game
## Game Description
PokeRot is a game where you can fight, catch, and battle other people with their own PokeRots! These PokeRot's are entities that went viral on the internet, which are AI generated images 
that are composed of completely random things and making them humanoid at times, and then giving them Italian names. PokeRot is game that is combined from the classic Pokemon games, and the new
and viral Italian Brainrots.

## Small Instruction
You can delete your save files by hovering over a slot and pressing "Backspace" on your keyboard at the Load Menu Screen

## Controls
`Moving`
W - go Up 
A - go Left
S - go Down
D - go Right
These buttons are also what you use to navigate through the buttons

`Selecting`
F - to interact with npc's
Enter - to select the button that was hovered
Esc - to pause the game and to also go back an option during battle

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
java -cp "bin;" Main
```
Fourth Step(Creating Jar):
Create `manifest.txt` first, the contents are:
```
Main-Class: Main

```
You must include the blank line at the end.

After creating the `manifest.txt`, You can now put this line in the terminal
```powershell
jar cfm PokeRot.jar manifest.txt -C bin . -C . .
```
Which will create the jar file.

You can run that jar file by doing:
```powershell
java -jar PokeRot.jar
```

If the Jar file is inside the Installer folder, you must first go back to the previous directory using the command:
```
java -jar ../Installer/Jar/PokeRot.jar
```

