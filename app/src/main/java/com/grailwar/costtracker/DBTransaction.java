package com.grailwar.costtracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Brandon on 9/25/2018.
 */

@Entity
public class DBTransaction {

    public DBTransaction(long amount, String category){
        this.amount = amount;
        this.category = category;
        this.timeStamp = System.currentTimeMillis();
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @ColumnInfo(name = "time_stamp")
    // Store this as a current time millis and we can convert to other stuff as needed
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @ColumnInfo(name = "amount")
    // Store the number of cents
    private long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @ColumnInfo(name = "category")
    // What category the transaction falls in: food, fun, bills, etc.
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
