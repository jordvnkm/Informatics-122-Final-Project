package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Communication extends Thread
{
	private String serverIP;
	private int serverPort;
	private int clientPort;
	private Socket socket;
	
    private DataInputStream input;
    private DataOutputStream output;
        
    boolean listening;
    
    boolean consoleDebug = true;
    
	Communication(String serverIP, int serverPort)
	{
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		
		listening = false;
	}
	
	/*
	 * Connects client to server
	 */
	public boolean connectToServer()
	{
		try{
			socket = new Socket(serverIP, serverPort);
			
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
            
			//this should be the welcome message
			String serverMessage = input.readUTF();
			
			if(true)
				System.out.println(serverMessage);
			
			//Check server msg
			if(false)
				throw new Exception("***Server Welcome Message is Incorrect***");
			
			}
			
		catch (IOException e)
		{
			if(consoleDebug)
			{
				System.out.println("****Could not connect to server****");
				e.printStackTrace();
				return false;
			}
		}	
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/*
	 * Sends a message to the server
	 */
	public boolean sendMessage(String s)
	{
		try 
		{
			output.writeUTF(s);
			return true;
		} catch (IOException e) 
		{
			if(consoleDebug)
			{
				System.out.println("****Message Failed to Send****");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}
		
	}
	
	/*
	 * Receives a message from the server
	 */
	public String receiveMessage()
	{
		try 
		{
			return(input.readUTF());
		} 
		
		catch (IOException e) {
			if(consoleDebug)
			{
				System.out.println("****Could not receive a message****");
				e.printStackTrace();
			}
			return "";
		}
	}
	
	/***************************************************************************
	 * 	public void loginHandshake()
	 * 
	 * 	This method gets the login information from the GUI as a JSON and sends it
	 * 		to the server. The server will return a JSON message that contains a
	 * 		status of if the login was good or not along with some other. If the
	 * 		connection fails, null will be returned 
	 ***************************************************************************/
	public String loginHandshake(String s)
	{
	
			//gets the input from the GUI and send the appropriate login information to the server
			sendMessage(s);
			
			
			//gets the login information from the server
			String serverMessage = receiveMessage();
			
			return serverMessage;
		
	}
	
	
	
    @Override
    public void run()
    {
    	String serverMessage;
    	listening = true;
    	
    	while(listening)
    	{
    		serverMessage = receiveMessage();
    		//update GUI???
    		//need to know if its a a game list or something els??
    	}
    }
    
    public static void main(String[] Args)
    {
    	Communication c = new Communication("localhost", 8000);
    	c.connectToServer();
    	
    	JSONObject j = new JSONObject();
    	j.put("type", "LoginType");
    	j.put("Command", "CreateUser");
    	c.sendMessage(j.toJSONString());
    	
    	j = new JSONObject();
    	j.put("type", "Username");
    	j.put("User", "Alex");
    	c.sendMessage(j.toJSONString());
    	
    	while(true){
    		try {
				c.sleep(2000);
			} catch (InterruptedException e) {
			}
    		break;
    	}
    	
    	j = new JSONObject();
    	j.put("type", "Description");
    	j.put("Bio", "Likes bikes");
    	c.sendMessage(j.toJSONString());
    	while(true)
    	{
    		// do nothing
    	}
    }
	
}
