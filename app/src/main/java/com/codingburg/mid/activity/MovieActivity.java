package com.codingburg.mid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codingburg.mid.adapter.MovieAdapter;
import com.codingburg.mid.R;
import com.codingburg.mid.model.MovieList;
import com.leo.simplearcloader.SimpleArcLoader;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    //a list to store all the products
    List<MovieList> productList;
    List<MovieList> topRated;
    List<MovieList> upcoming;
    //the recyclerview
    RecyclerView recyclerView, recyclerViewtvshow, recyclerViewtrending;
    LinearLayoutManager HorizontalLayout;
    private Toolbar toolbar;
    private SimpleArcLoader simpleArcLoader, simpleArcLoader2, simpleArcLoader3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        simpleArcLoader = findViewById(R.id.loader);
        simpleArcLoader2 = findViewById(R.id.loader2);
        simpleArcLoader3 = findViewById(R.id.loader3);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewtvshow = findViewById(R.id.recyclerViewtvshow);
        recyclerViewtrending = findViewById(R.id.recyclerViewtrending);

        recyclerView.setHasFixedSize(true);
        recyclerViewtvshow.setHasFixedSize(true);
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        /* recyclerViewtvshow.setLayoutManager(new LinearLayoutManager(this));*/
        //initializing the productlist
        productList = new ArrayList<>();
        topRated = new ArrayList<>();
        upcoming = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        //this method will fetch and parse json
        //to display it in recyclerview
        loadMoviePopular();
        topRatedMovies();
        upcomingMovies();
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.gnt_blue));
        spaceNavigationView.addSpaceItem(new SpaceItem("Movie", R.drawable.movie));
        spaceNavigationView.addSpaceItem(new SpaceItem("Tv Show", R.drawable.tv));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_baseline_home_24);
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.space_white));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

    private void upcomingMovies() {
        simpleArcLoader.start();
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerViewtrending.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS = "https://api.themoviedb.org/3/movie/upcoming?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        upcoming.add(new MovieList(
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
                    MovieAdapter adapter = new MovieAdapter(MovieActivity.this, upcoming);
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

    private void topRatedMovies() {
        simpleArcLoader2.start();
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewtvshow.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS = "https://api.themoviedb.org/3/movie/top_rated?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        topRated.add(new MovieList(
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
                    MovieAdapter adapter = new MovieAdapter(MovieActivity.this, topRated);
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
        String URL_PRODUCTS = "https://api.themoviedb.org/3/movie/popular?api_key=9fd3e2138534849340edf9888424bc38&language=en-US";

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
                    MovieAdapter adapter = new MovieAdapter(MovieActivity.this, productList);
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}