/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author malar
 */
public class Plugin implements IPlugin
{
 
    private final String pluginsDir = "plugins";

    URLClassLoader cl;
    Class pluginClass;
    Object instance;
    public Plugin (String fileName)
    {
        try
        {
            File file = new File(pluginsDir + "/" + fileName + ".jar");
            URL url = file.toURI().toURL();
            cl = new URLClassLoader(new URL[] { url });
            pluginClass = cl.loadClass("plugin.Plugin");
            instance = pluginClass.newInstance();
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean move(int dir)
    {
        try
        { 
            Method m = pluginClass.getMethod("movez", new Class[] { int.class }  );
            if (m != null)
                return (boolean)m.invoke(instance, new Object[] { dir });
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
