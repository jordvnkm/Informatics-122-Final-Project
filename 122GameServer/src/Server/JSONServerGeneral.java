package Server;


import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONServerGeneral 
{	
	//Returns String Array
	//For Example:
	//		If we receive {"type": "MovePiece", "X": 1, "Y": 2}
	//This returns the String Array:
	//		["MovePiece", "1", "2"]
	
	
	public static String[] checkType(String jsonString)
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
		String[] returnString = null;
		
		//For Adding pieces to board
		if(type.equals("MovePiece")){
			returnString = new String[3];
			returnString[0] = "MovePiece";
			returnString[1] = jsonObj.get("X").toString();
			returnString[2] = jsonObj.get("Y").toString();
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
			returnString[1] = jsonObj.get("User").toString();
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