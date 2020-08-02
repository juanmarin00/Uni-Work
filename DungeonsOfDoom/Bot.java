import java.util.Random;

public class Bot
{
  //Will use random() to create a random number between 1 and 4 which will decide where the bot moves
  public static int randomGen()
  {
      Random random = new Random();

      int randomDirection = random.nextInt(4);

      return randomDirection;
  }

  //This method will call randomGen() and use the generated randomNumber to call a move function
  public static void botMove()
  {
      int randInt = randomGen();

      if(randInt==1)
      {
          moveNorth();
      }
      else if(randInt==2)
      {
          moveEast();
      }
      else if(randInt==3)
      {
          moveSouth();
      }
      else
      {
          moveWest();
      }
  }

  //The following 4 methods will make the bot move
  protected static void moveNorth()
  {
      Map map = new Map();
      int[] botPosition = map.returnBotPosition();

      int[] tempPosN = {botPosition[0], botPosition[1] - 1};
      char tileAbove = map.getCoords(tempPosN);
      isMoveValid(tempPosN, tileAbove);
  }

  protected static void moveSouth()
  {

      Map map = new Map();
      int[] botPosition = map.returnBotPosition();

      int[] tempPosS = {botPosition[0], botPosition[1] + 1};
      char tileBelow = map.getCoords(tempPosS);
      isMoveValid(tempPosS, tileBelow);
  }


  protected static void moveEast()
  {

      Map map = new Map();
      int[] botPosition = map.returnBotPosition();

      int[] tempPosE = {botPosition[0] + 1, botPosition[1]};
      char tileRight = map.getCoords(tempPosE);
      isMoveValid(tempPosE, tileRight);
  }


  protected static void moveWest()
  {
      Map map = new Map();
      int[] botPosition = map.returnBotPosition();

      int[] tempPosW = {botPosition[0] - 1, botPosition[1]};
      char tileLeft = map.getCoords(tempPosW);
      isMoveValid(tempPosW, tileLeft);
  }


  //Similar to isMoveValid() for player,m checking if mov is valid, and if it is then moving and if it sint then not updating the position
  protected static void isMoveValid(int[] newPosition, char tile)
  {
      Map map = new Map();
      GameLogic gameLogic = new GameLogic();

      if (map.returnPlayersPosition()[0] == newPosition[0] && map.returnPlayersPosition()[1] == newPosition[1])
      {
          System.out.println("Collision with bot! Game over!");
          gameLogic.quit();
      }
      else if (Character.toString(tile).equals("#"))
      {
          System.out.println("Bot has moved");
          System.out.println("players turn.");
      }
      else
      {
          map.updatePosition("Bot",newPosition);
          System.out.println("Bot has moved");
          System.out.println("players turn.");
      }
  }
}
