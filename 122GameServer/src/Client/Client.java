package Client;
//import GamePlugins.*; // need to delete

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Client implements Runnable{
	public String serverIP;
	public int port;
	public Socket socket;
	private MainStage gui;
	private GameData gameData;
	private boolean myTurn = false;
	private boolean madeMove = false;
	private ArrayList<Integer> move;
	private String clientName;
	private String opponentName;
	private String gameName;
	private boolean isRunning = true;
	private String winner;
	private boolean choseGame = false;
	private Communication com;
	private boolean buttonPressed = false;
	private boolean buttonValid = false;
	private boolean connectionEstablished = false;
//	private boolean quitGame = false;
//	private GameState tic;
	
	
	///// testing purpose only  TODO delete
	//private GameState t;
	
	
	///////////////////////////////////////////
	//// client constructor
	public Client(String serverip, int portnum, MainStage inputgui)
	{
		serverIP = serverip;
		port = portnum;
		gameData = new GameData();
		gui = inputgui;
		setupBoard(); // will need to do this when parsing game state
		setupMenuListeners();
		selectServer();
	}
	
	
	/**
	 * These setup listeners to spawn Dialogs when
	 * These Menu Items are pressed.
	 * 
	 * Not all MenuItems need this, some do not need to
	 * be controlled by the Client.
	 */
	public void setupMenuListeners(){
		
		//When Select Server.. is pressed
		gui.getSelectServMenuItem().setOnAction((ActionEvent e) -> {
	    	selectServer();
	    });
		//When Player Profile Menu Item.. is pressed
		gui.getcreateloginMenuItem().setOnAction((ActionEvent e) -> {
	    	createlogin();
	    });
		//When Request Game.. is pressed
		gui.getrequestgameMenuItem().setOnAction((ActionEvent e) -> {
	    	requestGame();
	    });
		//When Quit Game.. is pressed
		gui.getQuitGameMenuItem().setOnAction((ActionEvent e) -> {
	    	quitGame();
	    });
		
		//When Login.. is pressed
		gui.getLoginMenuItem().setOnAction((ActionEvent e) -> {
	    	login();
	    });
		
		//When Disconnect is pressed
		gui.getDisconnectMenuItem().setOnAction((ActionEvent e) ->{
			if(com!=null){
				com.closeConnection();
				gui.logger("Disconnected from server.", false);
			}
		});
	}
	/**
	 * Opens the server select Dialog, and attempts to establish a conection with the server
	 */
	public void selectServer(){

		String[] info = Dialogs.getServerInfo();
		//if they canceled...
		if(info==null)
			return;
		
		serverIP = info[0];
		port =Integer.parseInt(info[1]);
		
		//Start the thread for managing connections
		(new Thread(this)).start();
	}
	
	/**
	 * Opens request game menu and sends the client response to the server
	 */
	public void requestGame(){
		if(com!=null){
			String players = "";
			for (String s:gameData.getPlayers())
				players+=s+"\n";
			String game = Dialogs.chooseGame(gameData.getGames().toArray(new String[]{}), players);
			if(game!=null){
				com.sendMessage(JSONClientTranslator.selectedGame(game));
				gui.logger("Looking for game..", false);
			}
			
		}

		
	}
	
	/**
	 * Sends the server that the client quits the game.
	 */
	public void quitGame(){
		if (connectionEstablished)
		{
			com.sendMessage(JSONClientTranslator.quitGame());
		}

	}
	
	/**
	 * opens dialog for the player to login.
	 */
	public void login(){
		String username = Dialogs.getLoginInfo();
		//check if they canceled
		if(username==null)
			return;
		if(connectionEstablished){
			com.sendMessage(JSONClientTranslator.loginType("Login"));
		  	com.sendMessage(JSONClientTranslator.username(username));
		  	clientName = username;
		}
	}
	
	/**
	 *Shows login username and info to the player
	 */
	public void createlogin(){
		String[] result = Dialogs.createLogin();
		
		//check if they canceled
		if(result==null)
			return;
		String username = result[0];
		String info = result[1];
		if(connectionEstablished){
			com.sendMessage(JSONClientTranslator.loginType("CreateUser"));
		  	com.sendMessage(JSONClientTranslator.username(username));
		  	com.sendMessage(JSONClientTranslator.description(info));
		  	clientName = username;
		}
	}
	public void setupBoard(){
		//gui.setBoard(0, 0);
		setupMouseListeners();
		setupActionButton();
	}
	
	public void setupBoard(int rows, int columns)
	{
		gui.setBoard(rows, columns);
		setupMouseListeners();
		setupActionButton();
	}
	
	////////////////////////////////////////////////////
	/// adds the gui to the client
	public void setGui(MainStage inputgui)
	{
		gui = inputgui;
	}
	
	
	//////////////////////////////////////////////////////////
	//// writes string to logger
	public void writeToLogger(String message)
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
					gui.logger(message, true);	
			}
		});
	}
	
	
	//////////////////////////////////////////////////////////
	/////// sets up action button
	public void setupActionButton()
	{
		gui.getButton().setOnAction((ActionEvent e) -> { // need to figure out action for button
			gui.logger("Button Pressed !",true);
			com.sendMessage(JSONClientTranslator.buttonPressed("roll"));
			if (myTurn)
			{
				if (buttonValid)
				{
					setButtonPressed();
				}
				setButtonPressed();
			}
			else
			{
				gui.logger("Not your turn!", true);
			}
		});
	}
	
	
	public void setButtonPressed(){
		buttonPressed = true;
		madeMove = true;
	}
	
	
	//////////////////////////////////////////////////////
	///sets up mouse listener on gui
	public void setupMouseListeners()
	{
		for(int i=0;i<gui.getRows();i++)
        	for(int j=0;j<gui.getColumns();j++){
        		gui.getBoard().getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	if (myTurn && isRunning)
                	{
                		gui.logger("Mouse clicked: "+xloc+","+yloc,true);
                		setMove( xloc, yloc);
                	}
                	else if (!isRunning)
                	{
                		gui.logger("Game Over", true);
                	}
                	else if (!myTurn)
                	{
 
                		gui.logger("Not Your Turn", true);
                	}

                });
        	}
	}
	
	
	
	///////////////////////////////////////////////////////////////////
	////// function that is used by mouse listeners.
	public void setMove(int xlocation, int ylocation)
	{
		move = new ArrayList<Integer>();
		move.add(xlocation);
		move.add(ylocation);
		com.sendMessage(JSONClientTranslator.movePiece(xlocation, ylocation));
		System.out.println(move);
		madeMove = true;
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////
///////////functions that send information through the socket///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	/////sends a request that states which game the player wants to join
	public void sendGameRequest(Communication com , String game)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "gameRequest");
		obj.put("game", game);
		
		if (com.sendMessage(obj.toJSONString()))
			writeToLogger("Game request sent.");
		else
			writeToLogger("Game request failed.");
	}
	
	
	
	//////////////////////////////////////////////////////////
	/// sends a string representing that the button on the GUI has been pressed
	public void sendButtonPressed(Communication com)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "ButtonPressed");
		obj.put("ButtonType", "Roll");

		if (com.sendMessage(obj.toJSONString()))
			writeToLogger("Button pressed sent");
		else
			writeToLogger("Button pressed failed.");
	}
	
	//////////////////////////////////////////////////////////////////
	//// sends a string representing that the player wants to quit game
	public void sendQuitGame(Communication com)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "QuitGame");
		
		if (com.sendMessage(obj.toJSONString()))
			writeToLogger("Quit game sent");
		else
			writeToLogger("Quit game failed.");
	}
	
	
	
	
	////////////////////////////////////////////////////////////////
	///// sends a string through the socket requesting players in the server
	public void requestPlayerList(Communication com)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "requestPlayerList");

		if (com.sendMessage(obj.toJSONString()))
			writeToLogger("Player list request sent.");
		else
			writeToLogger("Player list request failed.");
	}
	
	
	
	////////////////////////////////////////////
	///// sends a string through the socket requesting gamelist
	public void requestGameList(Communication com)
	{	
		JSONObject obj = new JSONObject();
		obj.put("type", "requestGameList");
		if (com.sendMessage(obj.toJSONString()))
			writeToLogger("Game list request sent.");
		else
			writeToLogger("Game list request failed.");
	}
	
	
	
	////////////////////////////////////////////////
	/// sends the location that the piece is supposed to move 
	public void sendMove(Communication com)
	{
		
		JSONObject obj = new JSONObject();
		obj.put("type", "movePiece");
		obj.put("X", move.get(0)); // xOrigin
		obj.put("Y", move.get(1)); // yOrigin
		
		if (com.sendMessage(obj.toJSONString())){
			writeToLogger("Move sent");
		}
		else{
			writeToLogger("Move not sent");
		}
		
		madeMove = false;
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////
///////////functions that parse information from the socket///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////	
	
	////////////////////////////////////////////////////////////
	//// parses board state
	public void parseGameState(String jsonString)
	{	
		JSONBoard state = new JSONBoard(jsonString);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run(){
				/////////////////////////////////////////////// 
				// set up board from server information
				setupBoard(state.getRowNum(), state.getColumnNum());
				for (int i = 0; i < state.getRowNum(); i ++)
				{
					for (int j = 0 ; j < state.getColumnNum(); j ++)
					{
						Tile t = gui.getBoard().getTile(i, j);
						int[] rgb = state.getTileColor(i, j);
						t.setBackgroundColor(rgb[0], rgb[1], rgb[2]);

						for (int x = 0 ; x < 2 ; x ++)
						{
							String shape = state.getPieceShape(i, j, x);
							if (!(shape == null))
							{
								int[] color = state.getPieceColor(i, j, x);
								String layer = state.getPieceLayer(i, j, x);
								String type = state.getPieceType(i, j, x);
								Piece piece = new Piece(color, shape , layer , type.charAt(0));
								t.addPiece(piece);
							}
						}
						t.draw();
					}
				}
				
				/////////////////////////////////////////////////////
				////// set turn
				if (state.getCurrentTurn().equals(clientName))
				{
					myTurn = true;
				}
				else
				{
					myTurn  = false;
				}
				
				//// set running state
				isRunning = state.getIsRunning();
				
				
				//// game is not running set winner
				if (!isRunning){
					if (state.getWinner().equals("TIE"))
					{
						gui.logger("Game is a tie!", true);
					}
					else{
						gui.logger("Winner is " + winner + "!", true);
					}
				}
			}
		});
		
//		/////////////////////////////////////////////////////
//		////// set turn
//		if (state.getCurrentTurn().equals(clientName))
//		{
//			myTurn = true;
//		}
//		
//		//// set running state
//		isRunning = state.getIsRunning();
//		
//		
//		//// game is not running set winner
//		if (!isRunning){
//			winner = state.getWinner();
//			
//		}
		

	}
	
	/////////////////////////////////////////////////
	//// parse valid move * may not need if valid field is sent with game state
	public void parseValidMove(Communication com)
	{
		madeMove = false;
		
		String jsonString = "";
		while (true)
		{
			//jsonString = com.receiveMessage();
			if (!jsonString.equals(""))
				break;
		}

	    
	    try {
	    	JSONParser parser = new JSONParser();
	    	Object object = parser.parse(jsonString);
	    	JSONObject jsonObject = (JSONObject) object;

	    	String valid = (String) jsonObject.get("valid");
	    	
	    	if (valid.equals("true"))
	    	{
	    		myTurn = false;
	    	}


	    } catch (ParseException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	}
	
	///////////////////////////////////////////////////////////////////
	////// parses gamelist that is sent from server
	public void parseGameList(Communication com)
	{
		String jsonString = "";
		while (true)
		{
			if (!jsonString.equals(""))
				break;
		}
		
		
	    try {
	    	JSONParser parser = new JSONParser();
	    	Object object = parser.parse(jsonString);
	    	JSONObject jsonObject = (JSONObject) object;
	    	
	    	////// parses game list
	    	JSONArray gameArray = (JSONArray) jsonObject.get("games");
	    	for (int i = 0 ; i < gameArray.size(); i ++)
	    	{
	    		JSONObject obj = (JSONObject)gameArray.get(i);
	    		String name = (String)obj.get("name");
	    		gameData.addGame(name);
	    	}
	    	
	    	
	    	JSONArray playerArray = (JSONArray) jsonObject.get("players");
	    	for (int i = 0 ; i < gameArray.size(); i ++)
	    	{
	    		JSONObject obj = (JSONObject)playerArray.get(i);
	    		String player = (String)obj.get("name");
	    		gameData.addPlayer(player);
	    	}

	    } catch (ParseException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	}

	
	//// parses list of players
	public void parsePlayerList(Communication com)
	{
		String jsonString = "";
		while (true)
		{
			//jsonString = com.receiveMessage();
			if (!jsonString.equals(""))
				break;
		}
		
		
	    try {
	    	JSONParser parser = new JSONParser();
	    	Object object = parser.parse(jsonString);
	    	JSONObject jsonObject = (JSONObject) object;
	    	
	    	///// parses player list
	    	JSONArray playerArray = (JSONArray) jsonObject.get("players");
	    	for (int i = 0 ; i < playerArray.size(); i ++)
	    	{
	    		JSONObject obj = (JSONObject)playerArray.get(i);
	    		String player = (String)obj.get("name");
	    		gameData.addPlayer(player);
	    	}

	    } catch (ParseException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	}


/**
 * Thread is started when the client makes a request to 
 * connect to the server (selectServer).
 * All gui calls must be done by using the Platform.runLater method
 */
	@Override
	public void run() {
//		String[] players = new String[]{"Adrian", "Alex"};
//		
//		tic = new ChutesAndLaddersGame(players);
//		
//		while (isRunning)
//		{
//			
//		}
//
//		System.out.println("Winner: " + tic.getWinner());
		
		
		System.out.println("Thread to start communicating with server started");
		//Attempt communication using serverIP and port
		com = new Communication(serverIP,port);
		
    	try {
			com.connectToServer();
			//if successful
			writeToLogger("Connected to "+serverIP+":"+port+".");
			 connectionEstablished=true;
		} catch (Exception e1) {
			//if error occurs
			Platform.runLater(new Runnable() {
				@Override
				public void run(){
					Dialogs.popupError("Failed to connect to "+serverIP+":"+port+".", "Connection Error", "Connection Error");
					selectServer();
					//kill/end thread
				}});
			e1.printStackTrace();
		}
    	
		
    	while (true){
    		try{
    		String message;
    		message= com.receiveMessage();
    		System.out.println("New Message: "+message);
    		//Respond to messages from server
    		ArrayList<String> parsed = JSONGeneral.checkType(message);
    		if(parsed.size()==0){
    			System.out.println("Malformed Message Sent:" + message);
    		}
    		String type = parsed.get(0);
    		
    		System.out.println(message);
    		
    		
    		if(type.equals("Welcome")){
    			writeToLogger(parsed.get(1));
//    			Platform.runLater(new Runnable() {
//    				@Override
//    				public void run(){
//    					//login();
//    				}});
    		}
    		else if (type.equals("LoginStatus")){
    			writeToLogger("Login " + parsed.get(1));
    			System.out.println(message);
    			
    		}
    		
    		else if (type.equals("GameList")){
    			for (int i = 1; i < parsed.size(); i ++)
    			{
    				gameData.addGame(parsed.get(i));
    			}
    		}

    		else if(type.equals("WaitingToPlay")){
    			//similar to PlayerList, may not implement
    		}
    		else if(type.equals("PlayerList")){
    			gameData.clearPlayerData();
    			for(int i=1;i<parsed.size();i++)
    				gameData.addPlayer(parsed.get(i));
    			
    		}
    		else if(type.equals("ButtonDisabled")){
    			Platform.runLater(new Runnable() {
    				@Override
    				public void run(){
    					gui.getButton().setDisable(parsed.get(1).equals("true"));
    				}});			
    			
    		}
    		else if(type.equals("ButtonText")){
    			Platform.runLater(new Runnable() {
    				@Override
    				public void run(){
    					gui.getButton().setText(parsed.get(1));
    				}});	
    		}
    		else if(type.equals("GameBoard")){
    			Platform.runLater(new Runnable() {
    				@Override
    				public void run(){
    					parseGameState(parsed.get(1));
    				}});

    		}
    		
    		
    		
    		}catch(Exception e){
    			e.printStackTrace();
    			break;
    		}

    	}
    	
    	writeToLogger("You have disconnected from the server.");
		com.closeConnection();
    	connectionEstablished=false;
    	com=null;

	}

}
