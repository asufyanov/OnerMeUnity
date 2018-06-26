package com.Sheberkhana.OnerMeUnity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Sheberkhana.OnerMeUnity.Models.ArtObject;
import com.Sheberkhana.OnerMeUnity.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Admin on 23.06.2018.
 */

public class RecyclerObjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ArtObject> objects;
    Context parentContext;
    Gson gson;

    public RecyclerObjectsAdapter(ArrayList<ArtObject> objects) {
        this.objects = objects;
        gson = new Gson();


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View objectsView = inflater.inflate(R.layout.object_item, parent, false);

        ObjectsViewHolder objectsViewHolder = new ObjectsViewHolder(objectsView);

        return objectsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ObjectsViewHolder vh = (ObjectsViewHolder) holder;
        vh.title_tv.setText(objects.get(position).getTitle());
        if (objects.get(position).getPreviewImage() != null) {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            Glide
                    .with(parentContext)
                    .load(objects.get(position).getPreviewImage())
                    .apply(options)
                    //.placeholder(R.drawable.app_banner)
                    .into(vh.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ObjectsViewHolder extends RecyclerView.ViewHolder {

        TextView title_tv;
        ImageView imageView;

        public ObjectsViewHolder(View itemView) {
            super(itemView);
            title_tv = (TextView) itemView.findViewById(R.id.tv_title);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
