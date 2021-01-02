package com.codingburg.mid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.codingburg.mid.adapter.MovieAdapterForCard;
import com.codingburg.mid.api.Api;
import com.codingburg.mid.model.MovieList;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    String key;
    String api_key;
    RecyclerView recyclerView;
    List<MovieList> productList;
    private RequestQueue mRequestQueue;
    private SimpleArcLoader simpleArcLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        simpleArcLoader = findViewById(R.id.loader2);
        mRequestQueue = Volley.newRequestQueue(this);
        productList = new ArrayList<>();
        key = getIntent().getExtras().getString("key");
        Api api = new Api();
        api_key = api.getApi_key();
        recyclerView = findViewById(R.id.movier);
        result();

    }

    private void result() {
        simpleArcLoader.start();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        String url1  = "https://api.themoviedb.org/3/search/movie?api_key=";
        String url2 = "&language=en-US&query=";
        String url3 = "&page=1&include_adult=true";
        String finalUrl = url1 + api_key + url2 + key + url3;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>() {
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
                    MovieAdapterForCard adapter = new MovieAdapterForCard(SearchActivity.this, productList);
                    recyclerView.setAdapter(adapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

            }
        });
        mRequestQueue.add(jsonObjectRequest);


    }

}