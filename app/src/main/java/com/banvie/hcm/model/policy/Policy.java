package com.banvie.hcm.model.policy;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Policy implements Serializable {
    String topic;
    long createdDate;
    String thumbnail;
    byte[] image;
    String longDescription;

    public Policy() {

    }

    public Policy(String topic, long createdDate, String thumbnail, String longDescription) {
        this.createdDate = createdDate;
        this.topic = topic;
        this.thumbnail = thumbnail;
        this.longDescription = longDescription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getTime() {
        return new Date(createdDate);
    }

    public String getTimeString(String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        return format.format(new Date(createdDate));
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
