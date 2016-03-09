/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author malar
 */
public class ServerMain
{
    public static void main(String[] args) throws IOException
    {
    	ArrayList<Server> serverList = new ArrayList<Server>();
    	
        serverList.add(new Server()); // This theoretically will us to have multiple
                                          // virtual servers running on one machine
                                          // each with its own player/game list
    }
}
