package com.dhakadigital.tdd.wtc.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dhakadigital.tdd.wtc.R;
import com.dhakadigital.tdd.wtc.model.Response;
import com.dhakadigital.tdd.wtc.service.ApiService;
import com.dhakadigital.tdd.wtc.model.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private TextView tvResponse;
    private FloatingActionButton fab;
    private Button btSave;
    private EditText etEmail, etPassword;
    private Retrofit retrofit;
    private ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initRetrofit();
        initView();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.105:8080/localhost/wtc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    private void initView() {
        //FAB
        fab = (FloatingActionButton) findViewById(R.id.fab);


        //EditText
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        //Button
        btSave = (Button) findViewById(R.id.btSave);

        //TextView
        tvResponse  = (TextView) findViewById(R.id.tvResponse);
        initListener();
    }

    private void initListener() {

        //Button
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final UserRequest userRequest = new UserRequest();
                userRequest.setEmail(etEmail.getText().toString());
                userRequest.setPassword(etPassword.getText().toString());

                Call<Response> userResponse = service.getResponse(userRequest);
                userResponse.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                        tvResponse.setText(response.body().getSuccess());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                        tvResponse.setText("Error");
                    }
                });


            }
        });


        //FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
