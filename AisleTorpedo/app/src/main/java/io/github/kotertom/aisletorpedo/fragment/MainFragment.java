package io.github.kotertom.aisletorpedo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.github.kotertom.aisletorpedo.R;
import io.github.kotertom.aisletorpedo.ShoppingListAdapter;
import io.github.kotertom.aisletorpedo.activity.MainActivity;
import io.github.kotertom.aisletorpedo.model.ShoppingItem;


public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ShoppingListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.remove(viewHolder.getAdapterPosition());
                Toast.makeText(getActivity(),
                        getString(R.string.item_removed),
                        Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        mFab = (FloatingActionButton)rootView.findViewById(R.id.fab_additem);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem(v);
            }
        });


        // workaround for bug that's placing FAB in top-left corner (default position)
        // occurring often when going back from settings fragment
        // found at: http://stackoverflow.com/questions/39600012/bug-with-anchored-floatingactionbutton-in-support-library-24-2-1
        mFab.post(new Runnable() {
            @Override
            public void run() {
                mFab.requestLayout();
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ShoppingListAdapter(new ArrayList<ShoppingItem>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.loadDataFromFile(getString(R.string.save_file));
//                Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();
//            }
//        });

        mAdapter.loadDataFromFile(getString(R.string.save_file));

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.action_settings: {
                ((MainActivity)getActivity()).setCurrentFragment(new SettingsFragment());
                break;
            }
            case R.id.action_remove_selected: {
                int count = mAdapter.removeSelectedItems();
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.removed_items) + Integer.toString(count),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_clear_list: {
                mAdapter.clearList();
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.list_cleared),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_save_list: {
                try {
                    mAdapter.saveDataToFile(getString(R.string.save_file));
                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.save_error),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.action_load_list: {
                mAdapter.loadDataFromFile(getString(R.string.save_file));
                Toast.makeText(getActivity().getApplicationContext(),
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
}
