package com.grailwar.costtracker;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class AppModel {
    private Balance mainBalance;
//    private LiveData<Balance> liveBalance;
    private Map<String, Set<Consumer<Balance>>> balanceSubscribers; // map balance name to updaters list

    private AppDatabase db;


    public AppModel(Context context) {
        this.balanceSubscribers = new HashMap<>();
        this.mainBalance = new Balance(Constants.MAIN_BALANCE, 0);
//        new Thread(() -> {
//            this.db = AppDatabaseFactory.initialBuild(context);
//            mainBalance = db.balanceDao().getBalanceWithName(Constants.MAIN_BALANCE);
//            if (mainBalance == null) {
//                db.balanceDao().insert(new Balance("main", 0));
//            }
//            mainBalance = db.balanceDao().getBalanceWithName(Constants.MAIN_BALANCE);
//            this.setMainBalance(mainBalance);
//        }).start();
    }

    public Balance getMainBalance() {
        return mainBalance;
    }

    /**
     * Give a runnable to be run when main balance is updated
     * UI elements can use this to update their values in a view when the balance is changed.
     * @param updater
     */
    public void subscribeToMainBalance(Consumer<Balance> updater) {
        Set<Consumer<Balance>> updaters = this.balanceSubscribers.get(Constants.MAIN_BALANCE);
        if (updaters == null) {
            updaters = new HashSet<Consumer<Balance>>();
            this.balanceSubscribers.put(Constants.MAIN_BALANCE, updaters);
        }
        updaters.add(updater);
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
//            new Thread(() -> {
//                db.balanceDao().updateBalance(mainBalance);
//            }).start();
        }
        // Notify the subscribers
        Set<Consumer<Balance>> updates = this.balanceSubscribers.get(Constants.MAIN_BALANCE);
        if(updates != null) {
            for (Consumer<Balance> updater : this.balanceSubscribers.get(Constants.MAIN_BALANCE)) {
                updater.accept(bal);
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
