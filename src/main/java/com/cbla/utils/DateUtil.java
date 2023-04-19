package com.cbla.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //returns today's date
    public static Date getTodaysDate() {
        Date date = new Date();
        return date;
    }

    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String getTodaysDate(String dateFormat) {
        Date date = getTodaysDate();
        String formattedDate = formatDate(date, dateFormat);
        return formattedDate;
    }

    public static String getYesterdaysDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String formattedDate = formatDate(yesterday, dateFormat);
        return formattedDate;
    }
}
