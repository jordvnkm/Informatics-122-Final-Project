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
public class Main
{

    private static final int PORT = 8080;
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        Server server = new Server(PORT); // This theoretically will us to have multiple
                                          // virtual servers running on one machine
                                          // each with its own player/game list
        server.execute();
    }
    
}
