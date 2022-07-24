package com.banvie.hcm.listener;

import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.organization_chart.OrganizationChart;

import java.util.List;

public interface OnLoadItemOrganizationListener extends OnLoadImageListener {
    void _setOnClickItemOrganizationListener(int position);
    void setOnClickItemOrganizationListener(int position, boolean isManager);
    void setOnLoadItemOrganizationListener(OrganizationChart organization);
    void setOnLoadFirstOrganizationListener(OrganizationChart organizations);
}
