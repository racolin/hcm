package com.banvie.hcm.param;

public class NotificationParam {
    public String userId;
    public int requestType;
    public String statusRead;
    public int pageNumber;
    public int pageSize;

    public NotificationParam(String userId, int requestType, String statusRead, int pageNumber, int pageSize) {
        this.userId = userId;
        this.requestType = requestType;
        this.statusRead = statusRead;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
