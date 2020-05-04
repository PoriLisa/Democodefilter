package com.example.asecingorderapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Employee> employeeArrayList;
    private RecyclerView mRvEmployeelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvEmployeelist = findViewById(R.id.rv_employee_list);
        mRvEmployeelist.setLayoutManager(new LinearLayoutManager(this));
        employeeArrayList = new ArrayList<>();
        getEmployeeName();
    }

    private void getEmployeeName() {

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.getAllEmployeeDetails();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (response.isSuccessful()) {
                        Log.d("TAG", "onResponse: " + response.toString());

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("TAG", "onResponse: data " + jsonObject.toString());
                        String status = jsonObject.optString("status");
                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Employee employee = new Employee();
                                employee.employee_age = jsonObject1.getString("employee_age");
                                employee.employee_name = jsonObject1.getString("employee_name");
                                employee.id = jsonObject1.getString("id");
                                employee.profile_image = jsonObject1.getString("profile_image");
                                employee.employee_salary = jsonObject1.getString("employee_salary");
                                employeeArrayList.add(employee);

                                setAdapter(employeeArrayList);


                            }
                        }
                    } else {
                        //
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(ArrayList<Employee> employeeArrayList) {

        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, employeeArrayList);
        mRvEmployeelist.setAdapter(employeeAdapter);
    }
}
