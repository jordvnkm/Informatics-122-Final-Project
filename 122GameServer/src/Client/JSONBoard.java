package Client;

import org.json.simple.*;
import org.json.simple.parser.*;

//This is the Parser for the JSONString produced by the GameStateToJSON class.

public class JSONBoard{
	private int rowNum;
	private int columnNum;
	private String currentTurn;
	private String winner;
	private boolean isRunning;
	private String jsonString;
	private String errorMsg;
	private String gameMsg;
	private JSONArray tileList;
	
	
	//Constructor...Pass in JSONString
	public JSONBoard(String jsonString){
		this.jsonString = jsonString;
		setValues();
	}
	
	//Called by Constructor. Sets values of rowNum, columnNum, currentTurn, winner, isRunning, and tileList
	private void setValues(){
		JSONParser parser = new JSONParser();
		JSONObject jsonGameState = new JSONObject();
		JSONObject jsonBoard = new JSONObject();
		try{
			Object obj = parser.parse(this.jsonString);
			jsonGameState = (JSONObject)obj;
			currentTurn = jsonGameState.get("currentTurn").toString();
			winner = jsonGameState.get("winner").toString();
			isRunning = Boolean.parseBoolean(jsonGameState.get("is_running").toString());
			rowNum = Integer.parseInt(jsonGameState.get("rowNum").toString());
			columnNum = Integer.parseInt(jsonGameState.get("columnNum").toString());
			//errorMsg = jsonGameState.get("errorMsg").toString();
			//gameMsg = jsonGameState.get("gameMsg").toString();
			jsonBoard = (JSONObject)jsonGameState.get("board");
			
			tileList = (JSONArray)jsonBoard.get("tiles");
			
		}catch(ParseException pe){
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}

	
	public int getRowNum() {
		return rowNum;
	}


	public int getColumnNum() {
		return columnNum;
	}


	public String getCurrentTurn() {
		return currentTurn;
	}


	public String getWinner() {
		return winner;
	}


	public boolean getIsRunning() {
		return isRunning;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String getGameMsg() {
		return gameMsg;
	}
	
	//Gets the tile color as int[]...for example [255,255,255]
	//uses rgb values
	//Enter (0,0) as params to get the color of the Tile at COORDS 0,0 (topleft of board)
	public int[] getTileColor(int row, int column){
		int ArrayPos = (columnNum * row + column);
		JSONObject tile = new JSONObject();
		tile = (JSONObject)tileList.get(ArrayPos);
		JSONArray colorJSONArray = (JSONArray)tile.get("backgroundColor");
		int[] colorArray = {Integer.parseInt(colorJSONArray.get(0).toString()), Integer.parseInt(colorJSONArray.get(1).toString()), Integer.parseInt(colorJSONArray.get(2).toString())};
		return colorArray;
	}
	
	//Gets a piece Shape. Parameters are row and column, and position
	//position parameter is for games that have more than 1 piece per Tile. 
	//position parameter will be default 0 for games like Othello, TicTacToe, and Chutes
	public String getPieceShape(int row, int column, int position){
		try{
			return getPiece(row, column, position).get("shape").toString();
		}catch(Exception pe){
			return null;
		}
	}
	
	
	//Gets a piece color in form of int[]. Example: [255,255,255]
	//position parameter is for games that have more than 1 piece per Tile. 
	//position parameter will be default 0 for games like Othello, TicTacToe, and Chutes
	public int[] getPieceColor(int row, int column, int position){
		try{
			JSONArray colorJSONArray = (JSONArray)getPiece(row, column, position).get("color");
			int[] colorArray = {Integer.parseInt(colorJSONArray.get(0).toString()), Integer.parseInt(colorJSONArray.get(1).toString()), Integer.parseInt(colorJSONArray.get(2).toString())};
			return colorArray;
		}catch(Exception pe){
			return null;
		}
	}
	
	
	//Gets a piece type as a String
    //position parameter is for games that have more than 1 piece per Tile.
	//position parameter will be default 0 for games like Othello, TicTacToe, and Chutes
	public String getPieceType(int row, int column, int position){
		try{
			return getPiece(row, column, position).get("type").toString();
		}catch(Exception pe){
			return null;
		}
	}
	
	//Gets a piece layer as a string
	//position parameter is for games that have more than 1 piece per Tile. 
	//position parameter will be default 0 for games like Othello, TicTacToe, and Chutes
	public String getPieceLayer(int row, int column, int position){
		try{
			return getPiece(row, column, position).get("layer").toString();
		}catch(Exception pe){
			return null;
		}
	}
	
	
	//Private helper method returning a JSONObject Piece.
	private JSONObject getPiece(int row, int column, int position){
		try{
			JSONObject tile;
			JSONArray pieceList;
			int ArrayPos = (columnNum * row + column);
			tile = (JSONObject)tileList.get(ArrayPos);
			pieceList = (JSONArray)tile.get("pieces");
			return (JSONObject)pieceList.get(position);
		}catch(Exception pe){
			return null;
		}
	}
	
	
}
	
	
