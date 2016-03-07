/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

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
    
    
    //non-default constructor
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
        

        //GUI setup/initialization
        output = new JTextArea();
        getContentPane().add(output, BorderLayout.CENTER);
        output.setText( "Waiting for connections...\n");
        setSize( 300, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //initializing games and players arrays
        games = new ArrayList<>();
        players = new ArrayList<>();
    }
    
    
    
    /*****************************************************************************
     *   public void execute()
     *   
     *   Begins listening for client connections. When connection is accepted,
     *   		that connection is stored within a instance of type Player. 
     *   		This player is then added to a list of other Player instances and
     *   		then the thread is started. 
     *****************************************************************************/
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
    
    public List<String> getPluginNames()
    {
        File folder = new File("plugins");
        File[] fileList = folder.listFiles();
        List<String> fileNames = new ArrayList<>();
        
        for (File file : fileList)
        {
            if (file.isFile())
            {
                int i = file.getName().lastIndexOf('.');
                if (file.getName().substring(i+1).equals("jar"));
                    fileNames.add(file.getName());
            }
        }
        return fileNames;
    }
}
