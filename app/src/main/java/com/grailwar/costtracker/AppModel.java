package com.grailwar.costtracker;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.view.View;

import java.util.Map;

public class AppModel {
    private Balance mainBalance;
    private LiveData<Balance> liveBalance;
    private Map<View, Runnable> mainBalanceSubscribers;

    private AppDatabase db;

    //TODO
    /*
    * tear all this down, just make a class to store state
    * when you write updates to the state through getters/setters, write to the db
    *
    * register runnables to POST to the view components that we register as listening for diff
    * values (p much just the balance for now)
    *
    * get a singleton factory thing setup for this state tracker, smash all the livedata crap from
    * the DB.
    *
    *
    * Actually the Room LiveData may still work if we store it here then pass it out, so let's try that.
     */

    public AppModel(Context context) {
        this.db = AppDatabaseFactory.build(context);

        new Thread(() -> {
            mainBalance = db.balanceDao().getBalanceWithName(Constants.MAIN_BALANCE);
            if (mainBalance == null) {
                db.balanceDao().insert(new Balance("main", 0));
            }
            mainBalance = db.balanceDao().getBalanceWithName(Constants.MAIN_BALANCE);
            liveBalance = db.balanceDao().getLiveBalanceWithName(Constants.MAIN_BALANCE);
        }).start();
    }

    public Balance getMainBalance() {
        return mainBalance;
    }

    public LiveData<Balance> getLiveBalance() {
        return liveBalance;
    }

    /**
     * Sets main balance
     * Also updates the value in the persistent database
     * @param bal Balance with the name "main" (Constants.MAIN_BALANCE)
     * @throws IllegalArgumentException if bal does not have the right name
     */
    public void setMainBalance(Balance bal) {
        if(!bal.getName().equals(Constants.MAIN_BALANCE) ||
                bal.getUid() != mainBalance.getUid()) {
            throw new IllegalArgumentException("Balance bal must have the name 'main'");
        }
        // No need to update if the value is the same, but this is still a valid case
        if(this.mainBalance.getBalance() != bal.getBalance()) {
            this.mainBalance = bal;

            // Update the db backing with the new value
            new Thread(() -> {
                db.balanceDao().updateBalance(mainBalance);
            }).start();

            // Notify the subscribers
            for (Runnable update : mainBalanceSubscribers.values()) {
                update.run();
            }
        }
    }

    //        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                db = AppDatabaseFactory.get();
//
//                Balance bal = db.balanceDao().getBalanceWithName("main");
//                if (bal == null) {
//                    db.balanceDao().insert(new Balance("main", 0));
//                }
//                balance = db.balanceDao().getLiveBalanceWithName("main");
//            }
//        }).start();
}
