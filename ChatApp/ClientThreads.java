
import java.io.*;
import java.net.Socket;

public class ClientThreads extends Thread
{

    Socket clientSocket;
    DataInputStream messageIn;
    DataOutputStream messageOut;
    boolean isChatting = true;

    public ClientThreads(Socket socket)
    {
        clientSocket = socket;
    }


    public void run()
    {
        try
        {
            //This will create two streams and input and output, one used to read incoming messages
            //and the other to write outgoing messages. Additionally it will creatte a bufferedReader to read the input from the Server
            messageIn = new DataInputStream(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            messageOut = new DataOutputStream(clientSocket.getOutputStream());

            //This loop will ensure that our incoming messages broadcasted by the server are printed in each Client thread
            while(isChatting == true)
            {
                try
                {
                    String reply = br.readLine();
                    if(reply==null)
                    {
                        System.out.println("The server has closed the connection between all clients");
                        System.exit(0);
                        break;
                    }
                    else
                    {
                        System.out.println("Client: " + reply);
                    }
                }
                catch(IOException ioException)
                {
                    close();
                    break;
                }
            }

        }
        catch (IOException ioException)
        {
            closedByServer();
        }
    }

    //This method is called in ChatClient's Clientlisten, it will write the given inpu onto the messageout stream, so that it can be sent later.
    public void clientSend(String text)
    {
        try
        {
            messageOut.writeUTF(text);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
            close();
        }
    }

    //This is the method which is called when only one client wants to leave, it will close() which will close both
    //incoming and outgoing streams and then close the socket.
    //Then it will aprint an exit message and finally it will execute a system.exit.
    public void clientClose()
    {
        close();
        System.out.println("Connection ended by user.");
        System.exit(0);
    }

    //Similarly to clientCLose, this will be called when our server ends all connections. It will also call close(),
    // then print an exit message and finally end the program for all clients with a system.exit.
    public void closedByServer()
    {
            close();
            System.out.println("Connection ended by server.");
            System.exit(0);
    }

    //This method will be used to reduce length of code and tidy up as this try and catch block was exectued in both closedByServer() and clientClose().
    public void close()
    {
        try
        {
            messageIn.close();
            messageOut.close();
            clientSocket.close();
        }
        catch (IOException ioException)
        {
        }

    }
}
