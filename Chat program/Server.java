package P5;
import java.io.*;
import java.util.*;

public class Server {

    public static void main(String [] args) throws Exception {
    
        //The serverFlag will control loops
        GFlag serverFlag = new GFlag();

        
        List<OutToClients> list = new LinkedList<OutToClients>(); //list of clients 

        //wait for clients to connect
        AcceptThread accepterThread = new AcceptThread(list, serverFlag);

        //Input for Server
        BufferedReader ServerIn = new BufferedReader(new InputStreamReader(System.in));

        accepterThread.start();

        System.out.println("Server Running. To terminate, type EXITEXIT");

        //While the chat is still going...
        while (serverFlag.get()) {
          
        	//Continue the chat until EXITEXIT
            if (ServerIn.readLine().contains("EXITEXIT")) {
                serverFlag.setFalse();
                for (int index = 0; index < list.size(); index++) {
                    list.get(index).endChat();
                }
            }
        }
        System.out.println("Shutting Down... Waiting on client confermation...");

        //Create one last thread to let the accepterThread make it through the final loop...
        ClientThread endingThread = new ClientThread();
    }


}