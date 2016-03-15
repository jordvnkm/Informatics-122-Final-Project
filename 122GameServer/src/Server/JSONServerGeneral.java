package Server;

import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONServerGeneral 
{
	JSONParser parser;
	String jsonString;
	Object obj;
	JSONObject jsonObj;
	
	//Receives String in JSONFormat
	public JSONServerGeneral(String jsonString){
		parser = new JSONParser();
		this.jsonString = jsonString;
		setupParseObj();
	}
	
	private void setupParseObj(){
		try{
			obj = parser.parse(this.jsonString);
			jsonObj = (JSONObject)obj;
		}catch(ParseException pe){
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}
	
	//Returns String Array
	//For Example:
	//		If we receive {"type": "MovePiece", "X": 1, "Y": 2}
	//This returns the String Array:
	//		["MovePiece", "1", "2"]
	
	public String[] checkType()
	{
		String type = jsonObj.get("type").toString();
		String[] returnString = null;
		
		//For Adding pieces to board
		if(type.equals("MovePiece")){
			returnString = new String[3];
			returnString[0] = "MovePiece";
			returnString[1] = jsonObj.get("X").toString();
			returnString[1] = jsonObj.get("Y").toString();
		}
		
		//For The button on the GUI: For example "roll", "skipTurn"
		else if(type.equals("ButtonPressed")){
			returnString = new String[2];
			returnString[0] = "ButtonPressed";
			returnString[1] = jsonObj.get("ButtonType").toString();
			
		}
		//If the user exits the game
		else if(type.equals("QuitGame")){
			returnString = new String[1];
			returnString[0] = "QuitGame";
		}
		
		//If the user confirms the game
		else if(type.equals("ConfirmGame")){
			returnString = new String[1];
			returnString[0] = "ConfirmGame";
		}
		
		//Login Type: will be either "Login" or "CreateUser"
		else if(type.equals("LoginType")){
			returnString = new String[2];
			returnString[0] = "LoginType";
			returnString[1] = jsonObj.get("Command").toString();
		}
		//Will be a Username
		else if(type.equals("Username")){
			returnString = new String[2];
			returnString[0] = "Username";
			returnString[1] = jsonObj.get("Command").toString();
		}
		//For user description
		else if(type.equals("Description")){
			returnString = new String[2];
			returnString[0] = "Description";
			returnString[1] = jsonObj.get("Bio").toString();
		}
		
		//For which game the user picks: "Othello", "Chutes", "TicTacToe"
		else if(type.equals("SelectedGame")){
			returnString = new String[2];
			returnString[0] = "SelectedGame";
			returnString[1] = jsonObj.get("Game").toString();
		}
		return returnString;
	}
}