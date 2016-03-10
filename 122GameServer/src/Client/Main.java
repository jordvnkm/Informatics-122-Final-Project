package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    MainStage gui;
    @Override
    public void start(Stage primaryStage) {
//    	gui = new MainStage();
//        primaryStage = gui;
        Client c = new Client("127.0.0.1",8000);
        int[] boardSize = c.getBoardSize();
        gui = new MainStage(boardSize[0], boardSize[1]);
        c.setupBoard();
        c.setupMouseListeners();

    }
    public static void main(String[] args) {
    	launch(args);
    }
}
