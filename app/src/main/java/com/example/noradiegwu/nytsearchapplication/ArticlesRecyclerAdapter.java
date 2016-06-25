package com.example.noradiegwu.nytsearchapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.noradiegwu.nytsearchapplication.Models.Article;

import java.util.ArrayList;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder> {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView ivImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

        }
    }


    // Store a member variable for the contacts 
    private ArrayList<Article> mArticles;
    // Store the context for easy access
    private Context mContext;

    // Pass in the articles array into the constructor
    public ArticlesRecyclerAdapter(Context context, ArrayList<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview 
    private Context getContext() {
        return mContext;
    }

    @Override
    public ArticlesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticlesRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on your views and data model
        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(article.getHeadline());

        ImageView ivImage = viewHolder.ivImage;

        // clear out the recycled image 
        ivImage.setImageResource(0);

        // populate the thumbnail image 
        // remote download the image in the background  
        String thumbnail = article.getThumbnail();

        if(!TextUtils.isEmpty(thumbnail)) {
            Glide.with(getContext()).load(thumbnail).placeholder(R.drawable.ic_nyt_load).into(ivImage);
        } else {
            Glide.with(getContext()).load(R.drawable.ic_nyt_load).into(ivImage);
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


}
