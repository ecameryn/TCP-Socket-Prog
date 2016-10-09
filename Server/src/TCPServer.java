import java.io.*;
import java.net.*;
//import org.apache.commons.io.FileUtils;

public class TCPServer {
  public static void main(String argv[]) throws Exception
  {
    String clientFile;
    String confirmTransferMsg;
    Socket connectionSocket;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    
    ServerSocket welcomeSocket = new ServerSocket(6789);
    System.out.println("I am waiting for a connection from Client Side...");
    connectionSocket = welcomeSocket.accept();
    
    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    
    int numTimeRecv = 0;
    System.out.println("I am starting now...");
    
    while(true)
    {
        numTimeRecv++;
        
        //Get the File that is sent OUT of Client Socket and INTO Server Socket
        clientFile = inFromClient.readLine();
        if(clientFile == null) break;
       
        System.out.println("I have received client file ("+ numTimeRecv + " times)");
        
        confirmTransferMsg = "I have received file "+ numTimeRecv + " times";
        outToClient.writeBytes(confirmTransferMsg + "\n");
    }
    System.out.println("I am done now");
    welcomeSocket.close();
  }
}



