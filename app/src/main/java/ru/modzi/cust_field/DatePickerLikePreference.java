package ru.modzi.cust_field;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatePickerLikePreference extends InputLikePreference {
    Calendar _value;

    private String[] months = {
            "Января", "Февраля",
            "Марта", "Апреля", "Мая",
            "Июня", "Июля", "Августа",
            "Сентября", "Октября", "Ноября",
            "Декабря"
    };

    public DatePickerLikePreference(Context context) {
        super(context);
    }

    public DatePickerLikePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerLikePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initControl() {
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        _value = Calendar.getInstance();
                        _value.set(year, month, dayOfMonth);
                        @SuppressLint("DefaultLocale")
                        String date = String.format("%02d %s %d", dayOfMonth, months[month], year);
                        value.setText(date);
                    }
                };

                Calendar now = Calendar.getInstance();
                if (_value != null) {
                    now = _value;
                }
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);
                int day = now.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog,
                        onDateSetListener,
                        year,
                        month,
                        day
                );
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void setValue(String value) {
        Calendar now = Calendar.getInstance();
        List<String> dateArray = Arrays.asList(value.split("-"));
        if (dateArray.size() == 3) {
            now.set(
                    Integer.valueOf(dateArray.get(0)),
                    Integer.valueOf(dateArray.get(1)) - 1,
                    Integer.valueOf(dateArray.get(2))
            );
        }
        this._value = now;
    }

    @Override
    public String getValue() {
        if (this._value != null) {
            int year = this._value.get(java.util.Calendar.YEAR);
            int month = this._value.get(java.util.Calendar.MONTH);
            int day = this._value.get(java.util.Calendar.DAY_OF_MONTH);

            @SuppressLint("DefaultLocale")
            String date = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);

            return date;
        }

        return null;
    }
}
