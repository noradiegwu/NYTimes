package com.example.noradiegwu.nytsearchapplication.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
@Parcel
public class Article {
    public Article() {

    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getHeadline() {
        return headline;
    }

    String webUrl;
    String headline;
    String thumbnail;


    public Article(JSONObject jsonObject) {
        try {
            // if statements
            if(jsonObject.has("headline")) { // if normal articleSearch api
                this.webUrl = jsonObject.getString("web_url");
                this.headline = jsonObject.getJSONObject("headline").getString("main");


                JSONArray multimedia = jsonObject.getJSONArray("multimedia");
                if (multimedia.length() > 1) {
                    JSONObject multimediaJson = multimedia.getJSONObject(1);
                    this.thumbnail = "https://www.nytimes.com/" + multimediaJson.getString("url");
                } else {
                    this.thumbnail = "";
                }
            }
            else { // else if top stories api
                this.webUrl = jsonObject.getString("url");
                this.headline = jsonObject.getString("title");

                JSONArray multimedia = jsonObject.getJSONArray("multimedia");
                if (multimedia.length() > 1) {
                    JSONObject multimediaJson = multimedia.getJSONObject(1);
                    this.thumbnail = multimediaJson.getString("url");
                } else {
                    this.thumbnail = "";
                }


            }

        } catch (JSONException e) {

        }
    }

        public static ArrayList<Article> fromJSONArray(JSONArray array) {
            ArrayList<Article> results = new ArrayList<>();

            for(int i = 0; i < array.length(); i++) {
                try {
                    results.add(new Article(array.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return results;

        }
    }

