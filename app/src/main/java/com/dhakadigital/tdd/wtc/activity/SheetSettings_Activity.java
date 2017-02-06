package com.dhakadigital.tdd.wtc.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.OrgInfoAdapter;
import com.dhakadigital.tdd.wtc.adapter.SheetInfoAdapter;
import com.dhakadigital.tdd.wtc.constants.Constants;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.OrgInfo;
import com.dhakadigital.tdd.wtc.pojo.SheetInfo;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SheetSettings_Activity extends AppCompatActivity {

    //String
    String orgName;

    //Edittext
    EditText etSheetName,etSalaryRate;

    //Button
    Button btSave;

    //Spinner
    MaterialSpinner spOrganization;

    //RecycleView
    RecyclerView rvSheetList;

    //database
    Database database;

    //adapter
    SheetInfoAdapter sheetInfoAdapter;
    ArrayAdapter<String> adapter;

    //ArrayList
    ArrayList<SheetInfo> sheetInfos = new ArrayList<>();

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

    private void initView() {
        //String
        String orgName;

        //edittext
        etSheetName = (EditText) findViewById(R.id.etSheetName);
        etSalaryRate = (EditText) findViewById(R.id.etSalaryRate);

        //TODO initialize org widget
        /*etOrgName = (EditText) findViewById(R.id.etOriganizationName);
        etOrgAddress  = (EditText) findViewById(R.id.etOrgAddress);*/

        //button
        btSave = (Button) findViewById(R.id.btSave);

        //Spinner
        spOrganization = (MaterialSpinner) findViewById(R.id.spOrganiztion);

        //recycleview
        rvSheetList = (RecyclerView) findViewById(R.id.rvSheetList);

        //database
        database = new Database(getApplicationContext());

        initListener();
        setUpAdapter();
        setUpSpinner();
    }

    private void initListener() {

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO change org  name and org address
                int spnrPosition = spOrganization.getSelectedItemPosition();
                orgName = adapter.getItem(spnrPosition-1);
                Log.e("Spinner", spnrPosition +"");
                Toast.makeText(SheetSettings_Activity.this, orgName, Toast.LENGTH_SHORT).show();

                String sheet_name = etSheetName.getText().toString();

                if(!sheet_name.isEmpty()){

                    //setting data to database
                    SheetInfo sheetInfo = new SheetInfo();


                    sheetInfo.setName(sheet_name);
                    sheetInfo.setOrg_name(orgName);
                    sheetInfo.setOrg_address("heelo");
                    Double hourRate = Double.parseDouble(etSalaryRate.getText().toString());
                    sheetInfo.setHourRate(hourRate);
                    //TODO change org uid
                    sheetInfo.setOrg_uid("1");

                    database.insertSheetInfo(sheetInfo);

                    updateAdapter(sheetInfo);
                }else {
                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        spOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                orgName = adapter.getItem(position);
//                /*OrgInfo orgInfo = database.searchOrg(orgName);
//                Toast.makeText(getApplicationContext(), orgInfo.getAddress(), Toast.LENGTH_SHORT).show();*/
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        /*spOrganization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orgName = adapter.getItem(position);
            }
        });*/
    }

    private void updateAdapter(SheetInfo sheetInfo) {
        if (sheetInfoAdapter.getItemCount() > 1) {
            sheetInfoAdapter.add(sheetInfoAdapter.getItemCount() - 1, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();

        } else {
            sheetInfoAdapter.add(0, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();
        }

        Toast.makeText(getApplicationContext(), sheetInfo.getHourRate() + "", Toast.LENGTH_SHORT).show();
    }


    private void setUpAdapter() {
        //get data form database
        sheetInfos = database.getAllSheetInfo();

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

    void setUpSpinner(){
        ArrayList<OrgInfo>  orgInfos = database.getAllOrgInfo();
        String[] orgNames = new String[orgInfos.size()];
        for (int i = 0; i < orgInfos.size(); i++){
            orgNames[i] = orgInfos.get(i).getName();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orgNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrganization.setAdapter(adapter);
    }
}
