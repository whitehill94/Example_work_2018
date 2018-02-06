package P5;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    public static void main(String [] args) throws Exception {
  
        ClientThread client;    
        BufferedReader reader;  
        GFlag localFlag;         
        String message;
        
        reader = new BufferedReader(new InputStreamReader(System.in));

       
        client = new ClientThread();
        localFlag = client.getLocalFlag();

        //Set username
        System.out.print("Enter a username: ");
        client.setUsername(reader.readLine());
        System.out.println("Username Accepted... Type EXITEXIT in order to leave.");

        client.start(); // Start Thread 
        
       
        while (localFlag.get()) {
            message = reader.readLine();
            client.sendMessageToServer(message);
           
            //if EXITEXIT terminate client
            if (message.contains("EXITEXIT")) {
                localFlag.setFalse();
            }
        }
        
        System.out.println("You have left the chatroom");
    }
}
