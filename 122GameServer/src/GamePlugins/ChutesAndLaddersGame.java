package GamePlugins;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class ChutesAndLaddersGame extends GameState{

	private HashMap<String, Piece> playerToPiece;
	private HashMap<int[],int[]> specialty;
	private HashMap<Integer, int[]> positioning;
	private HashMap<String,Integer> currentPos;
	HashMap<int[], Integer> reversedHashMap;
	private int rows;
	private int columns;
	private final int[] purple = {87,20,96};
	private final int[] green = {76,156,64};
	private Random r;
	
	public ChutesAndLaddersGame(String[] players){
		super(players);
		specialty = new HashMap<int[],int[]>();
		positioning = new HashMap<Integer,int[]>();
		currentPos = new HashMap<String,Integer>();
		r = new Random();
		
		playerToPiece = new HashMap<String, Piece>();
		playerToPiece.put(players[0],  new Piece(purple , "CIRCLE", "1", 'O'));
		playerToPiece.put(players[1],  new Piece(green, "CIRCLE", "1", 'O'));
		
		currentPos.put(players[0], 0);
		currentPos.put(players[1], 0);
		
		setUpBoard();
	}

	@Override
	public void setUpBoard(){
		board = new Board(8,8);
		rows = board.getRows();
		columns = board.getColumns();
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				board.setTile(i, j, new Tile());
			}
		}
		
		int pos=0;
		int i =0;
		// Setting the path black
		for(int h=0; h<2; h++){	
			for(int j=0; j<board.getColumns(); j++){
				positioning.put(pos, new int[]{i,j});
				pos++;
				board.setTile(i, j, new Tile(new int[]{0, 0, 0}));
			}
			i++;

			positioning.put(pos, new int[]{i,7});
			board.getBoard()[i][7] = new Tile(new int[]{0,0,0});
			pos++;
			i++;
			for(int j=board.getColumns() - 1; j>=0 ;j--){
				positioning.put(pos, new int[]{i,j});
				pos++;
				board.setTile(i, j, new Tile(new int[]{0, 0, 0}));
			}
			i++;

			positioning.put(pos, new int[]{i,0});
			board.setTile(i, 0, new Tile(new int[]{0, 0, 0}));
			pos++;
			i++;	
		}

		board.getTile(2, 5).setBackgroundColor(19, 208, 242);
		board.getTile(6, 4).setBackgroundColor(19, 208, 242);

		//chute landing
		board.getTile(4, 0).setBackgroundColor(255, 115, 0);
		board.getTile(2, 4).setBackgroundColor(255, 115, 0);

		//chute
		board.getTile(4, 7).setBackgroundColor(255, 0, 0);
		board.getTile(6, 0).setBackgroundColor(255, 0, 0);
		//ladder
		board.getTile(4, 0).setBackgroundColor(0,252,255);
		board.getTile(2, 4).setBackgroundColor(0,252,255);

		specialty.put( new int[]{4,7}, new int[]{2,4});
		specialty.put( new int[]{6,0}, new int[]{4,0});
		specialty.put( new int[]{0,2}, new int[]{2,5});
		specialty.put( new int[]{4,4}, new int[]{6,4});
		
		board.addPiece(0 , 0,  new Piece(purple, "CIRCLE", "1", 'O'));
		board.addPiece(0, 0,  new Piece(green, "CIRCLE", "1", 'O'));
	 reversedHashMap = new HashMap<int[], Integer>();
		for (Integer key : positioning.keySet()){
		    reversedHashMap.put(positioning.get(key), key);
		}
		
	}
public int randomNumGen(){
	  return r.nextInt(6) + 1;
}


public int[] giveNewSpot(String name){
	int moves =randomNumGen();
	int newPos= moves+currentPos.get(name);
	if(positioning.containsKey(newPos)){
		if(specialty.containsKey( positioning.get(moves+currentPos.get(name)))){
		return specialty.get( positioning.get(moves+currentPos.get(name)));
		}else{
		
			return positioning.get(moves+currentPos.get(name));
		}
	} else{ return new int[]{7,0};}
	
}
	@Override
	public boolean checkForGameOver(){
		return isRunning;
	}


	

	@Override
	public boolean playMove(int x, int y, String name){
		if(!currentTurn.equals(name)){
			return false;
		}
	
			int[] newSpotCoords =giveNewSpot(name);
			
			board.addPiece(newSpotCoords[0],newSpotCoords[1], playerToPiece.get(currentTurn));
			int[] a =positioning.get(currentPos.get(name));
			Piece toRemove = accessPiece(a[0],a[1],name);
			
			board.removePiece(a[0], a[1], toRemove);
			//how do i get back to the number instead of coords?
			currentPos.put(name,reversedHashMap.get(newSpotCoords));
			if(newSpotCoords[0]==7&&newSpotCoords[1]==0){
				winner=currentTurn;
				isRunning = false;
			
				
			}else{
			changeTurn();
			}
			return true;
					
				
			

			
		

	}

	

	@Override
	public void changeTurn(){
		turn += 1;
		currentTurn = players[turn % players.length];
	}


	

	//Used to check if pieces are capturable. Flips capturable pieces is flip bool is true.



	private Piece accessPiece(int row, int column, String name){
		ArrayList<Piece> pieceArray =board.getBoard()[row][column].getPieces();
		for(int i=0; i<pieceArray.size();i++){
			if(pieceArray.get(i).getColor().equals(playerToPiece.get(name).getColor())){
				return pieceArray.get(i);
			}
		}
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


	private boolean outOfBounds(int row, int column){
		if (row >= rows || row < 0){
			return true;
		}
		else if(column >= columns || column < 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkValidMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args){
		String[] players = new String[]{"Adrian", "Alex"};
		
		ChutesAndLaddersGame t = new ChutesAndLaddersGame(players);
		Scanner scan = new Scanner(System.in);
		String row;
		
		while(t.getIsRunning()){
			System.out.println("Current turn: " + t.getCurrentTurn());
			drawBoard(t.getBoard());
			System.out.print("Enter: ");
			row = scan.nextLine();
			t.playMove(2, 2, t.getCurrentTurn());
		}
		drawBoard(t.getBoard());
		System.out.println("Winner: " + t.getWinner());
		scan.close();
	}
	
	
	public static void drawBoard(Board b){
		int row = b.getRows();
		int col = b.getColumns();
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				System.out.print(b.getTile(i, j).getFirstPiece().getType() + " | ");
			}
			System.out.println("");
		}
	}

	@Override
	public boolean buttonPressed(String button, String name) {
		return playMove(0,0,name);
	}
    
    public String getGameState(){
        return super.getGameState();
    }
	
}

