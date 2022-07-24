package com.banvie.hcm.listener;

import com.banvie.hcm.model.employee.EmployeeContainer;

public interface OnLoadEmployeeListener extends OnLoadImageListener {
    void setOnLoadEmployeeListener(EmployeeContainer container);
}
