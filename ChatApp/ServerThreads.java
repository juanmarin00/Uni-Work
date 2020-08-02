
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


public class ServerThreads extends Thread
{
    Socket socket;
    ChatServer server;
    DataInputStream messageIn;
    DataOutputStream messageOut;
    boolean isChatting = true;


    public ServerThreads(Socket socket, ChatServer server)
    {
        this.socket = socket;
        this.server = server;
    }


    //This method will loop thrrough all clients and broadcast the string which is given
    // by reading it from the server threads in the run() method.
    public void broadcastStringToClient(String text)
    {
        for(int clientNumber = 0; clientNumber<server.clientsOnline.size(); clientNumber++)
        {
            ServerThreads sc = server.clientsOnline.get(clientNumber);
            try
            {
                sc.messageOut.writeUTF(text + "\n");
            }
            catch (SocketException socketException)
            {
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }


    public void run()
    {
        try
        {
            //This will create two streams and input and output, one used to read incoming messages
            // and the other to write outgoing messages.
            messageIn = new DataInputStream(socket.getInputStream());
            messageOut = new DataOutputStream(socket.getOutputStream());

            while(isChatting=true)
            {
                //This will sleep the threads if there is no outgoing message to release stress on the CPU
                while(messageIn.available() == 0)
                {
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch(InterruptedException interruptedException)
                    {
                        interruptedException.printStackTrace();
                    }
                }

                //This will create a string which will be the users inputed string and then call broadcastToClient with that string as its argument
                String message = messageIn.readUTF();

                broadcastStringToClient(message);
            }
            //when isChatting turns to false this will close our streams and our socket
            messageIn.close();
            messageOut.close();
            socket.close();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }


    //This method will close the socket for a given thread and then will be called in ChatServer's closeAll() method
    //to loop through each connection and close them all.
    public void closeConnection()
    {
        try
        {
            socket.close();
        }
        catch (IOException ioException)
        {
        }
        catch (NullPointerException nullPointerException)
        {
        }
    }
}
