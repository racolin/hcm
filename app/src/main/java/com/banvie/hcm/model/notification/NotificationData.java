package com.banvie.hcm.model.notification;


import java.util.ArrayList;
import java.util.List;

public class NotificationData {
    public List<Notification> items = new ArrayList<>();
    public int page = 0;
    public int size = 0;
    public int totalItems = 0;
    public int totalPages = 0;
    public int totalElements = 0;
    public boolean hasNext = true;
    public boolean hasPrevious = true;
}