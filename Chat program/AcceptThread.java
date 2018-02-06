package P5;
import java.net.*;
import java.io.*;
import java.util.*;

public class AcceptThread extends Thread {
   
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private ServerSocket welcomeSocket; 
    private Socket connectionSocket;       
    private int port = 5555;
    private List<OutToClients> list;   
    private OutToClients OutToClients;  
    private GFlag serverFlag;                

    //Instantiate the variables in the constructor
    public AcceptThread(List<OutToClients> list, GFlag serverFlag) throws Exception{
        this.list = list;
        this.serverFlag = serverFlag;
        welcomeSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while (serverFlag.get()) {
                //wait for clients to connect
                connectionSocket = welcomeSocket.accept();

                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                OutToClients = new OutToClients(inFromClient, list, outToClient, serverFlag);

              
                list.add(OutToClients);
                OutToClients.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
