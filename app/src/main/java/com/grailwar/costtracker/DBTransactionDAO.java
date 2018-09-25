package com.grailwar.costtracker;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Brandon on 9/25/2018.
 */

public interface DBTransactionDAO {
    @Query("SELECT * FROM dbtransaction")
    List<DBTransaction> getAll();

    @Insert
    void insert(DBTransaction transaction);
}
