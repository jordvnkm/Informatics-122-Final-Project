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
			output.writeChars(initialHandshake());
			
			//loops until login in reached for this player
			while(!loggedIn)
			{
				String loginName = input.readLine();

				//read input and check to see if its a login or new acct creation
				//calls loginPlayer() if logging in
				//calls new acct creation if acct creation
			}

			
			//sends player to select game method
		} 
    	
    	catch (IOException e) 
    	{
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, e);

        	JOptionPane.showMessageDialog(new JOptionPane(),
				    "Network Connection Error (within Player.run())",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
        	
        	System.exit(-1);
		}
    }
    
    private Profile loginPlayer(String username)
    {
    	//read login name
    	
    	//check profile to see if valid
    	
    	//return result
    	return null;
    }
    
    private Profile addNewPlayer()
    {
    	//check to see if name is valid
    	
    	//create profile
    	
    	//return profile
    	return null;
    }
    
    private void selectGame()
    {
    	
    }
    
	/********************************************************************
	 * 	public static String initialHandshake()
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
	 * 		in the intial handshake for when the client first connects to
	 * 		the server.
	 * 
	 ********************************************************************/
	private String initialHandshake()
	{
		JSONObject gameServer = new JSONObject();
		gameServer.put("Welcome", "Please send the login info");
		
		return gameServer.toJSONString();
	}
}
