package com.dhakadigital.tdd.wtc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.adapter.OrgInfoAdapter;
import com.dhakadigital.tdd.wtc.constants.Constants;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.model.OrgInfo;
import com.dhakadigital.tdd.wtc.model.SheetInfo;
import com.dhakadigital.tdd.wtc.utils.SharedPref;

import java.util.ArrayList;

public class OrganizationActivitySettings extends AppCompatActivity {

    //save selected organization position in shared preference
    String orgName, orgAddress;

    //RecycleView
    RecyclerView rvOrgList;

    FloatingActionButton fabCreateOrg;
    //database
    Database database;

    //adapter
    OrgInfoAdapter orgInfoAdapter;
    SharedPref sharedPref;
    //ArrayList
    ArrayList<OrgInfo> orgInfosArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        rvOrgList = (RecyclerView) findViewById(R.id.rvOrgList);
        database = new Database(getApplicationContext());
        orgInfosArrayList = database.getAllOrgInfo();
        fabCreateOrg = (FloatingActionButton) findViewById(R.id.fab);
        sharedPref = new SharedPref(getApplicationContext());

        initListener();
        setUpAdapter();
    }

    private void initListener() {

        fabCreateOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlartDialogue(view,"","",1,"insert" );
            }
        });
    }

    private void updateAdapter(OrgInfo orgInfo) {
        if (orgInfoAdapter.getItemCount() > 1) {
            orgInfoAdapter.add(orgInfoAdapter.getItemCount() - 1, orgInfo);
            orgInfoAdapter.notifyDataSetChanged();

        } else {
            orgInfoAdapter.add(0, orgInfo);
            orgInfoAdapter.notifyDataSetChanged();
        }
    }


    private void setUpAdapter() {
        //get data form database
        orgInfosArrayList = database.getAllOrgInfo();

        //set up adapter
        orgInfoAdapter = new OrgInfoAdapter(orgInfosArrayList);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutmanager.setAutoMeasureEnabled(true);
        rvOrgList.setLayoutManager(layoutmanager);
        rvOrgList.setAdapter(orgInfoAdapter);

        //adapter click event
        orgInfoAdapter.SetOnItemClickListener(new OrgInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent iToSheetSettingActivity = new Intent(OrganizationActivitySettings.this, SheetSettings_Activity.class);
                iToSheetSettingActivity.putExtra("Organizationinfo", orgInfosArrayList.get(position).getId());//sending organization unique id to sheet setting
                startActivity(iToSheetSettingActivity);
            }

            @Override
            public void onDeleteIconClick(View view, int position) {
                orgInfoAdapter.delete(position);
            }

            @Override
            public void onEditIconClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Edit Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOrgSelectClick(View view, int position) {
                sharedPref.setPrefSelectedOrg(position);
            }
        });
    }

    private void showAlartDialogue(View view, String getOrgName, String getOrgAddress, final int positionInDb, String updateORinsert) {
        final String status = updateORinsert;
        //Make new Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(OrganizationActivitySettings.this);
        dialog.setTitle("Create new Organization");
        LinearLayout layout = new LinearLayout(OrganizationActivitySettings.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        dialog.setMessage("Organization Name: ");
        final EditText etOrgName = new EditText(OrganizationActivitySettings.this);
        etOrgName.setText(getOrgName);
        layout.addView(etOrgName);

        dialog.setMessage("Address: ");
        final EditText etOrgAddress = new EditText(OrganizationActivitySettings.this);
        etOrgAddress.setText(getOrgAddress);
        layout.addView(etOrgAddress);

        dialog.setView(layout);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                orgName = etOrgName.getText().toString();
                orgAddress = etOrgAddress.getText().toString();
                if (status.equals("insert")) {
                    //TODO: declare this in start
                    OrgInfo newOrgInfo = new OrgInfo("1", orgName, orgAddress);//TODO: it is dummy data
                    database.insertOrgInfo(newOrgInfo);
                    updateAdapter(newOrgInfo);
                } else {
                    int result = database.updateOrgInfo(orgName, orgAddress, positionInDb);
                    if (result > 0)
                        Toast.makeText(OrganizationActivitySettings.this, "Information Updated", Toast.LENGTH_SHORT).show();
                }
                refrestRecyclerView();
            }
        });
        dialog.show();
        if (!orgName.isEmpty() && !orgAddress.isEmpty()) {
            Snackbar.make(view, "New organization created. \nName: " + orgName + "\nAddress: " + orgAddress, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void refrestRecyclerView() {
        orgInfoAdapter.notifyDataSetChanged();
    }

}
