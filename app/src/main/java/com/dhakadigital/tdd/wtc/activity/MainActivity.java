package com.dhakadigital.tdd.wtc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.EarningInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.EarningInfo;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;
import com.dhakadigital.tdd.wtc.utils.SharedPref;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


//
//    --------------------------issue to be fixed----------------------------------
//    -(SOLVED) we have to save data in shared preferance when press in start     done         -
//    - ------------------------start button action------------------------------ -
//    - (SOLVED)take start time and date, conver date to milli second, save date and      -
//    - millis to, shared preferances, disable spinner and start button           -
//    - saving data.                                            -
//    - ------------------------stop button action------------------------------- -
//    - (SOLVED)take stop time and date convert to millis, bring start time/date millis   -
//    - from sharedPref, calculate duration,
//
//    - save all data to database and showRV -
//    -----------------------------------------------------------------------------
//


    private static final String TAG = "main_activity";
    //------------------------------------------VARIABLES & VIEW----------------------------------------------------------
    //String
    String sheetName;
    String sheetUID;

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

    //SharedPrefrence
    SharedPref sharedPref;


    //Spinner
    MaterialSpinner spinnerEarningNew;

    //ArrayList
    ArrayList<EarningInfo> earningInfos = new ArrayList<>();
    ArrayList<SheetInfo> sheetInfos;

    //long
    long date1Time, date2Time;

    //Date
    Date date1, date2;

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


    //-----------------------------------ON CLICK LISTENERS--------------------------------------------------------------
    private void initListener() {

        //---------------start button on click--------------
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startTime;
                Calendar cc = Calendar.getInstance();
                int mHour = cc.get(Calendar.HOUR_OF_DAY);
                int mMinute = cc.get(Calendar.MINUTE);

                sharedPref.setKeyStarted(true);
                btStart.setEnabled(false);
                spinnerEarningNew.setEnabled(false);

                startTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
                sharedPref.setStartTime(startTime);

            }
        });


        //---------------stop button on click--------------
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date endDate = null;
                Date startDate = null;

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                Calendar cc = Calendar.getInstance();
                int mHour = cc.get(Calendar.HOUR_OF_DAY);
                int mMinute = cc.get(Calendar.MINUTE);
                int mSecond = cc.get(Calendar.SECOND);

                String endTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
                String startTime = sharedPref.getStartTime();

                try {
                    endDate = timeFormat.parse(endTime);
                    startDate = timeFormat.parse(startTime);

                    long difference = endDate.getTime() - startDate.getTime();
                    if(difference < 0)
                    {
                        Date dateMax = timeFormat.parse("24:00");
                        Date dateMin = timeFormat.parse("00:00");
                        difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
                    }
                    int days = (int) (difference / (1000*60*60*24));
                    int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                    int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

                    Toast.makeText(getApplicationContext(),"Time: " + hours + ":" + min, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                btStart.setEnabled(true);
                spinnerEarningNew.setEnabled(true);
            }
        });


        //---------------Spinner item select listner--------------
        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String sheetName = sheetInfos.get(position).getName();
                int sheetUID = sheetInfos.get(position).getId();
                Snackbar.make(view, "position " + sheetUID + ", name " + sheetName, Snackbar.LENGTH_LONG).show();

            }
        });

    }//-----VIEW ON CLICK END------


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

        //SharedPref
        sharedPref = new SharedPref(getApplicationContext());

        //get data from database
        sheetInfos = database.getAllSheetInfo();

        initListener();
        setUpSpinner();
    }

    //-------------------------------------------SPINNER-----------------------------------------------------------------
    private void setUpSpinner() {
        //getting data from database table sheetInfo
        String[] sheetNames = new String[sheetInfos.size()];
        for (int i = 0; i < sheetInfos.size(); i++) {
            sheetNames[i] = sheetInfos.get(i).getName();
            int sheetUidFromDB = sheetInfos.get(i).getId();
            sheetUID = String.valueOf(sheetUidFromDB);
        }

        //--------------new spinner------------
        if (sheetInfos.size() != 0) {
//            spinnerEarningNew.setItems(sheetNames);
            spinnerEarningNew.setItems(sheetInfos);
        } else {
            spinnerEarningNew.setItems("create a sheet first");
        }
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


    //-------------------------ACTION MENU-------------NO OTHER USE----------------------
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
