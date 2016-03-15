package Client;


import java.util.ArrayList;

import javafx.scene.layout.GridPane;

public class Board extends GridPane{
	private ArrayList <ArrayList<Tile>> tiles;
	private final int BOARD_WIDTH=600;
	private final int BOARD_HEIGHT=600;
	
	//not using default constructor
	private Board(){}
	//http://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx
	//main constructor
	public Board(int rows,int columns){
        this.setPrefSize(600, 600);
        tiles = new ArrayList<ArrayList<Tile>>();
        setDimensions(rows,columns);
	}
	
	//Changes the dimensions of the board according to the width and height.
	//all tiles are reset to default.
	public void setDimensions(int row,int column){
		getChildren().clear();
		tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<column;i++){
        	
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<row;j++)
        	{

        		//we divide by height for both width and height to keep the spaces as squares.
        		Tile x = new Tile(BOARD_WIDTH/row,BOARD_HEIGHT/row,i,j);

        		tiles.get(i).add(x);

        		x.setBackgroundColor(255, 255, 255);
        		x.setForegroundColor(0, 0, 0);
        		x.setText("");

        		this.add(x, i,j);
        		//System.out.println("added tile");
        	}
        }
        System.out.println("width "+getWidth());
        System.out.println("height "+getHeight());
	}
	
	public Tile getTile(int x,int y){
		return tiles.get(x).get(y);
	}
}
