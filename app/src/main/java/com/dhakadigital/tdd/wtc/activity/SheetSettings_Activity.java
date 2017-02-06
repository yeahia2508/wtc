package com.dhakadigital.tdd.wtc.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class SheetSettings_Activity extends AppCompatActivity {

    //Edittext
    EditText etOrgName, etOrgAddress, etSheetName;

    //Button
    Button btSave;

    //RecycleView
    RecyclerView rvSheetList;

    //database
    Database database;

    //adapter
    SheetInfoAdapter sheetInfoAdapter;

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
        //edittext
        etSheetName = (EditText) findViewById(R.id.etSheetName);

        //TODO initialize org widget
        /*etOrgName = (EditText) findViewById(R.id.etOriganizationName);
        etOrgAddress  = (EditText) findViewById(R.id.etOrgAddress);*/
        //button
        btSave = (Button) findViewById(R.id.btSave);

        //recycleview
        rvSheetList = (RecyclerView) findViewById(R.id.rvSheetList);

        //database
        database = new Database(getApplicationContext());

        initListener();
        setUpAdapter();
    }

    private void initListener() {

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO change org  name and org address

                String org_name = "The Dhaka Digital";
                String org_address = "Dhaka";
                String sheet_name = etSheetName.getText().toString();

                if(!sheet_name.isEmpty()){

                    //setting data to database
                    SheetInfo sheetInfo = new SheetInfo();


                    sheetInfo.setName(org_name);
                    sheetInfo.setOrg_name(org_address);
                    sheetInfo.setOrg_address(sheet_name);

                    //TODO change org uid
                    sheetInfo.setOrg_uid("1");

                    database.insertSheetInfo(sheetInfo);

                    updateAdapter(sheetInfo);
                }else {
                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAdapter(SheetInfo sheetInfo) {
        if (sheetInfoAdapter.getItemCount() > 1) {
            sheetInfoAdapter.add(sheetInfoAdapter.getItemCount() - 1, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();

        } else {
            sheetInfoAdapter.add(0, sheetInfo);
            sheetInfoAdapter.notifyDataSetChanged();
        }
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
}
