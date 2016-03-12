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
public class Server extends Thread
{

    private ServerSocket server;
    private boolean runServer;
    private Lobby lobby;
    private int portNum;
    private boolean threadStarted;
    
    //non-default constructor
    public Server()
    {
        //instantiates our lobby for the connections
        lobby = new Lobby();   
        threadStarted = false;
    }

    /**
     * ***************************************************************************
     * public void execute()
     *
     * Begins listening for client connections. When connection is accepted, the
     * player socket is sent to the Lobby
     * @throws IOException 
     * @throws InterruptedException 
     ****************************************************************************
     */
    public void startServer(int portNum) throws IOException, InterruptedException
    {
//    	this.portNum = portNum;
//    	
//    	if(!threadStarted)
//    	{
//    		threadStarted = true;
//    		this.start();
//       	}
//    	
//    	runServer = true;
    	
        server = new ServerSocket(portNum);

        runServer = true;

		while (runServer)
        {
            Socket tmpSocket = server.accept();
sleep(1000);
System.out.println("Hello");
            lobby.addNewConnection(tmpSocket);
        }
    }

    public void stopServer() throws IOException
    {
        runServer = false;
        server.close();
      
    }

    @Override
    public void run()
    {
    	try{
        server = new ServerSocket(portNum);

        runServer = true;

		while (runServer)
        {
            Socket tmpSocket = server.accept();

            lobby.addNewConnection(tmpSocket);
        }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

}
