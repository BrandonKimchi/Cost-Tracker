package com.grailwar.costtracker;

import android.content.Context;

/**
 * Created by Brandon on 9/28/2018.
 */

public class AppModelFactory {

    static AppModel model;

    public static AppModel initialBuild(Context c) {
        if(model == null) {
            model = new AppModel(c);
        }

        return model;
    }

    public static AppModel get() {
        if(model == null) {
            throw new IllegalStateException("Make sure you build the model first.");
        }
        return model;
    }

}
