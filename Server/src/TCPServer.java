import java.io.*;
import java.net.*;
import java.*;
//import org.apache.commons.io.FileUtils;

public class TCPServer {
  public static void main(String argv[]) throws Exception
  {
    String clientFile = "";
    String confirmTransferMsg;
    Socket connectionSocket;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    int countToLength = 0;
    int sizeOfLine = 0;
    int newFileNum = 1;
    //String line = null;
    //StringBuffer stringBuffer = new StringBuffer();
    
    ServerSocket welcomeSocket = new ServerSocket(3456);
    System.out.println("I am waiting for a connection from Client Side...");
    connectionSocket = welcomeSocket.accept();
    
    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    
    int numTimeRecv = 0;
    System.out.println("I am starting now...");
    String length;
    int fileLength = 0;
    File file = new File("c:/Users/Cameryn/Documents/NetBeansProjects/Server/filesOfSentData/testFile1.txt");
    
    //Create the file
    if (file.createNewFile()){
    System.out.println("File is created!");
    }else{
    System.out.println("File already exists.");
    }

    //Write Content
    FileWriter writer = new FileWriter(file);
    /*writer.write("Test data");
    writer.close()*/
    
    while(true)
    {
        numTimeRecv++;
        
        if(numTimeRecv == 1)
        {
            length = inFromClient.readLine();
            fileLength = Integer.parseInt(length);
            System.out.println("Length of file: "+fileLength);
        }
        
  
        
        //Get the first line in File that was sent OUT of Client Socket and INTO Server Socket
        clientFile = inFromClient.readLine();
        if(clientFile != null) 
        {
            sizeOfLine = clientFile.length();
        }
        
        
        
        
        if(countToLength <= fileLength)
        {
            countToLength += sizeOfLine;
            //writer = new FileWriter(file);
            System.out.println("Adding to file...");
            System.out.println("Created file counter before: "+ countToLength);
            
            System.out.println("Original file length: "+ fileLength);
            writer.write(clientFile+'\n');
            //writer.close();
            System.out.println("Created file counter after: "+ countToLength);
        }
        else
        {
            ++newFileNum;
            file = new File("c:/Users/Cameryn/Documents/NetBeansProjects/Server/filesOfSentData/testFile"+newFileNum+".txt");
            writer.close();
            writer = new FileWriter(file);
            countToLength = 0;
        }
            
        
        if(clientFile == null) 
        {
            break;
        }
        
       
        System.out.println("I have received a client file line ("+ numTimeRecv + " times)");
        //System.out.println("ClientFileLine: "+clientFile);
        
        confirmTransferMsg = "I have received file lines"+ numTimeRecv + " times";
        outToClient.writeBytes(confirmTransferMsg + "\n");
        
    }
   
    System.out.println("I am done now");
    //writer.close();
    welcomeSocket.close();
  }
}



