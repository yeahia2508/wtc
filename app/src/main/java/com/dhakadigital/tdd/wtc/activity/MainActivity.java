package com.dhakadigital.tdd.wtc.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "main_activity";
    //------------------------------------------VARIABLES & VIEW----------------------------------------------------------
    //String
    String sheetName;
    String sheetUID;
    Double dailyWage;
    String currentStartTime,currentEndTime;
//
    //TextView
    TextView tv_ern_start_end_time;
//
    //Button
    ImageButton btStartStop;
    //Fab
    FloatingActionButton fabToOrg;
//    //RecycleView
//    RecyclerView rvEarnList;
//
//    //Database
//    Database database;
//
//    //Adapter
//    EarningInfoAdapter earningInfoAdapter;
//
//    //SharedPrefrence
//    SharedPref sharedPref;
//
//    //Spinner
//    MaterialSpinner spinnerEarningNew;
//
//    //ArrayList
//    ArrayList<EarningInfo> earningInfos = new ArrayList<>();
//    ArrayList<SheetInfo> sheetInfos;
//
//    SimpleDateFormat timeFormat;
//    Calendar cc;
//    SimpleDateFormat df;


    //-----------------------------------ON CREATE MAIN ACTIVITY---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        database = new Database(getApplicationContext());
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        initView();
    }


    //-----------------------------------ON CLICK LISTENERS--------------------------------------------------------------
//    private void initListener() {

//        //---------------start button on click--------------
//        btStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String startTime;
//                Calendar cc = Calendar.getInstance();
//                int mHour = cc.get(Calendar.HOUR_OF_DAY);
//                int mMinute = cc.get(Calendar.MINUTE);
//
//                //text view shows start time
//                currentStartTime = timeFormat.format(cc.getTime());
//                tv_duration.setText("");
//                tv_start_time.setText("Start Time:"+ currentStartTime);
//
//                sharedPref.setKeyStarted(true);
//                btStart.setEnabled(false);
//                btStop.setEnabled(true);
//                spinnerEarningNew.setEnabled(false);
//
//                startTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
//                sharedPref.setStartTime(startTime);

//            }
//        });


//        //---------------stop button on click--------------
//        btStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    Date endDate = null;
//                    Date startDate = null;
//                    //text view shows start time
//                    String formattedDate = df.format(cc.getTime());
//                    currentEndTime = timeFormat.format(cc.getTime());
//                    String startTimeEndTime = currentStartTime + "/" + currentEndTime;
//
//                    int mHour = cc.get(Calendar.HOUR_OF_DAY);
//                    int mMinute = cc.get(Calendar.MINUTE);
////                    int mSecond = cc.get(Calendar.SECOND);
//                    long difference = 0;
//                    int hours = 0, min = 0;
//                    String endTime = String.format(Locale.ENGLISH, "%02d:%02d", mHour, mMinute);
//                    String startTime = sharedPref.getStartTime();
//
//                    try {
//                        endDate = timeFormat.parse(endTime);
//                        startDate = timeFormat.parse(startTime);
//                        difference = endDate.getTime() - startDate.getTime();
//                        if (difference < 0) {
//                            Date dateMax = timeFormat.parse("24:00");
//                            Date dateMin = timeFormat.parse("00:00");
//                            difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
//                        }
//                        int days = (int) (difference / (1000 * 60 * 60 * 24));
//                        hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//                        min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                //TODO: remove that later
//                Toast.makeText(MainActivity.this, "Hours: "+hours+"\nminutes: "+min, Toast.LENGTH_LONG).show();
//
//                    String totalWage = String.valueOf(dailyWage * (hours + (min / 60)));//total wage
//
//                    //showing duration in textView
//                String workedDuration = ""+ hours+":"+min;
//                    tv_duration.setText("Duration: " + hours+":"+min);
//                    //---EARNING INFO INSERTED INTO DATABASE HERE---
//                    EarningInfo insertEarningInfo = new EarningInfo(sheetUID, formattedDate, startTimeEndTime, workedDuration, totalWage);
//
//                    database.insertEarningInfo(insertEarningInfo);
//
//                    updateRecycleViewAdapter(insertEarningInfo);
//                    spinnerEarningNew.setEnabled(true);
//                    btStart.setEnabled(true);
//                    btStop.setEnabled(false);
//            }
//        });


        //---------------Spinner item select listner--------------
//        spinnerEarningNew.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                int sheetUIDinINT = sheetInfos.get(position).getId();
//                sheetUID = String.valueOf(sheetUIDinINT);
//                dailyWage = sheetInfos.get(position).getHourRate();
//                setUpEarningRecyclerAdapter(sheetUID);
////                bottomPanelShowInfo(sheetUIDinINT);
//                btStart.setEnabled(true);
//            }
//        });
//
//    }

    //show total of day worked, duration, wage;
//    private void bottomPanelShowInfo(int sheetUIDinINT) {
//        //this method will set a text in text view located in mainActivity total of day, duration, wage
//        if (database.getTotalDayCount(sheetUIDinINT)>0){
//
//            String totalOfDayDurationMoney="Total Day: "+database.getTotalDayCount(sheetUIDinINT)+
//                    "  Duration: "+database.getTotalDuration(sheetUIDinINT)+"  Wages: "+database.getTotalWage(sheetUIDinINT);
//            tv_bottom_panel.setText(totalOfDayDurationMoney);
//        }
//    }

    //-----VIEW ON CLICK END------

//    private void updateRecycleViewAdapter(EarningInfo insertEarningInfo) {
//        if (earningInfoAdapter.getItemCount() > 1) {
//            earningInfoAdapter.add(earningInfoAdapter.getItemCount() - 1, insertEarningInfo);
////            bottomPanelShowInfo(Integer.valueOf(insertEarningInfo.getSheet_uid()));//tvBottomPanel.setTotal
//            earningInfoAdapter.notifyDataSetChanged();
//
//        } else {
////            bottomPanelShowInfo(Integer.valueOf(insertEarningInfo.getSheet_uid()));//tvBottomPanel.setTotal
//            earningInfoAdapter.add(0, insertEarningInfo);
//            earningInfoAdapter.notifyDataSetChanged();
//        }
//
//    }
//
//
//    //-----------------------------------INITIALIZE VIEW HERE------------------------------------------------------------
//    private void initView() {
//        timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
//        cc = Calendar.getInstance();
//        df = new SimpleDateFormat("dd-MMM-yyyy");
//
//        //button
//        btStartStop = (ImageButton) findViewById(R.id.btStartStop);
//
//        //recycleView
//        rvEarnList = (RecyclerView) findViewById(R.id.rvEarnList);
//
//        //textView
//        tv_ern_start_end_time = (TextView) findViewById(R.id.tv_ern_start_end_time);
//        tv_bottom_panel = (TextView) findViewById(R.id.tvBottomPannel);
//
//        //database
//        database = new Database(getApplicationContext());
//
//        //Spinner
//        spinnerEarningNew = (MaterialSpinner) findViewById(R.id.spinnerEarningNew);
//
//        //SharedPref
//        sharedPref = new SharedPref(getApplicationContext());
//
//        //get data from database
//        sheetInfos = database.getAllSheetInfo();
//
//        initListener();
//        setUpSpinner();
//        setUpEarningRecyclerAdapter(sheetUID);
//    }
//
//    //-------------------------------------------SPINNER-----------------------------------------------------------------
//    private void setUpSpinner() {
//        //--------------new spinner------------
//        if (sheetInfos.size() != 0) {
//            spinnerEarningNew.setItems(sheetInfos);
//        } else {
//            spinnerEarningNew.setItems("create a sheet first");
//        }
//    }
//
//    //------------------------------------------RECYCLER VIEW------------------------------------------------------------
//    //this adapter will show item when something is selected in spinner
//    private void setUpEarningRecyclerAdapter(String sheetUid) {
//        //get data from database
////        earningInfos = database.getAllEarningInfo(); //for whole database value and we don't need that
//        earningInfos = database.getEarningInfoBySheet(sheetUid);
//
//        //set up adapter
//        earningInfoAdapter = new EarningInfoAdapter(earningInfos);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        layoutManager.setAutoMeasureEnabled(true);
//        rvEarnList.setLayoutManager(layoutManager);
//        rvEarnList.setAdapter(earningInfoAdapter);
//
//    }
//
//
//    //-------------------------ACTION MENU-------------NO OTHER USE----------------------
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        //Intent
//        Intent action_intent;
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_sheet_settings) {
//            action_intent = new Intent(this, SheetSettings_Activity.class);
//            startActivity(action_intent);
//        }
//        if (id == R.id.action_organization_settings) {
//            action_intent = new Intent(this, OrganizationActivitySettings.class);
//            startActivity(action_intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onResume() {
//        //get data from database
//        sheetInfos = database.getAllSheetInfo();
//        setUpSpinner();
//        earningInfoAdapter.notifyDataSetChanged();
//        super.onResume();
//    }
}
