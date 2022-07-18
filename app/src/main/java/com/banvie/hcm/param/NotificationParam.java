package com.banvie.hcm.param;

public class NotificationParam {
    private String userId;
    private int requestType;
    private String statusRead;
    private int pageNumber;
    private int pageSize;

    public NotificationParam() {

    }

    public NotificationParam(String userId, int requestType, String statusRead, int pageNumber, int pageSize) {
        this.userId = userId;
        this.requestType = requestType;
        this.statusRead = statusRead;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getStatusRead() {
        return statusRead;
    }

    public void setStatusRead(String statusRead) {
        this.statusRead = statusRead;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
