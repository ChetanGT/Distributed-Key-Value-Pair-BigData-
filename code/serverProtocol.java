import java.util.*;
import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;

public class serverProtocol 
{
    private static final int SENTKNOCKKNOCK = 1;
    StringBuilder sb = new StringBuilder("");//creating the string builder
    private int state = SENTKNOCKKNOCK;
    private int cur=5;
    JSONObject obj = new JSONObject();
    Map<String,String> hm = new HashMap<>(); // creating hashmap for storage and retreival of keys.
   
    public String processInput(String theInput)throws Exception {
        String theOutput = "";
        if (state == SENTKNOCKKNOCK) 
		{
            String[] ch = theInput.split(" "); //split input command based on space
            if(ch[0].equalsIgnoreCase("getMETA"))
            {
            	FileInputStream fis = new FileInputStream("META.txt"); // open the meta file and write the corresponding ip addresses of servers
          	BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    	String line = reader.readLine();
            	while(line != null){
               		 String[] input = line.split(" ");
                	 String temp = input[0] + " " + input[1];
                	 sb.append(temp+"\n");
       
                	 line = reader.readLine();
            	} 
            	theOutput = sb.toString();
            }
            
            if(ch[0].equalsIgnoreCase("Replica")) //If replica server ip address needs to be written in the meta file
            {
            	FileInputStream fis = new FileInputStream("META.txt");
            	StringBuilder sb = new StringBuilder("");
          	BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
          	String line = reader.readLine();
            	while(line != null){
               		 String[] input = line.split(" ");
               		 String temp;
               		 if(input.length==3) // except master server we have a server and its replica as : S.No, IP1, Replica IP1 =>length of 3
					 {
                	 temp = input[1]+" "+input[2];}
                	 else{temp = input[1]+" Master";}
                	 sb.append(temp+" ");
                	 line = reader.readLine();
            	} 
            	theOutput = sb.toString();
            }
            
            if (ch[0].equalsIgnoreCase("put")) { //if putting keys into servers

                String[] data = ch[1].split(",");
                obj.put(data[0],data[1]);
                theOutput = "OK";
                }
                else if(ch[0].equalsIgnoreCase("get"))
                {
					StringWriter out = new StringWriter();
					theOutput=(obj.get(ch[1])).toString();//get the value based on the object using key 
                }
            else if(ch[0].equalsIgnoreCase("exit")) {
                theOutput = "exit";
            }
        }
		else // if exiting
		{
            theOutput = "Bye.";
            state = WAITING;
        }   
        System.out.println(theOutput);
        return theOutput;
    }
}

