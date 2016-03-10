package Server;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Lobby 
{
    private List<Player> players;
    private List<Game> games;
    
    private List<Player> waitingForTicTacToe;

    
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
     * 
     * 
     */
    public void selectGame(Player p)
    {
    	//***FOr the demo they will only play tic-tac-toe
    	
    	if(waitingForTicTacToe.isEmpty())
    		waitingForTicTacToe.add(p);
//    	else
//    		games.add(new Game(waitingForTicTacToe.get(0), p));
    	
    		
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
