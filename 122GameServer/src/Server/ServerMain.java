/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
/**
 *
 * @author malar
 */
public class ServerMain
{

    private static final int PORT = 8000;
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        Server server = new Server(PORT); // This theoretically will us to have multiple
                                          // virtual servers running on one machine
                                          // each with its own player/game list
//        server.execute();

// This is a way to load plugins from jar files
//    Plugin plugin = new Plugin("plugin");

//    Profile profile = new Profile("Jason");
//    profile.addWin("TicTacToe");
                
    }
    
}
