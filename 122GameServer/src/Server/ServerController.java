package Server;

import java.util.List;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerController
{
	
    private ServerGUI gui;
    private Server server;
    
	public ServerController()
	{
		 //instantiates the server and ServerGUI instance
        gui = new ServerGUI();
        server = new Server();
        
        //sets the stopButton grayed out to start
        gui.stopButton.setEnabled(false);

        //listener for start button
        gui.startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startServer();
                
            }
        });

        //listener for stop button
        gui.stopButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stopServer();
            }
        });

        //thread safe runnable and sets visible
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                gui.setVisible(true);
            }
        });
	}
	
	
	private void startServer()
	{
  
		try{
			String portNum = gui.portTextField.getText();
	        
	        if (Integer.valueOf(portNum) < 1 || Integer.valueOf(portNum) > 49151 || Integer.valueOf(portNum) == 0)
	        {
	            throw new Exception();
	        }
	        

	        
	        //appends the starting message to the server GUI
	        gui.statusTextArea.append("Starting server... (Port #" + portNum + ")\n");
	        
	        gui.startButton.setEnabled(false);
	        gui.stopButton.setEnabled(true);
	        gui.portTextField.setEnabled(false);
	        
	        //starts the server with the specified port number
	        server.startServer(Integer.valueOf(portNum));
	
	//        gui.statusTextArea.append("Connection from IP: " + tmpSocket.getRemoteSocketAddress().toString() + " wiith the PORT: " + tmpSocket.getPort() + "\n");
		}
        
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(gui,
                    "Please try a different port number",
                    "Port Number in Use",
                    JOptionPane.ERROR_MESSAGE);
        } 
        
        catch (Exception exception)
        {
            System.out.println("This is the ex: " + exception.getStackTrace().toString());
            exception.printStackTrace();
            JOptionPane.showMessageDialog(gui,
                    "Please enter a port number between 1 and 49151",
                    "Invalid Port Number",
                    JOptionPane.ERROR_MESSAGE);
        }
        
	}
	
	private void stopServer()
	{
        try{
			gui.statusTextArea.append("Stopping server...\n");
	        gui.startButton.setEnabled(true);
	        gui.stopButton.setEnabled(false);
	        gui.portTextField.setEnabled(true);
	        
	        server.stopServer();
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

}
