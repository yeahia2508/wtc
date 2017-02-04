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
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.pojo.OrgInfo;

import java.util.ArrayList;

public class Setting extends AppCompatActivity {

    //Edittext
    EditText etOrgName, etOrgAddress;

    //Button
    Button btSave;

    //RecycleView
    RecyclerView rvOrgList;

    //database
    Database database;

    //adapter
    OrgInfoAdapter orgInfoAdapter;

    //ArrayList
    ArrayList<OrgInfo> orgInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        etOrgName = (EditText) findViewById(R.id.etOriganizationName);
        etOrgAddress  = (EditText) findViewById(R.id.etOrgAddress);
        //button
        btSave = (Button) findViewById(R.id.btSave);

        //recycleview
        rvOrgList = (RecyclerView) findViewById(R.id.rvOrgList);

        //database
        database = new Database(getApplicationContext());

        initListener();
        setUpAdapter();
    }

    private void initListener() {

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String org_name = etOrgName.getText().toString();
                String org_address = etOrgAddress.getText().toString();

                if(!org_name.isEmpty() && !org_address.isEmpty()){

                    //setting data to database
                    OrgInfo orgInfo = new OrgInfo();

                    orgInfo.setName(org_name);
                    orgInfo.setAddress(org_address);
                    database.insertOrgInfo(orgInfo);
                    updateAdapter();
                }else {
                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAdapter() {
    }

    private void setUpAdapter(){
        //get data form database
        orgInfos = database.getAllOrgInfo();

        //set up adapter
        orgInfoAdapter = new OrgInfoAdapter(orgInfos);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutmanager.setAutoMeasureEnabled(true);
        rvOrgList.setLayoutManager(layoutmanager);
        rvOrgList.setAdapter(orgInfoAdapter);

        //adapter click event
        orgInfoAdapter.SetOnItemClickListener(new OrgInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Item Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteIconClick(View view, int position) {
                orgInfoAdapter.delete(position);
            }

            @Override
            public void onEditIconClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Edit Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
