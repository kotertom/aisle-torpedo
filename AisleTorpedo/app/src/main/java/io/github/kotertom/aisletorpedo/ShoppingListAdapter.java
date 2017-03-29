package io.github.kotertom.aisletorpedo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import io.github.kotertom.aisletorpedo.model.ShoppingItem;

/**
 * Created by tom on 2017-03-01.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter {

    protected ArrayList<ShoppingItem> mShoppingItems;
    protected Context context;

    public ShoppingListAdapter(ArrayList<ShoppingItem> shoppingItems, Context context) {
        this.mShoppingItems = shoppingItems;
        this.context = context;
    }


    public static class ShoppingItemHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {


        protected EditText mEditTextItem;
        protected CheckBox mCheckBoxItem;


        public ShoppingItemHolder(View v, final ShoppingListAdapter adapter) {
            super(v);

            mEditTextItem = (EditText)v.findViewById(R.id.edittext_item);
            mCheckBoxItem = (CheckBox)v.findViewById(R.id.checkbox_item);
            //final ArrayList<ShoppingItem> mShoppingItems = items;

            mCheckBoxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    adapter.mShoppingItems.get(getAdapterPosition()).setChecked(isChecked);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(adapter.context);
                    if(prefs.getBoolean(adapter.context.getString(R.string.pref_autosave), true)) {
                        try {
                            adapter.saveDataToFile(
                                    prefs.getString(adapter.context.getString(R.string.pref_savefile),
                                    adapter.context.getString(R.string.save_file)));
                        } catch (IOException e) {
                            Log.e("Adapter/Save", "Exception: " + e.getMessage());
                        }
                    }
                }
            });

//            mEditTextItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(!hasFocus)
//                        v.setEnabled(false);
//                }
//            });

            mEditTextItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.mShoppingItems.get(getAdapterPosition()).setText(s.toString());
                    Log.d("DEBUG", PreferenceManager.getDefaultSharedPreferences(adapter.context).getString(adapter.context.getString(R.string.pref_savefile), "string N/A"));
                    try {
                        adapter.saveDataToFile(adapter.context.getString(R.string.save_file));
                    } catch (IOException e) {
                        Log.e("Adapter/Save", "Exception: " + e.getMessage());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mEditTextItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditText)v).setActivated(true);
                }
            });

            v.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("RecyclerView", "LONGCLICK!");
            return false;
        }
    }

    @Override
    public ShoppingItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ShoppingItemHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShoppingItemHolder h = (ShoppingItemHolder)holder;
        h.mEditTextItem.setText(mShoppingItems.get(position).getText());
        h.mCheckBoxItem.setChecked(mShoppingItems.get(position).isChecked());
    }

    public void addNewItem() {
        Log.d("Adapter", "Adding new item");
        ShoppingItem item = new ShoppingItem();
        item.setChecked(false);
        mShoppingItems.add(item);
        notifyDataSetChanged();
        try {
            saveDataToFile(context.getString(R.string.save_file));
        } catch (IOException e) {
            Log.e("Adapter/Save", "Exception: " + e.getMessage());
        }
    }

    public int removeSelectedItems() {
        ArrayList<ShoppingItem> toRemove = new ArrayList<ShoppingItem>();

        for (ShoppingItem item :
                mShoppingItems) {
            if (item.isChecked()) {
                toRemove.add(item);
            }
        }
        int count = toRemove.size();
        mShoppingItems.removeAll(toRemove);
        Log.d("Adapter", "Removing selected items");
        notifyDataSetChanged();
        try {
            saveDataToFile(context.getString(R.string.save_file));
        } catch (IOException e) {
            Log.e("Adapter/Save", "Exception: " + e.getMessage());
        }
        return count;
    }

    public void remove(int position) {
        mShoppingItems.remove(position);
        notifyDataSetChanged();
        try {
            saveDataToFile(context.getString(R.string.save_file));
        } catch (IOException e) {
            Log.e("Adapter/Save", "Exception: " + e.getMessage());
        }
    }

    public void clearList() {
        mShoppingItems.clear();
        notifyDataSetChanged();
        try {
            saveDataToFile(context.getString(R.string.save_file));
        } catch (IOException e) {
            Log.e("Adapter/Save", "Exception: " + e.getMessage());
        }
    }

    public void saveDataToFile(String filename) throws IOException {
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            for (ShoppingItem item :
                    mShoppingItems) {
                objOut.writeObject(item);
            }
            objOut.close();
            fileOut.close();
            Log.i("Adapter/Save", "Saved data is in "+filename);
        } catch (IOException e) {
            Log.e("Adapter/Save", "IOException: " + e.getMessage());
            throw e;
        }
    }

    public void loadDataFromFile(String filename) {
        ArrayList<ShoppingItem> list = new ArrayList<ShoppingItem>();
        try {
//            FileInputStream fileIn = new FileInputStream(filename);
            FileInputStream fileIn = context.openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while(true) {
                ShoppingItem item = (ShoppingItem)in.readObject();
                if(item == null)
                    break;
                list.add(item);
            }
            in.close();
            fileIn.close();
        } catch (IOException e) {
            Log.e("Adapter/Load", "IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Adapter/Load", "ClassNotFoundException: " + e.getMessage());
        }
        mShoppingItems = list;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }
}
