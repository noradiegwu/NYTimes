package com.example.noradiegwu.nytsearchapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {

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
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");


            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = "https://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbnail = "";
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

