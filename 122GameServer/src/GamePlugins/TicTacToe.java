package GamePlugins;

import java.util.HashMap;
import java.util.Scanner;

public class TicTacToe extends GameState{

	private HashMap<String, Piece> playerToPiece;
	
	public TicTacToe(String[] players){
		super(players);
		playerToPiece = new HashMap<String, Piece>();
		playerToPiece.put(players[0], new Piece(new int[]{0, 0, 255}, "CROSS", "1", 'X'));
		playerToPiece.put(players[1], new Piece(new int[]{255, 0, 0}, "OH", "1", 'O'));
		setUpBoard();
	}
	
	@Override
	public void setUpBoard() {
		this.board = new Board(3, 3);
		int rows = this.board.getRows();
		int cols = this.board.getColumns();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				this.board.setTile(i, j, new Tile(new int[]{255, 255, 255}));
			}
		}
	}

	@Override
	public boolean checkForGameOver() {
		if(checkForWin()){
			this.winner = this.currentTurn;
			return true;
		}
		
		return this.turn >= 8;
	}

	@Override
	public boolean playMove(int x, int y, String name) {
		if(!currentTurn.equals(name)){
			return false;
		}
		
		if(checkValidMove(x, y)){
			board.addPiece(x, y, playerToPiece.get(currentTurn));
			if(checkForGameOver()){
				this.isRunning = false;
			}
			changeTurn();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean buttonPressed(String button, String name)
	{
		return false;
	}

	@Override
	public boolean checkValidMove(int x, int y) {
		if(x < 0 || x > 2 || y < 0 || y > 2)
			return false;
		else if(board.getTile(x, y).hasPieces())
			return false;
		
		return true;
	}

	@Override
	public void changeTurn() {
		turn += 1;
		currentTurn = players[turn % players.length];
	}
	
	
	private boolean checkForWin(){
		return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
	}
	
	private boolean checkRowsForWin(){
		for(int i = 0; i < 3; i++){
			if(checkRowCol(board.getTile(i, 0).getFirstPiece(), 
					board.getTile(i,  1).getFirstPiece(), board.getTile(i, 2).getFirstPiece()))
				return true;
		}
		return false;
	}
	
	private boolean checkColumnsForWin(){
		for(int i = 0; i < 3; i++){
			if(checkRowCol(board.getTile(0, i).getFirstPiece(), 
					board.getTile(1, i).getFirstPiece(), board.getTile(2, i).getFirstPiece()))
				return true;
		}
		return false;
	}
	
	
	private boolean checkDiagonalsForWin(){
		return (checkRowCol(board.getTile(0, 0).getFirstPiece(), 
				board.getTile(1, 1).getFirstPiece(), board.getTile(2, 2).getFirstPiece()) ||
				 checkRowCol(board.getTile(0, 2).getFirstPiece(), 
						 board.getTile(1, 1).getFirstPiece(), board.getTile(2, 0).getFirstPiece()));	
	}
	
	
	private boolean checkRowCol(Piece p1, Piece p2, Piece p3){
		return (p1.getType() != 'E' && p1.getType() == p2.getType() && p2.getType() == p3.getType());
	}
	
	
	
	// Testing PURPOSES ONLY
	/*
	public static void main(String[] args){
		String[] players = new String[]{"Adrian", "Alex"};
		
		TicTacToe t = new TicTacToe(players);
		Scanner scan = new Scanner(System.in);
		int row, col;
		
		while(t.getIsRunning()){
			System.out.println("Current turn: " + t.getCurrentTurn());
			drawBoard(t.getBoard());
			System.out.print("Enter [ROW][COL]: ");
			row = scan.nextInt();
			col = scan.nextInt();
			t.playMove(row, col, t.getCurrentTurn());
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
	*/
    
    public String getGameState(){
        return super.getGameState();
    }
}
