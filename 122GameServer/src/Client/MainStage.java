package Client;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainStage extends Stage{
	//turns on debug messages in the console.
	private boolean Debug = true;
	//text area logger.
	private TextArea TAlog;

	private Board gameboard;
//	private Scene scene = new Scene(new BorderPane());
//	private MenuBar mb = new MenuBar();
//	private BorderPane bottom = new BorderPane();

	private Button actionButton;
	
	//MenuItems needed by the Client
	private MenuItem MIselectserv,
	MIcreatelogin,MIrequestgame,MIquitgame,MIlogin;
	
	//main GUI setup
	public MainStage(){
		super();

		setTitle("INF 122 Game Client");
        Scene scene = new Scene(new BorderPane());
        //Menus
        MenuBar mb = new MenuBar();
        Menu servermenu = new Menu("Server");
        MIselectserv = new MenuItem("Select Server");
        MIcreatelogin = new MenuItem("Create Login");
        MIlogin = new MenuItem("Login");
        MenuItem MIExit = new MenuItem("Exit");
        MIExit.setOnAction((ActionEvent e) -> {
	    	System.exit(0);
	    });
        servermenu.getItems().addAll(MIselectserv,MIcreatelogin,MIlogin,new SeparatorMenuItem(),MIExit);
        
        Menu gamemenu = new Menu("Game");
        MIrequestgame = new MenuItem("Request Game");
        MIquitgame = new MenuItem("Quit Game");
        gamemenu.getItems().addAll(MIrequestgame,MIquitgame);
        
        Menu windowmenu = new Menu("Window");
        MenuItem MIoptions = new MenuItem("Options");
        windowmenu.getItems().addAll(MIoptions);
        
        Menu helpmenu = new Menu("Help");
        MenuItem MIabout = new MenuItem("About");
        helpmenu.getItems().addAll(MIabout);
        
        mb.getMenus().addAll(servermenu,gamemenu,windowmenu,helpmenu);
        //Board
        gameboard = new Board(3, 3 );

        //text area
        TAlog = new TextArea();
        TAlog.setMaxHeight(100);

        //Button
        actionButton = new Button("Button");
        actionButton.setMinSize(100,100);
        
        //construct bottom
        BorderPane bottom = new BorderPane();
        bottom.setCenter(TAlog);
        BorderPane bottomright = new BorderPane();
        bottomright.setCenter(actionButton);
        bottom.setRight(bottomright);
        
		((BorderPane) scene.getRoot()).setTop(mb);
		((BorderPane) scene.getRoot()).setCenter(gameboard);
		((BorderPane) scene.getRoot()).setBottom(bottom);
		setScene(scene);
		show();
        
		
	}

	public int getRows()
	{
		return gameboard.getRows();
	}
	
	public int getColumns()
	{
		return gameboard.getColumns();
	}
	
	public void setBoard(int rows, int columns)
	{
//		gameboard = new Board(rows, columns);
		gameboard.setDimensions(rows, columns);

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
	
	public Board getBoard(){
		return gameboard;
	}
	public void setBoard(Board b){
		gameboard = b; 
	}
	
	public Button getButton(){
		return actionButton;
	}
	

	//Getters needed by the client to setup listeners, so it can send
	//messages to the server when they are clicked (as GUI doesn't know
	//anything about client presently)
	public MenuItem getSelectServMenuItem(){
		return MIselectserv;
	}
	
	public MenuItem getcreateloginMenuItem(){
		return MIcreatelogin;
	}
	
	public MenuItem getrequestgameMenuItem(){
		return MIrequestgame;
	}
	
	public MenuItem getQuitGameMenuItem(){
		return MIquitgame;
	}
	
	public MenuItem getLoginMenuItem(){
		return MIlogin;
	}
}
