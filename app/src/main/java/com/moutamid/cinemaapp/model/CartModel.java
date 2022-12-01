package com.moutamid.cinemaapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Cart")
public class CartModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    public CartModel(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
