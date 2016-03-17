/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * needed methods
 * 
 * 	-profileExists()
 *  -createNewProfile() 
 *  - just a normal constructor with no parameters
 *  -
 */
public class Profile {
    private String userName;  // The user whose file will be accessed
    private String description;     // Descrption of the user
    private String profileFile; // Filename to access
    private Map<String, Score> scores; // A collection of the users wins/losses
    
    
    /**
     * Constructor: Creates an Profile object for the user 
     * @param name name of the user to create the object for.
     */
    public Profile(String name)
    {
        this.userName = name;
        this.profileFile = "Profiles/" + this.userName + ".profile";
        scores = new HashMap<>();
        
        // Check to see if the file exists, if not, initialize it
        initFile();
        
        // Load the file
        loadFile();
    }
    
    
    
    /*
     * Constructor: No parameters for this profile
     */
    public Profile(){
    	this.profileFile = "Profiles/";
    	this.scores = new HashMap<>();
    }
    
    
    /*
     * Checks to see if the file exists
     * @param name - name to check for in directory
     */
    public boolean profileExists(String name)
    {
        File file = new File(profileFile + name + ".profile");
        
        // If the file doesn't exist, we'll create it with some initial values
        if (file.exists())
        	return true;
        
        return false;
    }
    
    
    /*
     * Will create a new file containing the user's name
     * @param name - The name of the user
     */
    public void createNewProfile(String name){
    	this.setProfileFile(name);
    	this.initFile();
    	this.loadFile();
    }
    
    
    
    /**
     * Sets the user's personal description
     * @param description 
     */
    public void SetDescription(String description)
    {
        this.description = description;
        saveFile();
    }
    
    /**
     * Loads the profile file, based on the user name
     */
    public final void loadFile()
    {
        try {
            String currentLine;
            String gameName;
            String values[];
            int wins;
            int losses;
            
            // Zero out the user's description and score, as it will be read
            // from the file
            this.description = "";
            this.scores = new HashMap<>();
            
            // If, for some reason, the profile file was deleted, we'll reinitialize
            // it here, this is just for error handling.
            if (profileFile.length() == 0)
            {
                initFile();
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(profileFile))) 
            {
                // Load the user description
                while (!(currentLine = reader.readLine()).equals("~"))
                {
                    this.description += currentLine;
                }
                
                // Load the user's scores
                while((currentLine = reader.readLine()) != null)
                {
                    values = currentLine.split(" ");
                    gameName = values[0];
                    wins = Integer.valueOf(values[1]);
                    losses = Integer.valueOf(values[2]);
                    scores.put(gameName, new Score(wins, losses));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    /**
     * Saves the user's profile to file
     */
    public void saveFile()
    {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(profileFile, false)))) 
        {
            out.println(this.description);
            out.println("~");
            scores.entrySet().stream().forEach((pair) -> {
                out.println(pair.getKey() + " " + pair.getValue().getWins()+ " " + pair.getValue().getLosses());
            });
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Checks to see if the user's profile file exists, if it doesn't the file
     * is created and saved for the first time.
     */
    public final void initFile()
    {
        File file = new File(profileFile);
        
        // If the file doesn't exist, we'll create it with some initial values
        if (!file.exists())
        {
            try {
                file.createNewFile();
                if (description == null)
                    this.description = "User has not created a description";
                
                saveFile();
            } catch (IOException ex) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Increment a game win by 1 point
     * @param game the game the user just won
     */
    public final void addWin(String game)
    {
        Score score = scores.get(game);
        if (score == null)
        {
            score = new Score();
        }
        score.incrementWins();
        scores.put(game, score);
        saveFile();
    }
    
    /**
     * Increment a game loss by 1 point
     * @param game the game the user just lost
     */
    public final void addLoss(String game)
    {
        Score score = scores.get(game);
        
        if (score == null)
        {
            score = new Score();
        }
        score.incrementLosses();
        scores.put(game, score);
        saveFile();
    }
    
    public final String getName()
    {
        return this.userName;
    }
    
    public final void setName(String name)
    {
    	this.userName = name;
    }
    
    public final void setProfileFile(String name){
    	this.profileFile = "Profiles/" + name + ".profile";
    }
}
