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
	public boolean connectToServer() throws Exception
	{

			socket = new Socket(serverIP, serverPort);
			
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
		
		

		return true;
	}
	
	/*
	 * Sends a message to the server
	 */
	public boolean sendMessage(String s)
	{
		System.out.println(s);
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
	public String receiveMessage() throws Exception
	{
			return(input.readUTF());
		
	}
	
	/***************************************************************************
	 * 	public void loginHandshake()
	 * 
	 * 	This method gets the login information from the GUI as a JSON and sends it
	 * 		to the server. The server will return a JSON message that contains a
	 * 		status of if the login was good or not along with some other. If the
	 * 		connection fails, null will be returned 
	 * @throws Exception 
	 ***************************************************************************/
	public String loginHandshake(String s) throws Exception
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
    		try {
				serverMessage = receiveMessage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//update GUI???
    		//need to know if its a a game list or something els??
    	}
    }
    
    public void closeConnection(){
    	try {
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public static void main(String[] Args)
    {
    	Communication c = new Communication("localhost", 8000);
    	try {
			c.connectToServer();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
    	c.sendMessage(JSONClientTranslator.loginType("Login"));
    	
    	while(true){
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
    		break;
    	}
    	
    	c.sendMessage(JSONClientTranslator.username("Alex"));
    	while(true)
    	{
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		break;
    	}
    	
    	String message = null;
		try {
			message = c.receiveMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(message);
    	
    	while(true){
    		
    	}
    }
	
}
