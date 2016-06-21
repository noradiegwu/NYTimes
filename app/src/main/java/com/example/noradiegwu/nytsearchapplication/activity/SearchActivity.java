package com.example.noradiegwu.nytsearchapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.noradiegwu.nytsearchapplication.Article;
import com.example.noradiegwu.nytsearchapplication.ArticleArrayAdapter;
import com.example.noradiegwu.nytsearchapplication.EndlessScrollListener;
import com.example.noradiegwu.nytsearchapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    Button btnSearch;
    GridView gvResults;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Toolbar toolbar = (Toolbar) findViewById(R.id....)
        //setSupportActionBar(toolbar);
        setUpViews();
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

        });
    }

    public void setUpViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        // hook up listener for grid click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an intent to display the article
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                // get the artcle I want to display
                Article article = articles.get(position);
                // pass in the article to the intent
                intent.putExtra("article", article);
                // launch article activity
                startActivity(intent);
            }
        });
        // add butterknife later:
            // @BindView(R.id.lvMovies) EditText etQuery etc.
            // ButterKnife.bind(this);
    }

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;

                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    // by doing adapter.addAll you still modify the underlying data and adds it to the array list
                    // you simply save a step by not having to notify the adapter
                    // adapter.notifyDataSetChanged();
                   //Log.d("response", articles.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

;    }

    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        String query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
        params.put("page", offset);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;

                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                    // by doing adapter.addAll you still modify the underlying data and adds it to the array list
                    // you simply save a step by not having to notify the adapter
                    // adapter.notifyDataSetChanged();
                    //Log.d("response", articles.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
