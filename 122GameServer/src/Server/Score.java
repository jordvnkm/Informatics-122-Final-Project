/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author ronw
 */
public class Score {
    private int wins;
    private int losses;

    public Score(int wins, int losses)
    {
        this.wins = wins;
        this.losses = losses;
    }
    
    public Score()
    {
        this(0,0);
    }
    
    public int incrementWins()
    {
        return ++this.wins;
    }

    public int getWins() {
        return this.wins;
    }

    public int incrementLosses() {
        return ++this.losses;
    }

    public int getLosses() {
        return this.losses;
    }
    
    
}
