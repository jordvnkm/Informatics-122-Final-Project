package GamePlugins;

public class Piece{
	
	private String color;
	private String shape;
	private char type;
	private int layer; 
	
	public Piece(String color, String shape, char type){
		this.color = color;
		this.shape = shape;
		this.type = type;
		this.layer = 1;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getShape(){
		return this.shape;
	}
	
	public char getType(){
		return this.type;
	}
	
	public int getLayer(){
		return this.layer;
	}
}