package io.github.kotertom.aisletorpedo.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.github.kotertom.aisletorpedo.R;
import io.github.kotertom.aisletorpedo.fragment.SettingsFragment;
import io.github.kotertom.aisletorpedo.model.ShoppingItem;
import io.github.kotertom.aisletorpedo.ShoppingListAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ShoppingListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PreferenceFragment mPreferenceFragment;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mFab = (FloatingActionButton)findViewById(R.id.fab_additem);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ShoppingItem i1 = new ShoppingItem();
        i1.setText("kek");
        i1.setChecked(true);
        ShoppingItem i2 = new ShoppingItem();
        i2.setText("lol");
        i2.setChecked(false);
        ArrayList<ShoppingItem> l = new ArrayList<ShoppingItem>();
        l.add(i1);
        l.add(i2);
        mAdapter = new ShoppingListAdapter(l, this);
        mRecyclerView.setAdapter(mAdapter);

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.loadDataFromFile(getString(R.string.save_file));
//                Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();
//            }
//        });

        mAdapter.loadDataFromFile(getString(R.string.save_file));

        mPreferenceFragment = new SettingsFragment();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.action_settings: {
                Toast.makeText(getApplicationContext(), "Lelo", Toast.LENGTH_LONG).show();
                showHideFragment(mPreferenceFragment);
                break;
            }
            case R.id.action_remove_selected: {
                int count = mAdapter.removeSelectedItems();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.removed_items) + Integer.toString(count),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_clear_list: {
                mAdapter.clearList();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.list_cleared),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_save_list: {
                try {
                    mAdapter.saveDataToFile(getString(R.string.save_file));
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.save_error),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.action_load_list: {
                mAdapter.loadDataFromFile(getString(R.string.save_file));
                Toast.makeText(getApplicationContext(),
                        getString(R.string.load_success),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNewItem(View v) {
        mAdapter.addNewItem();
    }



    public void showHideFragment(final Fragment fragment){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);

        if (fragment.isHidden()) {
            ft.show(fragment);
            Log.d("hidden","Show");
        } else {
            ft.hide(fragment);
            Log.d("Shown","Hide");
        }

        ft.commit();
    }




}
