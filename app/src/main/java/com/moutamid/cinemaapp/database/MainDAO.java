package com.moutamid.cinemaapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.moutamid.cinemaapp.model.CartModel;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(CartModel cartModel);

    @Query("SELECT * FROM Cart ORDER BY ID DESC")
    List<CartModel> getAll();

    @Delete
    void Delete(CartModel cartModel);
}
