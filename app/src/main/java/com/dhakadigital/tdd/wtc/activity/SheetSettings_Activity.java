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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.SheetInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.OrgInfo;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class SheetSettings_Activity extends AppCompatActivity {
    private static final String TAG = "sheet info";


    //-------------------------------------ALL VARIABLE & VIEW ----------------------------------------------------
    //String
    String orgName;
    String orgAddress;
    String orgUid;
    //Edittext
    EditText etSheetName, etSalaryRate;
    //Button
    Button btSave;
    //Spinner
    com.jaredrummler.materialspinner.MaterialSpinner spOrganization;
    //RecycleView
    RecyclerView rvSheetList;
    //database
    Database database;
    //adapter
    SheetInfoAdapter sheetInfoAdapter;
    //ArrayList
    ArrayList<SheetInfo> sheetInfos = new ArrayList<>();


    //-------------------------------------MAIN ACTIVITY----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_settings_);
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


    //-------------------------------------INITIALIZATION VIEW & VARIABLE----------------------------------------------------
    private void initView() {
        //edittext
        etSheetName = (EditText) findViewById(R.id.etSheetName);
        etSalaryRate = (EditText) findViewById(R.id.etSalaryRate);
        //button
        btSave = (Button) findViewById(R.id.btSave);
        //Spinner
        spOrganization = (com.jaredrummler.materialspinner.MaterialSpinner) findViewById(R.id.spOrganiztion);
        //recycleview
        rvSheetList = (RecyclerView) findViewById(R.id.rvSheetList);
        //database
        database = new Database(getApplicationContext());
        //get data form database
        sheetInfos = database.getAllSheetInfo();

        setUpSpinner();
        setUpAdapter();
        initListener();
    }


    //-----------------------------------------ONCLICK HERE-----------------------------------------------
    private void initListener() {
        //---------------save button to save sheet information------------
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sheet_name = etSheetName.getText().toString();
                Double hourRate = Double.parseDouble(etSalaryRate.getText().toString());

                if (!sheet_name.isEmpty() && hourRate >= 0) {
                    //setting data to database
                    SheetInfo sheetInfo = new SheetInfo(sheet_name, orgUid, orgName, orgAddress, hourRate);

                    //inserting data here about sheet info -----------------------
                    database.insertSheetInfo(sheetInfo);
                    updateAdapter(sheetInfo);
                } else {
                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //---------------Spinner item select listner--------------
        spOrganization.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });
    }


    //-----------------------------------------ADAPTER SETTING HERE-----------------------------------------------
    private void updateAdapter(SheetInfo sheetInfo) {
        if (sheetInfoAdapter.getItemCount() > 1) {
            sheetInfoAdapter.add(sheetInfoAdapter.getItemCount() - 1, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();

        } else {
            //TODO: boss why we pass 0 in adapter? please write here
            sheetInfoAdapter.add(0, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();
        }

        Toast.makeText(getApplicationContext(), sheetInfo.getHourRate() + "", Toast.LENGTH_SHORT).show();
    }


    private void setUpAdapter() {
        sheetInfos = database.getAllSheetInfo();
        Log.e(TAG, "setUpAdapter getting data or not? " + sheetInfos.get(0).getName());
        //set up adapter
        sheetInfoAdapter = new SheetInfoAdapter(sheetInfos);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutmanager.setAutoMeasureEnabled(true);
        rvSheetList.setLayoutManager(layoutmanager);
        rvSheetList.setAdapter(sheetInfoAdapter);

        //adapter click event
        sheetInfoAdapter.SetOnItemClickListener(new SheetInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Item Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteIconClick(View view, int position) {
                sheetInfoAdapter.delete(position);
            }

            @Override
            public void onEditIconClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Edit Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //----------spinner adapter set----------------------------
    void setUpSpinner() {
        final ArrayList<OrgInfo> orgInfos = database.getAllOrgInfo();
        for (int i = 0; i < orgInfos.size(); i++) {
            orgName = orgInfos.get(i).getName();
            int orgUidHere = orgInfos.get(i).getId();
            orgUid = String.valueOf(orgUidHere);
            orgAddress = orgInfos.get(i).getAddress();
        }
        //-------------this spinner can process direct arraylist---------
        if (orgInfos.size() != 0) {
            spOrganization.setItems(orgInfos);
        } else {
            spOrganization.setItems("create a organization first");
            Intent activityOrganizationSwitch = new Intent(this, OrganizationActivitySettings.class);
            startActivity(activityOrganizationSwitch);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpSpinner();
        sheetInfoAdapter.notifyDataSetChanged();
    }
}
