package ClientTesting;

import Client.MainStage;
import Client.Tile;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/*
 * This is a dummy Client for the purpose of testing the GUI (MainStage) and other windows
 */
public class GUITesting extends Application{

    MainStage gui;
    @Override
    public void start(Stage primaryStage) {

        gui = new MainStage();
        primaryStage = gui;
        DummyClient c = new DummyClient(gui);
    }
    
    public static void main(String[] args) {
    	launch(args);

    }
}

class DummyClient implements Runnable{
	
	private MainStage gui;
	
	public DummyClient(MainStage inputgui){
		gui = inputgui;
		setupMouseListeners();
		(new Thread(this)).start();
	}

	public void setupMouseListeners()
	{
		for(int i=0;i<3;i++)
        	for(int j=0;j<3;j++){
        		gui.getBoard().getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	gui.logger("Mouse clicked: "+xloc+","+yloc,true);
            		t.setText("X");
                });
        	}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

