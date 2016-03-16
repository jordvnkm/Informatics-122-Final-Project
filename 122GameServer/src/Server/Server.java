/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingUtilities;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author malar
 */
public class Server extends Thread
{

	private ServerSocket server;
	private boolean runServer;

	private Lobby lobby;
	private ServerController controller;

	private boolean consoleDebug = true;

	// non-default constructor
	public Server(ServerController controller)
	{
		this.controller = controller;

		// instantiates our lobby for the connections
		lobby = new Lobby();
	}

	/**
	 * *************************************************************************
	 * ** public void execute()
	 *
	 * Begins listening for client connections. When connection is accepted, the
	 * player socket is sent to the Lobby
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 ****************************************************************************
	 */
	public void startServer(int portNum) throws IOException
	{
		server = new ServerSocket(portNum);
		runServer = true;

	}

	public void stopServer()
	{
		try
		{
			runServer = false;
			server.close();
			Socket socket = new Socket("127.0.0.1", 8001);
		}

		catch (IOException e)
		{
			if (consoleDebug)
			{
				e.printStackTrace();
				System.out.println("handling closing the server connection to stop the server in the stopServer()");
			}
		}

	}

	private void displayConnection(String ip, int port)
	{
		if (!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					// displayConnection(ip, port);
					controller.GUINewConnection(ip, port);

				}
			});
		}
	}

	@Override
	public void run()
	{
		Socket tmpSocket;

		while (runServer)
		{
			try
			{
				// waits for the connection
				tmpSocket = server.accept();

				// adds player to lobby
				// lobby.addNewConnection(tmpSocket);
				displayConnection(tmpSocket.getRemoteSocketAddress().toString(), tmpSocket.getPort());

			}

			catch (IOException e)
			{

				if (runServer)
				{
					e.printStackTrace();
					System.out.println("handling in the run() method of the server class (IO)");
				}
			}
		}
	}
}
