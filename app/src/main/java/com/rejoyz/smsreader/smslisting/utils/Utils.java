package com.rejoyz.smsreader.smslisting.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date, String dateFormat) {
        return DateFormat.format(dateFormat, date).toString();
    }

    public static long getHoursAgo(Date date) {
        Date now = Calendar.getInstance().getTime();
        long timeDifference = now.getTime() - date.getTime();
        long hours = timeDifference / 3600000;
        hours += timeDifference % 3600000 > 1 ? 1 : 0;
        return hours;
    }


    public static String getMins(Date date) {
        Date now = Calendar.getInstance().getTime();
        long mins = (now.getTime() - date.getTime()) / 60000;
        if (mins < 60)
            return mins + " mins";
        else
            return getDayStringFromDayNumeric(date.getDay()).substring(0,3);
    }


    public static String getDayStringFromDayNumeric(int paramInt) {
        switch (paramInt) {
            default:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
        }

    }
}
