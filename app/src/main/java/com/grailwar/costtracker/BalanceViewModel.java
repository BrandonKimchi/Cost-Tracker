package com.grailwar.costtracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

/**
 * Created by Brandon on 9/28/2018.
 */

public class BalanceViewModel extends AndroidViewModel {
    private LiveData<Balance> balance;

    public BalanceViewModel (Application application) {
        super(application);

    }
}
