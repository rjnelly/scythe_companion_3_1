package com.example.scythecompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scythecompanion.databinding.ListItemPlayerDataBinding;
import com.example.scythecompanion.databinding.ListItemSummaryBinding;

import java.util.ArrayList;
import java.util.List;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.PlayerViewHolder> {
    private List<Player> playerList = new ArrayList<>();
    private ItemInteractionListener listener;

    public SummaryListAdapter() {

    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPlayerDataBinding binding = ListItemPlayerDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        listener = (ItemInteractionListener) parent.getContext();
        return new PlayerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        Context context = holder.itemView.getContext();

        int factionColor = ContextCompat.getColor(context, player.getFaction().COLOR);

        holder.playerNameTV.setText(player.getName());

        holder.factionNameTV.setText(player.getFaction().NAME);
        holder.factionNameTV.setBackgroundColor(factionColor);
        holder.factionNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playerNameSelected(holder.getAdapterPosition());
            }
        });
        if (player.getFaction() == Faction.POLANIA) {
            holder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        } else {
            holder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        holder.factionNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.factionSelected(holder.getAdapterPosition());
            }
        });

        holder.factionImage.setImageDrawable(ContextCompat.getDrawable(context, player.getFaction().IMAGE));
        holder.factionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.factionSelected(holder.getAdapterPosition());
            }
        });
        holder.factionImage.setBackgroundColor(factionColor);
        holder.playerMatNameTV.setText(player.getPlayerMat().NAME);
        holder.playerMatNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playerMatSelected(holder.getAdapterPosition());
            }
        });
        holder.playerMatImage.setImageDrawable(ContextCompat.getDrawable(context, player.getPlayerMat().IMAGE));
        holder.playerMatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.playerMatSelected(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        TextView playerNameTV;
        TextView factionNameTV;
        TextView playerMatNameTV;
        ImageView factionImage;
        ImageView playerMatImage;

        public PlayerViewHolder(ListItemPlayerDataBinding binding) {
            super(binding.getRoot());
            playerNameTV = binding.summaryPlayerNameTextview;
            factionNameTV = binding.summaryFactionTextview;
            playerMatNameTV = binding.summaryPlayermatTextview;
            factionImage = binding.summaryFactionImageview;
            playerMatImage = binding.summaryPlayermatImageview;
        }
    }

}
