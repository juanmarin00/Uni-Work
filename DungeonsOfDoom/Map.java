import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Map
{

    //Gameplay fields
    private static int[] playerPosition = new int[2];
    private static int[] botPosition = new int[2];
    private static int goldToWin = 0;
    private static boolean isOnGold = false;
    private static int goldOwned = 0;

    //Map fields
    private static char[][] myMap = null;
    private static int mapHeight = 0;
    private static int mapWidth = 0;
    private static String mapName;



    //Method to increase gold amount when picked up succesfully
    protected void goldPlusOne()
    {
        goldOwned = goldOwned + 1;
    }

    //will return goldowned to see if level is completed and display gold owned
    protected int showGold()
    {
      return goldOwned;
    }

    //function called when player enter "GOLD"
    protected void showGoldString()
    {
      System.out.println("Gold owned: " + goldOwned);
    }

    //boolean to check if gold owned is enough to conplete level
    protected boolean goldRequiredToWin()
    {
        if ( goldOwned >= goldToWin)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Will turn a gold tile into an empty floor tile when it is picked up
    protected void removeGold()
    {
        Map mapClass = new Map();
        myMap[mapClass.returnPlayersPosition()[0]][mapClass.returnPlayersPosition()[1]] = '.';
    }

    protected boolean isOnGold()
    {
        return isOnGold;
    }

    protected void falseOnGold()
    {
        isOnGold = false;
    }

    protected void trueOnGold()
    {
        isOnGold = true;
    }

    //This method will initialize the map
    protected void initializeMap()
    {
        myMap = new char[mapWidth][mapHeight];
    }

    //Series of return funtions which will be called later to obtain fields (getters)
    protected int getGoldRequired()
    {
        return goldToWin;
    }

    protected char[][] getMap()
    {
        return myMap;
    }

    protected int getHeight()
    {
        return mapHeight;
    }

    protected int getWidth()
    {
        return mapWidth;
    }


    protected String returnMapName()
    {
        return mapName;
    }


    protected int[] returnPlayersPosition()
    {
        return playerPosition;
    }


    protected int[] returnBotPosition()
    {
        return botPosition;
    }

    /*This method will read a map from a file when the user has a .txt file in the same directory as the other 4 java files
    The method will read in from a file using a BufferedReader. First it will use the assumption that the map file First
    contais the maps name, then the gold to win and then the map. Once it reads and deleted the name it will do the same
    for the gold and then copy the map into a 2d array. This will be done by separating each character until n characters are read,
    where n + the width of the map, it will then repeat this on a new array, which will represent a new line of the map.
    This will be repeated until the number of lines = the map height, giving us a 2d char array containing the map
    */
    protected void readMap(String fileName) throws FileNotFoundException
    {
        File theMap = new File(fileName);
        FileReader fileIn = new FileReader(theMap);
        BufferedReader input = new BufferedReader(fileIn);

        ArrayList<String> tempMap = new ArrayList<>();
        Map mapClass = new Map();

        try
        {
            String line = input.readLine();

            while (line != null)
            {
                tempMap.add(line);
                line = input.readLine();
            }

            mapName = tempMap.get(0).substring(5, tempMap.get(0).length());
            tempMap.remove(0);

            goldToWin = Integer.valueOf(tempMap.get(0).substring(4, tempMap.get(0).length()));
            tempMap.remove(0);

            mapHeight = tempMap.size();
            mapWidth = tempMap.get(0).length();

            mapClass.initializeMap();

            for (int i = 0; i < mapHeight; i++)
            {
                String[] singleRow = tempMap.get(i).split("");

                for (int j = 0; j < singleRow.length; j++)
                {
                    myMap[j][i] = singleRow[j].charAt(0);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    protected char getCoords(int[] coordinates)
    {
        return myMap[coordinates[0]][coordinates[1]];
    }


    //This method will take in two parameters, the player who needs his coordinates updated
    //and the new coordinates, when given these two it will update that players coordinates with the new ones
    protected void updatePosition (String player, int[] newLocation)
    {
        if(player.equals("player"))//check if the string is player
        {
          playerPosition[0] = newLocation[0];
          playerPosition[1] = newLocation[1];
        }
        else//must be a bot spawned
        {
          botPosition[0] = newLocation[0];
          botPosition[1] = newLocation[1];
        }
    }


    //similar to updatePosition() it will take as a parameter the type of player to spawn and spawn him in the map
    protected void spawnPlayer(String player)
    {
      if(player.equals("player"))//check if the string is player
      {
        Map mapClass = new Map();
        Random random = new Random();

        int height = mapClass.getHeight();
        int width = mapClass.getWidth();

        int randomHeight = random.nextInt(height);
        int randomWidth = random.nextInt(width);

        if (myMap[randomWidth][randomHeight] == '#' || myMap[randomWidth][randomHeight] == 'G' || myMap[randomWidth][randomHeight] == 'B')//If the chosen coordinates are taken by gold a wall or a bot then it will be called again until an empty position is found
        {
          spawnPlayer("player");
        }
        else//Empty spot found, assing players coordinates to the position found through random()
        {
            playerPosition[0] = randomWidth;
            playerPosition[1] = randomHeight;
        }
      }
      else
      {
        Map mapClass = new Map();
        Random random = new Random();

        int height = mapClass.getHeight();
        int width = mapClass.getWidth();

        int randomHeight = random.nextInt(height);
        int randomWidth = random.nextInt(width);

        if (myMap[randomWidth][randomHeight] == '#' || myMap[randomWidth][randomHeight] == 'G')//Same as players case.
        {
          spawnPlayer("bot");
        }
        else
        {
            botPosition[0] = randomWidth;
            botPosition[1] = randomHeight;
        }
      }
    }
}
