
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
    Board board;
    Plugin logic;
    
    public Game(Player player, String plugin)
    {
        players = new LinkedList<>();
        addPlayer(player);
    }
    public synchronized boolean legalMove(int x, int y, Player player) {
    /*    if (player == currentPlayer && board[location] == null) {
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        } */
        return true;
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
    }
    
    public boolean move()
    {
        return false;
    }
}
