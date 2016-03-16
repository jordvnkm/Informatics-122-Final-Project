package Client;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

//http://code.makery.ch/blog/javafx-dialogs-official/
public class Dialogs {
	/**
	 * displays to the user an error, where they have to press OK to continue.
	 * Header - header of the window.
	 * content - The error itself.
	 * if any of the strings is null, then a default for that field is displayed.
	 */
	public static void popupError(String content, String header,String title){
		if (content==null)
			content="There was an error.";
		if (header==null)
			header="";
		if (title==null)
			title="Error :(";
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	/**
	 * displays to user a window for requesting an ip address to connect to, 
	 * and a port number, and returns as an array of strings.
	 * first String is ip address, second string is port.
	 * returns null if they cancel.
	 */
	public static String[] getServerInfo(){
		Dialog<Pair<String,String>> d = new Dialog<>();
		d.setTitle("Server");
		d.setHeaderText("Please enter server info:");
		d.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(20,150,10,10));
		TextField server = new TextField();
		server.setText("127.0.0.1");
		TextField port = new TextField();
		port.setText("8000");
		gp.add(new Label("Server:"),0,0);
		gp.add(server, 1, 0);
		gp.add(new Label("Port:"), 0, 1);
		gp.add(port, 1,1);
		
		d.getDialogPane().setContent(gp);
		d.setResultConverter(dialogButton ->{
			if(dialogButton == ButtonType.OK){
				return new Pair<>(server.getText(),port.getText());
			}
			return null;
		});
		Optional<Pair<String,String>> result = d.showAndWait();
		
		if(result.isPresent())
			return new String[]{server.getText(),port.getText()};
		return null;
	}
	
	/**
	 * displays a window for requesting username (and possibly later, password).
	 * returns null if they cancel
	 * returns their login name and info string if they don't cancel.
	 */
	public static String getLoginInfo(){
		Dialog<String> d = new Dialog<>();
		d.setTitle("Login");
		d.setHeaderText("Please enter login information:");
		d.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(20,150,10,10));
		TextField login = new TextField();
		login.setText("");
		gp.add(new Label("Username:"),0,0);
		gp.add(login, 1, 0);
		
		d.getDialogPane().setContent(gp);
		d.setResultConverter(dialogButton ->{
			if(dialogButton == ButtonType.OK){
				return login.getText();
			}
			return null;
		});
		Optional<String> result = d.showAndWait();
		
		if(result.isPresent())
			return login.getText();
		return null;
	}
	
	/**
	 * displays a window for requesting login and an info string about the user.
	 * returns null if they cancel
	 * returns their login name and info string if they don't cancel.
	 */
	public static String[] createLogin(){
		Dialog<Pair<String,String>> d = new Dialog<>();
		d.setTitle("Create Login");
		d.setHeaderText("Please enter login info:");
		d.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(20,150,10,10));
		TextField login = new TextField();
		login.setText("");
		TextField info = new TextField();
		info.setText("");
		gp.add(new Label("Username:"),0,0);
		gp.add(login, 1, 0);
		gp.add(new Label("Info about yourself:"), 0, 1);
		gp.add(info, 1,1);
		
		d.getDialogPane().setContent(gp);
		d.setResultConverter(dialogButton ->{
			if(dialogButton == ButtonType.OK){
				return new Pair<>(login.getText(),info.getText());
			}
			return null;
		});
		Optional<Pair<String,String>> result = d.showAndWait();
		
		if(result.isPresent())
			return new String[]{login.getText(),info.getText()};
		return null;
	}
}
