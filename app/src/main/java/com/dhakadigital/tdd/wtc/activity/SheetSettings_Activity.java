package com.dhakadigital.tdd.wtc.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.dhakadigital.tdd.wtc.adapter.SheetInfoAdapter;
import com.dhakadigital.tdd.wtc.database.Database;
import com.dhakadigital.tdd.wtc.model.SheetInfo;

import java.util.ArrayList;

public class SheetSettings_Activity extends AppCompatActivity {
    private static final String TAG = "sheet info";


    //-------------------------------------ALL VARIABLE & VIEW ----------------------------------------------------
    //String
    String sheetName, wagePerHour;
    //Button
    FloatingActionButton fabAddSheet;
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
    }


    //-------------------------------------INITIALIZATION VIEW & VARIABLE----------------------------------------------------
    private void initView() {

        //button
        fabAddSheet = (FloatingActionButton) findViewById(R.id.fabAddSheet);
        //recycleview
        rvSheetList = (RecyclerView) findViewById(R.id.rvSheetList);
        //database
        database = new Database(getApplicationContext());
        //get data form database
        sheetInfos = database.getAllSheetInfo();

        setUpAdapter();
        initListener();
    }

    //-----------------------------------------ONCLICK HERE-----------------------------------------------
    private void initListener() {

        fabAddSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here will be the alart dialogue
                //TODO: make an sheet info object (get org infoo by intent)
                showAlartDialogue(view, "", "", 0, "insert");//perameter (view, orgName, orgAddress, sheetUID, dataUpdate/dataInsert
            }
        });
    }

    //-----------------------------------------ADAPTER SETTING HERE-----------------------------------------------
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
                showAlartDialogue(view, sheetInfos.get(position).getName(), sheetInfos.get(position).getHourRate().toString(), sheetInfos.get(position).getId(), "update");
            }
        });
    }

    private void showAlartDialogue(View view, String getSheetName, String getWagePerHour, final int positionInDb, String updateORinsert) {
        final String status = updateORinsert;
        //Make new Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(SheetSettings_Activity.this);
        dialog.setTitle("Create new Sheet");

        LinearLayout layout = new LinearLayout(SheetSettings_Activity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        dialog.setMessage("Sheet Name: ");
        final EditText etSheetName = new EditText(SheetSettings_Activity.this);
        etSheetName.setText(getSheetName);
        layout.addView(etSheetName);

        dialog.setMessage("Wage/Hour: ");
        final EditText etWagePerHour = new EditText(SheetSettings_Activity.this);
        etWagePerHour.setText(getWagePerHour);
        layout.addView(etWagePerHour);

        dialog.setView(layout);
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                sheetName = etSheetName.getText().toString();
                wagePerHour = etWagePerHour.getText().toString();
                if (status.equals("insert")) {
                    //TODO: declare this in start
                    SheetInfo newSheet = new SheetInfo("1", "1", sheetName, "dummYorgName", "dummyOrgAddress", Double.valueOf(wagePerHour));//TODO: it is dummy data

                    database.insertSheetInfo(newSheet);
                    updateAdapter(newSheet);
                } else {
                    int result = database.updateSheetInfo(sheetName, Double.valueOf(wagePerHour), positionInDb);
                    if (result > 0)
                        Toast.makeText(SheetSettings_Activity.this, "Information Updated", Toast.LENGTH_SHORT).show();
                }
                refrestRecyclerView();
            }
        });
//                dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        //What ever you want to do with the value
//                    }
//                });
        dialog.show();
        if (!sheetName.isEmpty() && !wagePerHour.isEmpty()) {
            Snackbar.make(view, "New sheet created. \nName: " + sheetName + "\nWage/Hour: " + wagePerHour, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public void refrestRecyclerView() {
        sheetInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrestRecyclerView();
    }
}
