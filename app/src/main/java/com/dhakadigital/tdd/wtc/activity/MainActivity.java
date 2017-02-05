package com.dhakadigital.tdd.wtc.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.EarningInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.EarningInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //TextView
    TextView tv_start_time, tv_duration;

    //Button
    Button btStart, btStop;

    //RecycleView
    RecyclerView rvEarnList;

    //Database
    Database database;

    //Adapter
    EarningInfoAdapter earningInfoAdapter;

    //ArrayList
    ArrayList<EarningInfo> earningInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
EarningInfo earningInfo = new EarningInfo("00","00","00","00");
        earningInfos.add(earningInfo);

        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView() {
        //button
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        //recycleView
        rvEarnList = (RecyclerView) findViewById(R.id.rvEarnList);
        //textView
        tv_start_time = (TextView) findViewById(R.id.bt_start);
        tv_duration = (TextView) findViewById(R.id.tvDuration);
        //database
        database = new Database(getApplicationContext());

        initListener();
        setUpAdapter();
    }

    private void setUpAdapter() {
        //get data from database
//        earningInfos;
    }

    private void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
