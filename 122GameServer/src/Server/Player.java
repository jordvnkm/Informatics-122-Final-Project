/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
    private DataInputStream input;
    private DataOutputStream output;

    private final Lobby lobby;
    private Profile profile;

    private boolean loggedIn;
    private Game game;

    /**
     * Constructor, creates a player object and 'connects' them to the lobby
     * @param connection the socket connection to talk over
     * @param lobby contains lists of players and games
     */
    public Player(Socket connection, Lobby lobby)
    {
        this.connection = connection;
        this.lobby = lobby;

        //at thread initialization the user is not logged in
        loggedIn = false;

        //initializing input and output streams
        try
        {
            input = new DataInputStream(connection.getInputStream());
            output = new DataOutputStream(connection.getOutputStream());
        } catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(new JOptionPane(),
                    "Network Connection Error",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(-1);
        }
    }
    
    /**
     * Getter to see whether the player is logged in currently.
     * @return true if player is logged in, otherwise false.
     */
    public boolean loggedIn()
    {
    	return loggedIn;
    }

  
    /**
     * Called when the thread is started.
     */
    @Override
    public void run()
    {
        // This is necessary to continuously receive messages from the
        // client, it has to be in a loop
        
        sendMessage(initialHandshake());
    
    	if(!loggedIn){
    		sendMessage(initialHandshake());
    	}
        
		//loops until login in reached for this player
		while(!loggedIn)
		{	
			//gets the message from the client
			String loginInfo = receiveMessage();
			String[] tokens = JSONServerGeneral.checkType(loginInfo);
			System.out.println(loginInfo);
			
			if(tokens[0].equals("LoginType") && tokens[1].equals("CreateUser")){
				loggedIn = addNewPlayer();
			} else if(tokens[0].equals("LoginType") && tokens[1].equals("Login")){
				loggedIn = loginPlayer();
			}
			
			//read input and check to see if its a login or new acct creation
			if(!loggedIn){
				sendMessage(badLogin());
			} else {
				sendMessage(JSONServerTranslator.loginStatus("Successful"));
			}

		}

		// TODO : send game plist and players to client
		System.out.println("Player " + profile.getName() + "  Created/Logged In");
        
		sendLists();
		String player = profile.getName();
        while (true)
        {
            String stringToParse = receiveMessage();

            // TODO: in here we parse the message using a JSON parser, and then
            // Call the proper function based on what we get parsed out to.
            
            String[] response = JSONServerGeneral.checkType(stringToParse);
            switch (response[0])
            {
                // Player is attempting a move
                case "MovePiece":
                    game.makeMove(Integer.valueOf(response[1]), Integer.valueOf(response[2]), player);
                    break;
                case "ButtonPressed":
                	game.buttonPressed(response[1], player);
                    break;
                case "QuitGame":
                    //TODO: Handle quit game events here
                    break;
                case "Description":
                    profile.SetDescription(response[1]);
                    break;
                case "SelectedGame":
            		String gameName = response[1];
            		game = lobby.selectGame(this, gameName);
                	break;
            }
        }
    }
    
    /**
     * Player is leaving the game they're currently in
     */
    public void leaveGame()
    {
    	//TODO: this probably needs more than just this
    	lobby.removeGameFromOpenList(game);
    	lobby.removeGameFromFullList(game);
        game = null;
    }

    public void sendLists()
    {
    	sendMessage(lobby.getGameList());
    }
	/**
	 * Send a message to the player socket
	 * @param message the message to be sent (as a JSON string)
	 */
    public void sendMessage(String message)
    {
        try
        {
            output.writeUTF(message);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            JOptionPane.showMessageDialog(new JOptionPane(),
                    "Network Connection Error (within Player.sendMessage())",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ******************************************************************
     * public String receiveMessage()
     *
     * This method receives the entire JSON string message from a player's
     * client GUI
     *
     *******************************************************************
     */
    public String receiveMessage()
    {
        try
        {
            return input.readUTF();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

            JOptionPane.showMessageDialog(new JOptionPane(),
                    "Network Connection Error (within Player.receiveMessage())",
                    "Disconnect Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return "";
    }

    /**
     * ******************************************************************
     * public static String initialHandshake()
     *
     * The structure of the message is as follows:
     *
     * <Welcome>
     * "Please send the login info"
     *
     * ------------------------------------------------------------------
     *
     * The intention of this method is to create a JSON message to be used in
     * the intial handshake for when the client first connects to the server.
     *
     *******************************************************************
     */
    private String initialHandshake()
    {
        JSONObject message = new JSONObject();
        message.put("Welcome", "Please send the login info");
        return message.toJSONString();
    }

    /**
     * ******************************************************************
     * public static String badLogin()
     *
     * The structure of the message is as follows:
     *
     * <gameServer>
     * <Welcome>
     * "Please send the login info"
     *
     * ------------------------------------------------------------------
     *
     * The intention of this method is to create a JSON message to be used to
     * inform that the username entered is invalid
     *
     *******************************************************************
     */
    private String badLogin()
    {
        JSONObject message = new JSONObject();
        message.put("error", "username not valid");

        return message.toJSONString();
    }
    public void wonGame(String game)
    {
        profile.addWin(game);
    }
    
    public void lostGame(String game)
    {
        profile.addLoss(game);
    }
    
    public String getPlayerName()
    {
    	if (loggedIn)
    		return profile.getName();
    	else
    		return "Logging in...";
    }
        
    public void startGame()
    {
        lobby.setGameAsFull(game);
    }
    
    private boolean addNewPlayer()
    {
    	String message = receiveMessage();
    	String[] tokens = JSONServerGeneral.checkType(message);
    	
    	this.profile = new Profile();
    	if(tokens[0].equals("Username") && this.profile.profileExists(tokens[1]))
    		return false;
    	
     	this.profile.createNewProfile(tokens[1]);
    	
    	message = receiveMessage();
    	tokens = JSONServerGeneral.checkType(message);
    	if(tokens[0].equals("Description"))
    		this.profile.SetDescription(tokens[1]);
    	
    	return true;
    }
    
    private boolean loginPlayer()
    {
    	String message = receiveMessage();
    	String[] tokens = JSONServerGeneral.checkType(message);
    	System.out.println("Logging Player In: " + message);
    	this.profile = new Profile();
    	if(tokens[0].equals("Username") && !this.profile.profileExists(tokens[1]))
    		return false;
    	
    	this.profile.createNewProfile(tokens[1]);
    	return true;
    }
}
