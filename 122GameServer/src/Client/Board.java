package Client;


import java.util.ArrayList;

import javafx.scene.layout.GridPane;

public class Board extends GridPane{
	private ArrayList <ArrayList<Tile>> tiles;

	private int rows;
	private int columns;

	private final int BOARD_WIDTH=600;
	private final int BOARD_HEIGHT=600;
	
	//not using default constructor
	//constructor set to private to prevent use of the constructor.
	private Board(){}
	//http://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx

	//main constructor
	public Board(int myrows,int mycolumns){
		rows = myrows;
		columns = mycolumns;
       // this.setPrefSize(600, 600);
       System.out.println("a"+getWidth());
        tiles = new ArrayList<ArrayList<Tile>>();
        setDimensions(rows,columns);
	}
	
	//Changes the dimensions of the board according to the width and height.
	//all tiles are reset to default.
	public void setDimensions(int row,int column){
		rows = row;
		columns = column;
		getChildren().clear();
		tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<column;i++){
        	
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<row;j++)
        	{

        		//we divide by height for both width and height to keep the spaces as squares.
        		Tile x = new Tile(BOARD_WIDTH/row,BOARD_HEIGHT/row,i,j);

        		tiles.get(i).add(x);
        		//x.setBackgroundColor(255, 255, 255);
//        		x.setForegroundColor(0, 0, 0);
//        		x.setText("");
        		

        		this.add(x, i,j);
        		//System.out.println("added tile");
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
		setDimensions(rows,columns);
	}
	
	
	public int getColumns()
	{
		return columns;
	}
	
	public void setColumns(int c)
	{
		columns = c;
		setDimensions(rows,columns);
	}
}
