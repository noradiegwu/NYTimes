package com.example.noradiegwu.nytsearchapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.noradiegwu.nytsearchapplication.Models.Article;
import com.example.noradiegwu.nytsearchapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {
    @BindView(R.id.wvArticle) WebView wvArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = (Toolbar) findViewById(R.id....)
        //setSupportActionBar(toolbar);
        setContentView(R.layout.activity_article);
        Article article = (Article) getIntent().getSerializableExtra("article");

        //WebView webView = (WebView) findViewById(R.id.wvArticle);

        ButterKnife.bind(this);

        wvArticle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wvArticle.loadUrl(article.getWebUrl());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // get reference to WebView
        WebView wvArticle = (WebView) findViewById(R.id.wvArticle);
        // pass in the URL currently being used by the WebView
        if (wvArticle != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());
        }

        miShare.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }
}
