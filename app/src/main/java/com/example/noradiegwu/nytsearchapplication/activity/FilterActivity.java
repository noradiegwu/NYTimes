package com.example.noradiegwu.nytsearchapplication.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.noradiegwu.nytsearchapplication.DatePickerFragment;
import com.example.noradiegwu.nytsearchapplication.R;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etBeginDate;
    EditText etEndDate;
    int BEGIN_DATE = 1;
    int END_DATE = 2;
    int DATE_BOOL = 0;
    String date;
    String viewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        etBeginDate = (EditText) findViewById(R.id.etBeginDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);

        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_BOOL = BEGIN_DATE;
                showDatePickerDialog(v);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_BOOL = END_DATE;
                showDatePickerDialog(v);
            }
        });

    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // concatenate date into string
        date = String.valueOf(year) + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth);
        viewDate = String.valueOf(monthOfYear) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
        //option: one for view, one for url

        // if DATE_BOOL == begin
        if(DATE_BOOL == BEGIN_DATE) {
            // place viewDate string into etBeginDate
            etBeginDate.setText(viewDate);
        }
        //else
        if (DATE_BOOL == END_DATE) {
            // place date string into etEndDate
            etEndDate.setText(viewDate);

        }

        // URL stuff


    }




}
