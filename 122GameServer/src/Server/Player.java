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

    /*
     * This is the method that will be called when the thread is started
     */
    @Override
    public void run()
    {
        // This is necessary to continuously receive messages from the
        // client, it has to be in a loop
        
        sendMessage(initialHandshake());
        
        while (!loggedIn)
        {
			//loops until login in reached for this player
			while(!loggedIn)
			{
				JSONParser parser = new JSONParser();

				//gets the message from the client
				String loginInfo = receiveMessage();

				// TODO: REMOVE THE TRY AND CATCH LATER
				Object obj = null;
				try {
					obj = parser.parse(loginInfo);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jsonObject = (JSONObject) obj;
				
				//read input and check to see if its a login or new acct creation
				String type = (String) jsonObject.get("type");

				if(type.equals("login"))
					loggedIn = loginPlayer(jsonObject);
				else if(type.equals("setup"))
					addNewPlayer(jsonObject);
				
				if(!loggedIn)
					sendMessage(badLogin());

			}

			
			//sends player to select game method
			//selectGame();
		} 
    	/*
    	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
        
        
//        while (true)
//        {
//            String stringToParse = receiveMessage();
//
//            // TODO: in here we parse the message using a JSON parser, and then
//            // Call the proper function based on what we get parsed out to.
//            
//            String[] response = parseMessage(stringToParse);
//            switch (response[0])
//            {
//                // A move for games such as tic tac toe
//                case "move1":
//                    game.makeMove(Integer.valueOf(response[1]), Integer.valueOf(response[2]), profile.getName());
//                    checkGame();
//                    break;
//                // A move for games such as checkers
//                case "move2":
//                    game.makeMove(Integer.valueOf(response[1]), Integer.valueOf(response[2]),Integer.valueOf(response[3]), Integer.valueOf(response[4]), profile.getName());
//                    checkGame();
//                    break;
//                // A move for games such as chutes and ladders
//                case "move3":
//                    game.makeMove(profile.getName());
//                    checkGame();
//                    break;
//                case "description":
//                    profile.SetDescription(response[1]);
//                    break;
//                case "selectGame":
//                    // set up a game for the player here
//                    break;
//                case "newGame":
//                    // set up a new game for players to join here
//                    break;
//            }
//        }
    }

    private void checkGame()
    {
        if(game.checkForGameOver())
        {
            //lobby.removeGame(game);
        }
        
    }
    
    
    public void leaveGame()
    {
        game = null;
    }
    
    
    private boolean loginPlayer(JSONObject json)
    {
    	/* Everything is temporary */
        //read login name
    	String name = (String)json.get("name");
    	
        //check profile to see if valid
    	this.profile = new Profile();
    	if(!this.profile.profileExists(name + ".profile")){
    		return false;
    	}
    	
    	this.profile.createNewProfile(name);
        return true;
    }

    private boolean addNewPlayer(JSONObject json)
    {
    	/* Everything is temporary */
        //read login name
    	String name = (String)json.get("name");
    	
        //check profile to see if valid
    	this.profile = new Profile();
    	if(this.profile.profileExists(name + ".profile")){
    		return false;
    	}
  
    	this.profile.createNewProfile(name);
        return true;
    }

    private void goToLobby()
    {
        //needs to push this player thread into the lobby. Just cuz the lobby
        // has a list of players, doesn't mean the thread is running in lobby
        // it's still running in Player and can only run in player.
        game = lobby.selectGame(this);
    }

    /**
     * ******************************************************************
     * public void sendMessage()
     *
     * This message sends all of it's string parameter's contents (we assume
     * that this is a JSON message) to the client GUI
     *
     *******************************************************************
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
                    "Network Connection Error (within Player.sendMessage())",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
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

}
