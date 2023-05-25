package com.example.scythecompanion;

import static android.content.Context.MODE_PRIVATE;

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

import java.util.prefs.PreferenceChangeListener;

public class FactionSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.factions_preferences, rootKey);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        preferences.registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) requireActivity());
        boolean invaders = preferences.getBoolean(getString(R.string.invaders_from_afar_preference_key), false);
        boolean rise = preferences.getBoolean(getString(R.string.rise_of_fenris_preference_key), false);
        PreferenceCategory factions = findPreference("factions");
        //Add factions from Invaders from Afar expansion
        if(invaders){
            CheckBoxPreference albion = new CheckBoxPreference(getContext());
            albion.setKey(getString(R.string.albion_preference_key));
            albion.setTitle(getString(R.string.albion));
            //albion.setDefaultValue(true);
            albion.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.albion_icon, requireActivity().getTheme()));
            factions.addPreference(albion);
            CheckBoxPreference togawa = new CheckBoxPreference(getContext());
            togawa.setKey(getString(R.string.togawa_preference_key));
            togawa.setTitle(getString(R.string.togawa));
            togawa.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.togawa_icon, requireActivity().getTheme()));
            togawa.setDefaultValue(true);
            factions.addPreference(togawa);
        }
        if(rise){
            CheckBoxPreference tesla = new CheckBoxPreference(getContext());
            tesla.setKey(getString(R.string.tesla_preference_key));
            tesla.setTitle(getString(R.string.tesla));
            tesla.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.tesla_icon, requireActivity().getTheme()));
            tesla.setDefaultValue(true);
            factions.addPreference(tesla);
            CheckBoxPreference fenris = new CheckBoxPreference(getContext());
            fenris.setKey(getString(R.string.fenris_preference_key));
            fenris.setTitle(getString(R.string.fenris));
            fenris.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.fenris_icon, requireActivity().getTheme()));
            fenris.setDefaultValue(true);
            factions.addPreference(fenris);

        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
