package Client;

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
	private String fgText;
	private int xlocation,ylocation;
	
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
		draw();
	}
	
	public void setup(){
		gc = getGraphicsContext2D();
		//centering text http://stackoverflow.com/questions/14882806/center-text-on-canvas
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		bg = Color.rgb(0, 0, 0);
		fg = Color.rgb(255, 255, 255);
		fgText="";
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
		//draw Background
        gc.setFill(bg);
		gc.fillRect(0,0, getWidth(),getHeight());
		//draw Border
		gc.setFill(Color.rgb(255, 255, 255));
		gc.strokeRect(0,0,getWidth(),getHeight());
		//draw Foreground
        gc.setFill(fg);
        gc.setFont(new Font(getHeight()));
        gc.fillText(fgText, Math.round(getWidth()/2),Math.round(getHeight()/2));
	}

	public int getXlocation(){
		return xlocation;
	}
	
	public int getYlocation(){
		return ylocation;
	}
}
