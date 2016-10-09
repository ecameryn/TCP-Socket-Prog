import java.io.*;
import java.net.*;
//import org.apache.commons.io.FileUtils;

public class TCPServer {
  public static void main(String argv[]) throws Exception
  {
    String clientFile;
    String confirmTransfer;
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
        
        //System.out.println("Server whileLoop begin");
        i++;
        
        clientFile = inFromClient.readLine();
        if(clientFile == null) break;
        
        confirmTransfer = "I have received file";
        System.out.println("I have received: "+ clientFile + "  ("+ i + " times)");
        
        outToClient.writeBytes(confirmTransfer + "\n");
    }
    System.out.println("I am done now");
    welcomeSocket.close();
  }
}



