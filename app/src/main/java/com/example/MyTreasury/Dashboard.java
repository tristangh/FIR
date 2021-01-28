package com.example.MyTreasury;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class Dashboard extends Fragment implements View.OnClickListener{

    private Button DateFrom;
    private Button DateTo;
    private TextView TotalExp;
    private SelectDate SelectDate;
    private PieChart Categories;
    private TextView CategoriesNull;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_view, container, false);
        DateFrom = (Button)rootView.findViewById(R.id.date_from);
        DateTo = (Button)rootView.findViewById(R.id.date_to);
        TotalExp = (TextView)rootView.findViewById(R.id.total_exp);
        Categories = (PieChart) rootView.findViewById(R.id.Chart_categories);
        //CategoriesNull = (TextView)rootView.findViewById(R.id.EmptyPieChart);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DateFrom.setOnClickListener(this);
        DateTo.setOnClickListener(this);
        updateDate(DateFrom, DateManager.getInstance().getDateFrom());
        updateDate(DateTo, DateManager.getInstance().getDateTo());
        //SelectDate.updateData();
    }

    public String getCurrentDateFormat() {
        return getDefaultSharedPreferences(getContext()).getString("date format key","MM/dd/yyyy");
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.date_from || v.getId() == R.id.date_to) {
            showDateDialog(v.getId());
        }
    }

    private void showDateDialog(final int id) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(id == R.id.date_from ? DateManager.getInstance().getDateFrom() : DateManager.getInstance().getDateTo());
        DialogManagerCalendar.getInstance()
                .showDatePicker(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                calendar.set(year, month, day);
                                Date_manager.setDateStartOfDay(calendar);
                                if (id == R.id.date_from) {
                                    DateManager.getInstance().setDateFrom(calendar.getTime());
                                    updateDate(DateFrom, DateManager.getInstance().getDateFrom());
                                } else {
                                    DateManager.getInstance().setDateTo(calendar.getTime());
                                    updateDate(DateTo, DateManager.getInstance().getDateTo());
                                }
                                SelectDate.updateData();
                            }
                        },
                        calendar,
                        (R.id.date_from == id) ? null : DateManager.getInstance().getDateFrom(),
                        (R.id.date_from == id) ? DateManager.getInstance().getDateTo() : null);
    }

    private void updateDate(Button btn, Date date) {
        btn.setText(formatDateToString(date, getCurrentDateFormat()));
    }

    public TextView getTextViewTotal() {
        return TotalExp;
    }

    public void setSelectDateFragment(SelectDate SelectDate) {
        this.SelectDate = SelectDate;
    }

    public static String formatDateToString(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    public static class DateManager {

        private Date mDateFrom;
        private Date mDateTo;

        private static DateManager ourInstance = new DateManager();

        public static DateManager getInstance() {
            return ourInstance;
        }

        private DateManager() {
            mDateFrom = Date_manager.getFirstDateOfCurrentMonth();
            mDateTo = Date_manager.getLastDateOfCurrentMonth();
        }

        public Date getDateFrom() {
            return mDateFrom;
        }

        public void setDateFrom(Date mDateFrom) {
            this.mDateFrom = mDateFrom;
        }

        public Date getDateTo() {
            return mDateTo;
        }

        public void setDateTo(Date mDateTo) {
            this.mDateTo = mDateTo;
        }


    }


    public interface OnFragmentInteractionListener {
        void OnFragmentInteractionChangeTitle(String title);
    }
}