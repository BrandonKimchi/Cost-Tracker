package com.grailwar.costtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class CreateTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        AppDatabase db = AppDatabaseFactory.build(this);

        List<DBTransaction> allTransactions = db.dbTransactionDao().getAll();
    }

}
