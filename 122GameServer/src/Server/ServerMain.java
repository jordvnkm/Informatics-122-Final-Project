/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.util.ArrayList;


public class ServerMain
{
    static ArrayList<Server> serverList; 
    
    public static void main(String[] args) throws IOException
    {
    	if (serverList == null) 
            serverList = new ArrayList<>();
    	
        serverList.add(new Server()); // This theoretically will us to have multiple
                                          // virtual servers running on one machine
                                          // each with its own player/game list
    }
}
