package com.banvie.hcm.model.employee_duration;

public class EmergencyContact {
    public String phone = "";
    public String relationship = "";

    @Override
    public String toString() {
        return phone + ", (" + relationship + ')';
    }
}
