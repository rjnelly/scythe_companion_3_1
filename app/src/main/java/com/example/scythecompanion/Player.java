package com.example.scythecompanion;

public class Player {

    private String name;
    private Faction faction;
    private PlayerMat playerMat;

    public Player() {
    }

    public Player(String name){
        this.name = name;
        faction = Faction.NONE;
        playerMat = PlayerMat.NONE;
    }

    public Player(String name, Faction faction, PlayerMat playerMat) {
        this.name = name;
        this.faction = faction;
        this.playerMat = playerMat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public PlayerMat getPlayerMat() {
        return playerMat;
    }

    public void setPlayerMat(PlayerMat playerMat) {
        this.playerMat = playerMat;
    }






}
