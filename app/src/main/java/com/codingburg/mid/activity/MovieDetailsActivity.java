package com.codingburg.mid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codingburg.mid.adapter.MovieAdapter;
import com.codingburg.mid.adapter.MovieAdapterForCard;
import com.codingburg.mid.adapter.ProductionCompanyAdapter;
import com.codingburg.mid.R;
import com.codingburg.mid.adapter.TypeAdapter;
import com.codingburg.mid.model.MovieList;
import com.codingburg.mid.model.ProductionCompanyData;
import com.codingburg.mid.model.TypeData;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private String id, rating,title,vote;
    String Url = "https://api.themoviedb.org/3/movie/";
    String secoundUrl = "?api_key=9fd3e2138534849340edf9888424bc38&language=en-US";
    String recommandationUrl = "/recommendations?api_key=9fd3e2138534849340edf9888424bc38&language=en-US&page=1";
    ImageView coverImage,imageView2;
    TextView ratingd,name, votes, overView, tagline, runtime, revenue, release_date, status, budget;
    String image_url = "https://image.tmdb.org/t/p/w500";
    private RecyclerView rproduction, movietype, recyclerView;
    private SimpleArcLoader simpleArcLoader, simpleArcLoader2, simpleArcLoader3;
    List<ProductionCompanyData> productionList;
    List<TypeData> typeList;
    LinearLayoutManager HorizontalLayout;
    private RequestQueue mRequestQueue;
    List<MovieList> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");
        vote = getIntent().getExtras().getString("vote");
        rating = getIntent().getExtras().getString("rating");
        mRequestQueue = Volley.newRequestQueue(this);
        coverImage = findViewById(R.id.poster);
        imageView2 = findViewById(R.id.imageView2);
        name =  findViewById(R.id.name);
        votes = findViewById(R.id.votes);
        ratingd = findViewById(R.id.ratingd);
        overView = findViewById(R.id.overview);
        tagline = findViewById(R.id.tagline);
        runtime = findViewById(R.id.runtime);
        revenue = findViewById(R.id.revenue);
        release_date = findViewById(R.id.release_date);
        status = findViewById(R.id.status);
        budget = findViewById(R.id.budget);
        rproduction = findViewById(R.id.rproduction);
        simpleArcLoader = findViewById(R.id.loader2);
        movietype = findViewById(R.id.type);
        recyclerView = findViewById(R.id.recyclerView);
        productionList = new ArrayList<>();
        typeList = new ArrayList<>();
        ratingd.setText(rating);
        name.setText(title);
        votes.setText(vote);
        simpleArcLoader2 = findViewById(R.id.loader);
        productList = new ArrayList<>();
      /*  HorizontalLayout
                = new LinearLayoutManager(
                MovieDetailsActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);*/
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        fetchData();
        productionData();
        moveType();
        loadMovie();
    }

    private void moveType() {
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        movietype.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS   = Url + id + secoundUrl;;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("genres");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        typeList.add(new TypeData(
                                movie.getString("id"),
                                movie.getString("name")
                        ));
                    }
                    TypeAdapter adapter = new TypeAdapter(getApplication(), typeList);
                    movietype.setAdapter(adapter);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }
    private void loadMovie() {

        String URL_PRODUCTS   = Url + id + recommandationUrl;;

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
                    MovieAdapterForCard adapter = new MovieAdapterForCard(MovieDetailsActivity.this, productList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });
        mRequestQueue.add(jsonObjectRequest);

    }


    private void productionData() {
        simpleArcLoader.start();
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        rproduction.setLayoutManager(HorizontalLayout);
        String URL_PRODUCTS   = Url + id + secoundUrl;;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("production_companies");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        productionList.add(new ProductionCompanyData(
                                movie.getString("logo_path"),
                                movie.getString("name"),
                                movie.getString("origin_country"),
                                movie.getString("id")
                        ));
                    }
                    ProductionCompanyAdapter adapter = new ProductionCompanyAdapter(getApplication(), productionList);
                    rproduction.setAdapter(adapter);
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

    private void fetchData() {
        String url = Url + id + secoundUrl;
        simpleArcLoader2.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                           /* todayCase.setText(jsonObject.getString("todayCases"));
                            todayDeath.setText(jsonObject.getString("todayDeaths"));
                            todayRecovered.setText(jsonObject.getString("todayRecovered"));
                            totalTest.setText(jsonObject.getString("tests"));
                            totalCase.setText(jsonObject.getString("cases"));
                            totalDeath.setText(jsonObject.getString("deaths"));
                            totalRecovered.setText(jsonObject.getString("recovered"));
                            activeCase.setText(jsonObject.getString("active"));
                            toolbar_title.setText(jsonObject.getString("country"));
                            counrtyName.setText(toolbar_title.getText().toString());*/
                           /* JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flagUrl = object.getString("flag");*/
                            overView.setText(jsonObject.getString("overview"));
                            status.setText(jsonObject.getString("status"));
                            if(Integer.parseInt(jsonObject.getString("budget")) == 0 ){
                                budget.setText("No data Available");
                            }
                            else {
                                budget.setText(jsonObject.getString("budget") + " " + "USD");
                            }

                            String tag = jsonObject.getString("tagline");
                            if(tag == "'"){
                                tagline.setText("No data available");
                            }
                            else {
                                tagline.setText(jsonObject.getString("tagline"));
                            }
                            release_date.setText(jsonObject.getString("release_date"));
                            if(Integer.parseInt(jsonObject.getString("revenue")) == 0){
                                revenue.setText("No data available");
                            }
                            else {
                                revenue.setText(jsonObject.getString("revenue")  + " " + "USD");
                            }
                            runtime.setText(jsonObject.getString("runtime") + " " + "minutes");

                            Glide.with(getApplicationContext()).load(image_url + jsonObject.getString("backdrop_path")).into(coverImage);
                            Glide.with(getApplicationContext()).load(image_url + jsonObject.getString("poster_path")).into(imageView2);






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
                simpleArcLoader2.stop();
                simpleArcLoader2.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

}