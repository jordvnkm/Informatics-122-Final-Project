package GamePlugins;

public class Piece{
	
	private String shape;
	private char type;
	private String layer; 
	//altered data type for RGB
	private int[]  color;
		
	public Piece(int[] color, String shape, String layer, char type){
		this.color = color;
		this.shape = shape;
		this.layer = layer;
		this.type = type;
	}
	
	//changed the return type to work for RGB
	public int[] getColor()
	{
		return this.color;
	}
	
	public String getShape(){
		return this.shape;
	}

	public String getLayer()
	{
		return layer;
	}
	
	public char getType()
	{
		return type;
	}
}