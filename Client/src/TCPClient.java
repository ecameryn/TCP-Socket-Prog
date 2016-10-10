import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPClient {
  public static void main(String argv[]) throws Exception
  {
      //Output from server, File/Content and FileInputStr variables
      String serverResponse;
      String contentFromFile;
      //Scanner scan;
      File fileToSend = new File("1kbfile.txt");
      
      FileInputStream fis = new FileInputStream(fileToSend);
      byte[] fileContent = new byte[(int) fileToSend.length()];
      fis.read(fileContent);
      fis.close();
      
      //Socket and Output/Input variables
      Socket clientSocket = new Socket("192.168.43.227", 3456);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      //Count times file is sent
      int numTimeSent = 0; 
      
     //send file 100 times
      while(numTimeSent < 100)
      {
          numTimeSent++;
          
          /*//Reset Scanner each loop and get File content
          scan = new Scanner(fileToSend);
          scan.useDelimiter("\\Z");  
          contentFromFile = scan.next();
          scan.close();*/
          
          contentFromFile = new String(fileContent, "UTF-8");
          
          if(numTimeSent == 1)
          {
            int length = fileContent.length;
            String fileLength = String.valueOf(length);
            outToServer.writeBytes(fileLength + '\n');
          }
          
          
          //Send file OUT of Client Socket and INTO Server Socket
          System.out.println("Sending file "+ numTimeSent +" times");
          
          
          outToServer.writeBytes(contentFromFile);
          
          //Response comes INTO Client Socket OUT of Server Socket
          serverResponse = inFromServer.readLine();
          System.out.println("From server : " + serverResponse + "\n");  
        }
     
      System.out.println("I am done now!");
      //System.out.println(contentFromFile);
      clientSocket.close();
   }
}
  
