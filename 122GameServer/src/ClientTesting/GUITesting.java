package ClientTesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.application.Platform;
import Client.Board;
import Client.Dialogs;
import Client.MainStage;
import Client.Tile;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	//private final ExecutorService executorService ;
	private MainStage gui;
	private int width = 8,height = 8;
	public DummyClient(MainStage inputgui){
		gui = inputgui;
	    gui.getButton().setOnAction((ActionEvent e) -> {
	    	Dialogs.popupError("Oh no there was an error", "Error!", null);
	    	Dialogs.getServerInfo();
	    	Dialogs.getLoginInfo();
	    	Dialogs.createLogin();
	    });
	   	(new Thread(this)).start();
	    }
		/*executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			}
		});
        gui.getButton().setOnAction((ActionEvent e) -> {
			final Task<Void> computerMoveTask = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					System.out.println("call made");
					gui.getBoard().setDimensions(8, 8);
					System.out.println("setting up listeners");
					setupMouseListeners(8,8);
					return null;
					
				}
			};
			executorService.submit(computerMoveTask);
        });*/
	

	public void setupMouseListeners(int width, int height)
	{
		for(int i=0;i<width;i++)
        	for(int j=0;j<height;j++){
        		gui.getBoard().getTile(i, j).setOnMouseClicked((MouseEvent e) -> {
        			Tile t = (Tile)e.getSource();
                	int xloc= t.getXlocation();
                	int yloc= t.getYlocation();
                	gui.logger("Mouse clicked: "+xloc+","+yloc,true);
            		t.setText("X");
                });
        	}
	}
	public void runtest(int x){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
					System.out.println(x+"x"+x);
					gui.getBoard().setDimensions(x, x);
					setupMouseListeners(x,x);	
			}
		});
	}
	//run will not be used in actual client! it is not safe
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			for(int i=0;i<20;i++){
				runtest(i);
				try{Thread.sleep(1000);}catch(InterruptedException e){}
			}
			
		}
		
	}
}

