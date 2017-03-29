package io.github.kotertom.aisletorpedo.activity;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.kotertom.aisletorpedo.R;
import io.github.kotertom.aisletorpedo.fragment.SettingsFragment;

public class SettingsActivity extends PreferenceActivity {

    protected SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        mSettingsFragment = new SettingsFragment();
//       // ft.replace(R.id.frag_settings, mSettingsFragment);
//        ft.replace(R.id.frag_settings, mSettingsFragment);
//        ft.commit();
    }
}
