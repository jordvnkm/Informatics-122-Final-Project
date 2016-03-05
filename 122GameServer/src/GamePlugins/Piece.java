package GamePlugins;

public class Piece{
	
	protected String color;
	protected String shape;
	protected PieceType type;
	
	public Piece(String color, String shape){
		this.color = color;
		this.shape = shape;
		this.type = PieceType.NONE;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getShape(){
		return this.shape;
	}
	
	public PieceType getType(){
		return this.getType();
	}
	
}