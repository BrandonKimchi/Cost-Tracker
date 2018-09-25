package com.grailwar.costtracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewTransactionHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_history);

        // fire bg event to query the db and create a list of transactions
        final Context context = this;
        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabaseFactory.build(context);
                List<DBTransaction> transactions = db.dbTransactionDao().getAll();
                String[] values = new String[transactions.size()];
                for(int i = 0; i < transactions.size(); i++) {
                    values[i] = transactions.get(i).toString();
                }

                ListView historyListView = (ListView) findViewById(R.id.transaction_history_list);
                ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        values
                );
                historyListView.setAdapter(historyAdapter);
            }
        }).start();
    }
}
