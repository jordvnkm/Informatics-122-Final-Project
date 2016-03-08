package Client;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Client {
	public String serverIP;
	public int port;
	public Socket socket;
	
	///////////////////////////////////////////
	//// client constructor
	public Client(String serverip, int portnum)
	{
		serverIP = serverip;
		port = portnum;
		try{
			socket = new Socket(serverIP, portnum);
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
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write("REQUESTING GAMELIST");
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
		obj.put("xVal", x);
		obj.put("yVal", y);
		
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toString());
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	////////////////////////////////////////////////
	/// sends the location that the piece is supposed to move 
	public void sendMovePiece(int x, int y)
	{
		JSONObject obj = new JSONObject();
		obj.put("xVal", x);
		obj.put("yVal", y);
		
		try 
		{
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		    out.write(obj.toString());
		    out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
