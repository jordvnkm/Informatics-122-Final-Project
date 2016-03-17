package GamePlugins;

import java.util.ArrayList;

import org.json.simple.*;

public class GameStateToJSON
{
	/********************************************************************
	 * 	public static String gameStateToJSON()
	 * 
	 * 	The structure of the game state is as follows:
	 * 
	 * Game State
	 * 	- rowNum
	 * 	- columnNum
	 *  - currentTurn
	 * 	- is_running
	 *  - winner
	 *  - errorMsg
	 *  - gameMsg
	 *  - board
	 * 		-tiles[]
	 * 			-backgroundColor
	 * 			-pieces[]
	 * 				-shape
	 * 				-color
	 * 				-type
	 * 				-layer
	 * 
	 * 
	 * 
	 * ------------------------------------------------------------------
	 * 
	 * The intention of this method is to translate a class of type GameState
	 * 	into a JSON Object String that is understood by the client GUI. This 
	 * 	method will utilize several helper methods to compose the JSON String.
	 * 
	 ********************************************************************/
	@SuppressWarnings("unchecked")
	public static String gameStateToJSON(Board board, String currentTurn, String winner, boolean isRunning, String errorMsg, String gameMsg)
	{
		//contains the entire state
		JSONObject jsonGameState = new JSONObject();

		//stores the game board composed of tiles
		JSONObject jsonBoard = new JSONObject();
		
		//stores the information of the tiles
		JSONObject jsonTile = new JSONObject();
	
		//stores the information of the pieces that are located on the tiles
		JSONObject jsonPieces = new JSONObject();
		
		//stores the JSON piece objects
		JSONArray pieceList = new JSONArray();
		
		//stores the JSON tile objects
		JSONArray tileList = new JSONArray();
		
		for(int row = 0; row < board.getRows(); row++)
		{
			for(int column = 0; column < board.getColumns(); column++)
			{
				//gets and stores the current tile
				Tile tile = board.getTile(row, column);
				
				//pushes the color into the Tile JSONObject
				jsonTile.put("backgroundColor", tile.getBackgroundColor());
				
				//gets the 1 or more pieces and cycles through for each
				ArrayList<Piece> pieces = tile.getPieces();
				for(Piece p : pieces)
				{
					//shape
					jsonPieces.put("shape", p.getShape());
					
					jsonPieces.put("color", p.getColor());

					//type
					jsonPieces.put("type", String.valueOf(p.getType()));

					//layer
					jsonPieces.put("layer", p.getLayer());
					
					pieceList.add(jsonPieces);
				}
				
				//adds all of the pieces to the tile
				jsonTile.put("pieces", pieceList);
				
				//adds the tile to the board
				tileList.add(jsonTile);
				
				pieceList = new JSONArray();
				jsonTile = new JSONObject();
				jsonPieces = new JSONObject();
			}
		}
		jsonBoard.put("tiles", tileList);
		
		//setting the run state of the game
		jsonGameState.put("is_running", isRunning);
		jsonGameState.put("currentTurn", currentTurn);
		jsonGameState.put("winner", winner);
		jsonGameState.put("rowNum", board.getRows());
		jsonGameState.put("columnNum", board.getColumns());
		jsonGameState.put("errorMsg", errorMsg);
		jsonGameState.put("gameMsg", gameMsg);
		jsonGameState.put("type", "GameBoard");
		
		
		
		//adds the board to the game state string
		jsonGameState.put("board", jsonBoard);

		return jsonGameState.toJSONString();
	}
	
	

	

}
