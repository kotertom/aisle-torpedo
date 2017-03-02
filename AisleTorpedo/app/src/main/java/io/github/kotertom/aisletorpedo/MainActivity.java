package io.github.kotertom.aisletorpedo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ShoppingItem i1 = new ShoppingItem();
        i1.setText("kek");
        ShoppingItem i2 = new ShoppingItem();
        i1.setText("lol");
        ArrayList<ShoppingItem> l = new ArrayList<ShoppingItem>();
        l.add(i1);
        l.add(i2);
        mAdapter = new ShoppingListAdapter(l);
        mRecyclerView.setAdapter(mAdapter);


    }
}
