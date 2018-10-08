package com.grailwar.costtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_transaction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), CreateTransactionActivity.class);
            startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup our DB accessor
        // Must be done first so we can use this elsewhere in the app
        this.appModel = AppModelFactory.initialBuild(this);
        this.setBalanceInView(appModel.getMainBalance());
        this.appModel.subscribeToMainBalance((Balance b) -> {
            setBalanceInView(b);
        });
        new Thread(() -> {
//            this.appModel.getLiveBalance().observe(this, new Observer<Balance>() {
//                @Override
//                public void onChanged(@NonNull final Balance b) {
//                    setBalanceInView(b);
//                }
//            });
        }).start();


        // Get balance
//        balanceViewModel = ViewModelProviders.of(this).get(AppModel.class);
//        balanceViewModel.getBalance().observe(MainActivity.this, new Observer<Balance>() {
//            @Override
//            public void onChanged(Balance bal) {
//                final TextView balanceTextView = (TextView) findViewById(R.id.balance_text_view);
//                balanceTextView.post(new Runnable() {
//                    public void run() {
//                        balanceTextView.setText(bal.toString());
//                    }
//                });
//            }
//        });

//        getBalance(this);


        // Setup Budgets View
        // Get budgets from database
        // mocked for now
        String[] budgetsStringArray = {"test", "asdf"};

        ArrayAdapter<String> budgetsAdapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            budgetsStringArray
        );

        ListView budgetsListView = (ListView) findViewById(R.id.budgetsListView);
        budgetsListView.setAdapter(budgetsAdapter);
    }

    private void setBalanceInView(final Balance b) {
        final TextView balanceTextView = (TextView) findViewById(R.id.balance_text_view);
        balanceTextView.setText(b.toString());
    }

    /**
     * Get balance from database and set it in the view
     */
    private void getBalance(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                AppDatabase db = AppDatabaseFactory.build(context);
                final String balanceText;
//                List<Balance> balances = db.balanceDao().getAll();

//                if(balances.size() > 0) {
//                    balanceText = balances.get(0).toString();
//                } else {
//                    balanceText = "No Balance";
//                }


                Balance bal = db.balanceDao().getBalanceWithName("main");
                if(bal == null) {
                    balanceText = "No Balance";
                } else {
                    balanceText = bal.toString();
                }

                final TextView balanceTextView = (TextView) findViewById(R.id.balance_text_view);
                balanceTextView.post(new Runnable() {
                    public void run() {
                        balanceTextView.setText(balanceText);
                    }
                });

//                LiveData<Balance> liveBalance = db.balanceDao().getLiveBalanceWithName("main");
//                liveBalance.observe(, balance -> {
//
//                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here. Use this to change the content view
        int id = item.getItemId();

        if (id == R.id.nav_transaction) {
            Intent intent = new Intent(this, CreateTransactionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_budgets) {
            Intent intent = new Intent(this, CreateBudgetActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, ViewTransactionHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_balance) {

        } else if (id == R.id.nav_create_balance) {
            Intent intent = new Intent(this, CreateBalanceActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
