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
    	
    }
    
    public void addNewConnection(Socket socket)
    {
     	//Player player = new Player(server.accept(), this, "Test");
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
