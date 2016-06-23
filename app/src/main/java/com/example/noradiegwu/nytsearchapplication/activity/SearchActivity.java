package com.example.noradiegwu.nytsearchapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.noradiegwu.nytsearchapplication.ArticleArrayAdapter;
import com.example.noradiegwu.nytsearchapplication.EndlessScrollListener;
import com.example.noradiegwu.nytsearchapplication.Models.Article;
import com.example.noradiegwu.nytsearchapplication.Models.Filter;
import com.example.noradiegwu.nytsearchapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    String queryText;
    int offsetNum;
    GridView gvResults;
    int FILTER_REQUEST_CODE = 155;
    private Filter filtered;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Toolbar tbSearch = (Toolbar) findViewById(R.id.tbSearch);
        //setSupportActionBar(tbSearch);
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

    public void fetchArticles(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";


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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        // on original search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // catch query string for use in later method
                queryText = query;

                //If I do not want to apply any filters from the beginning, I should simply call the default params on my query
                // clear articles
                adapter.clear();

                RequestParams params = createRequestParams(query, null, 0);

                // filter params get applied in onactivityresult();

                // fetch articles from client
                fetchArticles(params);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void setUpViews() {
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
                // get the article I want to display
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


    // Used for infinite scroll
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        //String query = etQuery.getText().toString();

        // catch offset for use in later method
        offsetNum = offset;

        // normal infScroll should happen here
        RequestParams params = createRequestParams(queryText, filtered, offset); // how do I use my filter from result?
        // issue with infinite scroll because infScroll does not have a filter/cannot grab THE filter so new articles lose filtering

        fetchArticles(params);
    }

    //////////////////////////
    ///////FILTERING/////////
    ////////////////////////

    // set listener for filter icon click and open intent on click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_item_filter) {
            Intent filterI = new Intent(getApplicationContext(), FilterActivity.class);
            startActivityForResult(filterI, FILTER_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // on activity result(s)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            // if it was the filter intent
            if (requestCode == FILTER_REQUEST_CODE) {
                Toast.makeText(this, "You filtered!", Toast.LENGTH_SHORT).show();

                // When the activity comes back, I want to "refresh" the whole screen and run query/client with filters set. So I should:
                // 1. clear the adapter
                adapter.clear();

                // 2. getintent
                filtered = Parcels.unwrap(data.getParcelableExtra("filter"));

                // 3. request params
                RequestParams params = createRequestParams(queryText, filtered, 0);

                // 4. run the client again with both orig query and new filter params --> fetchArticles(params)
                fetchArticles(params);

            }
        }

    }


    // default params applied with original query
/*    public RequestParams defaultParams (String query) {
        RequestParams params = new RequestParams();
        params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
        params.put("page", 0);
        params.put("q", query);
        return params;

    }*/

    // default params applied with original query and necessary offset
/*    public RequestParams defaultInfScrollParams(int offset) {
        RequestParams params = new RequestParams();
        params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
        params.put("page", offset);
        params.put("q", queryText);
        return params;
    }*/


    public RequestParams createRequestParams(String query, Filter filter, int pageNum) { // issue with infinite scroll
        // use String query as incoming parameter
        String EMPTY_STRING = "";

        //RequestParams params = defaultParams(query);

        RequestParams params = new RequestParams();

        if (query != null) {
            params.put("api-key", "e9eaa94eab9b4156ab10c4acdaf1780c");
            params.put("page", pageNum);
            params.put("q", query);
        }

        // clear
        //adapter.clear();

        if(filter != null) {

            String beginDate = filter.getBeginString();
            String endDate = filter.getEndString();
            String sort = filter.getSort();
            String newsDeskSports = filter.getSportsString();
            String newsDeskArts = filter.getArtsString();
            String newsDeskFashion = filter.getFashionString();
            String SPORTS;
            String ARTS;
            String FASHION;
            String newsDesk;

            // Set date params
            if (!(beginDate.equals(EMPTY_STRING))) {
                params.put("begin_date", beginDate);
            }
            if (!(endDate.equals(EMPTY_STRING))) {
                params.put("end_date", endDate);
            }

            // Set sort params
            if (!(sort.equals(EMPTY_STRING))) {
                params.put("sort", sort);
            }

            if(!(newsDeskSports.equals(EMPTY_STRING)) || !(newsDeskArts.equals(EMPTY_STRING)) || !(newsDeskFashion.equals(EMPTY_STRING))) {
                // if any on the values are selected
                if (!(newsDeskSports.equals(EMPTY_STRING))) {

                } else {  }

                if (!(newsDeskArts.equals(EMPTY_STRING))) {

                } else {  }

                if (!(newsDeskFashion.equals(EMPTY_STRING))) {

                } else {  }
            }
            /*// set news_desk params
            if (!(newsDeskSports.equals(EMPTY_STRING))) {
                SPORTS = ;
            } else {  }
                // set news_desk to have sports

            if (!(newsDeskArts.equals(EMPTY_STRING))) {
                // set news_desk to have arts
                ARTS = newsDeskArts;
            }

            if (!(newsDeskFashion.equals(EMPTY_STRING))) {
                // set news_desk to have fashion
                FASHION = newsDeskFashion;

            }*/

            //params.put("fq", newsDesk);
        }

        return params;

    }


/*    public RequestParams infScrollFilter(int offset) {

        // use String query as incoming parameter
        String emp = "";
        String beginDate = filter.getBeginString();
        String endDate = Filter.getEndString();
        String sort = Filter.getSort();
        String newsDeskSports = Filter.getSportsString();
        String newsDeskArts = Filter.getArtsString();
        String newsDeskFashion = Filter.getFashionString();

        RequestParams params = defaultInfScrollParams(offset);


        // Set date params
        if (!beginDate.equals(emp)) {
            params.put("begin_date", beginDate);
        }
        if (!endDate.equals(emp)) {
            params.put("end_date", endDate);
        }

        // Set sort params
        if (!sort.equals(emp)) {
            params.put("sort", sort);
        }

        // set news_desk params
        if (!newsDeskSports.equals(emp)) {
            // set news_desk to have sports
        }

        if (!newsDeskArts.equals(emp)) {
            // set news_desk to have arts
        }

        if (!newsDeskFashion.equals(emp)) {
            // set news_desk to have fashion
        }

        return params;

    }*/



}
