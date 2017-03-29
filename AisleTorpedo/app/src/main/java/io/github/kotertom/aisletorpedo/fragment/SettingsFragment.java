package io.github.kotertom.aisletorpedo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.kotertom.aisletorpedo.R;

/**
 * Created by tom on 2017-03-07.
 */

public class SettingsFragment extends PreferenceFragment {

    protected CheckBoxPreference mCheckboxAutosave;
    protected EditTextPreference mEditTextFilename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
////        mCheckboxAutosave = (CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.pref_autosave));
////        mEditTextFilename = (EditTextPreference)getPreferenceManager().findPreference(getString(R.string.pref_savefile));
//
//        findPreference(getString(R.string.pref_autosave)).setOnPreferenceChangeListener(
//                new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.i("PREF", "Autosave changed");
//                return false;
//            }
//        });
//
////        findPreference(getString(R.string.pref_savefile));
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}
