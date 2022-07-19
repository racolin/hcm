package com.banvie.hcm.listener;

import com.banvie.hcm.model.education.Education;
import com.banvie.hcm.model.employee_duration.EmployeeDuration;
import com.banvie.hcm.model.shui.Shui;
import com.banvie.hcm.model.summary.Summary;

public interface OnLoadProfileListener {
    void setOnSummaryListener(Summary summary);
    void setOnEmployeeDurationListener(EmployeeDuration employeeDuration);
    void setOnEducationListener(Education education);
    void setOnShuiListener(Shui shui);
}
