package com.example.noradiegwu.nytsearchapplication.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.noradiegwu.nytsearchapplication.DatePickerFragment;
import com.example.noradiegwu.nytsearchapplication.Models.Filter;
import com.example.noradiegwu.nytsearchapplication.R;

import org.parceler.Parcels;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etBeginDate;
    EditText etEndDate;
    RadioButton rbtnNewest;
    RadioButton rbtnOldest;
    CheckBox cbSports;
    CheckBox cbArts;
    CheckBox cbFashionandStyle;
    Button btnFilter;
    String date;
    String viewDate;
    String month;
    String day;
    final Filter filter = new Filter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setUpViews();


        // Sort click listeners
        rbtnNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.new_to_old = true;
                filter.old_to_new = false;
            }
        });
        rbtnOldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter.old_to_new = true;
                filter.new_to_old = false;
            }
        });

        // Date click listeners
        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.begin = true;
                filter.end = false;
                showDatePickerDialog(v);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.begin = false;
                filter.end = true;
                showDatePickerDialog(v);
            }
        });


        // News Desk click listeners
        cbSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.sports = true;
            }
        });
        cbArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.arts = true;
            }
        });
        cbFashionandStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.fashion_and_style = true;
            }
        });

        // apply btn listener
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("filter", Parcels.wrap(filter));
                data.putExtra("begin_date", Parcels.wrap(filter.begin_date));
                data.putExtra("end_date", Parcels.wrap(filter.end_date));
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    private void setUpViews() {
        etBeginDate = (EditText) findViewById(R.id.etBeginDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        rbtnNewest = (RadioButton) findViewById(R.id.rbtnNewest);
        rbtnOldest = (RadioButton) findViewById(R.id.rbtnOldest);
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashionandStyle = (CheckBox) findViewById(R.id.cbFashionandStyle);
        btnFilter = (Button) findViewById(R.id.btnFilter);
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

        // extract and concatenate date into string
        if(monthOfYear < 9) {
            month = "0" + String.valueOf(monthOfYear + 1);
        }
        else { month = String.valueOf(monthOfYear + 1); }

        if(dayOfMonth < 10) {
            day = "0" + String.valueOf(dayOfMonth);
        }
        else { day = String.valueOf(dayOfMonth); }

        /////////////
        date = String.valueOf(year) + month + day; // May need an else check for no string (ex. what if begin_date="")
        viewDate = month + "/" + day + "/" + String.valueOf(year);
        ////////////

        if(filter.begin) {
            // place viewDate string into etBeginDate
            filter.begin_date = date;
            etBeginDate.setText(viewDate);
        }
        if (filter.end) {
            // place date string into etEndDate
            filter.end_date = date;
            etEndDate.setText(viewDate);

        }

    }

}
