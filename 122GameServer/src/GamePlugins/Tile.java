package GamePlugins;

import java.util.ArrayList;

public class Tile{
	
	private ArrayList<Piece> pieces;
	private String backgroundColor;
	
	//new data member to store the background color of the tile
	private int[] backgroundColor;
	
	
	public Tile(){
		pieces = new ArrayList<Piece>();
		backgroundColor = "White";
	}
	
	public Tile(String color){
		pieces = new ArrayList<Piece>();
		backgroundColor = color;
	}
	
	public String getBackgroundColor(){
		return this.backgroundColor;
	}
	
	public void clearTile(){
		pieces.clear();
	}
	
	public void removePiece(Piece piece){
		pieces.remove(piece);
	}
	
	public void addPiece(Piece piece){
		pieces.add(piece);
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public boolean hasPieces(){
		if(pieces.isEmpty()){
			return false;
		}
		return true;
	}
	
<<<<<<< HEAD
	public char getPieceType(){
		return this.pieces.get(0).getType();
	}
	
	public Piece getFirstPiece(){
		if(this.pieces.size() > 0)
			return this.pieces.get(0);
		return null;
=======
	//new methods to set and get background color
	public void setBackgroundColor(int r, int g, int b)
	{
		backgroundColor[0] = r;
		backgroundColor[1] = g;
		backgroundColor[2] = b;		
	}
	
	public int[] getBackgroundColor()
	{
		return backgroundColor;
>>>>>>> refs/remotes/origin/master
	}
}