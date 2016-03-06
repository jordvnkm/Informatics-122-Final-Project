package GamePlugins;

public class Piece{
	
	private String shape;
	
	//altered datat type for RGB
	private int[]  color;

	//added data members
	private String layer;
	private char type;
	
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