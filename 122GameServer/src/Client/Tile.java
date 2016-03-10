package Client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tile extends Canvas{
	//resizable canvas source:https://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/
	private GraphicsContext gc;
	private double width=166,height=166;
	private Color bg,fg;
	private String fgText;
	private int xlocation,ylocation;
	public Tile(){
		super();
		gc = getGraphicsContext2D();
		this.setWidth(width);
		this.setHeight(height);
		bg = Color.rgb(255, 255, 255);
		fg = Color.rgb(0, 0, 0);
		fgText="X";
		draw();
	}
	public Tile(int x,int y,int xloc,int yloc){
		super(x,y);
		gc = getGraphicsContext2D();
		this.setWidth(width);
		this.setHeight(height);
		bg = Color.rgb(0, 0, 0);
		fg = Color.rgb(255, 255, 255);
		fgText="X";
		xlocation = xloc;
		ylocation = yloc;
		draw();
	}
	
	public void setBackgroundColor(int r,int g,int b){
		bg = Color.rgb(r, g, b);
		draw();
	}
	public void setForegroundColor(int r,int g,int b){
		fg = Color.rgb(r, g, b);
		draw();
	}
	public void setText(String s){
		fgText = s;
		draw();
	}
	public void draw(){
		//drawBackground
        gc.setFill(bg);
		gc.fillRect(0,0, width,height);
		//drawForeground
        gc.setFill(fg);
        gc.setFont(new Font(166));
        gc.fillText(fgText, 40,140);
	}

	public int getXlocation(){
		return xlocation;
	}
	
	public int getYlocation(){
		return ylocation;
	}
}
