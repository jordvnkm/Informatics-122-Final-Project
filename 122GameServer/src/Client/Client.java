package Client;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.scene.input.MouseEvent;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Client implements Runnable{
	public String serverIP;
	public int port;
	public Socket socket;
	private MainStage gui;
	///////////////////////////////////////////
	//// client constructor
	public Client(String serverip, int portnum, MainStage inputgui)
	{
		serverIP = serverip;
		port = portnum;
		gui = inputgui;
		setupBoard();
		setupMouseListeners();
		(new Thread(this)).start();
	}
	
	public void setupBoard(){
		
	}
	
	public void setupMouseListeners()
	{
		for(int i=0;i<3;i++)
        	for(int j=0;j<3;j++){
        		gui.gameboard.getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	gui.logger("Mouse clicked: "+xloc+","+yloc,true);
                	t.setText("X");
                	setMove(-1, -1, xloc, yloc);
                	
                });
        	}
	}
	
	
	
	
	public void setMove(int xOrigin, int yOrigin, int xDest, int yDest)
	{
		ArrayList<Integer> move = new ArrayList<Integer>();
		move.add(xOrigin);
		move.add(yOrigin);
		move.add(xDest);
		move.add(yDest);
		
		System.out.println(move);
	}
	
	
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
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	////////////////////////////////////////////////
	/// sends the location that the piece is supposed to move 
	public void sendMovePiece(int xOrigin, int yOrigin, int xDest, int yDest)
	{
		JSONObject obj = new JSONObject();
		obj.put("type", "movePiece");
		obj.put("xOrigin", xOrigin);
		obj.put("yOrigin", yOrigin);
		obj.put("xDest", xDest);
		obj.put("yDest", yDest);
		
		
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toJSONString());
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}




	@Override
	public void run() {
		try{
			socket = new Socket(InetAddress.getByName(serverIP), port);
			System.out.println("Successful connection.");
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
