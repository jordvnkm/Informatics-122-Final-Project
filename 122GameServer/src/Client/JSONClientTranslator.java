package Client;
import org.json.simple.*;


//For converting data into JSON strings to be sent to server
public class JSONClientTranslator{
	
	@SuppressWarnings("unchecked")
	public static String movePiece(int x, int y){
		JSONObject move = new JSONObject();
		move.put("type", "MovePiece");
		move.put("X", Integer.toString(x));
		move.put("Y", Integer.toString(y));
		return move.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String buttonPressed(String button){
		JSONObject buttonPress = new JSONObject();
		buttonPress.put("type", "ButtonPressed");
		buttonPress.put("ButtonType", button);
		return buttonPress.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String quiteGame(){
		JSONObject quit = new JSONObject();
		quit.put("type", "QuitGame");
		return quit.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String confirmGame(){
		JSONObject conf = new JSONObject();
		conf.put("type", "ConfirmGame");
		return conf.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String loginType(String login){
		JSONObject log = new JSONObject();
		log.put("type", "LoginType");
		log.put("Command", login);
		return log.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String username(String user){
		JSONObject name = new JSONObject();
		name.put("type", "Username");
		name.put("User", user);
		return name.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String description(String bio){
		JSONObject describe = new JSONObject();
		describe.put("type", "Description");
		describe.put("Bio", bio);
		return describe.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static String selectedGame(String game){
		JSONObject selected = new JSONObject();
		selected.put("type", "SelectedGame");
		selected.put("Game", game);
		return selected.toJSONString();
	}
	
	
}