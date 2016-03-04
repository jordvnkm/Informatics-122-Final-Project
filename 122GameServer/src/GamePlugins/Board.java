public class Board{
	private int rows;
	private int columns;
	private Tile[][] board;
	
	public Board(int rows, int columns){
		board = new Tile[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}
	
	public void addPiece(int row, int column, Piece piece){
		board[row][column].addPiece(piece);
	}
	
	public void removePiece(int row, int column, Piece piece){
		board[row][column].removePiece(piece);
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getColumns(){
		return columns;
	}
}