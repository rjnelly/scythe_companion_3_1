package com.example.scythecompanion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<Player>> players = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Faction>> availableFactions = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<PlayerMat>> availableMats = new MutableLiveData<>(new ArrayList<>());

    public PlayerDataViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Player>> getPlayers() {
        return players;
    }

    public MutableLiveData<List<Faction>> getAvailableFactions() {
        return availableFactions;
    }

    public MutableLiveData<List<PlayerMat>> getAvailableMats() {
        return availableMats;
    }

    public void setPlayers(List<Player> players) {
        this.players.setValue(players);
    }

    public void setAvailableFactions(List<Faction> availableFactions) {
        this.availableFactions.setValue(availableFactions);
    }

    public void setAvailableMats(List<PlayerMat> availableMats) {
        this.availableMats.setValue(availableMats);
    }

    public void addPlayer(Player player){
        players.getValue().add(player);
        players.setValue(players.getValue());
    }

    public void removePlayer(int playerPosition){
        players.getValue().remove(playerPosition);
        players.setValue(players.getValue());
    }

    public void changePlayerName(int playerPosition, String name){
        players.getValue().get(playerPosition).setName(name);
        players.setValue(players.getValue());
    }
}
