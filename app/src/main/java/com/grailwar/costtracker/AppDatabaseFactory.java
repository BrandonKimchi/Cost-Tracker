package com.grailwar.costtracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Brandon on 9/25/2018.
 */


public class AppDatabaseFactory {

    private static AppDatabase db;

    public static AppDatabase build(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class, "costly-db"
            ).build();
        }
        return db;
    }

    public static AppDatabase get() {
        return db;
    }

}

@Database(entities = {DBTransaction.class, Balance.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract DBTransactionDAO dbTransactionDao();
    public abstract BalanceDAO balanceDao();
}
