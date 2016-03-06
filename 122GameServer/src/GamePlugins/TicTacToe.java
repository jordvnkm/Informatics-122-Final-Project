package GamePlugins;

import java.util.HashMap;
import java.util.List;
import Server.Player;

public class TicTacToe extends GameState{

	private HashMap<String, Piece> playerToPiece;
	
	public TicTacToe(int x, int y, List<Player> players){
		super(x, y, players);
		playerToPiece = new HashMap<String, Piece>();
		playerToPiece.put(players.get(0).getName(), new Piece("Black", "Cross", 'X'));
		playerToPiece.put(players.get(1).getName(), new Piece("Red", "Circle", 'O'));
		setUpBoard();
	}
	
	@Override
	public void setUpBoard() {
		this.board = new Board(3, 3);
	}

	@Override
	public boolean checkForGameOver() {
		if(checkForWin()){
			this.winner = this.currentTurn;
			return true;
		}
		
		return this.turn >= 9;
	}

	@Override
	public boolean playMove(int x, int y) {
		if(checkValidMove(x, y)){
			board.addPiece(x, y, playerToPiece.get(currentTurn));
			changeTurn();
			return true;
		}
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
		currentTurn = players.get(turn % players.size()).getName();
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
		return (!(p1 == null || p2 == null || p3 == null) &&
				(p1.getType() != '\0' && p1.getType() == p2.getType() && p2.getType() == p3.getType()));
	}
}
