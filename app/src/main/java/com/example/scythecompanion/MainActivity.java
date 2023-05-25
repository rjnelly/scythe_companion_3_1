package com.example.scythecompanion;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scythecompanion.databinding.ActivityMainBinding;
import com.example.scythecompanion.databinding.DialogAddPlayerBinding;
import com.example.scythecompanion.databinding.DialogListBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ItemInteractionListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private AppBarConfiguration appBarConfiguration;
    private PlayerDataViewModel viewModel;
    private SharedPreferences preferences;
    private ActivityMainBinding binding;
    private NavController navController;

    private AlertDialog editPlayerNameDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        viewModel = new ViewModelProvider(this).get(PlayerDataViewModel.class);

        initializeFromPreferences();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            navController.navigate(R.id.settingsFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePlayers();
    }

    private void initializeFromPreferences() {
        getPlayerMatsFromPreferences();
        getFactionsFromPreferences();
        getPlayersFromPreferences();

    }

    public void getPlayersFromPreferences(){
        List<Player> playerList = new ArrayList<>();
        String playerListString = preferences.getString(getString(R.string.player_list_key), "");
        if (!playerListString.equals("")) {
            Type type = new TypeToken<List<Player>>() {
            }.getType();
            playerList = new Gson().fromJson(playerListString, type);
        }
        viewModel.setPlayers(playerList);
    }



    public void getFactionsFromPreferences(){
        List<Faction> factions = new ArrayList<>();
        if(preferences.getBoolean(getString(R.string.rusviet_preference_key), true))
            factions.add(Faction.RUSVIET);
        if(preferences.getBoolean(getString(R.string.polania_preference_key), true))
            factions.add(Faction.POLANIA);
        if(preferences.getBoolean(getString(R.string.crimea_preference_key), true))
            factions.add(Faction.CRIMEA);
        if(preferences.getBoolean(getString(R.string.nordic_preference_key), true))
            factions.add(Faction.NORDIC);
        if(preferences.getBoolean(getString(R.string.saxony_preference_key), true))
            factions.add(Faction.SAXONY);
        if(preferences.getBoolean(getString(R.string.invaders_from_afar_preference_key), false)) {
            if (preferences.getBoolean(getString(R.string.togawa_preference_key), true))
                factions.add(Faction.TOGAWA);
            if(preferences.getBoolean(getString(R.string.albion_preference_key), true))
                factions.add(Faction.ALBION);
        }
        if (preferences.getBoolean(getString(R.string.rise_of_fenris_preference_key), false)) {
            if (preferences.getBoolean(getString(R.string.tesla_preference_key), true))
                factions.add(Faction.TESLA);
            if (preferences.getBoolean(getString(R.string.fenris_preference_key), true))
                factions.add(Faction.FENRIS);
        }
        viewModel.setAvailableFactions(factions);
    }

    public void getPlayerMatsFromPreferences(){
        List<PlayerMat> playerMats= new ArrayList<>();
        if (preferences.getBoolean(getString(R.string.industrial_preference_key), true))
            playerMats.add(PlayerMat.INDUSTRIAL);
        if (preferences.getBoolean(getString(R.string.engineering_preference_key), true))
            playerMats.add(PlayerMat.ENGINEERING);
        if (preferences.getBoolean(getString(R.string.patriotic_preference_key), true))
            playerMats.add(PlayerMat.PATRIOTIC);
        if (preferences.getBoolean(getString(R.string.mechanical_preference_key), true))
            playerMats.add(PlayerMat.MECHANICAL);
        if (preferences.getBoolean(getString(R.string.agricultural_preference_key), true))
            playerMats.add(PlayerMat.AGRICULTURAL);
        if (preferences.getBoolean(getString(R.string.invaders_from_afar_preference_key), false)) {
            if (preferences.getBoolean(getString(R.string.militant_preference_key), true))
                playerMats.add(PlayerMat.MILITANT);
            if (preferences.getBoolean(getString(R.string.innovative_preference_key), true))
                playerMats.add(PlayerMat.INNOVATIVE);
        }
        Collections.sort(playerMats);
        viewModel.setAvailableMats(playerMats);
    }

    @Override
    public void playerNameSelected(int playerPosition) {
        DialogAddPlayerBinding addPlayerBinding = DialogAddPlayerBinding.inflate(getLayoutInflater());
        View dialogView = addPlayerBinding.getRoot();
        TextInputLayout playerInput = addPlayerBinding.textInputLayoutPlayerName;
        EditText editTextPlayerName = addPlayerBinding.editTextPlayerName;
        playerInput.setEndIconDrawable(R.drawable.ic_check_24);
        editTextPlayerName.setOnEditorActionListener(new EditNameListener(playerPosition));
        editTextPlayerName.setText(viewModel.getPlayers().getValue().get(playerPosition).getName());
        playerInput.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerName(playerPosition, playerInput.getEditText().getText().toString());
            }
        });
        editPlayerNameDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Edit Name")
                .create();

        playerInput.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayerName(playerPosition, playerInput.getEditText().getText().toString());
            }
        });
        editPlayerNameDialog.show();
        addPlayerBinding.editTextPlayerName.requestFocus();
    }
    public void changePlayerName(int playerPosition, String name){
        if(name.isEmpty())name = "Player " + (viewModel.getPlayers().getValue().size() + 1);
        viewModel.changePlayerName(playerPosition, name);
        editPlayerNameDialog.dismiss();
    }

    @Override
    public void factionSelected(int playerPosition) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.choose_faction)
                .setMessage(R.string.add_faction)
                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton(getString(R.string.choose), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showFactionListDialog(playerPosition);
                    }
                }).setNegativeButton(getString(R.string.btn_randomize), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    @Override
    public void playerMatSelected(int playerPosition) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.choose_playerMat)
                .setMessage(R.string.add_playerMat)
                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton(getString(R.string.choose), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPlayerMatListDialog(playerPosition);
                    }
                }).setNegativeButton(getString(R.string.btn_randomize), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    @Override
    public void factionDeleted(int playerPosition) {
        viewModel.setPlayerFaction(playerPosition, Faction.NONE);
    }

    private void showFactionListDialog(int playerPosition) {
        List<Faction> unusedFactions = new ArrayList<>(viewModel.getAvailableFactions().getValue());
        List<Player> players = viewModel.getPlayers().getValue();
        for(int i = 0; i < players.size(); i++)
        {
            if(i != playerPosition) unusedFactions.remove(players.get(i).getFaction());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_ScytheCompanion_AlertDialog);
        DialogListBinding dialogFactionListBinding = DialogListBinding.inflate(getLayoutInflater());
        View dialogView = dialogFactionListBinding.getRoot();
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        RecyclerView factionListView = dialogFactionListBinding.selectionList;
        FactionListAdapter adapter = new FactionListAdapter(playerPosition, dialog);
        adapter.setFactionList(unusedFactions);
        factionListView.setLayoutManager(new LinearLayoutManager(this));
        factionListView.setAdapter(adapter);
        dialog.show();
    }

    private void showPlayerMatListDialog(int playerPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_ScytheCompanion_AlertDialog);
        DialogListBinding dialogFactionListBinding = DialogListBinding.inflate(getLayoutInflater());
        View dialogView = dialogFactionListBinding.getRoot();
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        RecyclerView playerMatListView = dialogFactionListBinding.selectionList;
        PlayerMatListAdapter adapter = new PlayerMatListAdapter(playerPosition, dialog);
        adapter.setPlayerMatList(viewModel.getAvailableMats().getValue());
        playerMatListView.setLayoutManager(new LinearLayoutManager(this));
        playerMatListView.setAdapter(adapter);
        dialog.show();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getFactionsFromPreferences();
        getPlayerMatsFromPreferences();
    }
    private void savePlayers() {
        SharedPreferences.Editor editor = preferences.edit();
        String playersJSON = new Gson().toJson(viewModel.getPlayers().getValue());
        editor.putString("players", playersJSON).apply();
    }

    private class EditNameListener implements TextView.OnEditorActionListener {
        private int position;
        public EditNameListener(int playerPosition){
            this.position = playerPosition;
        }
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                changePlayerName(position, v.getText().toString());
                editPlayerNameDialog.dismiss();
                return true;
            }
            return false;
        }
    }
}