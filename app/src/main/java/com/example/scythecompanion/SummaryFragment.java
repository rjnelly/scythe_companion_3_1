package com.example.scythecompanion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scythecompanion.databinding.FragmentSummaryBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        players.add(new Player("Player 1",Faction.ALBION, PlayerMat.AGRICULTURAL));
        players.add(new Player("Player 2", Faction.CRIMEA, PlayerMat.NONE));
        players.add(new Player("Player 3", Faction.NONE, PlayerMat.NONE));
        RecyclerView playerSummaryList = binding.playerList;
        playerSummaryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        SummaryListAdapter adapter = new SummaryListAdapter();
        playerSummaryList.setAdapter(adapter);
        adapter.setPlayerList(players);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    players.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(playerSummaryList);
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
        View addPlayerView = getLayoutInflater().inflate(R.layout.dialog_add_player, null);
        new AlertDialog.Builder(view.getContext())
                .setView(addPlayerView)
                .create().show();

    }

    private List<Player> players = new ArrayList<>();

}