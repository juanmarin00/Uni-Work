import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer
{
    //will read input from console with a BufferedReader
    protected String getInputFromConsole() throws IOException
    {
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        return consoleInput.readLine();
    }

    //will process the users input
    //if users command is not like the ones in read me then it will be ignored and player will use turn
    protected String processCommand(String command)
    {
        switch (command.toLowerCase())//use.toLowerCase() to ignore case of command
        {
            case "hello":
                return "hello";
            case "move n":
                return "move north";
            case "move s":
                return "move south";
            case "move e":
                return "move east";
            case "move w":
                return "move west";
            case "pickup":
                return "pickup";
            case "look":
                return "look";
            case "quit":
                return "quit";
            case "gold":
                return "gold";
            default:
                return "other input";
        }
    }


    //This method will be called in every player's turn to exewcute their command with the corresponding method
    protected void selectNextAction() throws IOException
    {
        HumanPlayer humanPlayer = new HumanPlayer();
        GameLogic gameLogic = new GameLogic();
        Map mapClass = new Map();

        System.out.println("Please input your next move: ");
        String response = humanPlayer.processCommand(humanPlayer.getInputFromConsole());

        switch (response)
        {
            case "hello":
                System.out.println(gameLogic.inputHello());
                break;
            case "gold":
                mapClass.showGoldString();
                break;
            case "pickup":
                System.out.println(gameLogic.inputPikcup());
                break;
            case "look":
                System.out.println(gameLogic.inputLook(mapClass.getMap(), mapClass.returnPlayersPosition()));
                break;
            case "quit":
                gameLogic.levelCompleted();
                break;
            case "move north":
                System.out.println(gameLogic.moveNorth());
                break;
            case "move south":
                System.out.println(gameLogic.moveSouth());
                break;
            case "move east":
                System.out.println(gameLogic.moveEast());
                break;
            case "move west":
                System.out.println(gameLogic.moveWest());
                break;
            case "other input":
                System.out.println("Invalid input, You lost your turn.");
                break;
            default:
                selectNextAction();
                break;
        }
    }
}
