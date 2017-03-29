package io.github.kotertom.aisletorpedo.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import io.github.kotertom.aisletorpedo.R;

/**
 * Created by tom on 2017-03-07.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
