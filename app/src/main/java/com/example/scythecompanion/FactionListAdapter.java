package com.example.scythecompanion;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scythecompanion.databinding.ListItemFactionBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FactionListAdapter extends RecyclerView.Adapter<FactionListAdapter.FactionViewHolder>  {

    private List<Faction> factionList;
    private Context context;

    private Dialog dialog;

    private int playerPosition;

    //private ScytheCompanionViewModel viewModel;

    public FactionListAdapter(int playerPosition, Dialog dialog){
        this.playerPosition = playerPosition;
        this.dialog = dialog;

    }

    public void setFactionList(List<Faction> factions){
        factionList = factions;
        notifyDataSetChanged();
    }


                               @NonNull
                               @Override
    public FactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemFactionBinding binding = ListItemFactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        //viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ScytheCompanionViewModel.class);
        return new FactionViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FactionViewHolder holder, int position) {
        Faction faction = factionList.get(position);
        if(faction == Faction.POLANIA){
            holder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        else {
            holder.factionNameTV.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        holder.factionIcon.setImageDrawable(ContextCompat.getDrawable(context, faction.ICON));
        //holder.factionIcon.setBackgroundColor(ContextCompat.getColor(context, faction.COLOR));
        holder.factionNameTV.setText(faction.NAME);
        //holder.factionNameTV.setBackgroundColor(ContextCompat.getColor(context, faction.COLOR));
        holder.layout.getBackground().setTint(ContextCompat.getColor(context, faction.COLOR));
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)holder.layout.getLayoutParams();
        if(position == 0){
            lp.setMargins(lp.leftMargin, 0 , lp.rightMargin, lp.bottomMargin);
        }
        holder.factionNameTV.setClickable(true);
        /*holder.factionNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerMat playerMat = viewModel.getPlayers().getValue().get(playerPosition).getPlayerMat();
                if(!viewModel.validCombination(faction, playerMat)){
                    Toast.makeText(context, "Invalid Faction/Player Mat Combination", Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.setPlayerFaction(playerPosition, faction);
                    dialog.dismiss();
                }
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return factionList.size();
    }

    public static class FactionViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout layout;
        private final ImageView factionIcon;
        private final TextView factionNameTV;

        public FactionViewHolder(@NonNull ListItemFactionBinding binding) {
            super(binding.getRoot());
            factionIcon = binding.factionIcon;
            factionNameTV = binding.factionName;
            layout = binding.layoutListItemFaction;

        }
    }
}
