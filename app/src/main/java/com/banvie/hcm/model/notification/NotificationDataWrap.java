package com.banvie.hcm.model.notification;


import java.util.List;

public class NotificationDataWrap {
    private String code;
    private NotificationData data;

    public NotificationDataWrap() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data1{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
