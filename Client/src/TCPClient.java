import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPClient {
  public static void main(String argv[]) throws Exception
  {
      //Output from server, File/Content and Scanner variables
      String serverResponse;
      String contentFromFile;
      Scanner scan;
      File fileToSend = new File("testerFile.txt");
      
      //Socket and Output/Input variables
      Socket clientSocket = new Socket("192.168.1.159", 6789);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      //Count times file is sent
      int numTimeSent = 0; 
      
     //send file 100 times
      while(numTimeSent < 100)
      {
          numTimeSent++;
          
          //Reset Scanner each loop and get File content
          scan = new Scanner(fileToSend);
          scan.useDelimiter("\\Z");  
          contentFromFile = scan.next();
          scan.close();
          
          //Send file OUT of Client Socket and INTO Server Socket
          System.out.println("Sending file "+ numTimeSent +" times");
          outToServer.writeBytes(contentFromFile + '\n');
          
          //Response come INTO Client Socket OUT of Server Socket
          serverResponse = inFromServer.readLine();
          System.out.println("From server : " + serverResponse + "\n");  
        }
     
      System.out.println("I am done now!");
      clientSocket.close();
   }
}
  
