package com.example.baybay;

public class Gameplay {
    private String gameplay;

    public Gameplay(String username){
        this.gameplay = username;
    }

    public String getGameplay(){
        return gameplay;
    }

    public void setGameplay(String gameplay){
        this.gameplay = gameplay;
    }
}
