package com.banvie.hcm.model.notification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Notification implements Serializable {
    public String title = "";
    public String shortContent = "";
    public long sendDate = 0;
    public String notificationId = "";
    public String targetId = "";
    public int type = 0;
    public String fullName = "";
    public String image = "";
    public byte[] image_bytes = null;
    public String senderId = "";
//    String notifySetting;
    public boolean read = false;
}
