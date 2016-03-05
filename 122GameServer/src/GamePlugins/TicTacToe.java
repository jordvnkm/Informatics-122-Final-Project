package GamePlugins;

import java.util.HashMap;
import java.util.List;

import Server.Player;

public class TicTacToe extends GameState{

	private HashMap<String, Piece> playerToPiece;
	
	public TicTacToe(int x, int y, List<Player> players){
		super(x, y, players);
		playerToPiece = new HashMap<String, Piece>();
		playerToPiece.put(players.get(0).getName(), new TicTacToeXPiece("Black", "X"));
		playerToPiece.put(players.get(1).getName(), new TicTacToeOPiece("Red", "Circle"));
	}
	
	@Override
	public void setUpBoard() {
		
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
			return true;
		}
		return false;
	}

	@Override
	public boolean checkValidMove(int x, int y) {
		if(x < 0 || x > 2 || y < 0 || y > 2)
			return false;
		else if(board.getTile(x, y).getPieceType() != PieceType.NONE)
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
			if(checkRowCol(board.getTile(i, 0).getPiece(), board.getTile(i,  1).getPiece(), board.getTile(i, 2).getPiece()))
				return true;
		}
		return false;
	}
	
	private boolean checkColumnsForWin(){
		for(int i = 0; i < 3; i++){
			if(checkRowCol(board.getTile(0, i).getPiece(), board.getTile(1, i).getPiece(), board.getTile(2, i).getPiece()))
				return true;
		}
		return false;
	}
	
	private boolean checkDiagonalsForWin(){
		return (checkRowCol(board.getTile(0, 0).getPiece(), board.getTile(1, 1).getPiece(), board.getTile(2, 2).getPiece()) ||
				 checkRowCol(board.getTile(0, 2).getPiece(), board.getTile(1, 1).getPiece(), board.getTile(2, 0).getPiece()));	
	}
	
	
	private boolean checkRowCol(Piece p1, Piece p2, Piece p3){
		return ((p1.getType() != PieceType.NONE) && (p1.equals(p2)) && (p2.equals(p3)));
	}


}
