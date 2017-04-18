package io.github.kotertom.aisletorpedo.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.github.kotertom.aisletorpedo.R;
import io.github.kotertom.aisletorpedo.activity.SettingsActivity;
import io.github.kotertom.aisletorpedo.fragment.MainFragment;
import io.github.kotertom.aisletorpedo.fragment.SettingsFragment;
import io.github.kotertom.aisletorpedo.model.ShoppingItem;
import io.github.kotertom.aisletorpedo.ShoppingListAdapter;

public class MainActivity extends AppCompatActivity {

    private MainFragment mMainFragment;
    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFragment = new MainFragment();
        mSettingsFragment = new SettingsFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, mMainFragment)
                .commit();

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        prefs.edit()
//                .putBoolean(getString(R.string.pref_autosave), true)
//                .putString(getString(R.string.pref_savefile), getString(R.string.save_file))
//                .commit();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }


    public void setCurrentFragment(Fragment frag) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, frag)
                .addToBackStack(null)
                .commit();
    }

}
