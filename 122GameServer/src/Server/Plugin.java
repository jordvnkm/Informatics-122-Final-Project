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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author malar
 */
public class Plugin 
{
 
    private final String pluginsDir = "plugins";

    URLClassLoader cl;
    Class<?> pluginClass;
    Object instance;
    
    
    //non-default constructor that finds the correct plugin to load 
    public Plugin (String fileName)
    {
        try
        {
            File file = new File(pluginsDir + "/" + fileName + ".jar");
            URL url = file.toURI().toURL();
            cl = new URLClassLoader(new URL[] { url });
            pluginClass = cl.loadClass("plugin.Plugin");
            Constructor<?> ctor=pluginClass.getConstructor(String[].class);
            instance = ctor.newInstance((Object) new String[]{"test1", "test2"});
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
     * Since we are unable to implement concrete classes with URLClassLoader (could use CGLib), we must not
     * make Plugin abstract. To ensure all game plugins have the correct methods implemented we will check
     * here to make sure they exist. We are essentially simulating a abstract class with this. 
     */
    private boolean validPlugin()
    {
    	return true;
    }
    
    //**************************
    //plugin interaction methods
    //**************************
    
    public void initializeGame(List<Player> players)
    {
    	
    }

    
    
    
    public boolean makeMove(int x, int y, String player)
    {
        try
        { 
            Method m = pluginClass.getMethod("playMove", new Class[] { int.class, int.class, String.class }  );
            if (m != null)
                return (boolean)m.invoke(instance, new Object[] { x, y, player });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkForGameOver()
    {
        try
        { 
            Method m = pluginClass.getMethod("checkForGameOver", new Class[] { }  );
            if (m != null)
                return (boolean)m.invoke(instance, new Object[] { });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getWinner()
    {
        try
        { 
            Method m = pluginClass.getMethod("getWinner", new Class[] { }  );
            if (m != null)
                return (String)m.invoke(instance, new Object[] { });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String currentPlayer()
    {
        try
        { 
            Method m = pluginClass.getMethod("currentPlayer", new Class[] { }  );
            if (m != null)
                return (String)m.invoke(instance, new Object[] { });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }    

    public String setUpBoard()
    {
        try
        { 
            Method m = pluginClass.getMethod("setUpBoard", new Class[] { }  );
            if (m != null)
                return (String)m.invoke(instance, new Object[] { });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }    
    
    public String getBoard()
    {
        throw new UnsupportedOperationException();
    }
    
}
