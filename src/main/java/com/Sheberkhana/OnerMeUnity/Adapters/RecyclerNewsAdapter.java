package com.Sheberkhana.OnerMeUnity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Sheberkhana.OnerMeUnity.DisplayNewsActivity;
import com.Sheberkhana.OnerMeUnity.Models.News;
import com.Sheberkhana.OnerMeUnity.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Admin on 17.06.2018.
 */

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context parentContext;
    ArrayList<News> news;
    Gson gson;

    public RecyclerNewsAdapter(ArrayList<News> news) {
        this.news = news;
        gson = new Gson();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View newsView = inflater.inflate(R.layout.news_item, parent, false);

        NewsViewHolder newsViewHolder = new NewsViewHolder(newsView);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        NewsViewHolder vh = (NewsViewHolder) holder;
        vh.title_tv.setText(news.get(position).getTitle());
        vh.description_tv.setText(news.get(position).getDescription());
        if (news.get(position).getPreviewImage() != null) {
            Glide
                    .with(parentContext)
                    .load(news.get(position).getPreviewImage())
                    //.centerCrop()
                    //.placeholder(R.drawable.app_banner)
                    .into(vh.imageView);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentContext, DisplayNewsActivity.class);
                String myJson = gson.toJson(news.get(position));
                intent.putExtra("myJson", myJson);
                parentContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        if (news != null)
            return news.size();
        else return 0;
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title_tv;
        TextView description_tv;
        ImageView imageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title_tv = (TextView) itemView.findViewById(R.id.tv_title);
            description_tv = (TextView) itemView.findViewById(R.id.tv_description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
