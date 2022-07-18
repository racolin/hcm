package com.banvie.hcm.model.notification;

import java.util.Arrays;
import java.util.Objects;

public class Notification {
    private String title;
    private String shortContent;
    private long sendDate;
    private String notificationId;
    private String targetId;
    private int type;
    private String fullName;
    private String image;
    private byte[] image_bytes;
    private String senderId;
//    private String notifySetting;
    private boolean read;

    public byte[] getImage_bytes() {
        return image_bytes;
    }

    public void setImage_bytes(byte[] image_bytes) {
        this.image_bytes = image_bytes;
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

//    public String getNotifySetting() {
//        return notifySetting;
//    }

//    public void setNotifySetting(String notifySetting) {
//        this.notifySetting = notifySetting;
//    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(notificationId, type, read);
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "title='" + title + '\'' +
                ", shortContent='" + shortContent + '\'' +
                ", sendDate=" + sendDate +
                ", notificationId='" + notificationId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", type=" + type +
                ", fullName='" + fullName + '\'' +
                ", image='" + image + '\'' +
                ", image_bytes=" + Arrays.toString(image_bytes) +
                ", senderId='" + senderId + '\'' +
//                ", notifySetting='" + notifySetting + '\'' +
                ", read=" + read +
                '}';
    }
}
