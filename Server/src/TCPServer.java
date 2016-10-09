import java.io.*;
import java.net.*;
//import org.apache.commons.io.FileUtils;

public class TCPServer {
  public static void main(String argv[]) throws Exception
  {
    String clientSentence;
    String capitalizedSentence;
    Socket connectionSocket;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    
    ServerSocket welcomeSocket = new ServerSocket(6789);
    System.out.println("I am waiting for a connection from Client Side...");
    connectionSocket = welcomeSocket.accept();
    
    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    
    int i=0;
    System.out.println("I am starting now...");
    
    while(true){
        i++;
        
        clientSentence = inFromClient.readLine();
        if(clientSentence == null) break;
        
        System.out.println("I have received: "+ clientSentence + "   "+ i + "th times");
        capitalizedSentence = clientSentence.toUpperCase() + '\n';
        outToClient.writeBytes(capitalizedSentence);
    }
    System.out.println("I am done now");
    welcomeSocket.close();
  }
}



