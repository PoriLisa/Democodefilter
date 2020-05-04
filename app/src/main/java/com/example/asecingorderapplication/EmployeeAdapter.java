package com.example.asecingorderapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private Context mContex;
    private ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.mContex = context;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.cell_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeArrayList.get(position);
        holder.mTvEmployeeName.setText(employee.getEmployee_name());
        holder.mTvemployeeAge.setText(employee.getEmployee_age());
        holder.mTvemployeeSalary.setText(employee.getEmployee_salary());
        Log.d("TAG", "onBindViewHolder: "+employee.getEmployee_salary());


    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView mTvEmployeeName;
        TextView mTvemployeeAge;
        TextView mTvemployeeSalary;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvEmployeeName = itemView.findViewById(R.id.tv_emp_name);
            mTvemployeeAge = itemView.findViewById(R.id.tv_age);
            mTvemployeeSalary = itemView.findViewById(R.id.tv_salary);
        }
    }
}
