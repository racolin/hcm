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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static byte[] reduceImage(byte[] image) {
        int len = image.length;
        if (len > 5000) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, len);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, (int) ((5000.0 /  len) * 100), bos);
            Log.d("rrr", image.length + "  " + bos.toByteArray().length);
            return bos.toByteArray();
        }
        return image;
    }

    private static String convertUpper(String str) {
        String s = str.toLowerCase();

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String convertAllUpper(String str) {
        String[] ss = str.split("_");
        List<String> r = new ArrayList<>();
        for (String s : ss) {
            r.add(convertUpper(s));
        }
        return String.join(" ", r);
    }

    public static String convertFirstUpper(String str) {
        String[] ss = convertUpper(str).split("_");
        return String.join(" ", ss);
    }

    public static String getFirstTextOfName(String firstName) {
        String[] s = firstName.split(" ");
        return s[s.length - 1].substring(0, 1);
    }
}
