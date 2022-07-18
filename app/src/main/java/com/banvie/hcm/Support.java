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
import java.util.concurrent.TimeUnit;

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

    public static String convertToTimeAgo(Date date) {
        return convertToTimeAgo(date.getTime());
    }

    public static String convertToTimeAgo(long time) {
        long diff = TimeUnit.SECONDS.convert((new Date()).getTime() - time, TimeUnit.MILLISECONDS);
        if (diff < 60) {
            return diff == 1 ? "A second" : (diff + " seconds") + " ago";
        } else if ((diff /= 60) < 60) {
            return diff == 1 ? "A minute" : (diff + " minutes") + " ago";
        } else if ((diff /= 60) < 24) {
            return diff == 1 ? "A hour" : (diff + " hours") + " ago";
        } else if ((diff /= 24) < 7) {
            return diff == 1 ? "A day" : (diff + " days") + " ago";
        } else if ((diff /= 7) < 30) {
            return diff == 1 ? "A week" : (diff + " weeks") + " ago";
        } else if ((diff /= 30) < 12) {
            return diff == 1 ? "A month" : (diff + " months") + " ago";
        } else {
            diff /= 12;
            return diff == 1 ? "A year" : (diff + " years") + " ago";
        }
    }
}
