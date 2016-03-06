package Server;

import java.util.ArrayList;

import org.json.simple.*;
import GamePlugins.*;

public class JSONTranslator 
{
	/********************************************************************
	 * 	public static String gameStateToJSON()
	 * 
	 * 	The structure of the game state is as follows:
	 * 
	 * Game State
	 * 	- Board
	 * 		-Tile
	 * 			-Background color
	 * 			-Pieces
	 * 				-Shape
	 * 				-Color
	 * 				-Type
	 * 				-Layer
	 * 	- is running
	 * 
	 * ------------------------------------------------------------------
	 * 
	 * The intention of this method is to translate a class of type GameState
	 * 	into a JSON Object String that is understood by the client GUI. This 
	 * 	method will utilize several helper methods to compose the JSON String.
	 * 
	 ********************************************************************/
	@SuppressWarnings("unchecked")
	public static String gameStateToJSON(Board board)
	{
		//contains the entire state
		JSONObject jsonGameState = new JSONObject();

		//stores the game board composed of tiles
		JSONObject jsonBoard = new JSONObject();
		
		//stores the information of the tiles
		JSONObject jsonTile = new JSONObject();
	
		//stores the information of the pieces that are located on the tiles
		JSONObject jsonPieces = new JSONObject();
	
		//used multiple times throughout every iteration between BG and Piece
		JSONObject color = new JSONObject();
		
		for(int row = 0; row < board.getRows(); row++)
		{
			for(int column = 0; column < board.getColumns(); column++)
			{
				//gets and stores the current tile
				Tile tile = board.getTile(row, column);

				//gets and translates the background color
				color.put("red", tile.getBackgroundColor()[0]);
				color.put("green", tile.getBackgroundColor()[1]);
				color.put("blue", tile.getBackgroundColor()[2]);
				
				//pushes the color into the Tile JSONObject
				jsonTile.put("backgroundColor", color);
				
				//clears the color object for reuse
				color.clear();
				
				//gets the 1 or more pieces and cycles through for each
				ArrayList<Piece> pieces = tile.getPieces();
				
				for(Piece p : pieces)
				{
					//shape
					jsonPieces.put("shape", p.getShape());
					
					//color
					color.put("red", p.getColor()[0]);
					color.put("green", p.getColor()[1]);
					color.put("blue", p.getColor()[2]);
					
					jsonPieces.put("color", p.getColor());

					//type
					jsonPieces.put("type", p.getType());

					//layer
					jsonPieces.put("layer", p.getLayer());
					
					//clearing the color out
					color.clear();
				}
				
				//adds all of the pieces to the tile
				jsonTile.put("pieces", jsonPieces);
				
				//adds the tile to the board
				jsonBoard.put("tile", jsonTile);
			}
		}
			
		
		//setting the run state of the game
		jsonGameState.put("is_running", new Boolean(true));
		
		//adds the board to the game state string
		jsonGameState.put("board", jsonBoard);

		return jsonGameState.toJSONString();
	}
}
