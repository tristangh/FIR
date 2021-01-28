package com.example.MyTreasury;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;


public class DialogManagerCalendar {

    private static DialogManagerCalendar ourInstance = new DialogManagerCalendar();

    public static DialogManagerCalendar getInstance() {
        return ourInstance;
    }

    private DialogManagerCalendar() {
    }

    public void showDatePicker(Context context, DatePickerDialog.OnDateSetListener dateSetListener, Calendar calendar, Date minDate, Date maxDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (minDate != null) datePickerDialog.getDatePicker().setMinDate(minDate.getTime());
        if (maxDate != null) datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        datePickerDialog.show();
    }
}
