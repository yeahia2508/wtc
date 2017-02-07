package com.dhakadigital.tdd.wtc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.EarningInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.EarningInfo;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";
    //------------------------------------------VARIABLES & VIEW----------------------------------------------------------
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

    //Spinner
//    MaterialSpinner spSheetName; // old spinner
    MaterialSpinner spinnerEarningNew;//imports jaredrummler.materialspinner.MaterialSpinner

    //ArrayList
    ArrayList<EarningInfo> earningInfos = new ArrayList<>();


    //-----------------------------------ON CREATE MAIN ACTIVITY---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    //-----------------------------------INITIALIZE VIEW HERE------------------------------------------------------------
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

        //Spinner old
        spinnerEarningNew = (MaterialSpinner) findViewById(R.id.spinnerEarningNew);

        initListener();
        setUpSpinner();
    }

    //-------------------------------------------SPINNER-----------------------------------------------------------------
    private void setUpSpinner() {
        //getting data from database table sheetInfo
        final ArrayList<SheetInfo> sheetInfos = database.getAllSheetInfo();
        String[] sheetNames = new String[sheetInfos.size()];
        for (int i = 0; i < sheetInfos.size(); i++) {
            sheetNames[i] = sheetInfos.get(i).getName();
            int position = sheetInfos.get(i).getId();
            Log.e(TAG, "from db sheet name "+sheetNames[i] );
            Log.e(TAG, "from db sheet id/position "+position );
        }

        //--------------new spinner------------
        if (sheetInfos.size()!=0){
//            spinnerEarningNew.setItems(sheetNames);
            spinnerEarningNew.setItems(sheetInfos);
        }else {
        spinnerEarningNew.setItems("create a sheet first");
        }
        //---------------Spinner item select listner--------------
        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String sheetName = sheetInfos.get(position).getName();
                int sheetUID = sheetInfos.get(position).getId();
                Snackbar.make(view,"position "+sheetUID+", name "+sheetName, Snackbar.LENGTH_LONG).show();

            }
        });

//        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<ArrayList<SheetInfo>>() {
//
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, ArrayList<SheetInfo> item) {
//                String sheetName = sheetInfos.get(position).getName();
//                int sheetUID = sheetInfos.get(position).getId();
//                Snackbar.make(view,"position "+sheetUID+", name "+sheetName, Snackbar.LENGTH_LONG).show();
//            }
//
////            @Override
////            public void onItemSelected(MaterialSpinner view, int position, long id, Integer item) {
////
////            }
//
////            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//////                Snackbar.make(view, "Clicked " + position, Snackbar.LENGTH_LONG).show();
//////                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
////            }
//        });
    }

    //------------------------------------------RECYCLER VIEW------------------------------------------------------------
    //this adapter will show item when something is selected in spinner
    private void setUpEarningRecyclerAdapter(String sheetUid) {
        //get data from database
//        earningInfos = database.getAllEarningInfo(); //for whole database value and we don't need that
        earningInfos = database.getEarningInfoBySheet(sheetUid);

        //set up adapter
        earningInfoAdapter = new EarningInfoAdapter(earningInfos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        rvEarnList.setLayoutManager(layoutManager);
        rvEarnList.setAdapter(earningInfoAdapter);

    }


    //-----------------------------------ON CLICK LISTENERS--------------------------------------------------------------
    private void initListener() {

        //---------------stop button on click--------------
//        btStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int spPosition = spSheetName.getSelectedItemPosition();
//                String sheetUID = spnr_adapter.getItem(spPosition-1);
//                Toast.makeText(MainActivity.this, "from database ", Toast.LENGTH_SHORT).show();
//
//                //TODO: it was demo, delete it
//                EarningInfo earningInfo = new EarningInfo("","later","14","00");
//                earningInfos.add(earningInfo);
//
//                database.insertEarningInfo(earningInfo);
//            }
//        });



//
//        //---------------Spinner item select listner--------------
//        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this, "items array position "+position+"\nlong id is "+id, Toast.LENGTH_SHORT).show();
//            }
//        });

    }//-----VIEW ON CLICK END------





    //------------------------------------------ACTION MENU-------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Intent
        Intent action_intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sheet_settings) {
            action_intent = new Intent(this, SheetSettings_Activity.class);
            startActivity(action_intent);
        }
        if (id == R.id.action_organization_settings) {
            action_intent = new Intent(this, OrganizationActivitySettings.class);
            startActivity(action_intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpSpinner();
    }
}
