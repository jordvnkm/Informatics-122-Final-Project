package Client;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
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
        Board gameboard = new Board();
        gameboard.setMinSize(500,500);
        gameboard.setPrefSize(500, 500);
        gameboard.setMaxSize(500, 500);
        ArrayList <ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
        for(int i=0;i<3;i++){
        	tiles.add(new ArrayList<Tile>());
        	for(int j=0;j<3;j++)
        	{
        		tiles.get(i).add(new Tile(500/3,500/3));
        		Tile x = tiles.get(i).get(j);
                GraphicsContext gc = x.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
        		gc.fillRect(0,0,x.getWidth(),x.getHeight());
        		gc.setFill(Color.BLACK);
        		gc.fillText(i+","+j, 10, 10);
        		gameboard.add(x, i,j);
        	}
        }
        //gameboard.getColumnConstraints().add(new ColumnConstraints(500/3));
        //gameboard.getRowConstraints().add(new RowConstraints(500/3));
		System.out.println(gameboard.getWidth());
        gameboard.setOnMouseClicked((MouseEvent e) -> {
        	logger("mouse pressed",true);
        	System.out.println(gameboard.getHeight()+" "+gameboard.getWidth());
        });
        
        

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
    public static void main(String[] args) {
    	GUI g = new GUI();
    	g.launch(args);
    }
}
