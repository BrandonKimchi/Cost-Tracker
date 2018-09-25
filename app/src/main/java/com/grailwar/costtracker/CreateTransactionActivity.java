package com.grailwar.costtracker;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class CreateTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        // Handle the submit button
        Button submitButton = (Button) findViewById(R.id.submit_transaction_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // get data from UI
                EditText amountField = (EditText) findViewById(R.id.create_transaction_amount_field);
                long amount = Long.parseLong(amountField.getText().toString());
                final DBTransaction transaction = new DBTransaction(amount, "asdf");

                new Thread(new Runnable() {
                    public void run() {
                        AppDatabase db = AppDatabaseFactory.build(view.getContext());
                        db.dbTransactionDao().insert(transaction);
                    }
                }).start();
                finish();
            }
        });
    }

}