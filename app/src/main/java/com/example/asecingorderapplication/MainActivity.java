package com.example.asecingorderapplication;

import android.os.Bundle;
import android.os.Trace;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomSheetFragment.AlphabetOrder {
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private RecyclerView mRvEmployeelist;
    private ImageView mIvFilter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ProgressBar progressBar;
    private RelativeLayout toolbar_container;
    private EditText et_search;
    private EmployeeAdapter employeeAdapter;
    public static final String TAG =MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_View();

        getEmployeeName();

        btnOnClick();
    }

    private void init_View(){
        mRvEmployeelist = findViewById(R.id.rv_employee_list);
        mRvEmployeelist.setLayoutManager(new LinearLayoutManager(this));
        //mRvEmployeelist.setHasFixedSize(true);
        employeeAdapter = new EmployeeAdapter(this, employeeArrayList);
        mRvEmployeelist.setAdapter(employeeAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRvEmployeelist.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mRvEmployeelist.addItemDecoration(dividerItemDecoration);


        mIvFilter = findViewById(R.id.iv_filter);
        progressBar = findViewById(R.id.progressbar_);
        toolbar_container = findViewById(R.id.rl_toolbar);
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employeeAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void show_loader(){
        progressBar.setVisibility(View.VISIBLE);
        mRvEmployeelist.setVisibility(View.GONE);
    }
    private void hide_loader(){
        progressBar.setVisibility(View.GONE);
        mRvEmployeelist.setVisibility(View.VISIBLE);
    }

    private void btnOnClick() {

        mIvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

            }
        });
    }

    private void show_snacbar_fail(String message) {
        Snackbar.make(findViewById(R.id.main_), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getEmployeeName();

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    private void getEmployeeName() {

        show_loader();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.getAllEmployeeDetails();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (response.isSuccessful()) {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String status = jsonObject.optString("status");
                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Employee employee = new Employee();
                                employee.employee_age = jsonObject1.optString("employee_age");
                                employee.employee_name = jsonObject1.optString("employee_name");
                                employee.id = jsonObject1.optString("id");
                                employee.profile_image = jsonObject1.optString("profile_image");
                                employee.employee_salary = jsonObject1.optString("employee_salary");
                                employeeArrayList.add(employee);
                            }
                            employeeAdapter.setData(employeeArrayList);
                        }

                        hide_loader();
                    } else {
                        hide_loader();
                        show_snacbar_fail("Network Failure!!!");
                    }

                } catch (Exception e) {
                    hide_loader();
                    show_snacbar_fail("Network Failure. Please try again.");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hide_loader();
                show_snacbar_fail("Network Failure.Please try again.");
            }
        });
    }


    @Override
    public void asecndingOrder() {
        Collections.sort(employeeArrayList, new SortByEmployeeName());
        employeeAdapter.setData(employeeArrayList);
    }

    @Override
    public void desecndingOrder() {
        Comparator comparator = Collections.reverseOrder(new SortByEmployeeName());
        Collections.sort(employeeArrayList, comparator);
        employeeAdapter.setData(employeeArrayList);
    }
}
