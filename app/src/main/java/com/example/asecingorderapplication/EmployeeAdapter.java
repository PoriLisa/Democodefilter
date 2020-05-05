package com.example.asecingorderapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> implements Filterable {
    private Context mContex;
    private ArrayList<Employee> employeeArrayList;
    private ArrayList<Employee> filter_employeeArrayList;
    String TAG = "MainActivity";



    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.mContex = context;
        this.employeeArrayList = employeeArrayList;
        this.filter_employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContex).inflate(R.layout.cell_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = filter_employeeArrayList.get(position);
        holder.mTvEmployeeName.setText(employee.getEmployee_name());
        holder.mTvemployeeSalary.setText("Salary: ".concat(" ").concat(mContex.getResources().getString(R.string.rupee_symbol)).concat(employee.getEmployee_salary()));
        holder.mTvemployeeAge.setText("Age: ".concat(" ").concat(employee.getEmployee_age()));
        Log.d("TAG", "onBindViewHolder: " + employee.getEmployee_salary());


    }


    public void setData(ArrayList<Employee> employees){

        employeeArrayList = new ArrayList<>();
        employeeArrayList.addAll(employees);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filter_employeeArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if (constraint == null || TextUtils.isEmpty(constraint)) {
                    filter_employeeArrayList = employeeArrayList;
                } else {
                    ArrayList<Employee> filteredList = new ArrayList<>();
                    String charString = constraint.toString();
                    for (Employee employee : employeeArrayList) {

                        if (employee.getEmployee_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(employee);
                        }
                    }

                    filter_employeeArrayList = filteredList;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = filter_employeeArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filter_employeeArrayList = (ArrayList<Employee>) results.values;
                notifyDataSetChanged();
            }
        };
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
