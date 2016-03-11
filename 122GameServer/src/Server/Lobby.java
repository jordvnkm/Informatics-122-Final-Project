package Server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Lobby 
{
    private List<Player> players;
    private List<Game> games;
    
    public Lobby()
    {
    	players = new ArrayList<Player>();
    	games = new ArrayList<Game>();
    }
    
    public void addNewConnection(Socket socket)
    {
    	Player tmp = new Player(socket, this);
    	tmp.start();
     	players.add(tmp);
    }
    
    
    /*
     * This won't work for the non-demo, player is on a totally separate thread
     * And just calling selectGame and passing the player doesn't enable us to
     * do anything with the listener, the listener has to handle EVERYTHING
     * WITHIN PLAYER and then call functions based on what it receives.
     */
    public Game selectGame(Player p)
    {
        Game game;
    	//***FOr the demo they will only play tic-tac-toe
    	if (games.isEmpty())
        {
            game = new Game(p, "TicTacToe");
            games.add(game);
        }
        else
        {
            game = games.get(0);
            game.addPlayer(p);
            game.startGame();
        }
    	
    	return game;
    }
    
    
    public void passesMessage(Player p, String s)
    {
    	for(Player player : players)
    	{
    		if(player != p && player != players.get(0))
    		{
    			player.sendMessage(s);
    		}
    	}
    	
    }
    
//    public List<Player> getInactivePlayers()
//    {
//        List<Player> retList = new ArrayList<>();
//        players.stream().filter((player) -> (player.getGame() == null)).forEach((player) ->
//        {
//            retList.add(player);
//        });
//        
//        return retList;
//    }
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
