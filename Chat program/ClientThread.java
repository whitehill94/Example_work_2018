package P5;
import java.io.*;
import java.net.*;

public class ClientThread extends Thread {
	
    private BufferedReader inFromServer;  
    private DataOutputStream outToServer;   
    private Socket socket;              
    private int port = 5555;         
    private String host = "localhost";  
    private GFlag localFlag;             
    private String messageFromServer;     
    private String username;                

    //Instantiate the variables in the constructor
    public ClientThread() throws Exception {
        socket = new Socket(host, port);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer = new DataOutputStream(socket.getOutputStream());
        localFlag = new GFlag();
        username = "";
    }

    //Provide a setter method for the username
    public void setUsername(String input) {
        this.username = input;
    }

 
    public void sendMessageToServer (String message) throws Exception {
      
        outToServer.writeBytes(username + ": " + message + "\n");
        //if the client wants to leave the chat
        if (message.contains("EXITEXIT")) {
            localFlag.setFalse();
        }
    }


    public GFlag getLocalFlag() {
        return localFlag;
    }

    public void run() {
        try {
            //While this client is still chatting...
            while (localFlag.get()) {
                messageFromServer = inFromServer.readLine();
                System.out.println(messageFromServer);
                //Check to see if the server has ended the chat
                if (messageFromServer.equals("Server Shutting Down...")) {
                    localFlag.setFalse();
              //If server has ended...
                    System.out.println("press enter to shutdown");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
