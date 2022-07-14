package com.banvie.hcm.model.notification;

public class Notification {
    private String title;
    private String shortContent;
    private long sendDate;
    private String notificationId;
    private String targetId;
    private int type;
    private String fullName;
    private String image;
    private String senderId;
    private String notifySetting;
    private boolean read;

    public Notification(String title, String shortContent, long sendDate,
                        String notificationId, String targetId, int type,
                        String fullName, String image, String senderId,
                        String notifySetting, boolean read) {
        this.title = title;
        this.shortContent = shortContent;
        this.sendDate = sendDate;
        this.notificationId = notificationId;
        this.targetId = targetId;
        this.type = type;
        this.fullName = fullName;
        this.image = image;
        this.senderId = senderId;
        this.notifySetting = notifySetting;
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getNotifySetting() {
        return notifySetting;
    }

    public void setNotifySetting(String notifySetting) {
        this.notifySetting = notifySetting;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
