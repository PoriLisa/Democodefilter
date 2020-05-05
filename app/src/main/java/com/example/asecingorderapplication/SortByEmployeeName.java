package com.example.asecingorderapplication;

import java.util.Comparator;

public class SortByEmployeeName implements Comparator<Employee> {


    @Override
    public int compare(Employee employee1, Employee employee2) {
        return employee1.getEmployee_name().compareTo(employee2.getEmployee_name());
    }
}
