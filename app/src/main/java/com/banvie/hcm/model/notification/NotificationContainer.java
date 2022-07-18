package com.banvie.hcm.model.notification;

import java.util.List;

public class NotificationContainer {
    private NotificationDataWrap data;
    private int unReadCount;
    private boolean turnOff;

    public NotificationContainer() {

    }

    public NotificationDataWrap getData() {
        return data;
    }

    public void setData(NotificationDataWrap data) {
        this.data = data;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public boolean isTurnOff() {
        return turnOff;
    }

    public void setTurnOff(boolean turnOff) {
        this.turnOff = turnOff;
    }

    @Override
    public String toString() {
        return "NotificationFull{" +
                "data=" + data +
                ", unReadCount=" + unReadCount +
                ", turnOff=" + turnOff +
                '}';
    }
}
