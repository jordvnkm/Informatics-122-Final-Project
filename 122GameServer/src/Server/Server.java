/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author malar
 */
public class Server implements Runnable
{
    private ServerSocket server;
    private ServerGUI gui;
    private boolean runServer;
    private Lobby lobby;
        
    
    //non-default constructor
    public Server()
    {      
    	//instantiates our lobby for the connections
    	lobby = new Lobby();
    	
    	//instantiates the ServerGUI instance
    	gui = new ServerGUI();
    	
    	//sets the stopButton grayed out to start
		gui.stopButton.setEnabled(false);

    	//listener for start button
    	gui.startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
						startServer();
			}
		});
    	
    	//listener for stop button
    	gui.stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});
    	
    	//thread safe runnable and sets visible
      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
          public void run() 
          {
              gui.setVisible(true);
          }
      });    	 
    }
    
    
    /*****************************************************************************
     *   public void execute()
     *   
     *   Begins listening for client connections. When connection is accepted,
     *   		the player socket is sent to the Lobby
     *****************************************************************************/
    private void startServer()
    {
        try 
        { 	
			String portNum = gui.portTextField.getText();
			
			if(Integer.valueOf(portNum) < 1 || Integer.valueOf(portNum) > 49151 || Integer.valueOf(portNum) == 0)
				throw new Exception();

			
	    	gui.statusTextArea.append("Starting server... (Port #" + portNum + ")\n");
	    	
            server = new ServerSocket(Integer.valueOf(portNum));
            
			runServer = true;	
			gui.startButton.setEnabled(false);
			gui.stopButton.setEnabled(true);
			gui.portTextField.setEnabled(false);
            
            while (runServer)
            {
            	Socket tmpSocket = server.accept();
            	lobby.addNewConnection(tmpSocket);
            	gui.statusTextArea.append("Connection from IP: " + tmpSocket.getRemoteSocketAddress() + " wiith the PORT: " + tmpSocket.getPort() + "\n");

            }

        }
        catch(IOException e)
        {
        	JOptionPane.showMessageDialog(gui,
				    "Please try a different port number",
				    "Port Number in Use",
				    JOptionPane.ERROR_MESSAGE);
        }
        
		catch(Exception exception)
		{
			JOptionPane.showMessageDialog(gui,
				    "Please enter a port number between 1 and 49151",
				    "Invalid Port Number",
				    JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void stopServer()
    {
    	gui.statusTextArea.append("Stopping server...\n");
		runServer = false;
		gui.startButton.setEnabled(true);
		gui.stopButton.setEnabled(false);
		gui.portTextField.setEnabled(true);
		
		try 
		{
			server.close();
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(gui,
				    "Could not stop the server",
				    "Fatal Error",
				    JOptionPane.ERROR_MESSAGE);
			
			System.exit(-1);
		}

    }


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
    
   
}
