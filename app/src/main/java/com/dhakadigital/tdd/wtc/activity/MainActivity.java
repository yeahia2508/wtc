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
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.EarningInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.model.EarningInfo;
import com.dhakadigital.tdd.wtc.model.SheetInfo;
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
    Double dailyWage;
    String currentStartTime,currentEndTime;

    //boolean
    boolean spinnerSheetSelectedOrNot=false;

    //TextView
    TextView tv_start_time, tv_duration,tv_bottom_panel;

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


    SimpleDateFormat timeFormat;
    Calendar cc;
    SimpleDateFormat df;


    //-----------------------------------ON CREATE MAIN ACTIVITY---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initView();
        //TODO: Log please remove it
        Log.e(TAG, "onClick: stop button spinner selected or not position "+spinnerSheetSelectedOrNot );
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

                //text view shows start time
                currentStartTime = timeFormat.format(cc.getTime());
                tv_duration.setText("");
                tv_start_time.setText("Start Time:"+ currentStartTime);

                sharedPref.setKeyStarted(true);
                btStart.setEnabled(false);
                btStop.setEnabled(true);
                spinnerEarningNew.setEnabled(false);

                startTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
                sharedPref.setStartTime(startTime);

            }
        });


        //---------------stop button on click--------------
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!spinnerSheetSelectedOrNot){
                    Snackbar.make(view,"Please select a sheet first",Snackbar.LENGTH_SHORT);
                    //TODO: Log please remove it
                    Log.e(TAG, "onClick: stop button spinner selected or not position "+spinnerSheetSelectedOrNot );
                }else {
                    Date endDate = null;
                    Date startDate = null;

                    //TODO: Log please remove it
                    Log.e(TAG, "onClick: stop button spinner selected or not position "+spinnerSheetSelectedOrNot );

                    //text view shows start time
                    tv_start_time.setText("");
                    String formattedDate = df.format(cc.getTime());
                    currentEndTime = timeFormat.format(cc.getTime());
                    String startTimeEndTime = currentStartTime + "/" + currentEndTime;

                    int mHour = cc.get(Calendar.HOUR_OF_DAY);
                    int mMinute = cc.get(Calendar.MINUTE);
                    int mSecond = cc.get(Calendar.SECOND);

                    String endTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
                    String startTime = sharedPref.getStartTime();

                    long difference = 0;
                    int hours = 0, min = 0;
                    try {
                        endDate = timeFormat.parse(endTime);
                        startDate = timeFormat.parse(startTime);

                        difference = endDate.getTime() - startDate.getTime();
                        if (difference < 0) {
                            Date dateMax = timeFormat.parse("24:00");
                            Date dateMin = timeFormat.parse("00:00");
                            difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
                        }
                        int days = (int) (difference / (1000 * 60 * 60 * 24));
                        hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                        min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

                        Toast.makeText(getApplicationContext(), "Time: " + hours + ":" + min, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String totalWage = String.valueOf(dailyWage * (hours + (min / 60)));//total wage


                    //showing duration in textView
                    tv_duration.setText("Duration: " + difference);
                    //TODO: Log test clear the log
                    Log.e(TAG, "onDatabase insert: \ndailyWage = " + dailyWage + "\nHour = " + hours + "\nMinute = " + min + "\n-------------" +
                            "totalWage = " + totalWage + "\n\n\nreview information going in database\n"+
                    "sheet UID = "+sheetUID+"\nDate = "+formattedDate+"\nStart & End time = "+startTimeEndTime+"\n" +
                            "Duration = "+difference+"\nTotal Wage = "+totalWage);
//                String sTotalWage = String.valueOf(totalWage);
                    //---EARNING INFO INSERTED INTO DATABASE HERE---
                    EarningInfo insertEarningInfo = new EarningInfo(sheetUID, formattedDate, startTimeEndTime, difference, totalWage);
                    //TODO:all wages are same : showing toast
                    Toast.makeText(MainActivity.this, "totalDuration: " + difference + "\nwage from Sheet: " + dailyWage +
                            "totalCalculated wage: " + totalWage, Toast.LENGTH_LONG).show();
                    database.insertEarningInfo(insertEarningInfo);

                    updateRecycleViewAdapter(insertEarningInfo);
                    btStart.setEnabled(true);
                    spinnerEarningNew.setEnabled(true);

                }
            }
        });


        //---------------Spinner item select listner--------------
        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                int sheetUidFromDB = sheetInfos.get(position).getId();
                sheetUID = String.valueOf(sheetUidFromDB);
                dailyWage = sheetInfos.get(position).getHourRate();
                spinnerSheetSelectedOrNot=true;
                setUpEarningRecyclerAdapter(sheetUID);
                int sheetUIDinINT = Integer.valueOf(sheetUID);
                String totalOfDayDurationMoney="Total Day: "+database.getTotalDayCount(sheetUIDinINT)+
                        "  Duration: "+database.getTotalDuration(sheetUIDinINT)+"  Wages: "+database.getTotalWage(sheetUIDinINT);
                tv_bottom_panel.setText(totalOfDayDurationMoney);
            }
        });

    }

    //-----VIEW ON CLICK END------

    private void updateRecycleViewAdapter(EarningInfo insertEarningInfo) {
        if (earningInfoAdapter.getItemCount() > 1) {
            earningInfoAdapter.add(earningInfoAdapter.getItemCount() - 1, insertEarningInfo);
            earningInfoAdapter.notifyDataSetChanged();

        } else {
            earningInfoAdapter.add(0, insertEarningInfo);
            earningInfoAdapter.notifyDataSetChanged();
        }

    }


    //-----------------------------------INITIALIZE VIEW HERE------------------------------------------------------------
    private void initView() {


        timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        cc = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");

        //button
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btStop.setEnabled(false);

        //recycleView
        rvEarnList = (RecyclerView) findViewById(R.id.rvEarnList);

        //textView
        tv_start_time = (TextView) findViewById(R.id.tvStartTime);
        tv_duration = (TextView) findViewById(R.id.tvDuration);
        tv_bottom_panel = (TextView) findViewById(R.id.tvBottomPannel);

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
        setUpEarningRecyclerAdapter(sheetUID);
    }

    //-------------------------------------------SPINNER-----------------------------------------------------------------
    private void setUpSpinner() {
        //--------------new spinner------------

        if (sheetInfos.size() != 0) {
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

        //get data from database
        sheetInfos = database.getAllSheetInfo();
        setUpSpinner();
        earningInfoAdapter.notifyDataSetChanged();
        spinnerSheetSelectedOrNot=true;
        super.onResume();
    }
}
