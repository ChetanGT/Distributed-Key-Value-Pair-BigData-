import java.net.*;
import java.io.*;
import java.lang.*;

public class server {
 public static void main(String[] args) throws Exception {

  if (args.length != 2) {
   System.err.println("Usage: java Server <port number>");
   System.exit(1);
  }
  String[] Replicas = {
   "0",
   "",
   ""
  };  //Replica array to maintain the replica server IP address
  String Replica = "";
  String inputLine, outputLine;
  serverProtocol ser = new serverProtocol(); //instantiating server Protocol
  String ip = args[0];
  if (args[1].equals("true")) {
   try (
    ServerSocket serverSocket = new ServerSocket(6000, 5, InetAddress.getByName(ip)); Socket clientSocket = serverSocket.accept(); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   ) {

    System.out.println("Server 1-1");
    inputLine = in .readLine(); //reading the input
    outputLine = ser.processInput(inputLine);
    out.println(outputLine);
    serverSocket.close();

    outputLine = ser.processInput("Replica");
    String[] temp = outputLine.split(" "); //replica IP address from the serverprotocol
    for (int i = 0, j = 1; i < 5; i++) {
     System.out.println(temp[i]);
     if (i == 0 || i == 1) {} else {
      Socket cSocket = new Socket(temp[i], 6000); //creating sccket for each server based on ip address and fixed port number
      PrintWriter out2 = new PrintWriter(cSocket.getOutputStream(), true);
      if (i % 2 == 1) {
       out2.println("");
      } else {
       Replicas[j] = temp[i + 1];
       j++;
       out2.println(temp[i + 1]);
      }
      cSocket.close();
     }
    }
    Socket cSocket = new Socket(temp[5], 6000);
    PrintWriter out2 = new PrintWriter(cSocket.getOutputStream(), true);
    out2.println("");
   } catch (IOException e) {
    System.out.println(e.getMessage());
   }
  }
  if (!(args[1].equals("true"))) {
   ServerSocket serverSocket = new ServerSocket(6000, 5, InetAddress.getByName(ip));//connecting the the server using IP address
   Socket clientSocket = serverSocket.accept();
   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   Replica = in .readLine();
   System.out.println(Replica);
   serverSocket.close();
  }
  
  //Keeping the server active always, using while loop
  while (true) {

   try (
    ServerSocket serverSocket = new ServerSocket(6000, 5, InetAddress.getByName(ip)); Socket clientSocket = serverSocket.accept(); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   ) {


    System.out.println("Server 1-2");
    String tempInput = "";
    while ((inputLine = in .readLine()) != null) {
     tempInput = inputLine;
	 //if the serever fails connecting to the replica server based on the ip address
     if (inputLine.charAt(0) != 'p' && inputLine.charAt(0) != 'P' && inputLine.charAt(0) != 'G' && inputLine.charAt(0) != 'g') {
      outputLine = Replicas[Integer.parseInt(tempInput)]; //getting the ip address of the replica 
     } else {
      outputLine = ser.processInput(inputLine);
     }
     out.println(outputLine);
     if (outputLine.equals("Bye."))
      break;
    }
    serverSocket.close();
	//Every time putting the key value pair in the corresponding  replica server after putting in the main server
    if (!(args[1].equals("true")) && Replica != "" && (tempInput.charAt(0) == 'p' || tempInput.charAt(0) == 'P')) {
     Socket cSocket = new Socket(Replica, 6000);
     PrintWriter out2 = new PrintWriter(cSocket.getOutputStream(), true);
     System.out.println(tempInput + "REPLICA");//putting the value in the replica
     out2.println(tempInput);
     while ((outputLine = in .readLine()) != null) {
      System.out.println("Server111: " + outputLine);
      if (outputLine.equals("exit")) {

       System.out.println("Server: " + "disconnected");
       System.exit(0);
      }
      cSocket.close();//closing the socket connection
     }
    }
   } catch (IOException e) {
    System.out.println("ZSZSZSZ" + e.getMessage());
   }
  }
 }
}