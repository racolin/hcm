package com.banvie.hcm.model.policy;

import com.banvie.hcm.Support;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Policy implements Serializable {
    public String topic = "";
    public long createdDate = 0;
    public String thumbnail = "";
    public byte[] image;
    public String longDescription = "";

    public String getTimeString(String s) {
        return Support.convertDateToString(new Date(createdDate), s);
    }
}
