import java.io.*;
import java.net.*;
import java.*;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;

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
    int correctTransfer = 0;
    int errorTransfer = 0;
    int numTimeRecv = 0;
    String length;
    int fileLength = 0;
    int fileNum = 1;
    
    ServerSocket welcomeSocket = new ServerSocket(9876);
    System.out.println("I am waiting for a connection from Client Side...");
    connectionSocket = welcomeSocket.accept();
    
    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    
    
    System.out.println("I am starting now...");
   
    File file = new File("c:/Users/Cameryn/Documents/NetBeansProjects/Server/filesOfSentData/testFile1.txt");
    FileWriter writer = new FileWriter(file);
    
    //Create the file
    if (file.createNewFile())
    {
        System.out.println("File is created!");
    }
    else
    {
        System.out.println("File already exists.");
    }

    while(true)
    {
        numTimeRecv++;
        
        //Get the length of the files that will be received
        if(numTimeRecv == 1)
        {
            length = inFromClient.readLine();
            fileLength = Integer.parseInt(length);
            System.out.println("Length of file: "+fileLength);
        }
        
  
        
        //Get the first line in File that was sent OUT of Client Socket and INTO Server Socket
        clientFile = inFromClient.readLine();
        
        //Extract the length of the line
        if(clientFile != null) 
        {
            sizeOfLine = clientFile.length();
        }
     
        //Add the length of the line to cumulative count of all lines; this will determine when the whole file has been received
        //Writes to blank or incomplete file on server side as long as the whole file hasn't already been received from client
        if(countToLength <= fileLength)
        {
            countToLength += sizeOfLine;
            System.out.println("Adding to file...\n");
            writer.write(clientFile+'\n');
        }
        else 
        //file has been completely received now compare with original file to check error rate
        //Create new blank file and new writer on server side; Reset cumulative count of lines
        {
            ++newFileNum;
            file = new File("filesOfSentData/testFile"+newFileNum+".txt");
            
            System.out.println("1 whole file transfer complete! \n");
            
            /*Path original = Paths.get("c:/Users/Cameryn/Documents/NetBeansProjects/Server","largeOriginal.txt");
            Path toTest = Paths.get("c:/Users/Cameryn/Documents/NetBeansProjects/Server/filesOfSentData", "testFile"+ fileNum+".txt");
            fileNum++;
            byte[] originalFile = Files.readAllBytes(original);
            byte[] fileToTest = Files.readAllBytes(toTest);
            
            if(Arrays.equals(originalFile, fileToTest))
            {
               correctTransfer++; 
            }
            else 
            {
                errorTransfer++;
                System.out.println("Correct: "+correctTransfer+" Error: "+errorTransfer+"\n");
            }*/
            
            File originalFile = new File("largeOriginal.txt");
            File fileToTest = new File("testFile"+ fileNum+".txt");
            
            
            if(FileUtils.contentEquals(originalFile, fileToTest))
            {
                correctTransfer++;
            }
            else
            {
                errorTransfer++;
                System.out.println("Correct: "+correctTransfer+" Error: "+errorTransfer+"\n");
            }
            writer.close();
            writer = new FileWriter(file);
            countToLength = 0;
        }
            
        
        if(clientFile == null) 
        {
            break;
        }
        
        //MESSAGE TO CLIENT SIDE
        try
        {
            confirmTransferMsg = "I have received file lines "+ numTimeRecv + " times";
            outToClient.writeBytes(confirmTransferMsg + "\n");
        }
        catch(SocketException e)
        {
            e.getMessage();
        }    
    }
   
    System.out.println("I am done now");
    welcomeSocket.close();
  }
}



