package com.example.scythecompanion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerDataViewModel extends AndroidViewModel {


    private MutableLiveData<List<StructureBonus>> structureBonuses = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<StructureBonus> structureBonus = new MutableLiveData<>();
    private MutableLiveData<Boolean> structureBonusVisible = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> campaingModeOn = new MutableLiveData<>(false);
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

    public MutableLiveData<Boolean> getStructureBonusVisible() {
        return structureBonusVisible;
    }

    public MutableLiveData<Boolean> getCampaingModeOn() {
        return campaingModeOn;
    }

    public void setCampaingModeOn(boolean campaingModeOn){
        this.campaingModeOn.setValue(campaingModeOn);
    }

    public void setStructureBonuses(List<StructureBonus> structureBonuses) {
        this.structureBonuses.setValue(structureBonuses);
    }

    public MutableLiveData<StructureBonus> getStructureBonus() {
        return structureBonus;
    }

    public void setStructureBonus(StructureBonus structureBonus){
        this.structureBonus.setValue(structureBonus);
    }

    public void setRandomStructureBonus(){
        List<StructureBonus> structureBonusList = structureBonuses.getValue();
        Collections.shuffle(structureBonusList);
        structureBonus.setValue(structureBonusList.get(0));
    }

    public void setStructureBonusVisible(boolean structureBonusVisible) {
        this.structureBonusVisible.setValue(structureBonusVisible);
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
        if(players.getValue().size() == 0){
            setStructureBonusVisible(false);
            setStructureBonus(StructureBonus.NONE);
        }

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
    public void setRandomFaction(int playerPosition) {
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
        } else {
        }

    }

    public void setRandomPlayerMat(int playerPosition) {
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
        } else {
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
                if(!campaingModeOn.getValue() || playerList.get(i).getFaction() == Faction.NONE){
                    Faction faction = factionList.remove(0);
                    setPlayerFaction(i, faction);
                }
                if(!playerList.get(i).getName().equals("Automa")) {
                    PlayerMat mat = playerMatList.remove(0);
                    setPlayerMat(i, mat);
                }
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
            if(playerList.size() == 1){
                Player automa = new Player("Automa");
                addPlayer(automa);
                if(factionList.size() > 0)
                    setPlayerFaction(1, factionList.remove(0));
                else{
                    return false;
                }
            }
            setRandomStructureBonus();
            structureBonusVisible.setValue(true);
            return true;
        } else {
            return false;
        }
    }

    public void toggleStructureBonus() {
        structureBonusVisible.setValue(Boolean.FALSE.equals(structureBonusVisible.getValue()));
    }

    public void resetPlayers() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Player 1"));
        players.setValue(playerList);
        setStructureBonusVisible(false);
    }

    public void resetFactions() {
        for(int i = 0; i < players.getValue().size(); i++){
            setPlayerFaction(i, Faction.NONE);
        }
        setStructureBonusVisible(false);
    }

    public void resetPlayerMats() {
        for(int i = 0; i < players.getValue().size(); i++){
            setPlayerMat(i, PlayerMat.NONE);
        }
        setStructureBonusVisible(false);
    }
}
