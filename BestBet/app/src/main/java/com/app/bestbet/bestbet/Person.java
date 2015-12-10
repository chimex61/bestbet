package com.app.bestbet.bestbet;

/**
 * Created by mcouture-cc on 12/4/2015.
 */
public class Person
{
    private int id;
    private String name;
    private int wins;
    private int losses;
    private int gains;

    public Person(){}

    public Person(int id, String name, int wins, int losses, int gains){
        this.setId(id);
        this.setName(name);
        this.setWins(wins);
        this.setLosses(losses);
        this.setGains(gains);
    }

    public Person(String name) {
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGains() {
        return gains;
    }

    public void setGains(int gains) {
        this.gains = gains;
    }


    //Overrides the toString method, used for displaying the players in the leaderboard correctly
    @Override
    public String toString() {
        return this.name + "\nWins: " + this.wins + " Losses: " + this.losses + " Net Gain: $" + this.gains;
    }
}
