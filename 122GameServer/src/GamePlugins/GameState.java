package GamePlugins;


public abstract class GameState {
	protected Board board;
	protected int turn;
	protected String[] players;
	protected String currentTurn;
	protected String winner;
	protected boolean isRunning;
	
	
	public GameState(String[] players){
		this.players = players;
		this.isRunning = true;
		this.turn = 0;
		this.currentTurn = players[0];
		this.winner = "TIE";
	}
	
	
	public abstract void setUpBoard();
		
	public abstract boolean checkForGameOver();
	
	public abstract boolean playMove(int x, int y, String name);
	
	public abstract boolean checkValidMove(int x, int y);
	
	public abstract void changeTurn();
	
	
	
	public String getCurrentTurn(){
		return this.currentTurn;
	}
	
	public String getWinner(){
		return this.winner;
	}
	
	public int getTurn(){
		return this.turn;
	}
	
	public boolean getIsRunning(){
		return this.isRunning;
	}
	
	public Board getBoard(){
		return this.board;
	}
	
	public String getGameState(){
		String jsonString = GameStateToJSON.gameStateToJSON(this.board, this.currentTurn, this.winner, this.isRunning);
		return jsonString;
	}
}