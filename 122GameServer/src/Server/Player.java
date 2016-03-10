/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 *
 * @author malar
 */
public class Player extends Thread
{
    private final Socket connection;
    private BufferedReader input;
    private DataOutputStream output;
    
    private final Lobby lobby;
    private Profile profile;
    
    private boolean loggedIn;
    protected boolean threadSuspended = true;
    
    private char mark;

    public Player(Socket connection, Lobby lobby)
    {
        this.connection = connection;
        this.lobby = lobby;
        
        //at thread initialization the user is not logged in
        loggedIn = false;
        
        //initializing input and output streams
        try 
        {
            input = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            output = new DataOutputStream(connection.getOutputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);

        	JOptionPane.showMessageDialog(new JOptionPane(),
				    "Network Connection Error",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
        	
        	System.exit(-1);
        }  
    }
    
    
    /*
     * This is the method that will be called when the thread is started
     */
    @Override
    public void run()
    {
    	//tells the server it is ready for login information
    	try 
    	{
			sendMessage(initialHandshake());
			
			//loops until login in reached for this player
			while(!loggedIn)
			{
				JSONParser parser = new JSONParser();

				//gets the message from the client
				String loginInfo = receiveMessage();

				Object obj = parser.parse(loginInfo);
				JSONObject jsonObject = (JSONObject) obj;
				
				//read input and check to see if its a login or new acct creation
				String type = (String) jsonObject.get("type");

				if(type.equals("login"))
					loginPlayer(jsonObject);
				else if(type.equals("setup"))
					addNewPlayer(jsonObject);
				
				if(!loggedIn)
					sendMessage(badLogin());

			}

			
			//sends player to select game method
			selectGame();
		} 
    	
    	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	catch (Exception e) 
    	{
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, e);

        	JOptionPane.showMessageDialog(new JOptionPane(),
				    "Network Connection Error (within Player.run())",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
        	
        	System.exit(-1);
		} 
    }
    
    private Profile loginPlayer(JSONObject json)
    {
    	//read login name
    	
    	//check profile to see if valid
    	
    	
    	
    	return null;
    }
    
    private Profile addNewPlayer(JSONObject json)
    {
    	//check to see if name is valid
    	
    	//create profile
    	
    	//return profile
    	return null;
    }
    
    private void selectGame()
    {
    	//needs to push this player thread into the lobby
    }
    
	/********************************************************************
	 * 	public void sendMessage()
	 * 
	 * 	This message sends all of it's string paramater's contents (we
	 * 		assume that this is a JSON message) to the client GUI
	 * 
	 ********************************************************************/
    public void sendMessage(String message)
    {
    	try {
			output.writeChars(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
        	JOptionPane.showMessageDialog(new JOptionPane(),
				    "Network Connection Error (within Player.sendMessage())",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
		}
    }
    
	/********************************************************************
	 * 	public String receiveMessage()
	 * 
	 * 	This method receives the entire JSON string message from a player's
	 * 		client GUI
	 * 
	 ********************************************************************/
    public String receiveMessage()
    {
    	return "";
    }
    
	/********************************************************************
	 * 	public static String initialHandshake()
	 * 
	 * 	The structure of the message is as follows:
	 * 
	 *  	<Welcome>
	 *  		"Please send the login info"
	 * 
	 * ------------------------------------------------------------------
	 * 
	 * The intention of this method is to create a JSON message to be used
	 * 		in the intial handshake for when the client first connects to
	 * 		the server.
	 * 
	 ********************************************************************/
	private String initialHandshake()
	{
		JSONObject message = new JSONObject();
		message.put("Welcome", "Please send the login info");
		
		return message.toJSONString();
	}
	
	/********************************************************************
	 * 	public static String badLogin()
	 * 
	 * 	The structure of the message is as follows:
	 * 
	 *  <gameServer>
	 *  	<Welcome>
	 *  		"Please send the login info"
	 * 
	 * ------------------------------------------------------------------
	 * 
	 * The intention of this method is to create a JSON message to be used
	 * 		to inform that the username entered is invalid
	 * 
	 ********************************************************************/
	private String badLogin()
	{
		JSONObject message = new JSONObject();
		message.put("error", "username not valid");
		
		return message.toJSONString();
	}
	
}
