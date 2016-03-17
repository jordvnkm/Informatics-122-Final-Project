package Server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lobby
{

    private final List<Player> players;	//stores all of the players

    private final List<Player> playerSelectingGame; //players currently selecting a game

    private final List<Game> fullGames; //games that already have the max 
    //	number of players and are being played
    private final List<Game> openGames; //games that are awaiting n numbers of players
    //	to join so they can be played

    public Lobby()
    {
        //initializes all of the lists
        players = new ArrayList<>();
        playerSelectingGame = new ArrayList<>();
        
        fullGames = new ArrayList<>();
        openGames = new ArrayList<>();
    }
    
    public void broadcastLists(){
        for(Player p:players)
        	p.sendLists();
    }
    
    public void addNewConnection(Socket socket)
    {
        Player tmp = new Player(socket, this);

        //starts player thread
        tmp.start();

        players.add(tmp);
        

    }

    /**
     * ****************************************************************
     * public String getGameList()
     *
     *
     * This method compiles a list of the plugins that are currently running on
     * the server. 
     ******************************************************************
     */
    public String getGameList()
    {
        List<String> tmpList = Game.getGameList();
        //creates and returns the JSON string from the list of open games
        return JSONServerTranslator.gameList(tmpList, false);
    }

    /**
     * ****************************************************************
     * public String getOpenGameList()
     *
     *
     * This method compiles a list of games that are awaiting more players to
     * begin. It just returns the list of "openGames"
     ******************************************************************
     */
    public String getOpenGameList()
    {
        List<String> tmpList = new ArrayList<>();
        //compiles a string list of games open
        for (Game g : openGames)
        {
            tmpList.add(g.getPluginName());
        }
        //creates and returns the JSON string from the list of open games
        return JSONServerTranslator.gameList(tmpList, true);
    }

    /**
     * ****************************************************************
     * public void joinLobby(Player p)
     *
     *
     * This method pushes the player thread into the lobby. Upon joining the
     * lobby, the lobby will send the player a list of the available plugins to
     * play along with a current list of the open games
     *
     * @param p
     ******************************************************************
     */
    public void joinLobby(Player p)
    {
        //adds the player to the lobby 
        playerSelectingGame.add(p);
    }

    /**
     * ****************************************************************
     * public Game selectGame(Player p, String gameName)
     *
     *
     * This method takes in the Players game selection. When the selected game
     * is passed into the method, it first checks the list of players waiting in
     * lobbies to see if it can match the new player with one already waiting.
     * If there is no one waiting to play the same game, a new game is created.
     *
     * After either creating a new game or adding a player to a game, a message
     * is sent out to all players currently selecting a game which will update
     * the open available lobbies.
     *
     * Players in the "playerSelectingGame" list are removed.
     ******************************************************************
     */
    public Game selectGame(Player p, String gameName)
    {
        boolean removed = false;
        boolean gameFound = false;

        Game tmpGame = lookForOpenGames(p, gameName);

        //if there are no open games, it creates a new game to wait for people.
        //	we check for open games again incase it is a single player game
        if (tmpGame == null)
        {
            tmpGame = new Game(p, gameName);
            openGames.add(tmpGame);
            //this method will pop off the open game if it is a
            //		single player game
            //lookForOpenGames(p, gameName);
        }
        else
            tmpGame.addPlayer(p);

        //removes player from playerSelectingGame list
        for (int i = 0; i < playerSelectingGame.size() && !removed; i++)
        {
            if (playerSelectingGame.get(i) == p)
            {
                playerSelectingGame.remove(i);
                removed = true;
            }
        }

        //updates players waiting in the lobby.
        //	(the player p is already removed from the list so it will never
        //		find a case where p == playerSelectingGame)
        passesMessage(p, getOpenGameList());
        return tmpGame;
    }

    /**
     * ****************************************************************
     * public Game lookForOpenGames(Player p, String gameName)
     *
     *
     * This method looks through all of the open games to try and find a
     * matching game to fill it. If the game is full the game is removed from
     * the open list and placed in the full list. If a open game cannot be found
     * it returns null
     ******************************************************************
     */
    public Game lookForOpenGames(Player p, String gameName)
    {
        //using a for-loop because we will need to remove the game
        for (Game game : openGames)
        {

            //if there is an open game awaiting players that already exists
            if (game.getPluginName().equals(gameName))
            {
                game.addPlayer(p);

                //handles games that require N number of player
                if (game.getMaxPlayers() == game.getCurrentNumPlayers())
                {
                    openGames.remove(game);
                    fullGames.add(game);
                }
                return game;
            }
        }
        return null;
    }

    /**
     * ****************************************************************
     * public void passesMessage(Player p, String s)
     *
     *
     * This method is intended to be used by the selectGame method. It will take
     * a string message and Player type. The method will cycle through all of
     * the players in the "playerSelectingGame" list and update their open game
     * types with the list of "openGames".
     ******************************************************************
     */
    public void passesMessage(Player p, String s)
    {
        for (Player player : playerSelectingGame)
        {
            if (player != p)
            {
                player.sendMessage(s);
            }
        }

    }

    public void removeGameFromFullList(Game game)
    {
        if (fullGames.contains(game))
        {
            fullGames.remove(game);
        }
    }

    public void removeGameFromOpenList(Game game)
    {
        if (fullGames.contains(game))
        {
            fullGames.remove(game);
        }
    }
    
    public void setGameAsFull(Game game)
    {
        if (openGames.contains(game))
        {
            openGames.remove(game);
            fullGames.add(game);
        }
    }
    
    public String getPlayers()
    {
        return JSONServerTranslator.playerList(getPlayerList());
    }
    
    public ArrayList<String> getPlayerList()
    {
        ArrayList<String> retList = new ArrayList<>();

        players.stream().forEach((player) ->
        {
            retList.add(player.getPlayerName());
        });
        return retList;
    }
//    
//    public List<Player> getAllPlayers()
//    {
//        return players;
//    }
//    
//    public List<Game> getOpenGames()
//    {
//        List<Game> retList = new ArrayList<>();
//        games.stream().filter((game) -> (game.getMaxPlayers() > game.getCurrentNumPlayers())).forEach((game) ->
//        {
//            retList.add(game);
//        });
//        
//        return retList;
//    }
//    
//    public void addGame(Game game)
//    {
//        games.add(game);
//    }
//    
//    public List<String> getPluginNames()
//    {
//        File folder = new File("plugins");
//        File[] fileList = folder.listFiles();
//        List<String> fileNames = new ArrayList<>();
//        
//        for (File file : fileList)
//        {
//            if (file.isFile())
//            {
//                int i = file.getName().lastIndexOf('.');
//                if (file.getName().substring(i+1).equals("jar"));
//                    fileNames.add(file.getName());
//            }
//        }
//        return fileNames;
//    }
}
