package Server;

//***
//importing the GamePlugins package to access board

//***

import java.util.LinkedList;
import java.util.List;

/**
 * This class will initialize a game for players The player who creates the game
 * will determine which game it actually is.
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
     *
     * @param plugin
     */
    public Game(String plugin)
    {
        logic = new Plugin("TicTacToe");
        logic.initializeGame(new String[] {"test1", "test2"});
        System.out.println(getBoard());
        System.out.println("test1  moved to 0,1");
        logic.makeMove(0, 1, "test1");
        System.out.println("test2 moved to 0,0");
        logic.makeMove(0, 0, "test2");
        System.out.println("test1 moved to 1,0");
        logic.makeMove(1, 0, "test1");
        System.out.println("test 2 moved to 0,2");
        logic.makeMove(0, 2, "test2");
        System.out.println("test 1 moved to 1,1");
        logic.makeMove(1, 1, "test1");
        System.out.println("test 2 moved to 1,2");
        logic.makeMove(1, 2, "test2");
        System.out.println("test 1 moved to 2,0");
        logic.makeMove(2, 0, "test1");
        System.out.println("test 2 moved to 2,1");
        logic.makeMove(2, 1, "test2");
        System.out.println("test1 moved to 2,2");
        logic.makeMove(2, 2, "test1");
/*        
        System.out.println(logic.makeMove(0, 0, "Test1"));
        logic.makeMove(1, 0, "Test2");
        logic.makeMove(0, 1, "Test1");
        logic.makeMove(1, 1, "Test2");
        logic.makeMove(0, 2, "Test1");
*/        
        System.out.println(logic.getBoard());
    }

    /**
     * main constructor, creates a game object, adds the player to the game
     * stores 
     * @param player
     * @param plugin
     */
    public Game(Player player, String plugin)
    {
        players = new LinkedList<>();
        addPlayer(player);
        pluginName = plugin;
    }

    public synchronized void makeMove(int x, int y, String player)
    {
        String winner = "";
        boolean goodMove;

        if (logic.makeMove(x, y, player))
        {
            messagePlayers(getBoard());
        }
    }
    
    public synchronized void buttonPressed(String button, String player)
    {
    	if (logic.buttonPressed(button, player))
    	{
    		messagePlayers(getBoard());
    	}
    }

    public synchronized String getBoard()
    {
        return logic.getBoard();
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
        	startGame();
        }
    }
    
    public final synchronized void messagePlayers(String message)
    {
        players.stream().forEach((player) -> {
            player.sendMessage(message);
        });
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
        // Todo: We need to start the game here
        logic = new Plugin(pluginName);
//        logic.initializeGame((String[])players.toArray());
        
    	// TODO: get game state via getBoard, send gamestate to everyone in the 'players' list
        for (Player player : players)
        {
            player.sendMessage(getBoard());
        }    
    }
    
    public final String getPluginName()
    {
        return this.pluginName;
    }
    
    public final static List<String> getGameList()
    {
        return Plugin.getGameList();
    }
}
