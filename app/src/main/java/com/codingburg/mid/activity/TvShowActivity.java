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
import com.codingburg.mid.R;
import com.codingburg.mid.adapter.MovieAdapter;
import com.codingburg.mid.adapter.TvShowAdapter;
import com.codingburg.mid.model.MovieList;
import com.codingburg.mid.model.TvList;
import com.leo.simplearcloader.SimpleArcLoader;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TvShowActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    //a list to store all the products
    List<TvList> popularTV;
    List<TvList> topTV;
    List<TvList> onAirTV;
    //the recyclerview
    RecyclerView  recyclerViewtvshow, recyclerViewtrending, recyclerView3;
    LinearLayoutManager HorizontalLayout;
    private Toolbar toolbar;
    private SimpleArcLoader simpleArcLoader, simpleArcLoader3, simpleArcLoader4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);
        simpleArcLoader = findViewById(R.id.loader);
        simpleArcLoader3 = findViewById(R.id.loader3);
        simpleArcLoader4 = findViewById(R.id.loader4);
        topTV = new ArrayList<>();
        onAirTV = new ArrayList<>();
        popularTV = new ArrayList<>();



        recyclerViewtvshow = findViewById(R.id.recyclerViewtvshow);
        recyclerViewtrending = findViewById(R.id.recyclerViewtrending);
        recyclerView3 = findViewById(R.id.recyclerView3);

        recyclerViewtvshow.setHasFixedSize(true);

        /* recyclerViewtvshow.setLayoutManager(new LinearLayoutManager(this));*/
        //initializing the productlist


        mRequestQueue = Volley.newRequestQueue(this);
        //this method will fetch and parse json
        //to display it in recyclerview
        topRated();
        onAir();
        popularTvshow();
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
    private void popularTvshow() {
        HorizontalLayout
                = new LinearLayoutManager(
                TvShowActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView3.setLayoutManager(HorizontalLayout);
        simpleArcLoader4.start();
        String URL_PRODUCTS = "https://api.themoviedb.org/3/tv/popular?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        popularTV.add(new TvList(
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
                    TvShowAdapter adapter = new TvShowAdapter(TvShowActivity.this, popularTV);
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
                simpleArcLoader4.stop();
                simpleArcLoader4.setVisibility(View.GONE);
                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }

    private void onAir() {
        simpleArcLoader.start();
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerViewtrending.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS = "https://api.themoviedb.org/3/tv/on_the_air?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        onAirTV.add(new TvList(
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
                    TvShowAdapter adapter = new TvShowAdapter(TvShowActivity.this, onAirTV);
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

    private void topRated() {
        simpleArcLoader3.start();
        HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewtvshow.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS = "https://api.themoviedb.org/3/tv/top_rated?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        topTV.add(new TvList(
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
                    TvShowAdapter adapter = new TvShowAdapter(TvShowActivity.this, topTV);
                    recyclerViewtvshow.setAdapter(adapter);
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
                error.printStackTrace();
                simpleArcLoader3.stop();
                simpleArcLoader3.setVisibility(View.GONE);
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