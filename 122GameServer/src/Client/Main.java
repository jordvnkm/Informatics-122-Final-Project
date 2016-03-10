package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    MainStage gui;
    @Override
    public void start(Stage primaryStage) {
    	gui = new MainStage();
        primaryStage = gui;
        Client c = new Client("127.0.0.1",8000,gui);
        
    }
    public static void main(String[] args) {
    	launch(args);
    }
}
