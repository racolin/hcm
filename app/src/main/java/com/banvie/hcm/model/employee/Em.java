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

public class Em implements Serializable {
    public String id = "";
    public String image = "";
    public byte[] image_bytes = null;
    public JobTitle jobTitle = new JobTitle();
    public String fullName = "";
    public String username = "";
    public boolean isLoading = false;

//    public void loadImage() {
//        isLoading = true;
//        apiService.getImage(image).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    try {
//                        image_bytes = response.body().bytes();
//                        isLoading = false;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public byte[] getImage() {
//        if (image.equals("")) {
//            return null;
//        }
//        if (null != image_bytes) {
//            return image_bytes;
//        }
//        if (!isLoading) {
//            loadImage();
//        }
//        return null;
//    }
}
