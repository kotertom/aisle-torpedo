package io.github.kotertom.aisletorpedo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tom on 2017-03-01.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter {

    private ArrayList<ShoppingItem> mShoppingItems;

    public ShoppingListAdapter(ArrayList<ShoppingItem> shoppingItems) {
        this.mShoppingItems = shoppingItems;
    }


    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView mItemText;


        public ItemHolder(View v) {
            super(v);

            mItemText = (TextView)v.findViewById(R.id.item_text);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!");
        }
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder)holder).mItemText.setText(mShoppingItems.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }
}
