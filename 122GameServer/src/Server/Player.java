/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author malar
 */
public class Player extends Thread
{
    private final Socket connection;
    private BufferedReader input;
    private DataOutputStream output;
    private final Server controller;
    private char mark;
    private Game game;
    protected boolean threadSuspended = true;
    
    public Player(Socket socket, Server server, String name)
    {
        this.connection = socket;
        this.controller = server;
        setName(name);
        
        try {
            input = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            output = new DataOutputStream(connection.getOutputStream());
        }
        catch (IOException ex)
        {
            System.exit( -1 );
        }
        
    }
    
    @Override
    public void run()
    {
        try
        {
            String playerList = new String();
            playerList = controller.getAllPlayers().stream().map((player) -> "\n" + player.getName()).reduce(playerList, String::concat);
            
            output.writeUTF(playerList);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public void createGame(String plugin)
    {
        if (game == null)
        {
            game = new Game(this, plugin);
        }
    }
}
