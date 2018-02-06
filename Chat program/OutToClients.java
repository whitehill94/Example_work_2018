package P5;
import java.io.*;
import java.util.*;

public class OutToClients extends Thread {
    //Declare needed variables
    private BufferedReader inFromClient;   
    private String message;               
    private List<OutToClients> list;      
    private DataOutputStream outToClient;   
    private GFlag serverFlag;                

    public OutToClients(BufferedReader inFromClient, List<OutToClients> list, DataOutputStream outToClient, GFlag serverFlag) {
        this.inFromClient = inFromClient;
        this.list = list;
        this.outToClient = outToClient;
        this.serverFlag = serverFlag;
    }

  
    public void sendToClient(String message) throws Exception {
        outToClient.writeBytes(message + "\n");
    }

    public void endChat() throws Exception {
        sendToClient("Server Shutting Down...");
    }

    public void run() {
        try {
            //specificIndex will be the index of the specific client associated with OutToClients
            int specificIndex = 0;
          
            
            while (serverFlag.get()) {
                message = inFromClient.readLine();
                //Make sure the chat wasn't ended while waiting for a message...
                if (serverFlag.get()) {
                    //Iterate through the list of Clients and broadcast the message
                    for (int index = 0; index < list.size(); index++) {
                       
                    	// Don't Broadcast to self
                        if (!list.get(index).equals(this)) {
                            list.get(index).sendToClient(message);
                        } else {
                            specificIndex = index;
                        }
                    }

                    //If the client EXITEXIT
                    if (message.contains("EXITEXIT")) {
                        //Remove them from the LinkList, break out of the loop.
                        list.remove(specificIndex);
                        sendToClient("Server has acknowledged and disconnected you");
                        //Provides Main with a clean exit.
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
