package GamePlugins;

public class OthelloPiece extends Piece{
	
	private String color;
	private String otherColor;
	private String shape;
	
	public OthelloPiece(String color, String shape, String otherColor){
		super(color, shape, 'W');
		this.otherColor = otherColor;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String getShape(){
		return this.shape;
	}
	
	public void switchColor(){
		this.color = this.otherColor;
	}
	
	
}