package com.banvie.hcm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notice {
    String title;
    Date time;
    byte[] image;

    public Notice(String title, Date time, byte[] image) {
        this.time = time;
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public String getTimeString(String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        return format.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
