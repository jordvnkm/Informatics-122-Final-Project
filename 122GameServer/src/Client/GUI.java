package Client;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application{
	//turns on debug messages in the console.
	private boolean Debug = true;
	//text area logger.
	private TextArea TAlog;
	private ArrayList <ArrayList<Tile>> tiles;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("INF 122 Game Client");
        Scene scene = new Scene(new BorderPane(), 800, 720);
        
        //Menus
        MenuBar mb = new MenuBar();
        Menu servermenu = new Menu("Server");
        MenuItem MIselectserv = new MenuItem("Select Server");
        MenuItem MIplayerprofile = new MenuItem("Player Profile");
        MenuItem MIExit = new MenuItem("Exit");
        servermenu.getItems().addAll(MIselectserv,MIplayerprofile,new SeparatorMenuItem(),MIExit);
        
        Menu gamemenu = new Menu("Game");
        MenuItem MIrequestgame = new MenuItem("Request Game");
        MenuItem MIquitgame = new MenuItem("Quit Game");
        gamemenu.getItems().addAll(MIrequestgame,MIquitgame);
        
        Menu windowmenu = new Menu("Window");
        MenuItem MIoptions = new MenuItem("Options");
        windowmenu.getItems().addAll(MIoptions);
        
        Menu helpmenu = new Menu("Help");
        MenuItem MIabout = new MenuItem("About");
        helpmenu.getItems().addAll(MIabout);
        
        mb.getMenus().addAll(servermenu,gamemenu,windowmenu,helpmenu);
        
        //canvas to draw on
        /*Canvas gameboard = new Canvas (500,500);
        GraphicsContext gc = gameboard.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);*/
        Board gameboard = new Board(3,3);
        for(int i=0;i<3;i++)
        	for(int j=0;j<3;j++){
        		gameboard.getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	logger("Mouse clicked: "+xloc+","+yloc,true);
                	t.setText("X");
                	
                });
        	}
        /*gameboard.setMinSize(166*3,166*3);
        gameboard.setPrefSize(166*3, 166*3);
        gameboard.setMaxSize(166*3, 166*3);
        tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<3;i++){
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<3;j++)
        	{
        		tiles.get(i).add(new Tile(166,166,i,j));
        		Tile x = tiles.get(i).get(j);
        		x.setBackgroundColor(255, 255, 255);
        		x.setForegroundColor(0, 0, 0);
        		x.setText("");
                x.setOnMouseClicked((MouseEvent e) -> {
                	int xloc=((Tile)(e.getSource())).getXlocation();
                	int yloc=((Tile)(e.getSource())).getYlocation();
                	logger("Mouse clicked: "+xloc+","+yloc, true);
                	x.setText("X");
                });
        		gameboard.add(x, i,j);
        	}
        }
		System.out.println(gameboard.getWidth());*/
        //text area
        TAlog = new TextArea();

        //Button
        Button actionButton = new Button("Button");
        actionButton.setMinSize(100,100);
        actionButton.setOnAction((ActionEvent e) -> {
        	logger("Button Pressed!",true);
        });
        
        //construct bottom
        BorderPane bottom = new BorderPane();
        bottom.setCenter(TAlog);
        BorderPane bottomright = new BorderPane();
        bottomright.setCenter(actionButton);
        bottom.setRight(bottomright);
        
        
        ((BorderPane) scene.getRoot()).setTop(mb);
        ((BorderPane) scene.getRoot()).setCenter(gameboard);
        ((BorderPane) scene.getRoot()).setBottom(bottom);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
	//Add a string to the logger. Set debug to true if its a debugger line.
	//Debugger lines should be lines not shown to the player.
	public void logger(String s, boolean debuggerline){
		if(Debug || (!Debug && !debuggerline))
			TAlog.appendText("\n" + s);
	}
	public void setDebug(boolean input){
		Debug = input;
	}
	public ArrayList <ArrayList<Tile>> getTiles(){
		return tiles;
	}
    public static void main(String[] args) {
    	GUI g = new GUI();
    	g.launch(args);
    }
}
