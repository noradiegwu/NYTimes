package com.example.noradiegwu.nytsearchapplication;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noradiegwu.nytsearchapplication.Models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter (Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for the current position
        Article article = this.getItem(position);
        // check to see if existing view is being reused/recycled
            // if not using recycled view
                // inflate the layout
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }
        // find the image view
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        // clear out the recycled image
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(article.getHeadline());

        // populate the thumbnail image
        // remote download the image in the background

        String thumbnail = article.getThumbnail();

        if(!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).placeholder(R.drawable.newspaper).into(ivImage);
        } else {
            Picasso.with(getContext()).load(R.drawable.newspaper).into(ivImage);
        }

        return convertView;
    }
}