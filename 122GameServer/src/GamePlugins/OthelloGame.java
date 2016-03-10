package GamePlugins;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class OthelloGame extends GameState{
	private final int[] white = {255,255,255};
	private final int[] black = {0,0,0};
	private HashMap<String, Piece> playerToPiece;
	private int rows;
	private int columns;
	
	public OthelloGame(String[] players){
		super(players);
		setUpBoard();
		rows = board.getRows();
		columns = board.getColumns();
		playerToPiece = new HashMap<String, Piece>();
		playerToPiece.put(players[0],  new Piece(black , "CIRCLE", "1", 'O'));
		playerToPiece.put(players[1],  new Piece(white, "CIRCLE", "1", 'O'));
	}
	
	@Override
	public void setUpBoard(){
		board = new Board(8,8);
		for(int i = 0; i < board.getRows(); i++){
			for(int j = 0; j < board.getColumns(); j++){
				board.getBoard()[i][j] = new Tile(new int[]{0, 255, 0}); // Green Background
			}
		}
		board.addPiece(board.getRows()/2-1, board.getColumns()/2-1,  new Piece(white, "CIRCLE", "1", 'O'));
		board.addPiece(board.getRows()/2, board.getColumns()/2,  new Piece(white, "CIRCLE", "1", 'O'));
		board.addPiece(board.getRows()/2-1, board.getColumns()/2, new Piece(black , "CIRCLE", "1", 'O'));
		board.addPiece(board.getRows()/2, board.getColumns()/2-1, new Piece(black , "CIRCLE", "1", 'O'));
	}
	
	@Override
	public boolean checkForGameOver(){
		return isRunning;
	}
	
	
	public boolean checkIfMoveExists(){
		for(int x = 0; x < rows; x++){
			for(int y = 0; y < columns; y++){
				if(spaceTaken(x,y)){}
				else if(!isValidForFlip(x,y,false)){}
				else
					return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean playMove(int x, int y, String name){
		if(!currentTurn.equals(name)){
			return false;
		}
		
		if (checkValidMove(x, y)){
			board.addPiece(x,y, playerToPiece.get(currentTurn));
			changeTurn();
			if(!checkIfMoveExists()){
				changeTurn();
				if(!checkIfMoveExists()){
					findWinner();
					isRunning = false;
				}
			}

			return true;
		}
		return false;
	}
	
	private void findWinner(){
		int p1 = 0;
		int p2 = 0;
		for(int x = 0; x < rows; x++){
			for(int y = 0; y < columns; y++){
				if(Arrays.equals(getColor(x,y), black)){
					p1+=1;
				}
				else if(Arrays.equals(getColor(x,y), white)){
					p2+=1;
				}
			}	
		}
		if(p1>p2)
			winner = players[0];
		else if(p2>p1)
			winner = players[1];
		else
			winner = "TIE";
	}
	
	
	@Override
	public boolean checkValidMove(int x, int y){
		if (spaceTaken(x,y)){
			return false;
		}
		else if (!isValidForFlip(x,y, true)){
			return false;
		}
		else
			return true;
	}
	
	
	
	@Override
	public void changeTurn(){
		turn += 1;
		currentTurn = players[turn % players.length];
	}
	
	private boolean spaceTaken(int x, int y){
		return board.getBoard()[x][y].hasPieces();
	}
	
	private boolean isValidForFlip(int x, int y, boolean flip){
		List<int[]> listOfAdjacent = eightDirections(x,y);
		if (listOfAdjacent.size() > 0){
			if(captured(listOfAdjacent, x, y, flip)){
				return true;
			}
		}
		return false;
	}
	
	//Used to check if pieces are capturable. Flips capturable pieces is flip bool is true.
	private boolean captured(List<int[]> adjacent, int row, int column, boolean flip){
		boolean capture = false;
		for(int[] coords : adjacent){
			int fliprow = coords[0];
			int flipcolumn = coords[1];
			int spotrow = coords[0];
			int spotcolumn = coords[1];
			int rowPattern = coords[0]-row;
			int columnPattern = coords[1]-column;
			
			while(!outOfBounds(spotrow+rowPattern, spotcolumn+columnPattern) && getColor(spotrow+rowPattern, spotcolumn+columnPattern)[0] != 1){
				spotrow += rowPattern; 
				spotcolumn += columnPattern;
				if(Arrays.equals(getColor(spotrow,spotcolumn), getTurnColor())){
					capture = true;
					if(flip == true){
						while(!Arrays.equals(getColor(fliprow,flipcolumn), getTurnColor())){
							board.removePiece(fliprow, flipcolumn, accessPiece(fliprow, flipcolumn));
							board.addPiece(fliprow,flipcolumn, playerToPiece.get(currentTurn));
							fliprow += rowPattern;
							flipcolumn += columnPattern;		
						}
					}
					break;
				}
			}
		}
		return capture;
	}

	
	private Piece accessPiece(int row, int column){
		return board.getBoard()[row][column].getPieces().get(0);
	}
	
	private int[] getColor(int row, int column){
		return board.getBoard()[row][column].getPieces().get(0).getColor();
	}

	
	private int[] getTurnColor(){
		return playerToPiece.get(currentTurn).getColor();
	}
	
	private int[] getOtherTurnColor(){
		return playerToPiece.get(players[(turn+1) % players.length]).getColor();
	}
	
    private List<int[]> eightDirections(int row, int column){
        List<int[]> moveList = new ArrayList<int[]>();
        for(int i = -1; i<=1; i++){
        	for(int j = -1; j <=1; j++){
        		if(!(i == 0 && j == 0)){
        			if(moveCoords(row+i, column+j) != null){
        				moveList.add(moveCoords(row+i, column+j));
        			}
        		}
        	}
        }
        return moveList;
    }
    
    
    
    private int[] moveCoords(int row, int column){
        if (!outOfBounds(row, column)){
            if (Arrays.equals(getColor(row,column), getOtherTurnColor())){
            	int [] arr = {row, column};
            	return arr;
            }
        }
        return null;
    }
    
    private boolean outOfBounds(int row, int column){
        if (row >= rows || row < 0){
            return true;
        }
        else if(column >= columns || column < 0){
            return true;
        }
        return false;
    }
	
	
	
}