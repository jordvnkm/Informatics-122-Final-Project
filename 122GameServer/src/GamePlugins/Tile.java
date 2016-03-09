package GamePlugins;
import java.util.ArrayList;
public class Tile{
	
	private ArrayList<Piece> pieces;	
	//new data member to store the background color of the tile
	private int[] backgroundColor;
	
	
	public Tile(){
		pieces = new ArrayList<Piece>();
		pieces.add(new Piece(new int[]{1, 1, 1}, "empty", "empty",'E'));
		backgroundColor = new int[]{255, 255, 255}; // default white background
	}
	
	public Tile(int[] color){
		pieces = new ArrayList<Piece>();
		pieces.add(new Piece(new int[]{1, 1, 1}, "empty", "empty",'E'));
		backgroundColor = color;
	}
	
	public int[] getBackgroundColor(){
		return backgroundColor;
	}
	
	public void clearTile(){
		pieces.clear();
		pieces.add(new Piece(new int[]{1, 1, 1}, "empty", "empty",'E'));
	}
	
	public void removePiece(Piece piece){
		if(pieces.get(0).getShape() == "empty"){
			throw new NullPointerException("Tile is already empty!!!");
		}
		pieces.remove(piece);
		if(pieces.isEmpty()){
			pieces.add(new Piece(new int[]{1, 1, 1}, "empty", "empty",'E'));
		}
	}
	
	public void addPiece(Piece piece){
		if(pieces.get(0).getShape() == "empty"){
			pieces.remove(0);
		}
		pieces.add(piece);
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public boolean hasPieces(){
		if(pieces.get(0).getShape() == "empty"){
			return false;
		}
		return true;
	}
	
	public char getPieceType(){
		return this.pieces.get(0).getType();
	}
	
	public Piece getFirstPiece(){
		return this.pieces.get(0);

	}

	//new methods to set and get background color
	public void setBackgroundColor(int r, int g, int b)
	{
		backgroundColor[0] = r;
		backgroundColor[1] = g;
		backgroundColor[2] = b;		
	}
}