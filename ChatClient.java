
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ChatClient
{
    ClientThreads clientthreads;

    public static void main(String[] args)
    {
        /*
        The following 50 lines are used to take inputted flags on the command line to optionally change the port and address
        to which this client connects. If no flags are given and the only argument passed to the command line is
        java ChatClient then the client will have address localhost and port number 14001.
        If the format is wrong then the client will automatically be assigned these default values.
         */
        int port = 14001;
        String address = "localhost";

        if (args.length >= 1)
        {
            System.out.println("Client successfully created!");

            for (int i = 0; i < args.length; i++)
            {
                if (args[i].equals("-ccp"))
                {
                    //if the user types -ccp the next argument will be the desired port, so this value will be
                    //parsed into an integer as it is passed as a string and then assigned to the port variable instead of 14001.
                    port = Integer.parseInt(args[i + 1]);
                    System.out.println("Client Port Number: " + port);

                    for (int j = 0; j < args.length; j++)
                    {
                        if (args[j].equals("-cca"))
                        {
                            //If the user types -cca the next argument will be the desired address, so this value will be
                            //assigned to the address variable instead of localhost.
                            address = args[j + 1];
                        }
                    }
                    System.out.println("Client Address: " + address);
                    break;
                }
                else if (args[i].equals("-cca"))
                {
                    address = args[i + 1];
                    System.out.println("Client Address: " + address);

                    for (int j = 0; j < args.length; j++)
                    {
                        if (args[j].equals("-ccp"))
                        {
                            port = Integer.parseInt(args[j + 1]);
                        }
                    }
                    System.out.println("Client Port Number: " + port);
                    break;
                }
                else
                {
                    System.out.println("Sorry I couldnt understand your input. \nCreating client on default values\n" + "Port Number: " + port + " \nAddress" + address);
                }
            }
        }
        else
        {
            System.out.println("Client successfully created on default values:");
            System.out.println("Client Port Number: " + port);
            System.out.println("Client Address: " + address);
        }

        //this will call the client constructor with the selected port and address as arguments.
       new ChatClient(address , port);
    }


    public ChatClient(String Address, int port)
    {
        try
        {
            //This will create a ChatClient thread which will use the desired socket.
            Socket clientSocket = new Socket(Address , port);
            clientthreads = new ClientThreads(clientSocket);
            //This will start the thread.
            clientthreads.start();
            //This will call the method which allows the user to input text from the console.
            clientListen();
        }
        catch (UnknownHostException unknownHostException)
        {
            //if the address is wrong the exception will be caught and will return to the console.
            System.out.println("Sorry this address doesn't exist. Please try again!");
        }
        catch(ConnectException connectException)
        {
            //if there exists no server at the desired port this error will return to the console.
            System.out.println("\nSorry no server was found here. Please try again \n");
        }
        catch (IOException ioException)
        {
        }
    }


    public void clientListen()
    {
        //This method will allow the client to type in from the console with a scanner and then this will be analyzed,
        // and depending on what is inputted it will either send the string to the server or quit te program.
        Scanner console = new Scanner(System.in);
        {
            //this loop will allow the client to enter messages until it types quit or the connection is ended by the server
            while (true)
            {
                //This loop will sleep threads which are not typing to release stress on the CPU
                while (!console.hasNextLine())
                {
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch (InterruptedException interruptedException)
                    {
                        interruptedException.printStackTrace();
                    }
                }

                String input = console.nextLine();

                if(input.equals("quit"))
                {
                    //This is an added feature, where if the user inputs "quit" then the loop will be broken,
                    // thus not sending the string to the server and calling clientClose(), which will close that client,
                    // but will allow other clients to carry on chatting.
                    break;
                }
                clientthreads.clientSend(input);
            }

            clientthreads.clientClose();
        }
    }
}
