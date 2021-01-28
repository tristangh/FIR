package com.example.MyTreasury;

import java.util.Calendar;
import java.util.Date;


public class Date_manager {

    public static Calendar setDateStartOfDay(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static Date getFirstDateOfCurrentMonth() {
        Calendar cal = setDateStartOfDay(Calendar.getInstance());
        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getLastDateOfCurrentMonth() {
        Calendar cal = setDateStartOfDay(Calendar.getInstance());
        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }


    public static boolean isToday(Date date) {
        return  android.text.format.DateUtils.isToday(date.getTime());
    }

    public static Date getToday() {
        return setDateStartOfDay(Calendar.getInstance()).getTime();
    }

}
