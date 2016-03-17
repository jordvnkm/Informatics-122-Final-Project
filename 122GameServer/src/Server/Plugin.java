/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author malar
 */
public class Plugin {

	private final static String pluginsDir = "plugins";

	URLClassLoader cl;
	Class<?> pluginClass;
	Object instance;
	boolean isValidPlugin;
	
	/**
	 * Constructor: takes in a file name, checks to make sure the plugin is valid, if so
	 * we load the plugin.
	 * @param fileName the name of the file to load (without .jar)
	 */
	public Plugin(String fileName) {
			loadPlugin(fileName); // This is to reset the plugin to pristine
	}
	
	/**
	 * Returns whether the plugin is valid or not
	 * @return isValidPlugin
	 */
	public boolean isValidPlugin()
	{
		return isValidPlugin;
	}

	/**
	 * Load the plugin into memory.
	 * @param fileName the file to load (filename without extension)
	 */
	public void loadPlugin(String fileName) {
		try {
			File file = new File(pluginsDir + "/" + fileName + ".jar");
			URL url = file.toURI().toURL();
			cl = new URLClassLoader(new URL[] { url });
			pluginClass = cl.loadClass("plugin." + fileName);
		} catch (MalformedURLException | ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
			Logger.getLogger(Plugin.class.getName()).log(Level.WARNING, null, ex);
		}
	}

	/**
	 * Check to make sure the plugin implements the playMove method, which is how we interface with the plugin.
	 * @param fileName the file to load (without extension)
	 * @return false if the plugin isn't valid, otherwise true
	 */
	private boolean validatePlugin(String fileName) {
		loadPlugin(fileName);
		initializeGame(new String[] { "test1", "test2" });

		try {
			// Test for playMove
			Method m = pluginClass.getMethod("playMove", new Class[] { int.class, int.class, String.class });
			m.invoke(instance, new Object[] { 0, 0, "test1" });


			// Test for Button Pressed
			m = pluginClass.getMethod("buttonPressed", new Class[] { String.class, String.class });
			m.invoke(instance, new Object[] { "test1", "test1" });

			// Test for 
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return isValidPlugin = false;
		}
		return isValidPlugin = true;
	}


	/**
	 * Initializes the plugin, calling its constructor and passing in a list of players.
	 * @param players an array of player names
	 */
	public void initializeGame(String[] players) {
		Constructor<?> ctor;
		try {
			ctor = pluginClass.getConstructor(String[].class);
			instance = ctor.newInstance(new Object[] { players });
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
	}

	/**
	 * Send a move to the plugin.
	 * @param x the x coordinate to send a move to
	 * @param y the y coordinate to send a move to
	 * @param player the player making the move
	 * @return true if valid move, otherwise false
	 */
	public boolean makeMove(int x, int y, String player) {
		try {
			Method m = pluginClass.getMethod("playMove", new Class[] { int.class, int.class, String.class });
			if (m != null)
				return (boolean) m.invoke(instance, new Object[] { x, y, player });
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	/**
	 * Send a buttonpress to the plugin.
	 * @param button the name of the button that was pressed
	 * @param player the player making the move
	 * @return true if valid move, otherwise false
	 */
	public boolean buttonPressed(String button, String player) {
		try {
			Method m = pluginClass.getMethod("buttonPressed", new Class[] { String.class, String.class });
			if (m != null)
				return (boolean) m.invoke(instance, new Object[] { button, player });
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	
	/**
	 * gets the gamestate from the plugin, in json string format
	 * @return the string as returned from the plugin
	 */
	public String getBoard() {
		try {
			Method m;
			m = pluginClass.getMethod("getGameState", new Class[] {});
			if (m != null)
				return (String) m.invoke(instance, new Object[] {});		
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return "Error";
	}
        
    public static List<String> getGameList()
    {
    	Plugin test;
        File folder = new File(pluginsDir);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> pluginList = new ArrayList<>();
        for (File file : listOfFiles) 
        {
            if (file.isFile() && file.getName().endsWith(".jar")) 
            {
//            	String fileName = file.getName().split("\\.")[0];
//            	test = new Plugin(fileName);
//            	if (validatePlugin(fileName))
            		pluginList.add(file.getName().split("\\.")[0]);
            }
        }
        return pluginList;
    }
}
