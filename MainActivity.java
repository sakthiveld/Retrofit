package com.example.sakthivel.retrofitapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<Albums> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    Albums albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if(BuildConfig.FLAVOR.equals("trial")){
            getTrial();
        }
        else if(BuildConfig.FLAVOR.equals("full")){
            getFull();
        }

}

    private void getTrial(){
        Albums albums = new Albums("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(albums);

        albums = new Albums("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(albums);

        albums = new Albums("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(albums);

        albums = new Albums("Shaun the Sheep", "Animation", "2015");
        movieList.add(albums);

        albums = new Albums("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(albums);

        albums = new Albums("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(albums);

        albums = new Albums("Up", "Animation", "2009");
        movieList.add(albums);

        albums = new Albums("Star Trek", "Science Fiction", "2009");
        movieList.add(albums);

        albums = new Albums("The LEGO Movie", "Animation", "2014");
        movieList.add(albums);

        albums = new Albums("Iron Man", "Action & Adventure", "2008");
        movieList.add(albums);

        albums = new Albums("Aliens", "Science Fiction", "1986");
        movieList.add(albums);

        albums = new Albums("Chicken Run", "Animation", "2000");
        movieList.add(albums);

        albums = new Albums("Back to the Future", "Science Fiction", "1985");
        movieList.add(albums);

        albums = new Albums("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(albums);

        albums = new Albums("Goldfinger", "Action & Adventure", "1965");
        movieList.add(albums);

        albums = new Albums("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(albums);

        mAdapter.notifyDataSetChanged();
    }

    private void getFull() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        final Api api = retrofit.create(Api.class);

        Call<List<Albums>> call = api.getMovies();

        call.enqueue(new Callback<List<Albums>>() {
            @Override
            public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {
                List<Albums> moviesList = response.body();

                String[] title = new String[moviesList.size()];
                String[] id = new String[moviesList.size()];
                String[] userId = new String[moviesList.size()];

                for (int i = 0; i < moviesList.size(); i++) {
                    title[i] = moviesList.get(i).getTitle();
                    id[i] = moviesList.get(i).getId();
                    userId[i] = moviesList.get(i).getUserId();

                    albums = new Albums(userId[i], id[i], title[i]);
                    movieList.add(albums);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}