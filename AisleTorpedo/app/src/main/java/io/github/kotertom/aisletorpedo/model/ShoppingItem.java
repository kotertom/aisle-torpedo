package io.github.kotertom.aisletorpedo.model;

/**
 * Created by tom on 02.03.17.
 */

public class ShoppingItem implements java.io.Serializable {

    private String text;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private boolean checked;

    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
