package Client;

import static java.lang.Math.toIntExact;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	private ArrayList<Integer> move = new ArrayList<Integer>();
	private String clientName;
	private boolean isRunning = true;
	private boolean wonGame = false;
	
	///////////////////////////////////////////
	//// client constructor
	public Client(String serverip, int portnum, MainStage inputgui)
	{
		serverIP = serverip;
		port = portnum;
		gameData = new GameData();
		gui = inputgui;
		/*try{
			socket = new Socket(serverIP, portnum);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	*/
		setupBoard();
		setupMouseListeners();
		(new Thread(this)).start();
	}
	
	public void setupBoard(){
		
	}
	
	////////////////////////////////////////////////////
	/// adds the gui to the client
	public void setGui(MainStage inputgui)
	{
		gui = inputgui;
	}
	
	
	
	//////////////////////////////////////////////////////
	///sets up mouse listener on gui
	public void setupMouseListeners()
	{
		for(int i=0;i<3;i++)
        	for(int j=0;j<3;j++){
        		gui.gameboard.getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	gui.logger("Mouse clicked: "+xloc+","+yloc,true);
                	if (myTurn)
                	{
                		t.setText("X");
                		setMove(-1, -1, xloc, yloc);
                	}
                	else
                	{
                		gui.logger("Not Your Turn", true);
                	}
                });
        	}
	}
	
	
	
	///////////////////////////////////////////////////////////////////
	////// function that is used by mouse listeners.
	public void setMove(int xOrigin, int yOrigin, int xDest, int yDest)
	{
		move.add(xOrigin);
		move.add(yOrigin);
		move.add(xDest);
		move.add(yDest);

		System.out.println(move);
		madeMove = true;
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////
///////////functions that send information through the socket///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	/////sends a request that states which game the player wants to join
	public void sendGameRequest(String game, String opponent)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "gameRequest");
		obj.put("game", game);
		obj.put("opponent", opponent);
		
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/////////////////////////////////////////////////////////////////
	//// requests the board size
	public void requestBoardSize()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "requestBoardSize");

		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	//////////////////////////////////////////////////////////
	/// sends a string representing that the button on the GUI has been pressed
	public void sendButtonPressed()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "ButtonPressed");

		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	////////////////////////////////////////////////////////////////
	///// sends a string through the socket requesting players in the server
	public void requestPlayerList()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "requestPlayerList");

		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	////////////////////////////////////////////
	///// sends a string through the socket requesting gamelist
	public void requestGameList()
	{	
		JSONObject obj = new JSONObject();
		obj.put("type", "requestGameList");
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	////////////////////////////////////////////////////
	///// sends location of piece that has been selected
//	public void sendSelectPiece(int x, int y)
//	{
//		JSONObject obj = new JSONObject();
//		obj.put("type", "selectPiece");
//		obj.put("xVal", x);
//		obj.put("yVal", y);
//		
//		try 
//		{
//			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
//		    out.write(obj.toJSONString());
//		    out.flush();
//		    out.close();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	
	
	////////////////////////////////////////////////
	/// sends the location that the piece is supposed to move 
	public void sendMove()
	{
		
		JSONObject obj = new JSONObject();
		obj.put("type", "movePiece");
		obj.put("xOrigin", move.get(0)); // xOrigin
		obj.put("yOrigin", move.get(1)); // yOrigin
		obj.put("xDest", move.get(2));   // xDest
		obj.put("yDest", move.get(3));   // yDest
		
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.flush();
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		
		madeMove = false;
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////
///////////functions that parse information from the socket///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	/// parses board size
	public void parseBoardSize()
	{
	    String input;
	    String jsonString = "";
	    long[] boardSize = new long[2];
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((input = br.readLine()) != null) {
				jsonString += input;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(jsonString);
			JSONObject jsonObject = (JSONObject) object;
			
			long rows = (long) jsonObject.get("rows");
			long columns = (long) jsonObject.get("columns");
			boardSize[0] = rows;
			boardSize[1] = columns;
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	////////////////////////////////////////////////////////////
	//// parses board state
	public void parseGameState()
	{
		String jsonString = "";
		String input;
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((input = br.readLine()) != null) {
				jsonString += input;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONparse state = new JSONparse(jsonString);
		
		
		/////////////////////////////////////////////// 
		// set up board from server information
		gui.setBoard(state.getRowNum(), state.getColumnNum());
		for (int i = 0; i < state.getRowNum(); i ++)
		{
			for (int j = 0 ; j < state.getColumnNum(); j ++)
			{
				Tile t = gui.gameboard.getTile(i, j);
				int[] rgb = state.getTileColor(i, j);
            	t.setBackgroundColor(rgb[0], rgb[1], rgb[2]);

            	for (int x = 0 ; x < 2 ; x ++)
            	{
            		String shape = state.getPieceShape(i, j, x);
            		if (!shape.equals(""))
            		{
            			int[] color = state.getPieceColor(i, j, x);
            			String layer = state.getPieceLayer(i, j, x);
            			String type = state.getPieceType(i, j, x);
            			Piece piece = new Piece(color, shape , layer , type.charAt(0));
            			t.addPiece(piece);
            		}
            		
            	}
			}
		}
		
		/////////////////////////////////////////////////////
		////// set turn
		if (state.getCurrentTurn().equals(clientName))
		{
			myTurn = true;
		}
		
		//// set running state
		isRunning = state.getIsRunning();
		
		
		//// if winner is not empty, set winner
		if (!state.getWinner().equals("")){
			if (state.getWinner().equals(clientName)){
				wonGame = true;
			}
			else{
				wonGame = false;
			}
		}
		

	}




	@Override
	public void run() {
		try{
			socket = new Socket(InetAddress.getByName(serverIP), port);
			System.out.println("Successful connection.");
			
			while (isRunning)
			{
				while (myTurn)
				{
					if (madeMove)
					{
						sendMove(); // sends move;
						madeValidMove(); // listens for server response .sets myTurn;
					}
				}
				
				while (!myTurn)
				{
					parseGameState();
				}
			}
			
		}
		catch (IOException e)
		{
			System.out.println("failed to connect");
			e.printStackTrace();
		}
		
		/*while(true){
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++){
					gui.getBoard().getTile(i, j).setBackgroundColor(255, 0, 127);
					try{Thread.sleep(100);}catch(InterruptedException e){}
			}
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++){
					gui.getBoard().getTile(j, i).setBackgroundColor(127, 0, 255);
					try{Thread.sleep(100);}catch(InterruptedException e){}
			}
		}*/
	}

}
