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
    
    protected boolean threadSuspended = true;
    
    private char mark;

    public Player(Socket connection, Lobby lobby)
    {
        this.connection = connection;
        this.lobby = lobby;
        
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
			output.writeChars("ready");
			input.readLine();
			
			//loops until login in reached for this player
			
				//read input and check to see if its a login or new acct creation
				//calls loginPlayer() if logging in
				//calls new acct creation if acct creation
			
			//sends player to select game method
		} 
    	
    	catch (IOException e) 
    	{
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);

        	JOptionPane.showMessageDialog(new JOptionPane(),
				    "Network Connection Error",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
        	
        	System.exit(-1);
		}
    }
    
    private Profile loginPlayer()
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
}
