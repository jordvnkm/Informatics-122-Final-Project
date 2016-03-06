package GamePlugins;

import java.util.ArrayList;

public class Tile{
	private ArrayList<Piece> pieces;
	private String backgroundColor;
	
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
	
	public char getPieceType(){
		return this.pieces.get(0).getType();
	}
	
	public Piece getFirstPiece(){
		if(this.pieces.size() > 0)
			return this.pieces.get(0);
		return null;
	}
}