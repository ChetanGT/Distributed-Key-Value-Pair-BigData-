import java.io.*;
import java.net.*;
import java.lang.*;

public class client {
    public static void main(String[] args) throws Exception {
		String[] iptable=new String[3];//Ip table array to hold the IP address of the server and master
        boolean second=true;  
        int key=0;
        if (args.length != 2) 
		{
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0]; //command line argument IP address
        String Master=hostName;
        int portNumber = Integer.parseInt(args[1]);
        try (
            Socket cSocket = new Socket(hostName, portNumber);  //Creating socket to connect to the server
            PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true); //to  write the data to the server using socket
			//reading the input using buffer Reader
			BufferedReader in = new BufferedReader(
            new InputStreamReader(cSocket.getInputStream()));
        ) 
		{
            BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in)); 
            String fromServer= "";   //Server data 
			String fromUser = "";    // User input 
                fromUser = "getMETA";//initially passing the getMETA string to get the all server IP address
                    out.println(fromUser);
                   	int i=0;
                   	if (fromUser.equals("exit"))   //if user input is exit then close the connection
					{
						System.out.println("Server: " + "disconnected");
						System.exit(0);}
                    	while((fromServer=in.readLine())!="\n" && i<3){
                   		String[] x=fromServer.split(" ");
                   		iptable[i]=x[1];                   //storing the ip address of the server in the iptable array 
                   		i++;
					}
						System.out.println(in.readLine());
						for(i=0;i<3;i++)
						{
							System.out.println(iptable[i]);
						}
                cSocket.close();    //closing the socket connection  
        } 
		//if the given hostname(IP address is wrong then catch the error
		catch (UnknownHostException e)
		{
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e)
		{
            System.err.println("Couldn't get I/O for the connection to " +hostName);
            System.exit(1);
        }
        BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));
        String fromServer="";
        String fromUser="";
		//keeping all the server active using while loop
        while(true){
       
        if(second){
        fromUser = stdIn.readLine();
        if (fromUser.equals("exit"))
        {
			System.out.println("Server: " + "disconnected");
            System.exit(0);
        }
		key = ((int)fromUser.split(" ")[1].charAt(0)-(int)'a')/9; //dividing the key value to   parts (as a to i element as one key and j to next 9 as 2 key so on)
        hostName=iptable[key];}
           
		second=true;
		System.out.println("Re-esatblish connection"+hostName);
	 
			try (
				Socket cSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
				new InputStreamReader(cSocket.getInputStream()));
			) 
			{
				 if (fromUser != null)
				{
							System.out.println("Client: " + fromUser);
							out.println(fromUser);
				}
				while ((fromServer = in.readLine()) != null)
				{
					System.out.println("Server111: " + fromServer);
					break;
				}
					cSocket.close();
			} 
			catch (UnknownHostException e) 
			{
				System.err.println("Don't know about host " + hostName);
				System.exit(1);
			}
			catch (IOException e) 
			{
				System.err.println("Couldn't get I/O for the connection to " +
					hostName);
					//if the main server fails then connect to replica server by passing key to the server
					try (
							Socket cSocket = new Socket(Master, portNumber);
							PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
							BufferedReader in = new BufferedReader(
							new InputStreamReader(cSocket.getInputStream()));
						)  
						{	
							second=false;//so the socket will not wait for the user input because its user input is already taken in the while loop
							String R = Integer.toString(key);//converting key to string and passing it to the server
							out.println(R);   //passing the key value as a string to the server   
							hostName= in.readLine();
							System.out.println("Server11R: " + hostName);
							iptable[key]=hostName;
							cSocket.close();
						}
						catch (UnknownHostException f) {
							System.err.println("Don't know about host " + hostName);
							System.exit(1);
						} 
						catch (IOException f) {
							System.err.println("Couldn't get I/O for the connection to " +hostName);
						}
			}
		}
    }
}
