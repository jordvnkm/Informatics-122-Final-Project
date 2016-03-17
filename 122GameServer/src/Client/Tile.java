package Client;


import java.util.*;

import javafx.geometry.VPos;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tile extends Canvas{
	//resizable canvas source:https://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/
	private GraphicsContext gc;
	private Color bg,fg;
	private String fgText1;
	private String fgText2;
	private int xlocation,ylocation;
	private ArrayList<Piece> pieces;
	
	//do not use default constructor.
	private Tile(){}
	
	/*
	 * x = tile width, y = tile height
	 * xloc = x position in the grid
	 * yloc = y position in the grid
	 */

	public Tile(int x,int y,int xloc,int yloc){
		super(x,y);
		setup();
		xlocation = xloc;
		ylocation = yloc;
		pieces = new ArrayList<Piece>();
		draw();
	}
	
	public void setup(){
		gc = getGraphicsContext2D();
		//centering text http://stackoverflow.com/questions/14882806/center-text-on-canvas
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		bg = Color.rgb(255, 255, 255);
		fg = Color.rgb(0, 0, 0);
		fgText1="";
		fgText2 = "";
	}
	
	public void setBackgroundColor(int r,int g,int b){
		bg = Color.rgb(r, g, b);
		//draw();
	}
//	public void setForegroundColor(int r,int g,int b){
//		fg = Color.rgb(r, g, b);
//		draw();
//	}
//	public void setText(String s){
//		fgText = s;
//		draw();
//	}
	
	
	public void draw(){
		//draw Background
        gc.setFill(bg);
		gc.fillRect(0,0, getWidth(),getHeight());
		//draw Border
		gc.setFill(Color.rgb(255, 255, 255));
		gc.strokeRect(0,0,getWidth(),getHeight());
		//draw Foreground
		
		if (pieces.size() == 1)
		{
			String shape = pieces.get(0).getShape();
			if (shape.equals("CROSS"))
			{
				fgText1 = "X";
			}
			else if (shape.equals("CIRCLE"))
			{
				fgText1 = "\u25cf";
			}
			else if (shape.equals("OH"))
			{
				fgText1 = "O";
			}
		}
		else if (pieces.size() == 2)
		{
			String shape1 = pieces.get(0).getShape();
			String shape2 = pieces.get(1).getShape();
			if (shape1.equals("CROSS"))
			{
				fgText1 = "X";
			}
			else if (shape1.equals("CIRCLE"))
			{
				fgText1 = "\u25cf";
			}
			else if (shape1.equals("OH"))
			{
				fgText1 = "O";
			}
			
			if (shape2.equals("CROSS"))
			{
				fgText2 = "X";
			}
			else if (shape2.equals("CIRCLE"))
			{
				fgText2 = "\u25cf";
			}
			else if (shape2.equals("OH"))
			{
				fgText2 = "O";
			}
		}
		else
		{
			fgText1 = "";
		}
		
		if ( pieces.size() == 1)
		{
			int[] color = pieces.get(0).getColor();

			gc.setFill(Color.rgb(color[0], color[1], color[2]));
			gc.setFont(new Font(getHeight()));
			gc.fillText(fgText1, Math.round(getWidth()/2),Math.round(getHeight()/2));
		}
		else if (pieces.size() == 2) // more than one piece on a tile
		{
			int[] color1 = pieces.get(0).getColor();
			int[] color2 = pieces.get(1).getColor();
			System.out.println("HELLO");

			System.out.println(color1[0] + " " + color1[1]+ " " + color1[2]);
			System.out.println(color2[0] + " " + color2[1] + " " +color2[2]);
			
			// piece 1
			gc.setFill(Color.rgb(color1[0], color1[1], color1[2]));
			gc.setFont(new Font(getHeight()));
			gc.fillText(fgText1,  Math.round(getWidth()/4), Math.round(getHeight()/3));
			
			//piece 2
			gc.setFill(Color.rgb(color2[0], color2[1], color2[2]));
			gc.setFont(new Font(getHeight()));
			gc.fillText(fgText2, Math.round((getWidth()/4) + (getWidth()/4) + (getWidth()/4)), Math.round(getHeight()/3));
		}
		else
		{
			//gc.setFill(Color.rgb(0, 0, 0));
			gc.setFont(new Font(getHeight()));
			gc.fillText(fgText1, Math.round(getWidth()/2),Math.round(getHeight()/2));
		}
	}

	public int getXlocation(){
		return xlocation;
	}
	
	public int getYlocation(){
		return ylocation;
	}
	
	public void addPiece(Piece p)
	{
		pieces.add(p);
	}
}
