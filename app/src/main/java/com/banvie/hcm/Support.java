package com.banvie.hcm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.banvie.hcm.config.Environment;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Support {
    public static byte[] convertDrawableToBytes(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }

    public static Date convertStringToDate(String date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        Date time = null;
        try {
            time = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convertDateToString(Date date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        String time = format.format(date);
        return time;
    }
}
