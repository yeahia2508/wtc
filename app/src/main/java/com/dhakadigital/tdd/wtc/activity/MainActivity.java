package com.dhakadigital.tdd.wtc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.EarningInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.EarningInfo;
import com.dhakadigital.tdd.wtc.pojo.OrgInfo;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

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
    ArrayAdapter<String> spnr_adapter;

    //Spinner
    MaterialSpinner spSheetName;

    //ArrayList
    ArrayList<EarningInfo> earningInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: it was demo, delete it
        EarningInfo earningInfo = new EarningInfo("12/12/2012","00","00","00");
        earningInfos.add(earningInfo);

        database.insertEarningInfo(earningInfo);


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

        //Spinner
        spSheetName = (MaterialSpinner) findViewById(R.id.spnr_sheetName);

        initListener();
        setUpAdapter();
        setUpSpinner();
    }

    private void setUpSpinner() {
        ArrayList<SheetInfo>  sheetInfos = database.getAllSheetInfo();
        String[] sheetNames = new String[sheetInfos.size()];
        for (int i = 0; i < sheetInfos.size(); i++){
            sheetNames[i] = sheetInfos.get(i).getName();
        }
        spnr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sheetNames);
        spnr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSheetName.setAdapter(spnr_adapter);
    }

    private void setUpAdapter() {
        //get data from database
        earningInfos = database.getAllEarningInfo();

        //set up adapter
        earningInfoAdapter = new EarningInfoAdapter(earningInfos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        rvEarnList.setLayoutManager(layoutManager);
        rvEarnList.setAdapter(earningInfoAdapter);

    }

    private void initListener() {

        //---------------stopp button on click--------------
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spPosition = spSheetName.getSelectedItemPosition();
                String sheetName = spnr_adapter.getItem(spPosition-1);
                Toast.makeText(MainActivity.this, "from database "+sheetName, Toast.LENGTH_SHORT).show();
            }
        });

        //---------------Spinner item select listner--------------
        spSheetName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set an error report if nothing is selected in spinner
            }
        });

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

        //Intent
        Intent action_intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sheet_settings) {
            action_intent = new Intent(this,SheetSettings_Activity.class);
            startActivity(action_intent);
        }
        if (id == R.id.action_organization_settings) {
            action_intent = new Intent(this,Setting.class);
            startActivity(action_intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
