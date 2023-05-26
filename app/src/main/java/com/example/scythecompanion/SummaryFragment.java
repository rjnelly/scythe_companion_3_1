package com.example.scythecompanion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scythecompanion.databinding.DialogAddPlayerBinding;
import com.example.scythecompanion.databinding.FragmentSummaryBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;
    private PlayerDataViewModel viewModel;
    private List<Player> players = new ArrayList<>();


    private AlertDialog addPlayerDialog;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PlayerDataViewModel.class);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*players.add(new Player("Player 1",Faction.ALBION, PlayerMat.AGRICULTURAL));
        players.add(new Player("Player 2", Faction.CRIMEA, PlayerMat.NONE));
        players.add(new Player("Player 3", Faction.NONE, PlayerMat.NONE));*/
        RecyclerView playerSummaryList = binding.playerList;
        playerSummaryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        SummaryListAdapter adapter = new SummaryListAdapter();
        playerSummaryList.setAdapter(adapter);
        viewModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> playerList) {
                players = playerList;
                adapter.setPlayerList(players);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    viewModel.removePlayer(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(playerSummaryList);

        ImageView structureImage = binding.structureBonusImage;
        structureImage.setVisibility(View.GONE);
        binding.fabToggleStructureBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(structureImage.getVisibility() == View.GONE){
                    structureImage.setVisibility(View.VISIBLE);
                } else {
                    structureImage.setVisibility(View.GONE);
                }
            }
        });
        binding.newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.fabAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPlayerDialog(v);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAddPlayerDialog(View view) {
        DialogAddPlayerBinding addPlayerBinding = DialogAddPlayerBinding.inflate(getLayoutInflater());
        View dialogView = addPlayerBinding.getRoot();
        TextInputLayout playerInput = addPlayerBinding.textInputLayoutPlayerName;
        addPlayerBinding.editTextPlayerName.setOnEditorActionListener(new PlayerNameListener());
        playerInput.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer(playerInput.getEditText().getText().toString());
            }
        });
        addPlayerDialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Add Player")
                .create();

        playerInput.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = playerInput.getEditText().getText().toString();
                if(name.isEmpty())name = "Player " + (players.size() + 1);
                viewModel.addPlayer(new Player(name, Faction.NONE, PlayerMat.NONE));
                addPlayerDialog.dismiss();
            }
        });

        addPlayerDialog.show();
        addPlayerBinding.editTextPlayerName.requestFocus();
    }
    public void addPlayer(String playerName){
        if(playerName.isEmpty())playerName = "Player " + (players.size() + 1);
        viewModel.addPlayer(new Player(playerName, Faction.NONE, PlayerMat.NONE));
    }

    private class PlayerNameListener implements TextView.OnEditorActionListener {
         @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_NEXT)) {
                addPlayer(v.getText().toString());
                addPlayerDialog.dismiss();
                return true;
            }
            return false;
        }

    }



}