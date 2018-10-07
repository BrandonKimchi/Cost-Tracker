package com.grailwar.costtracker;

import android.content.Context;

/**
 * Created by Brandon on 9/28/2018.
 */

public class AppModelFactory {

    static AppModel model;

    public static AppModel build(Context c) {
        if(model == null) {
            model = new AppModel(c);
        }

        return model;
    }

}
