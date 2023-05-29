package com.example.scythecompanion;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.scythecompanion.databinding.ListItemPlayermatBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class PlayerMatListAdapter extends RecyclerView.Adapter<PlayerMatListAdapter.PlayerMatViewHolder>  {

    private List<PlayerMat> playerMatList;
    private Context context;

    private Dialog dialog;

    private int playerPosition;

    private PlayerDataViewModel viewModel;

    public PlayerMatListAdapter(int playerPosition, Dialog dialog){
        this.playerPosition = playerPosition;
        this.dialog = dialog;

    }

    public void setPlayerMatList(List<PlayerMat> mats){
        playerMatList = mats;
        notifyDataSetChanged();
    }


                               @NonNull
                               @Override
    public PlayerMatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPlayermatBinding binding = ListItemPlayermatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PlayerDataViewModel.class);
        return new PlayerMatViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PlayerMatViewHolder holder, int position) {
        PlayerMat mat = playerMatList.get(position);

        holder.playerMatIcon.setImageDrawable(ContextCompat.getDrawable(context, mat.ICON));
        holder.playerMatTV.setText(mat.NAME);

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)holder.layout.getLayoutParams();
        if(position == 0){
            lp.setMargins(lp.leftMargin, 0 , lp.rightMargin, lp.bottomMargin);
        }
        holder.playerMatTV.setClickable(true);
        holder.playerMatTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faction playerFaction = viewModel.getPlayers().getValue().get(playerPosition).getFaction();
                if(!viewModel.validCombination(playerFaction, mat)){
                    Toast.makeText(context, "Invalid Faction/Player Mat Combination", Toast.LENGTH_SHORT).show();
                }
                else{
                    viewModel.setPlayerMat(playerPosition, mat);
                    dialog.dismiss();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return playerMatList.size();
    }

    public static class PlayerMatViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout layout;
        private final ImageView playerMatIcon;
        private final TextView playerMatTV;
        public PlayerMatViewHolder(@NonNull ListItemPlayermatBinding binding) {
            super(binding.getRoot());
            playerMatIcon = binding.playermatIcon;
            playerMatTV = binding.playermatName;
            layout = binding.layoutListItemPlayermat;

        }
    }
}
