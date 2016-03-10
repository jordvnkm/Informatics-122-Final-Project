package Client;


import java.util.ArrayList;

import javafx.scene.layout.GridPane;

public class Board extends GridPane{
	private ArrayList <ArrayList<Tile>> tiles;
	public Board(){
		super();
	}
	//main constructor
	public Board(int rows,int columns){
        this.setMinSize(166*3,166*3);
        this.setPrefSize(166*3, 166*3);
        this.setMaxSize(166*3, 166*3);
        tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<rows;i++){
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<columns;j++)
        	{
        		tiles.get(i).add(new Tile(166,166,i,j));
        		Tile x = tiles.get(i).get(j);
        		x.setBackgroundColor(255, 255, 255);
        		x.setForegroundColor(0, 0, 0);
        		x.setText("");
        		this.add(x, i,j);
        	}
        }
	}
	public Tile getTile(int x,int y){
		return tiles.get(x).get(y);
	}
}
