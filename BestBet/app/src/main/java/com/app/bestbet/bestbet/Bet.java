package com.app.bestbet.bestbet;

import java.util.Date;

/**
 * Created by mcouture-cc on 12/4/2015.
 */
public class Bet
{
    private int id;
    private int personId;
    private String description;
    private int amount;
    private String date;
    private int completed;
    private int won;

    public Bet(){}

    public Bet(int id, int personId, String description, int amount, String date, int completed, int won)
    {
        this.setId(id);
        this.setPersonId(personId);
        this.setDescription(description);
        this.setAmount(amount);
        this.setDate(date);
        this.setCompleted(completed);
        this.setWon(won);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public String toString() {
        return this.getPersonId() + this.getDescription();
    }

}
