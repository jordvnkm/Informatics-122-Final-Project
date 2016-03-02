/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author malar
 */
public class Server extends JFrame
{
    private List<Game> games;
    private boolean xMove;
    private final JTextArea output;
    private List<Player> players;
    private ServerSocket server;
    private int currentPlayer;
    
    public Server(int port)
    {
        super( "Server" );
        
        try 
        {
            server = new ServerSocket(port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        
        output = new JTextArea();
        getContentPane().add(output, BorderLayout.CENTER);
        output.setText( "Waiting for connections...\n");
        setSize( 300, 300);
        setVisible(true);
        games = new ArrayList<>();
        players = new ArrayList<>();
    }
    
    /**
     * Wait for two connections, then we can hand off control to Game(?)
     */
    public void execute()
    {
        while (true)
        {
            try
            {
               Player player = new Player(server.accept(), this, "Test");
               output.append("Player " + player.getName() + " has connected\n");
               players.add(player);
               player.start();
            }
            catch( IOException e)
            {
                
            }
        }
    }
    
    public List<Player> getInactivePlayers()
    {
        List<Player> retList = new ArrayList<>();
        players.stream().filter((player) -> (player.getGame() == null)).forEach((player) ->
        {
            retList.add(player);
        });
        
        return retList;
    }
    
    public List<Player> getAllPlayers()
    {
        return players;
    }
    
    public List<Game> getOpenGames()
    {
        List<Game> retList = new ArrayList<>();
        games.stream().filter((game) -> (game.getMaxPlayers() > game.getCurrentNumPlayers())).forEach((game) ->
        {
            retList.add(game);
        });
        
        return retList;
    }
    
    public void addGame(Game game)
    {
        games.add(game);
    }
}
