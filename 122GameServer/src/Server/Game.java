
package Server;

//***
//importing the GamePlugins package to access board
import GamePlugins.*;
//***

import java.util.LinkedList;
import java.util.List;

/**
 *  This class will initialize a game for players
 * The player who creates the game will determine
 * which game it actually is.
 *
 */
public class Game
{
    Player currentPlayer;
    int maxPlayers = 2;  // This is so we can have games with more players later
    List<Player> players;
    Plugin logic;
    String pluginName;
    
    public Game(Player player, String plugin)
    {
        players = new LinkedList<>();
        addPlayer(player);
        pluginName = plugin;
    }
    
    public synchronized boolean makeMove(int x, int y, String player) {
        String winner = "";
        boolean goodMove = false;
        if ((goodMove = logic.makeMove(x, y, player)) == true)
        {
            if (logic.checkForGameOver())
            {
                winner = logic.getWinner();
            }
            
        }
        return goodMove;
    }
    
    public synchronized boolean checkForGameOver()
    {
        return logic.checkForGameOver();
    }
    
    public synchronized String getBoard()
    {
        return logic.getBoard();
    }
    
    public synchronized String getWinner()
    {
        return logic.getWinner();
    }
    
    public int getCurrentNumPlayers()
    {
        return players.size();
    }
    
    public int getMaxPlayers()
    {
        return maxPlayers;
    }
    
    public final void addPlayer(Player player)
    {
        players.add(player);
        if (players.size() == maxPlayers)
        {
            // Todo: We need to start the game here
            logic = new Plugin(pluginName);
        }
    }
}
