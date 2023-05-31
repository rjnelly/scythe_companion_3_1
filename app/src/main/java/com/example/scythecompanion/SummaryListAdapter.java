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
import com.example.scythecompanion.databinding.ListItemStructureBonusBinding;

import java.util.ArrayList;
import java.util.List;

public class SummaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PLAYER_VIEW = 0;
    private static final int STRUCTURE_VIEW = 1;
    private List<Player> playerList = new ArrayList<>();
    private ItemInteractionListener listener;

    private boolean structureBonusVisible;

    public SummaryListAdapter() {

    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position < playerList.size()) return PLAYER_VIEW;
        return STRUCTURE_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listener = (ItemInteractionListener) parent.getContext();
        if(viewType == PLAYER_VIEW){
            ListItemPlayerDataBinding binding = ListItemPlayerDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PlayerViewHolder(binding);
        }
        ListItemStructureBonusBinding binding = ListItemStructureBonusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StructureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if(holder.getItemViewType() == PLAYER_VIEW){
            PlayerViewHolder playerViewHolder = (PlayerViewHolder) holder;
            Player player = playerList.get(position);

            int factionColor = ContextCompat.getColor(context, player.getFaction().COLOR);

            playerViewHolder.playerNameTV.setText(player.getName());
            playerViewHolder.playerNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.playerNameSelected(holder.getAdapterPosition());
                }
            });

            playerViewHolder.factionNameTV.setText(player.getFaction().NAME);
            playerViewHolder.factionNameTV.setBackgroundColor(factionColor);

            if (player.getFaction() == Faction.POLANIA) {
                playerViewHolder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else {
                playerViewHolder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            playerViewHolder.factionNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.factionSelected(holder.getAdapterPosition());
                }
            });
            playerViewHolder.factionNameTV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.resetFaction(holder.getAdapterPosition());
                    return true;
                }
            });

            playerViewHolder.factionImage.setImageDrawable(ContextCompat.getDrawable(context, player.getFaction().IMAGE));
            playerViewHolder.factionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.factionSelected(holder.getAdapterPosition());
                }
            });
            playerViewHolder.factionImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.resetFaction(holder.getAdapterPosition());
                    return true;
                }
            });
            playerViewHolder.factionImage.setBackgroundColor(factionColor);
            playerViewHolder.playerMatNameTV.setText(player.getPlayerMat().NAME);
            playerViewHolder.playerMatNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.playerMatSelected(holder.getAdapterPosition());
                }
            });
            playerViewHolder.playerMatNameTV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.resetPlayerMat(holder.getAdapterPosition());
                    return true;
                }
            });
            playerViewHolder.playerMatImage.setImageDrawable(ContextCompat.getDrawable(context, player.getPlayerMat().IMAGE));
            playerViewHolder.playerMatImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.playerMatSelected(holder.getAdapterPosition());
                }
            });
            playerViewHolder.playerMatImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.resetPlayerMat(holder.getAdapterPosition());
                    return true;
                }
            });

        } else {
            StructureViewHolder structureViewHolder = (StructureViewHolder) holder;
            structureViewHolder.structureImage.setImageDrawable(ContextCompat.getDrawable(context,listener.getStructureBonusImage()));
        }
    }

    @Override
    public int getItemCount() {
        return structureBonusVisible ? playerList.size() + 1 : playerList.size();
    }

    public void setStructureVisible(Boolean visible) {
        structureBonusVisible = visible;
        notifyDataSetChanged();
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

    private class StructureViewHolder extends RecyclerView.ViewHolder {
        ImageView structureImage;
        public StructureViewHolder(ListItemStructureBonusBinding binding) {
            super(binding.getRoot());
            structureImage = binding.structureImage;
        }

    }
}
