package com.example.sakthivel.retrofitapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
    private MoviesAdapter moviesAdapter;
    Albums albums;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private BroadcastReceiver mNetworkReceiver;
    static RelativeLayout relativeLayout;
    String networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        relativeLayout = findViewById(R.id.relative_layout);


        FirebaseApp.initializeApp(MainActivity.this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    Log.w(TAG, "Failed to get Instance Id", task.getException());
                    return;
                }
                String instanceId = task.getResult().getToken();        // Get the value of Firebase Instance Id or token id
                String message = getString(R.string.instance_id, instanceId);
                Log.d(TAG, message);                                    // Log and toast the message value
                //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        sharedPreferences = getSharedPreferences("Retrofit_Api", MODE_PRIVATE);  // Specify preference name and preference method
        editor = sharedPreferences.edit();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        moviesAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);

        String titleshare = sharedPreferences.getString("title", "");     // Get the value for title from shared preference
        String idshare = sharedPreferences.getString("id", "");           // Get the value for id from shared preference
        String useridshare = sharedPreferences.getString("userid", "");   // Get the value for user id from shared preference

        if(idshare.equals("")) {
            if (BuildConfig.FLAVOR.equals("trial")) {
                getTrial();                                        // If trial version means getTrial() function will be call
            } else if (BuildConfig.FLAVOR.equals("full")) {
                getFull();                                         // If full version means getFull() function will be call
            }
        }
        else {
            String[] title = titleshare.split(",");         // Split title using "," and store to the string array
            String[] id = idshare.split(",");               // Split id using "," and store to the string array
            String[] userid = useridshare.split(",");       // Split user id using "," and store to the string array

            for(int i=0;i<title.length;i++){
                albums = new Albums(userid[i], id[i], title[i]);     // Albums constructor is called and store the datas
                movieList.add(albums);                            // Class instance add or store to the List
            }
            moviesAdapter.notifyDataSetChanged();                 // Note the datas are changed or not
        }


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Albums albums1 = movieList.get(position);
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
                    }
                }));
}

    private void getTrial(){
        Albums albums = new Albums("Mad Max: Fury Road", "Action & Adventure", "2015");    // Albums constructor is called and store the datas
        movieList.add(albums);                                                                             // Class instance add or store to the List

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

        moviesAdapter.notifyDataSetChanged();
    }

    private void getFull() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();  // Create retrofit builder and initialize url

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
                    moviesAdapter.notifyDataSetChanged();
                }

                StringBuilder sbtitle = new StringBuilder();
                StringBuilder sbid = new StringBuilder();
                StringBuilder sbuserid = new StringBuilder();

                for (int i = 0; i < title.length; i++) {
                    sbtitle.append(title[i]).append(",");          // title values are stored to sbtitle
                    sbid.append(id[i]).append(",");                // id values are stored to sbid
                    sbuserid.append(userId[i]).append(",");        // user id values are stored to sbuserid
                }

                editor.putString("title", String.valueOf(sbtitle));   // key is used to identify the data
                editor.putString("id", String.valueOf(sbid));
                editor.putString("userid", String.valueOf(sbuserid));
                editor.apply();                                       // come back from edit

            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void dialog(boolean status, String message){
        if(status){
            Snackbar snackbar = Snackbar.make(relativeLayout, ""+message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            Snackbar snackbar = Snackbar.make(relativeLayout, ""+message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

}