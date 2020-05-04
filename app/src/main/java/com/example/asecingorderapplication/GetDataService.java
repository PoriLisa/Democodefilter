package com.example.asecingorderapplication;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("api/v1/employees")
    Call<ResponseBody> getAllEmployeeDetails();
}
