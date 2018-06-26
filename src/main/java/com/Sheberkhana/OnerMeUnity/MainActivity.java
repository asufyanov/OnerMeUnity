package com.Sheberkhana.OnerMeUnity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.Sheberkhana.OnerMeUnity.Adapters.RecyclerNewsAdapter;
import com.Sheberkhana.OnerMeUnity.Adapters.RecyclerObjectsAdapter;
import com.Sheberkhana.OnerMeUnity.Models.ArtObject;
import com.Sheberkhana.OnerMeUnity.Models.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn;
    FloatingActionButton fab;
    ArrayList<News> news;
    ArrayList<ArtObject> artObjects;


    RecyclerView recyclerView;
    RecyclerView recyclerViewObjects;

    RecyclerNewsAdapter recyclerNewsAdapter;
    RecyclerObjectsAdapter recyclerObjectsAdapter;



    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagerH;



    private SwipeRefreshLayout swipeContainer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setReferences();


    }

    private void setReferences() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerViewObjects = (RecyclerView) findViewById(R.id.recyclerViewH);
        recyclerViewObjects.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManagerH = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewObjects.setLayoutManager(linearLayoutManagerH);

        news = new ArrayList<>();
        artObjects = new ArrayList<>();
        recyclerNewsAdapter = new RecyclerNewsAdapter(news);
        recyclerObjectsAdapter = new RecyclerObjectsAdapter(artObjects);

        recyclerView.setAdapter(recyclerNewsAdapter);
        recyclerViewObjects.setAdapter(recyclerObjectsAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                news.clear();
                artObjects.clear();
                recyclerNewsAdapter.notifyDataSetChanged();
                fetchNews();
                fetchObjects();
                recyclerNewsAdapter.notifyDataSetChanged();

            }
        });

        fetchNews();
        fetchObjects();


    }

    private void fetchNews() {
        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FirestoreTest", document.getId() + " => " + document.getData());
                                News tempNews = document.toObject(News.class);
                                news.add(tempNews);


                            }
                            recyclerNewsAdapter.notifyDataSetChanged();

                            swipeContainer.setRefreshing(false);
                        } else {
                            swipeContainer.setRefreshing(false);
                            Log.w("FirestoreTest", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void fetchObjects() {
        db.collection("artObjects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArtObject tempObject = document.toObject(ArtObject.class);
                                artObjects.add(tempObject);


                            }
                            recyclerObjectsAdapter.notifyDataSetChanged();

                            swipeContainer.setRefreshing(false);
                        } else {
                            swipeContainer.setRefreshing(false);
                            Log.w("FirestoreTest", "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}
