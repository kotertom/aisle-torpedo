package io.github.kotertom.aisletorpedo;

import android.provider.BaseColumns;

/**
 * Created by tom on 2017-03-29.
 */

public final class ShoppingListContract {
    private ShoppingListContract() {}

    public static class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_CHECKED = "checked";
    }
}
