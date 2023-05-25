package com.example.scythecompanion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class PlayerMatSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.playermat_preferences, rootKey);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean invaders = preferences.getBoolean(getString(R.string.invaders_from_afar_preference_key), false);
        PreferenceCategory playerMats = findPreference("player_mats");
        //Add factions from Invaders from Afar expansion
        if(invaders){
            CheckBoxPreference militant = new CheckBoxPreference(getContext());
            militant.setKey(getString(R.string.militant_preference_key));
            militant.setTitle(getString(R.string.militant));
            militant.setIcon(ResourcesCompat.getDrawable(getResources(),R.drawable.icon_militant,requireActivity().getTheme()));
            militant.setDefaultValue(true);
            playerMats.addPreference(militant);
            CheckBoxPreference innovative = new CheckBoxPreference(getContext());
            innovative.setKey(getString(R.string.innovative_preference_key));
            innovative.setTitle(getString(R.string.innovative));
            innovative.setIcon(ResourcesCompat.getDrawable(getResources(),R.drawable.icon_innovative,requireActivity().getTheme()));
            innovative.setDefaultValue(true);
            playerMats.addPreference(innovative);
            militant.setOrder(2);
            innovative.setOrder(3);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
