
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
    
    /**
     * This is just a temporary constructor
     * @param plugin 
     */
    public Game(String plugin)
    {
        logic = new Plugin("TicTacToe");
        logic.initializeGame(null);

        System.out.println(logic.currentPlayer() + " moved to 0,1");
        logic.makeMove(0, 1, "test1");
        System.out.println(logic.currentPlayer() + " moved to 0,0");
        logic.makeMove(0, 0, "test2");
        System.out.println(logic.currentPlayer() + " moved to 1,0");
        logic.makeMove(1, 0, "test1");
        System.out.println(logic.currentPlayer() + " moved to 0,2");
        logic.makeMove(0, 2, "test2");
        System.out.println(logic.currentPlayer() + " moved to 1,1");
        logic.makeMove(1, 1, "test1");
        System.out.println(logic.currentPlayer() + " moved to 1,2");
        logic.makeMove(1, 2, "test2");
        System.out.println(logic.currentPlayer() + " moved to 2,0");
        logic.makeMove(2, 0, "test1");
        System.out.println(logic.currentPlayer() + " moved to 2,1");
        logic.makeMove(2, 1, "test2");
        System.out.println(logic.currentPlayer() + " moved to 2,2");
        logic.makeMove(2, 2, "test1");
        if (logic.checkForGameOver() == true)
        {
            System.out.println("Game over: " + logic.getWinner());
        }
    }
    
    public Game(Player player, String plugin)
    {
        players = new LinkedList<>();
        addPlayer(player);
        pluginName = plugin;
    }
    
    public synchronized boolean makeMove(int x, int y, String player) {
        String winner = "";
        boolean goodMove;
        
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
    
    public final synchronized void addPlayer(Player player)
    {
        players.add(player);
        if (players.size() == maxPlayers)
        {
            // Todo: We need to start the game here
            logic = new Plugin(pluginName);
            startGame();
        }
    }
    
    public final synchronized void announceWinners()
    {
        for (Player player : players)
        {
            player.sendMessage("PUT IN WINNER JSON HERE");
        }
        // Possibly handle logic for leaving games here.
    }
    
    public final synchronized void startGame()
    {
        // TODO: get game state via getBoard, send gamestate to everyone in the 'players' list
    }
}
