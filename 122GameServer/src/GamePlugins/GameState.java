package GamePlugins;

import java.util.List;

import Server.Player;



public abstract class GameState {

	protected Board board;
	protected int turn;
	protected List<Player> players;
	protected String currentTurn;
	protected String winner;
	protected boolean isRunning;
	
	
	public GameState(int row, int col, List<Player> players){
		this.players = players;
		this.isRunning = true;
		this.turn = 0;
		this.currentTurn = players.get(0).getName();
		this.winner = "tie";
	}
	
	
	public abstract void setUpBoard();
		
	public abstract boolean checkForGameOver();
	
	public abstract boolean playMove(int x, int y);
	
	public abstract boolean checkValidMove(int x, int y);
	
	public abstract void changeTurn();
	
	
	
	public String getCurrentTurn(){
		return this.currentTurn;
	}
	
	public String getWinner(){
		return this.winner;
	}
	
	public void setWinner(String name){
		this.winner = name;
	}
	
	public int getTurn(){
		return this.turn;
	}
	
	
	public boolean getIsRunning(){
		return this.isRunning;
	}
}
