package Server;
import org.json.simple.*;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;


//For converting data into JSON strings to be sent to client
public class JSONServerTranslator{
	
	@SuppressWarnings("unchecked")
	public static String welcomeMessage(){
		JSONObject welcome = new JSONObject();
		welcome.put("type", "Welcome");
		welcome.put("Msg", "Please send the login info");
		return welcome.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String loginStatus(String status){
		JSONObject login = new JSONObject();
		login.put("type", "LoginStatus");
		login.put("Msg", status);
		return login.toJSONString();
	}
	
	
	@SuppressWarnings("unchecked")
	public static String waitingToPlay(ArrayList<String> players){
		JSONObject people = new JSONObject();
		JSONArray playerArray = new JSONArray();
		people.put("type", "WaitingToPlay");
		for(String g : players){
			playerArray.add(g);
		}
		people.put("List", playerArray);
		return people.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String playerList(ArrayList<String> players){
		JSONObject people = new JSONObject();
		JSONArray playerArray = new JSONArray();
		people.put("type", "PlayerList");
		for(String g : players){
			playerArray.add(g);
		}
		people.put("List", playerArray);
		return people.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String invalidPlugin(boolean valid){
		JSONObject validity = new JSONObject();
		validity.put("type", "InvalidPlugin");
		validity.put("Valid", Boolean.toString(valid));
		return validity.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String sendTextMessage(String message){
		JSONObject msg = new JSONObject();
		msg.put("type", "SendTextMessage");
		msg.put("message", message);
		return msg.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String buttonDisabled(boolean val){
		JSONObject msg = new JSONObject();
		msg.put("type", "ButtonDisabled");
		msg.put("message", Boolean.toString(val));
		return msg.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String buttonText(boolean txt){
		JSONObject text = new JSONObject();
		text.put("type", "ButtonText");
		text.put("message", txt);
		return text.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String gameList(List<String> games, boolean open)
	{
		JSONObject msg = new JSONObject();
		msg.put("type", open ? "OpenGameList" : "GameList");
		JSONArray gameArray = new JSONArray();
		for(String game : games){
			gameArray.add(game);
		}
		msg.put("List", gameArray);
		return msg.toJSONString();
	}
	
}