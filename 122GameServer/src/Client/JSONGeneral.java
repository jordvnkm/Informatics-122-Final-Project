package Client;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.ArrayList;

public class JSONGeneral
{
	//Returns ArrayList of Strings
	//For Example:
	//		If we receive {"type": "MovePiece", "X": 1, "Y": 2}
	//This returns the String ArrayList:
	//		["MovePiece", "1", "2"]
	//Example 2:
	// 		If we receive {"type": "GameList", "List": ["Othello", "Chutes", "TicTacToe"]}
	//This returns the String ArrayList:
	//		["GameList", "Othello", "Chutes", "TicTacToe"]
	
	public static ArrayList<String> checkType(String jsonString)
	{
		JSONParser parser = null;
		Object obj = null;
		try{
			parser = new JSONParser();
			obj = parser.parse(jsonString);
		}catch(ParseException pe){
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
		JSONObject jsonObj = (JSONObject)obj;
		String type = jsonObj.get("type").toString();
		ArrayList<String> returnString = new ArrayList<String>();
		
		//Sent to client immediately after connection to verify client is connected to the correct server
		//Message should be "Please send the login info"
		if(type.equals("Welcome")){
			returnString.add("Welcome");
			returnString.add(jsonObj.get("Msg").toString());
		}
		
		//Sent to client to communicate whether a login was a success or a failure
		//Message will be either "Successful" or "Failure"
		else if(type.equals("LoginStatus")){
			returnString.add("LoginStatus");
			returnString.add(jsonObj.get("Msg").toString());
		}
		
		//returns a list of all the games
		else if(type.equals("GameList")){
			returnString.add("GameList");
			JSONArray GameList = (JSONArray)jsonObj.get("List");
			for (int i = 0; i < GameList.size(); i++) {
				  returnString.add(GameList.get(i).toString());
			}
		}
		//returns a list of players waiting to play
		else if(type.equals("WaitingToPlay")){
			returnString.add("WaitingToPlay");
			JSONArray NameList = (JSONArray)jsonObj.get("List");
			for (int i = 0; i < NameList.size(); i++) {
				  returnString.add(NameList.get(i).toString());
			}
		}
		//returns a list of all the players
		else if(type.equals("PlayerList")){
			returnString.add("PlayerList");
			JSONArray NameList = (JSONArray)jsonObj.get("List");
			for (int i = 0; i < NameList.size(); i++) {
				  returnString.add(NameList.get(i).toString());
			}
		}
		
		//returns whether a plugin is valid.
		else if(type.equals("InvalidPlugin")){
			returnString.add("InvalidPlugin");
			returnString.add(jsonObj.get("Valid").toString());
		}
		
		//For textual messages to display on the clientside GUI
		else if(type.equals("SendTextMessage")){
			returnString.add("SendTextMessage");
			returnString.add(jsonObj.get("message").toString());
		}
		
		//For if the button should be disabled or not
		else if(type.equals("ButtonDisabled")){
			returnString.add("ButtonDisabled");
			returnString.add(jsonObj.get("value").toString());
		}
		
		//Button label
		else if(type.equals("ButtonText")){
			returnString.add("ButtonText");
			returnString.add(jsonObj.get("text").toString());
		}
		
		//When the server passes the gameBoard
		else if(type.equals("GameBoard")){
			returnString.add("GameBoard");
			returnString.add(jsonObj.toString());
		}
		
		//When the server pass the openGameList
		else if(type.equals("OpenGameList")){
			returnString.add("OpenGameList");
			JSONArray GameList = (JSONArray)jsonObj.get("List");
			for (int i = 0; i < GameList.size(); i++) {
				  returnString.add(GameList.get(i).toString());
			}
		}
		
		else if(type.equals("error")){
			returnString.add("error");
			returnString.add(jsonObj.get("message").toString());
		}
		return returnString;
	}
	
}
