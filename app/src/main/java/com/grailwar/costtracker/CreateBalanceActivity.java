package com.grailwar.costtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_balance);


        Button cancelButton = (Button) findViewById(R.id.create_balance_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button doneButton = (Button) findViewById(R.id.create_balance_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // get data from UI
                EditText nameField = (EditText) findViewById(R.id.create_balance_name);
                String name = nameField.getText().toString();

                EditText balanceField = (EditText) findViewById(R.id.create_balance_value_field);
                long balance = Long.parseLong(balanceField.getText().toString());

//                final Balance b = new Balance(name, balance);
                // for now, only allow a single balance
                final Balance b = new Balance("main", balance);

//                new Thread(new Runnable() {
//                    public void run() {
//                        AppDatabase db = AppDatabaseFactory.initialBuild(view.getContext());
//                        Balance bal = db.balanceDao().getBalanceWithName("main");
//                        if(bal != null) {
//                            db.balanceDao().updateBalance(b);
//                        } else {
//                            db.balanceDao().insert(b);
//                        }
//                    }
//                }).start();
                AppModel model = AppModelFactory.get();
                model.setMainBalance(b);
                finish();
            }
        });
    }
}
