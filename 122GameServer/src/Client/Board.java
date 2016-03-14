package Client;


import java.util.ArrayList;

import javafx.scene.layout.GridPane;

public class Board extends GridPane{
	private ArrayList <ArrayList<Tile>> tiles;
	private int rows;
	private int columns;
	public Board(){
		super();
	}
	//main constructor
	public Board(int myrows,int mycolumns){
		rows = myrows;
		columns = mycolumns;
        this.setPrefSize(600, 600);
        tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<rows;i++){
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<columns;j++)
        	{
        		tiles.get(i).add(new Tile(600/columns,600/columns,i,j));
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
	
	public int getRows()
	{
		return rows;
	}
	
	public void setRows(int r)
	{
		rows = r;
	}
	
	
	public int getColumns()
	{
		return columns;
	}
	
	public void setColumns(int c)
	{
		columns = c;
	}
}
