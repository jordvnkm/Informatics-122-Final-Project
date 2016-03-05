package GamePlugins;

import java.util.ArrayList;

public class Tile{
	private ArrayList<Piece> pieces;
	
	public Tile(){
		pieces = new ArrayList<Piece>();
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
	
	public PieceType getPieceType(){
		return this.pieces.get(0).getType();
	}
	
	public Piece getPiece(){
		return this.pieces.get(0);
	}
}