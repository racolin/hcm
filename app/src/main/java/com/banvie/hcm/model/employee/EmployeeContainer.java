package com.banvie.hcm.model.employee;

import java.util.List;

public class EmployeeContainer<T> {
    public String code = "";
    public EmployeeWrap<T> data = new EmployeeWrap();
}
