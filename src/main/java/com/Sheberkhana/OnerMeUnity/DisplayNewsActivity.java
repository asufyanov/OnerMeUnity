package com.Sheberkhana.OnerMeUnity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Sheberkhana.OnerMeUnity.Models.News;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DisplayNewsActivity extends AppCompatActivity {

    ImageView toolbarImageView;
    News news;
    TextView descriptionTextView;
    Toolbar toolbar;

    CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        setReferences();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Glide
                .with(this)
                .load(news.getPreviewImage())
                //.centerCrop()
                //.placeholder(R.drawable.app_banner)
                .into(toolbarImageView);
        descriptionTextView.setText(Html.fromHtml(news.getDescription()));

        Toast.makeText(this, news.getImages().toString(), Toast.LENGTH_LONG).show();

    }

    private void setReferences() {

        Gson gson = new Gson();
        news = gson.fromJson(getIntent().getStringExtra("myJson"), News.class);

        descriptionTextView = (TextView) findViewById(R.id.tv_description);
        toolbarImageView = (ImageView) findViewById(R.id.toolbarImageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(news.getImages().size());


        carouselView.setImageListener(imageListener);

        setSupportActionBar(toolbar);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Glide
                    .with(getApplicationContext())
                    .load(news.getImages().get(position))
                    //.centerCrop()
                    //.placeholder(R.drawable.app_banner)
                    .into(imageView);
        }
    };
}
