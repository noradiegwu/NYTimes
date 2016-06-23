package com.example.noradiegwu.nytsearchapplication.Models;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Filter {

    public boolean old_to_new;
    public boolean new_to_old;
    public boolean arts = false;
    public boolean fashion_and_style = false;
    public boolean sports = false;
    public boolean begin = false;
    public boolean end = false;
    public String begin_date;
    public String end_date;
    public String NEWEST = "newest";
    public String OLDEST = "oldest";
    public String SPORTS_STRING = "\"Sports\"";
    public String ARTS_STRING = "\"Arts\"";
    public String FASHION_STRING = "\"Fashion & Style\"";
    public String newsDeskParamValue = "";
    private String EMPTY_STRING = "";
    public ArrayList<String> newsDeskItems = new ArrayList<>();

    /*

    String newsDeskItemsStr =
            android.text.TextUtils.join(" ", newsDeskItems);
    String newsDeskParamValue =
            String.format("news_desk:(%s)", newsDeskItemsStr);
    */


    public Filter() {
    }


    // Getters

    public String getSort() {
        if(isNew_to_old()) {
            return NEWEST;
        }
        else if(isOld_to_new()) {
            return OLDEST;
        }
        else { return EMPTY_STRING; } //cause issues?
    }

    // News desk methods //
    //////////////////////
    public void constructNewsDeskArray() { // no need to check to remove values on the chance the user's mind is changed,
        // because this is only called when the "apply" filter button is clicked
        if(isSports()) {
            newsDeskItems.add(SPORTS_STRING);
        } //else { newsDeskItems.remove(SPORTS_STRING); }

        if(isArts()) {
            newsDeskItems.add(ARTS_STRING);
        } //else { newsDeskItems.remove(ARTS_STRING); }

        if(isFashion_and_style()) {
            newsDeskItems.add(FASHION_STRING);
        } // else { newsDeskItems.remove(FASHION_STRING);}
    }

    public String getNewsDeskParamValue() {
        return newsDeskParamValue;
    }

    // DAte methods

    public String getBeginString() {
        if(isBegin()) {
            return begin_date;
        } else { return EMPTY_STRING; }
    }

    public String getEndString() {
        if(isEnd()) {
            return end_date;
        } else { return EMPTY_STRING; }
    }

    public boolean isNew_to_old() {
        return new_to_old;
    }

    public boolean isOld_to_new() {
        return old_to_new;
    }

    public boolean isSports() {
        return sports;
    }

    public boolean isFashion_and_style() {
        return fashion_and_style;
    }

    public boolean isArts() {
        return arts;
    }

    public boolean isEnd() {
        return end;
    }

    public boolean isBegin() {
        return begin;
    }





}
