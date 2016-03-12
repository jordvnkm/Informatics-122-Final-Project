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
	
	///////////////////////////////////////////
	//// client constructor
	public Client(String serverip, int portnum, MainStage inputgui)
	{
		serverIP = serverip;
		port = portnum;
		gameData = new GameData();
		gui = inputgui;
		setupMouseListeners();
		/*try{
			socket = new Socket(serverIP, portnum);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	*/
		//setupBoard();
		//setupMouseListeners();
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
        		gui.getBoard().getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
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

		ArrayList<Integer> move = new ArrayList<Integer>();
		move.add(xOrigin);
		move.add(yOrigin);
		move.add(xDest);
		move.add(yDest);

		System.out.println(move);
		sendMove(move);
		
		myTurn = false;
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
	public void sendSelectPiece(int x, int y)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "selectPiece");
		obj.put("xVal", x);
		obj.put("yVal", y);
		
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
	
	
	
	////////////////////////////////////////////////
	/// sends the location that the piece is supposed to move 
	public void sendMove(ArrayList<Integer> move)
	{
		
		JSONObject obj = new JSONObject();
		obj.put("type", "movePiece");
		obj.put("xOrigin", move.get(0)); // xOrigin
		obj.put("yOrigin", move.get(1)); // yOrigin
		obj.put("xDest", move.get(2));   // xDest
		obj.put("yDest", move.get(3));   // yDest
		
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
	}
	
	///////////////////////////////////////////////////////////
	//// sends board state
	public void sendBoard()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "boardState");
		
		int position = 0;
		for (int i = 0; i < 3; i ++)
		{
			for (int j = 0 ; j < 3; j ++)
			{
				JSONArray myarray = new JSONArray();
				myarray.add(i);
				myarray.add(j);
				if (i == 2)
				{
					myarray.add(0);
				}
				else
				{
					myarray.add(1);
				}
				obj.put(position, myarray);
				position ++;
			}
			
		}
		
		
//		System.out.println(obj.toJSONString());
//		
//		parseBoard(obj.toJSONString());
		
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
	public boolean parseBoard()
	{
		boolean gotInput = false;
	    String input;
	    String jsonString = "";
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((input = br.readLine()) != null) {
				jsonString += input;
			}
			gotInput = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return gotInput;
		}
	    	    
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(jsonString);
			JSONObject jsonObject = (JSONObject) object;
			
			for (int i = 0 ; i < 9 ; i ++)
			{
				JSONArray position = (JSONArray)jsonObject.get(Integer.toString(i));
				Tile t = gui.getBoard().getTile(toIntExact((long)position.get(0)), toIntExact((long)position.get(1)));
				if (toIntExact((long) position.get(2)) == 1)
				{
					t.setText("X");
				}
				else
				{
					t.setText("");
				}
				
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gotInput;

	}




	@Override
	public void run() {
		try{
			socket = new Socket(InetAddress.getByName(serverIP), port);
			System.out.println("Successful connection.");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendBoard();
			while (true)
			{
				if(parseBoard())
				{
					break;
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
