package com.grailwar.costtracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Brandon on 9/25/2018.
 */

@Entity
public class Balance {

    public Balance (String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    @ColumnInfo(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ColumnInfo(name = "balance")
    private long balance;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "$" + balance/100 + "." + balance%100;
    }
}
