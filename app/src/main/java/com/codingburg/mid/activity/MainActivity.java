package com.codingburg.mid.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codingburg.mid.R;
import com.codingburg.mid.adapter.MovieAdapter;

import com.codingburg.mid.adapter.TrendingAdapter;
import com.codingburg.mid.adapter.TvShowAdapter;
import com.codingburg.mid.api.Api;
import com.codingburg.mid.model.MovieList;
import com.codingburg.mid.model.TrendingList;
import com.codingburg.mid.model.TvList;
import com.facebook.ads.AudienceNetworkAds;
import com.leo.simplearcloader.SimpleArcLoader;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.onesignal.OneSignal;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "d221ffbc-af32-4294-95fd-15cf9389dd78";
    String api_key;
    private RequestQueue mRequestQueue;
    //a list to store all the products
    List<MovieList> productList;
    List<MovieList> hindiproductList;
    List<TvList> TvShowList;
    List<TrendingList> trendingList;
    //the recyclerview
    RecyclerView recyclerView, recyclerViewtvshow, recyclerViewtrending, recyclerView3;
    LinearLayoutManager HorizontalLayout;
    private Toolbar toolbar;
    private SimpleArcLoader simpleArcLoader, simpleArcLoader2, simpleArcLoader3, simpleArcLoader4;
    int backPress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        Api api = new Api();
        api_key = api.getApi_key();


        simpleArcLoader = findViewById(R.id.loader);
        simpleArcLoader2 = findViewById(R.id.loader2);
        simpleArcLoader3 = findViewById(R.id.loader3);
        simpleArcLoader4 = findViewById(R.id.loader4);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewtvshow = findViewById(R.id.recyclerViewtvshow);
        recyclerViewtrending = findViewById(R.id.recyclerViewtrending);
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerViewtvshow.setHasFixedSize(true);
        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
       /* recyclerViewtvshow.setLayoutManager(new LinearLayoutManager(this));*/
        //initializing the productlist
        productList = new ArrayList<>();
        TvShowList = new ArrayList<>();
        trendingList = new ArrayList<>();
        hindiproductList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        //this method will fetch and parse json
        //to display it in recyclerview
        loadMoviePopular();
        loadPopularTvShow();
        loadTrending();
        hindiMovie();
        SpaceNavigationView spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.gnt_blue));
        spaceNavigationView.addSpaceItem(new SpaceItem("Movie", R.drawable.movie));
        spaceNavigationView.addSpaceItem(new SpaceItem("Tv Show", R.drawable.tv));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_baseline_keyboard_voice_24);

        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.space_white));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
               Tell();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    startActivity(new Intent(getApplicationContext(), MovieActivity.class));

                }
                if (itemIndex == 1) {
                    startActivity(new Intent(getApplicationContext(), TvShowActivity.class));

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    startActivity(new Intent(getApplicationContext(), MovieActivity.class));

                }
                if (itemIndex == 1) {
                    startActivity(new Intent(getApplicationContext(), TvShowActivity.class));

                }
            }
        });


    }

    private void Tell() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    private void hindiMovie() {
        HorizontalLayout
                = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView3.setLayoutManager(HorizontalLayout);
        simpleArcLoader4.start();
        //url
        String firstUrl = "https://api.themoviedb.org/3/discover/movie?api_key=";
        String hindiUrl = "&language=hi-IN&region=IN&sort_by=popularity.desc&page=1&with_original_language=hi";
        String URL_PRODUCTS = firstUrl + api_key + hindiUrl;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        hindiproductList.add(new MovieList(
                                movie.getString("id"),
                                movie.getString("original_language"),
                                movie.getString("original_title"),
                                movie.getString("overview"),
                                movie.getString("popularity"),
                                movie.getString("poster_path"),
                                movie.getString("release_date"),
                                movie.getString("title"),
                                movie.getString("vote_average"),
                                movie.getString("vote_count")

                        ));
                    }
                    MovieAdapter adapter = new MovieAdapter(MainActivity.this, hindiproductList);
                    recyclerView3.setAdapter(adapter);
                    simpleArcLoader4.stop();
                    simpleArcLoader4.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader4.stop();
                    simpleArcLoader4.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader3.stop();
                simpleArcLoader3.setVisibility(View.GONE);
                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }

    private void loadTrending() {
        simpleArcLoader.start();
        recyclerViewtrending.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        String URL_PRODUCTS = "https://api.themoviedb.org/3/trending/all/day?api_key=" + api_key;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        trendingList.add(new TrendingList(
                                movie.getString("id"),
                                movie.getString("original_language"),
                                movie.getString("overview"),
                                movie.getString("popularity"),
                                movie.getString("poster_path"),
                              /*  movie.getString("release_date"),*/
                            /*    movie.getString("title"),*/
                                movie.getString("vote_average"),
                                movie.getString("vote_count")


                        ));
                    }
                    TrendingAdapter adapter = new TrendingAdapter(MainActivity.this, trendingList);
                    recyclerViewtrending.setAdapter(adapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void loadPopularTvShow() {
        simpleArcLoader2.start();
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
         recyclerViewtvshow.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS = "https://api.themoviedb.org/3/tv/popular?api_key=" + api_key + "&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        TvShowList.add(new TvList(
                                movie.getString("id"),
                                movie.getString("original_language"),
                                movie.getString("name"),
                                movie.getString("overview"),
                                movie.getString("popularity"),
                                movie.getString("poster_path"),
                                movie.getString("vote_average"),
                                movie.getString("vote_count")

                        ));

                    }
                    TvShowAdapter adapter = new TvShowAdapter(MainActivity.this, TvShowList);
                    recyclerViewtvshow.setAdapter(adapter);
                    simpleArcLoader2.stop();
                    simpleArcLoader2.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader2.stop();
                    simpleArcLoader2.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                simpleArcLoader2.stop();
                simpleArcLoader2.setVisibility(View.GONE);
            }
        });
        mRequestQueue.add(jsonObjectRequest);


    }

    private void loadMoviePopular() {
        simpleArcLoader3.start();
        String URL_PRODUCTS = "https://api.themoviedb.org/3/movie/popular?api_key=" + api_key + "&language=en-US";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        productList.add(new MovieList(
                                movie.getString("id"),
                                movie.getString("original_language"),
                                movie.getString("original_title"),
                                movie.getString("overview"),
                                movie.getString("popularity"),
                                movie.getString("poster_path"),
                                movie.getString("release_date"),
                                movie.getString("title"),
                                movie.getString("vote_average"),
                                movie.getString("vote_count")

                        ));
                    }
                    MovieAdapter adapter = new MovieAdapter(MainActivity.this, productList);
                    recyclerView.setAdapter(adapter);
                    simpleArcLoader3.stop();
                    simpleArcLoader3.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader3.stop();
                    simpleArcLoader3.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader3.stop();
                simpleArcLoader3.setVisibility(View.GONE);
                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0) != null){
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("key", result.get(0));
                        startActivity(intent);
                    }
                    /*Toast.makeText(getApplicationContext(), result.get(0),Toast.LENGTH_SHORT).show();  */              }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPress++;
        if(backPress == 1){
            Toast.makeText(getBaseContext(),
                    "Press once again to exit!", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (backPress == 2){
            MainActivity.this.finish();
            System.exit(0);
        }

    }
}