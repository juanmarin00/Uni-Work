
import java.io.IOException;
import java.io.*;

public class GameLogic
{
    private static boolean running = true;

    protected boolean isRunning()
    {
        return running;
    }

    //Will swith running to false and call quit()
    protected void flipAndQuitRunning()
    {
        running = false;
        quit();
    }

    //Method which is called when user inputs "HELLO"
    protected String inputHello()
    {
        Map map = new Map();
        String goldRequired = String.valueOf(map.getGoldRequired());
        String informOnLevel = "Gold to win: " + goldRequired;
        return informOnLevel;
    }


    //Following 4 methods atre all the same but with different directions. They will first call returnPlayersPosition()
    //to have the players position before the move, then modify it corresponding to the moves direction, and then check
    // if that move is valid through isMoveValid, if it is it will mobve and print "Success" if it isnt the player wont move and it will print "Fail"
    protected String moveNorth()
    {
      Map mapClass = new Map();
      GameLogic gameLogic = new GameLogic();

      int[] playerPosition = mapClass.returnPlayersPosition();
      int[] tempPosN = {playerPosition[0], playerPosition[1] - 1};
      char tileAbove = mapClass.getCoords(tempPosN);

      return gameLogic.isMoveValid(tempPosN, tileAbove);
    }

    protected String moveEast()
    {
      Map mapClass = new Map();
      GameLogic gameLogic = new GameLogic();

      int[] playerPosition = mapClass.returnPlayersPosition();
      int[] tempPosN = {playerPosition[0] + 1, playerPosition[1]};
      char tileAbove = mapClass.getCoords(tempPosN);

      return gameLogic.isMoveValid(tempPosN, tileAbove);
    }

    protected String moveSouth()
    {
      Map mapClass = new Map();
      GameLogic gameLogic = new GameLogic();

      int[] playerPosition = mapClass.returnPlayersPosition();
      int[] tempPosN = {playerPosition[0] , playerPosition[1]+1};
      char tileAbove = mapClass.getCoords(tempPosN);

      return gameLogic.isMoveValid(tempPosN, tileAbove);
    }

    protected String moveWest()
    {
      Map mapClass = new Map();
      GameLogic gameLogic = new GameLogic();

      int[] playerPosition = mapClass.returnPlayersPosition();
      int[] tempPosN = {playerPosition[0] - 1, playerPosition[1]};
      char tileAbove = mapClass.getCoords(tempPosN);

      return gameLogic.isMoveValid(tempPosN, tileAbove);
    }



    //This method will check if the move is valid, depending on where the user is before the move and the kind of tile he is moving onto
    protected String isMoveValid(int[] tempPos, char tile)
    {
        Map mapClass = new Map();
        GameLogic gameLogic = new GameLogic();

        if (mapClass.returnBotPosition()[0] == tempPos[0] && mapClass.returnBotPosition()[1] == tempPos[1])
        {
            System.out.println("Collision with bot! Game over!");
            gameLogic.quit();
            return null;
        }
        else if (Character.toString(tile).equals("#"))
        {
          return "FAIL";
        }
        else if (Character.toString(tile).equals("E"))
        {
            mapClass.updatePosition("player",tempPos);
            return null;
        }
        else if (Character.toString(tile).equals("G"))
        {
            mapClass.updatePosition("player",tempPos);
            mapClass.trueOnGold();
            return null;
        }
        else
        {
            mapClass.updatePosition("player",tempPos);
            return "SUCCESS";
        }
    }


    //This function will use the parameters of map and playerPos to create a 5*5 array of what is around the player
    protected String inputLook(char[][] map, int[] playerPos)
    {
        Map mapClass = new Map();

        String viewPlayer = "";

        int x = playerPos[0];
        int y = playerPos[1];

        int a = mapClass.returnBotPosition()[0];
        int b = mapClass.returnBotPosition()[1];

        for (int i = y - 2; i <= y + 2; i++)
        {
            for (int j = x - 2; j <= x + 2; j++)
            {
                if (x == j && y == i) viewPlayer += "P ";
                else if (a == j && b == i) viewPlayer += "B ";
                else if (j < 0 || j >= mapClass.getWidth()) viewPlayer += "# ";
                else if (i < 0 || i >= mapClass.getHeight()) viewPlayer += "# ";
                else viewPlayer += map[j][i] + " ";
            }
            viewPlayer += "\n";
        }
        return viewPlayer;
    }

    //Method called when "Pickup" is entered. It will first check if the position of the player is the same as a gold tiles
    //If it is it will increment the gold owned, and change that tile for a=n empty one, followed by the printing of a succes message
    //If it isnt then nothing will happen apart from a fail message
    protected String inputPikcup()
    {
        Map mapClass = new Map();
        String result;
        if (mapClass.isOnGold())
        {
            mapClass.removeGold();
            mapClass.falseOnGold();
            mapClass.goldPlusOne();
            String myGold = String.valueOf(mapClass.showGold());
            result = "Success " + myGold;
            return result ;
        }
        else
        {
            String myGold = String.valueOf(mapClass.showGold());
            result = "Fail " + myGold;
            return result;
        }

    }

    //Will call system.exit
    protected void quit()
    {
        System.exit(0);
    }

    //Method called with "QUIT" command, it will check for two conditions, enough gold and player on else {
    //If it is it will give win message, if it isnt then lose message
    //Regardless of win or lose it will quit the game
    protected void levelCompleted()
    {
        Map map = new Map();
        GameLogic gameLogic = new GameLogic();
        HumanPlayer humanPlayer = new HumanPlayer();


        if (Character.toString(map.getCoords(map.returnPlayersPosition())).equals("E") && (map.goldRequiredToWin() == true))
        {
            System.out.println("Win\nCongrats, you beat this level!");

        } else
        {
            System.out.println("Lose\nSorry you called a QUIT command without satisfying the two conditions required to beat the level");
        }

        System.out.println("Quitting game");
        flipAndQuitRunning();
    }

    //This will start a game with the defaul map "examplemap.txt". Spawning in a player, a bot and with a while loop which will give player and bot alternating turns until the game ends
    public static void playDefaultMap()throws FileNotFoundException,IOException
    {
        System.out.println("No arguments passed, playing with default map");
        HumanPlayer humanPlayer = new HumanPlayer();
        Bot bot = new Bot();
        GameLogic gameLogic = new GameLogic();
        Map mapClass = new Map();


        mapClass.readMap("examplemap.txt");
        mapClass.spawnPlayer("bot");
        mapClass.spawnPlayer("player");

        System.out.println("Welcome to Dungeons of Doom.\nThe map name is: " + mapClass.returnMapName() + ".");

        while (gameLogic.isRunning())
        {
            humanPlayer.selectNextAction();
            bot.botMove();
        }
    }

    //The same as playDefaultMap() but will use args from console to run a .txt file as map (More intructions on readMe)
    public static void playOtherMap(String[] args) throws IOException,FileNotFoundException
    {
      //Will create a string by concatenating the name passed in args with .txt and use that as a file
      String mapName = args[0] + ".txt";

        HumanPlayer humanPlayer = new HumanPlayer();
        Bot bot = new Bot();
        GameLogic gameLogic = new GameLogic();
        Map mapClass = new Map();


        mapClass.readMap(mapName);
        mapClass.spawnPlayer("bot");
        mapClass.spawnPlayer("player");

        System.out.println("Welcome to Dungeons of Doom.\nThe map name is: " + mapClass.returnMapName() + ".");

        while (gameLogic.isRunning())
        {
            humanPlayer.selectNextAction();
            bot.botMove();
        }

        System.out.println("You are now quitting the game.");
        gameLogic.quit();
    }


    public static void main(String[] args) throws IOException,FileNotFoundException
    {
        if(args.length>0)//If length is more than 0 then the player tried to ruin a map file
        {
            //It will try to find and run the game with that file as a map
          try
          {
            playOtherMap(args);
          }
          //If the fileisnotfound then an error message will be dislayed and playDefaultMap will be called meaning the player will play on the defaultMap
          catch(FileNotFoundException f)
          {
            System.out.println("Sorry couldnt find map, playing with example map instead");
            playDefaultMap();
          }
        }
        //If no arguments are passed, then default map is used
        else
        {
          playDefaultMap();
        }
    }
}
