package Client;

import java.util.*;

public class GameData {
	
	private ArrayList<String> games;
	private ArrayList<String> players;
	private ArrayList<String> playersWaiting;
	
	public GameData()
	{
		games = new ArrayList<String>();
		players = new ArrayList<String>();
		playersWaiting = new ArrayList<String>();
	}
	
	public void addGame(String gameName)
	{
		games.add(gameName);
	}
	
	public void addWaiting(String player)
	{
		playersWaiting.add(player);
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
