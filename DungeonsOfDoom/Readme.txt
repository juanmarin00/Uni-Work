
WELCOME TO THE DUNGEONS OF DOOOOOOOM! 

-HOW TO INSTALL AND RUN
Players must compile the HGameLogic.java file and if they wish to use a map of their own then enter the name of the map file (without the .txt extension) as an argument when compiled. For example if the user wants to play on a map who's file is map.txt then the player would have to run in the terminal the command java GameLogic map and execute. If no other arguments are passed the file examplemap.txt will be used as a map.

-OBJECTIVE OF THE GAME
The player must navigate through the map and collect enough gold and go to an exit tile before the bot catches them.

-HOW TO PLAY:
During the player’s turn they will be able to input one of the following commands into the terminal in order to play the game. Warning: The input is NOT case-sensitive. If a player fails to input a valid command an error message will be triggered and their turn will be lost.

-HELLO: 
This will return the gold needed to collect in order to complete the level.

-GOLD: 
This will return the gold owned by the player.
	

-PICKUP: 
This command will be used to pick up gold by the player. If the player is on a 	gold tile gold will be picked up and a message will be outputted, telling the player that 	they have picked up gold. However, if this command is inputted and the player isn’t on a 	gold tile the player will receive an error message and lose their turn.

-LOOK: This will print onto the console a  5x5 grid, showing the map around the player eg:
# # # . .	     
# . # . E
. . P . .
# . . . .
# . . G .

-MOVE  <DIRECTION> : 
This will move the player around the map. The 4 possible movements are north, east, south, west. To move in each direction the player must also enter the first letter of the direction they wish to move in. E.g: MOVE N = Move North.
 
-QUIT: This will quit the game and output a certain message depending on two conditions. If the player has enough gold as that demanded In the map file and is standing on an Exit (E) tile, then a WIN message will be displayed. If only one or none of these conditions are followed then a LOSE message will be displayed.

