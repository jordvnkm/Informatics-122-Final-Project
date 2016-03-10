package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    MainStage gui;
    @Override
    public void start(Stage primaryStage) {
    	//connect to server
        Client c = new Client("127.0.0.1",8000);
        
        // set up the gui
        //c.requestBoardSize();
        //int[] boardSize = c.parseBoardSize();
        //gui = new MainStage(boardSize[0], boardSize[1]);
        gui = new MainStage(3, 3);
        primaryStage = gui;
        c.setGui(gui);
        c.setupMouseListeners();
       
    }
    
    
    public static void main(String[] args) {
    	launch(args);
    }
}
