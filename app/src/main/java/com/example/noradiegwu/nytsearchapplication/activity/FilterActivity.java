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

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Butterknife views
    @BindView(R.id.etBeginDate) EditText etBeginDate;
    @BindView(R.id.etEndDate) EditText etEndDate;
    @BindView(R.id.rbtnNewest) RadioButton rbtnNewest;
    @BindView(R.id.rbtnOldest) RadioButton rbtnOldest;
    @BindView(R.id.cbSports) CheckBox cbSports;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashionandStyle) CheckBox cbFashionandStyle;
    @BindView(R.id.btnFilter) Button btnFilter;
    String date;
    String viewDate;
    String month;
    String day;
    final Filter filter = new Filter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        //setUpViews();


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


        // News Desk checkbox click listeners
        cbSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked) { // if the box if checked
                    filter.sports = true; // set the filter value to true (which, in filter model, adds the correct string to the array)
                } else { filter.sports = false; } // else, (if it is ever checked then unchecked or never checked at all)
                // set to false
            }
        });
        cbArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked) { // if the box if checked
                    filter.arts = true; // set the filter value to true (which, in filter model, adds the correct string to the array)
                } else { filter.arts = false; } // else, (if it is ever checked then unchecked or never checked at all)
                // set to false
            }
        });
        cbFashionandStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if(checked) { // if the box if checked
                    filter.fashion_and_style = true; // set the filter value to true (which, in filter model, adds the correct string to the array)
                } else { filter.fashion_and_style = false; } // else, (if it is ever checked then unchecked or never checked at all)
                // set to false
            }
        });

        // apply btn listener
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///// construct news_desk /////
                filter.constructNewsDeskArray(); // at this time the array is created
                String newsDeskItemsStr = "";
                if(filter.newsDeskItems.size() > 0) { // if the array has anything in it (any boxes were checked at the time of "apply" button click)
                    newsDeskItemsStr = android.text.TextUtils.join(" ", filter.newsDeskItems); // join the params together w/ a space in b/w
                    filter.newsDeskParamValue = String.format("news_desk:(%s)", newsDeskItemsStr); // set the param string value in the filter
                    // this is to use when the filter is passed into the search activity
                }

                Intent data = new Intent();
                data.putExtra("filter", Parcels.wrap(filter));
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    /* private void setUpViews() {
        etBeginDate = (EditText) findViewById(R.id.etBeginDate);
        // @BindView(R.id.etBeginDate) EditText etBeginDate;
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        // @BindView(R.id.etEndDate) EditText etEndDate;
        rbtnNewest = (RadioButton) findViewById(R.id.rbtnNewest);
        // @BindView(R.id.rbtnNewest) RadioButton rbtnNewest;
        rbtnOldest = (RadioButton) findViewById(R.id.rbtnOldest);
        // @BindView(R.id.rbtnOldest) RadioButton rbtnOldest;
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        // @BindView(R.id.cbSports) CheckBox cbSports;
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        // @BindView(R.id.cbArts) CheckBox cbArts;
        cbFashionandStyle = (CheckBox) findViewById(R.id.cbFashionandStyle);
        // @BindView(R.id.cbFashionandStyle) CheckBox cbFashionandStyle;
        btnFilter = (Button) findViewById(R.id.btnFilter);
        // @BindView(R.id.btnFilter) Button btnFilter;
    } */

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
