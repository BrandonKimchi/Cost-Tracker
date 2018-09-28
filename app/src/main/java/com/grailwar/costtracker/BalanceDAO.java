package com.grailwar.costtracker;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Brandon on 9/25/2018.
 */

@Dao
public interface BalanceDAO {
    @Query("SELECT * FROM balance WHERE uid == :uid LIMIT 1")
    Balance getBalance(int uid);

    @Query("SELECT * FROM balance")
    LiveData<List<Balance>> getAll();

    @Query("SELECT * FROM balance WHERE name == :name LIMIT 1")
    Balance getBalanceWithName(String name);

    @Insert
    void insert(Balance balance);

    @Update
    void updateBalance(Balance balance);
}
