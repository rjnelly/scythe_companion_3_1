package com.example.scythecompanion;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
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

    public boolean validCombination(Faction faction, PlayerMat playerMat){
        return !(faction == Faction.RUSVIET && playerMat == PlayerMat.INDUSTRIAL ||
                faction == Faction.CRIMEA && playerMat == PlayerMat.PATRIOTIC);
    }

    public void setPlayerFaction(int playerPosition, Faction faction) {
        players.getValue().get(playerPosition).setFaction(faction);
        players.setValue(players.getValue());
    }

    public void setPlayerMat(int playerPosition, PlayerMat playerMat) {
        players.getValue().get(playerPosition).setPlayerMat(playerMat);
        players.setValue(players.getValue());
    }

    public List<PlayerMat> getUnUsedPlayerMats(int playerPosition){
        List<PlayerMat> unusedMats = new ArrayList<>(getAvailableMats().getValue());
        List<Player> players = getPlayers().getValue();
        for (int i = 0; i < players.size(); i++) {
            if (i != playerPosition) unusedMats.remove(players.get(i).getPlayerMat());
        }
        return unusedMats;
    }
    public List<Faction> getUnUsedFactions(int playerPosition) {
        List<Faction> unusedFactions = new ArrayList<>(getAvailableFactions().getValue());
        List<Player> players = getPlayers().getValue();
        for (int i = 0; i < players.size(); i++) {
            if (i != playerPosition) unusedFactions.remove(players.get(i).getFaction());
        }
        return unusedFactions;
    }
    public boolean setRandomFaction(int playerPosition) {
        List<Faction> factions = getUnUsedFactions(playerPosition);
        Collections.shuffle(factions);
        int index = 0;
        boolean valid = false;
        PlayerMat mat = getPlayers().getValue().get(playerPosition).getPlayerMat();
        Faction faction = Faction.NONE;
        while (!valid && index < factions.size()) {
            faction = factions.get(index);
            valid = validCombination(faction, mat);
            index++;
        }
        if (valid) {
            setPlayerFaction(playerPosition, faction);
            return true;
        } else {
            return false;
        }

    }

    public boolean setRandomPlayerMat(int playerPosition) {
        List<PlayerMat> mats = getUnUsedPlayerMats(playerPosition);
        Collections.shuffle(mats);
        int index = 0;
        boolean valid = false;
        Faction faction = getPlayers().getValue().get(playerPosition).getFaction();
        PlayerMat mat = PlayerMat.NONE;
        while (!valid && index < mats.size()) {
            mat = mats.get(index);
            valid = validCombination(faction, mat);
            index++;
        }
        if (valid) {
            setPlayerMat(playerPosition, mat);
            return true;
        } else {
            return false;
        }
    }

    public boolean setUpNewGame() {
        List<Player> playerList = players.getValue();
        List<Faction> factionList = new ArrayList<>(getAvailableFactions().getValue());
        List<PlayerMat> playerMatList = new ArrayList<>(getAvailableMats().getValue());
        Collections.shuffle(factionList);
        Collections.shuffle(playerMatList);
        if(playerList.size() <= factionList.size() && playerList.size() <= playerMatList.size()) {
            for(int i = 0; i < playerList.size(); i++){
                Faction faction = factionList.remove(0);
                PlayerMat mat = playerMatList.remove(0);
                setPlayerFaction(i, faction);
                setPlayerMat(i, mat);
            }
            for(int i = 0; i < playerList.size(); i++){
                if(!validCombination(playerList.get(i).getFaction(), playerList.get(i).getPlayerMat())){
                    if(playerMatList.isEmpty()){
                        setUpNewGame();
                    }else{
                        setPlayerMat(i,playerMatList.remove(0));
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
