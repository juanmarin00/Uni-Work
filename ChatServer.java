
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ChatServer
{
    ServerSocket serverSocket;
    ArrayList<ServerThreads> clientsOnline = new ArrayList<ServerThreads>();//ArrayList used to store all connections between client and server threads
    boolean isChatting = true; //boolean which will remain as true until serverClose is called, it will be used to constantly accept connections with other clients
    ServerThreads serverThreads;


    public static void main(String[] args) throws IOException
    {
        /*
        Just like the one in ChatClient's main method, this block of code will be used to check
        if any flags are given when starting the programm to use a chosen port number instead of 14001,
        if no flag is given then the port used will be 14001.
         */
        int port = 14001;

        if(args.length == 0)
        {
            System.out.println("Server successfully created on default port: " + port);
        }
        else if(args.length >= 1)
        {
            for (int i = 0; i < args.length; i++)
            {
                if (args[i].equals("-csp"))
                {
                    port = Integer.parseInt(args[i + 1]);
                    System.out.println("Server successfully created on: \nPort number: " + port);
                    break;
                }
                else
                {
                    System.out.println("Sorry you inputted wrong input. Server created on default port: " + port);
                    break;
                }
            }
        }

        //This will start a ChatServer with the desired Port.
        new ChatServer(port).go();
    }


    public ChatServer(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (BindException bindException)
        {
            //if someone is already using this port then you will be given this message and exit the program.
            System.out.println("Sorry this port is already in use!");
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }


    //This method will be use to close connections when you type "EXIT" onto the Servers console.
    public void closeServer()
    {
        //it will turn isChatting to false, to stop accepting connections and then close both the serversocket,
        // and call closeAll(), which will close all clients.
        isChatting = false;
        try
        {
            serverSocket.close();
            closeAll();

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }


    //This method will both create a thread of keyboardlistener thread and call start() to constantly check if "EXIT" is typed into the servers console
    // and also constantly accept connections with clients and create threads of ServerThreads to enable chatting.
    public void go()
    {
        new Thread(new KeyboardListener(this)).start();

            try
            {
                while(isChatting)
                {
                Socket s = serverSocket.accept();
                ServerThreads sc = new ServerThreads(s, this);
                sc.start();//this will start the threads
                clientsOnline.add(sc);// this will add those threads to our arrayList, so we can later loop around it
                    //to broadcast client's messages or close the server
                System.out.println("\nClient connected!");

                }
            }
            catch (SocketException socketException)
            {
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
            finally
            {
                //this block will only happen when isCHatting = false, after closeServer is called, which will print a message and close the server
                System.out.println("Closing server\n");
                System.exit(0);
            }
    }

    public void closeAll()
    {
        try
        {
            //this will loop around all of our clientsOnline arraylist and then call closeConnection on each one of them.
            for(int clientNumber = 0; clientNumber<clientsOnline.size(); clientNumber++)
            {
                serverThreads.closeConnection();
            }
        }
        catch (NullPointerException nullPointerException)
        {

        }
    }
}
