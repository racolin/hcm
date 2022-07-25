package com.banvie.hcm.model.employee;

import static com.banvie.hcm.api.ApiService.apiService;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.model.summary.DirectReport;
import com.banvie.hcm.model.summary.JobTitle;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee implements Serializable {
    public String id = "";
    public String image = "";
    public String cif = "";
    public byte[] image_bytes = null;
    public JobTitle jobTitle = new JobTitle();
    public String fullName = "";
    public String username = "";
}
