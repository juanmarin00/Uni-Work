
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class KeyboardListener implements Runnable
{
    //This class is only used to be able to create a thread in the ChatClient class so that we can allow and analyze
    // what is entered in its terminal whilst we carry on accepting and establishing connections with clients.
    ChatServer server;


    public KeyboardListener(ChatServer server)
    {
       this.server=server;
    }


    public void run()
    {
        //This will always be inside of the loop trying to read what we enter into the console and see if it is "EXIT".
        //if it is "EXIT" then in will close closeServer, if it isnt it will just ignore it.
        BufferedReader console=new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try
            {
                String input = console.readLine();

                if(input.equals("EXIT"))
                {
                    server.closeServer();
                }
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}
