package GamePlugins;

// This class might not be needed anymore
public class OthelloPiece extends Piece{
	
	private String otherColor;
	private String shape;
	
	// Have to change constructor
	public OthelloPiece(int[] color, String shape, String layer, char type){
		super(color, shape, layer, type);
	}
	
	/*
	public int[] getColor(){
		return this.color;
	}
	
	public String getShape(){
		return this.shape;
	}
	
	public void switchColor(){
		this.color = this.otherColor;
	}
	*/
	
}