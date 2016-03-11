package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    MainStage gui;
    @Override
    public void start(Stage primaryStage) {
    	//connect to server
        gui = new MainStage();
        primaryStage = gui;
        Client c = new Client("127.0.0.1",8001, gui);
        
        // set up the gui
        //c.requestBoardSize();
        //long[] boardSize = c.parseBoardSize();
        //gui = new MainStage(boardSize[0], boardSize[1]);
        //c.setupMouseListeners();
       
    }
    
    
    public static void main(String[] args) {
    	launch(args);
    }
}
