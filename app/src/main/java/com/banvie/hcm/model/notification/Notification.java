package com.banvie.hcm.model.notification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Notification implements Serializable {
    public String shortContent = "";
    public long sendDate = 0;
    public String notificationId = "";
    public int type = 0;
    public String image = "";
    public byte[] image_bytes = null;
    public boolean read = false;

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
}
