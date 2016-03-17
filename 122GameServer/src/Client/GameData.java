package Client;

import java.util.*;

public class GameData {
	
	private ArrayList<String> games;
	private ArrayList<String> players;
	
	
	public GameData()
	{
		games = new ArrayList<String>();
		players = new ArrayList<String>();
	}
	
	public void addGame(String gameName)
	{
		games.add(gameName);
	}
	
	public void addPlayer(String playerName)
	{
		players.add(playerName);
	}
	public ArrayList<String> getGames(){
		return games;
	}
	
	public void clearGameData(){
		games.clear();
	}
	
	public void clearPlayerData(){
		players.clear();
	}
	public ArrayList<String> getPlayers(){
		return players;
	}
}
