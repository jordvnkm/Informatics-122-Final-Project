package Server;

import java.util.List;

import javax.management.timer.Timer;
import javax.swing.JOptionPane;

import org.w3c.dom.css.Counter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerController
{
	
    private ServerGUI gui;
    private Server server;
    
    private boolean threadRunning;
    
	private javax.swing.Timer tick; //used for the countdown
	private int countDown;

    private boolean consoleDebug = true;
    
	public ServerController()
	{
		 //instantiates the server and ServerGUI instance
        gui = new ServerGUI();
        server = new Server(this);
        
        //initializes what we want the coutdown of the shutdown procedure to be
        countDown = 5;
        
        threadRunning = false;
        
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
                gui.setResizable(false);
            }
        });
	}
	
	
	private void startServer()
	{
  
		try{
			String portNum = gui.portTextField.getText().replace(" ", "");
	        
	        if (Integer.valueOf(portNum) < 1 || Integer.valueOf(portNum) > 49151 || portNum.equals(""))
	        {
	            throw new Exception(portNum);
	        }
	        
	        
	        //appends the starting message to the server GUI
	        gui.statusTextArea.append("Starting server... (Port #" + portNum + ")\n");
	        
	        gui.startButton.setEnabled(false);
	        gui.stopButton.setEnabled(true);
	        gui.portTextField.setEnabled(false);
	        
	        //starts the server with the specified port number
	        server.startServer(Integer.valueOf(portNum));
	        
	        if(!threadRunning)
	        {
	        	threadRunning = true;
	        	server.start();
	        }
	
		}
        
        catch (IOException e)
        {
        	if(consoleDebug)
        	{
        		System.out.println("Error in trying to turn the port string into a number");
                e.printStackTrace();
        	}
        	
            JOptionPane.showMessageDialog(gui,
                    "Please try a different port number",
                    "Port Number in Use",
                    JOptionPane.ERROR_MESSAGE);
        } 
        
        catch (Exception exception)
        {
        	if(consoleDebug)
        	{
                System.out.println("Error in trying to turn the port string into a number (Exception type):-" + exception.getMessage() +"-");
                exception.printStackTrace();
        	}

            JOptionPane.showMessageDialog(gui,
                    "Please enter a port number between 1 and 49151",
                    "Invalid Port Number",
                    JOptionPane.ERROR_MESSAGE);
        }
        
	}
	
	private void stopServer()
	{
   
			gui.statusTextArea.append("Stopping server...\n");
	        gui.startButton.setEnabled(true);
	        gui.stopButton.setEnabled(false);
	        gui.portTextField.setEnabled(true);
	        
	        server.stopServer();
	        	        		
	       ActionListener action = new ActionListener() 
	       {
				@Override
				public void actionPerformed(ActionEvent e) {
						if(countDown < 1)
						{
							tick.stop();
							System.exit(0);
						}
						else
						{
							gui.statusTextArea.append(String.valueOf(countDown) + "\n");
							countDown--;
							gui.repaint();

						}
				
				}
			};
			
			gui.statusTextArea.append("\n*****SHUTTING DOWN*****\n");
			gui.statusTextArea.append("Shutting down server in:\n");

			
	        tick = new javax.swing.Timer(1000, action);
	        tick.setInitialDelay(0);
	        tick.start();

     
	}
	
	public void GUINewConnection(String ip, int port)
	{
		gui.statusTextArea.append("Connection from IP: " + ip + " with the PORT: " + port + "\n");
	}

}
