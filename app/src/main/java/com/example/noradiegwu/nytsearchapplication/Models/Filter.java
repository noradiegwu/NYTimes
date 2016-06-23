package com.example.noradiegwu.nytsearchapplication.Models;

import org.parceler.Parcel;

@Parcel
public class Filter {

    public boolean old_to_new;
    public boolean new_to_old;
    public boolean arts;
    public boolean fashion_and_style;
    public boolean sports;
    public boolean begin = false;
    public boolean end = false;
    public String begin_date;
    public String end_date;
    public String NEWEST = "newest";
    public String OLDEST = "oldest";
    public String SPORTS_STRING = "\"Sports\"";
    public String ARTS_STRING = "\"Arts\"";
    public String FASHION_STRING = "\"Fashion & Style\""; // need proper form/string
    private String EMPTY_STRING = "";

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

    public String getSportsString() {
        if(isSports()) {
            return SPORTS_STRING;
        } else { return EMPTY_STRING;}
    }

    public String getArtsString() {
        if(isArts()) {
            return ARTS_STRING;
        } else { return EMPTY_STRING;}
    }

    public String getFashionString() {
        if(isFashion_and_style()) {
            return FASHION_STRING;
        } else { return EMPTY_STRING;}
    } // need proper form/string


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
