package io.github.kotertom.aisletorpedo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tom on 2017-03-29.
 */

public class ShoppingListDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingList.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ShoppingListContract.ShoppingListEntry.TABLE_NAME + " (" +
                    ShoppingListContract.ShoppingListEntry._ID + " INTEGER PRIMARY KEY," +
                    ShoppingListContract.ShoppingListEntry.COLUMN_NAME_TEXT + " TEXT," +
                    ShoppingListContract.ShoppingListEntry.COLUMN_NAME_CHECKED + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ShoppingListContract.ShoppingListEntry.TABLE_NAME;

    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
