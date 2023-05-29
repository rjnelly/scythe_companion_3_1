package com.example.scythecompanion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class ExpansionSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.expansions_preferences, rootKey);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SwitchPreferenceCompat rise = findPreference(getString(R.string.rise_of_fenris_preference_key));
        rise.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue instanceof Boolean){
                    final boolean isEnabled = (Boolean) newValue;
                    if(isEnabled){
                        new AlertDialog.Builder(getContext(), R.style.Theme_ScytheCompanion_AlertDialog)
                                .setTitle("Confirm")
                                .setIcon(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_warning_accent_24dp, requireActivity().getTheme()))
                                .setMessage(R.string.confirm_message)
                                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rise.setChecked(false);
                                    }
                                }).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rise.setChecked(true);
                                    }
                                }).create().show();
                    }
                    else rise.setChecked(false);
                }
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
