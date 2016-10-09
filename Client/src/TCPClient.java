import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPClient {
  public static void main(String argv[]) throws Exception
  {
      String contentFromFile;
      String serverConfirm;
      
      Socket clientSocket = new Socket("192.168.1.159", 6789);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      int numTimeSent = 0;
       
      while(numTimeSent <= 5)
      {
          numTimeSent++;
          
          File fileToSend = new File("testerFile.txt");
          Scanner scan = new Scanner(fileToSend);
          scan.useDelimiter("\\Z");  
          contentFromFile = scan.next();
          
          System.out.println("This is the infor sent: "+contentFromFile);
          outToServer.writeBytes(contentFromFile + '\n');
         
          serverConfirm = inFromServer.readLine();
          
          System.out.println("From server :" + serverConfirm); 
          
          scan.close();
        }
      
      System.out.println("I am done now!");
      clientSocket.close();
   }
}
  
