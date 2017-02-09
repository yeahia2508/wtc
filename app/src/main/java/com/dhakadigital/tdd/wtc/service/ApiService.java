package com.dhakadigital.tdd.wtc.service;

import com.dhakadigital.tdd.wtc.model.Response;
import com.dhakadigital.tdd.wtc.model.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by y34h1a on 2/8/17.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("user_control.php")
    Call<Response> getResponse(@Body UserRequest userRequest);
}
