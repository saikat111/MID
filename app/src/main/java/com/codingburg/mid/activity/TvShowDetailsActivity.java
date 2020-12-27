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
import com.codingburg.mid.R;
import com.codingburg.mid.adapter.CastAdapter;
import com.codingburg.mid.adapter.ProductionCompanyAdapter;
import com.codingburg.mid.adapter.TvCastAdapter;
import com.codingburg.mid.adapter.TvShowAdapter;
import com.codingburg.mid.adapter.TvVideoAdapter;
import com.codingburg.mid.adapter.TypeAdapter;
import com.codingburg.mid.adapter.VideoAdapter;
import com.codingburg.mid.api.Api;
import com.codingburg.mid.model.MovieList;
import com.codingburg.mid.model.ProductionCompanyData;
import com.codingburg.mid.model.TvCast;
import com.codingburg.mid.model.TvList;
import com.codingburg.mid.model.TypeData;
import com.codingburg.mid.model.Video;
import com.codingburg.mid.utiles.AddLifecycleCallbackListener;
import com.leo.simplearcloader.SimpleArcLoader;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TvShowDetailsActivity extends AppCompatActivity implements AddLifecycleCallbackListener {

    String api_key;
    private String id, rating,title,vote;
    String Url = "https://api.themoviedb.org/3/tv/";
    List<TvList> TvShowList;

    ImageView coverImage,imageView2;
    TextView ratingd,name, votes, overView, tagline, runtime, release_date, status, budget;
    String image_url = "https://image.tmdb.org/t/p/w500";
    private RecyclerView rproduction, movietype, recyclerView, recyclerView2,recyclerView3;
    private SimpleArcLoader simpleArcLoader, simpleArcLoader2, simpleArcLoader3;
    List<ProductionCompanyData> productionList;
    List<TypeData> typeList;
    LinearLayoutManager HorizontalLayout;
    private RequestQueue mRequestQueue;
    List<MovieList> productList;
    List<TvCast> castList;
    List<Video> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);
        Api api = new Api();
        api_key = api.getApi_key();
        String recommandationUrl = "/recommendations?api_key=" + api_key +"&language=en-US&page=1";
        String castUrl = "/credits?api_key=" + api_key +"&language=en-US";
        String videoUrl ="/videos?api_key="+api_key +"&language=en-US";
        String secoundUrl = "?api_key="+ api_key + "&language=en-US";
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
        release_date = findViewById(R.id.release_date);
        status = findViewById(R.id.status);
        budget = findViewById(R.id.budget);
        rproduction = findViewById(R.id.rproduction);
        simpleArcLoader = findViewById(R.id.loader2);
        movietype = findViewById(R.id.type);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);
        productionList = new ArrayList<>();
        typeList = new ArrayList<>();
        castList = new ArrayList<>();
        videoList = new ArrayList<>();
        TvShowList = new ArrayList<>();

        ratingd.setText(rating);
        name.setText(title);
        votes.setText(vote);
        simpleArcLoader2 = findViewById(R.id.loader);
        productList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        fetchData(secoundUrl);
        productionData(secoundUrl);
        moveType(secoundUrl);
        loadMovie(recommandationUrl);
        castData(castUrl);
        videoLoad(videoUrl);
    }

    private void videoLoad(String videoUrl) {
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        String URL_PRODUCTS   = Url + id + videoUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        videoList.add(new Video(
                                movie.getString("id"),
                                movie.getString("key"),
                                movie.getString("type")
                        ));
                        if(i <=3){
                            break;
                        }
                    }
                    TvVideoAdapter adapter = new TvVideoAdapter(TvShowDetailsActivity.this, videoList);
                    recyclerView3.setAdapter(adapter);

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

    private void castData(String castUrl) {
        String URL_PRODUCTS   = Url + id + castUrl;;
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("cast");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        castList.add(new TvCast(
                                movie.getString("gender"),
                                movie.getString("id"),
                                movie.getString("known_for_department"),
                                movie.getString("name"),
                                movie.getString("original_name"),
                                movie.getString("popularity"),
                                movie.getString("profile_path"),
                                movie.getString("character")


                        ));
                    }
                    TvCastAdapter adapter = new TvCastAdapter(TvShowDetailsActivity.this, castList);
                    recyclerView2.setAdapter(adapter);

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

    private void moveType(String secoundUrl) {
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
    private void loadMovie(String recommandationUrl) {

        String URL_PRODUCTS   = Url + id + recommandationUrl;;

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
                    TvShowAdapter adapter = new TvShowAdapter(TvShowDetailsActivity.this, TvShowList);
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


    private void productionData(String secoundUrl) {
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

    private void fetchData(String secoundUrl) {
        String url = Url + id + secoundUrl;
        simpleArcLoader2.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            overView.setText(jsonObject.getString("overview"));
                            runtime.setText(jsonObject.getString("number_of_seasons"));
                            budget.setText(jsonObject.getString("number_of_episodes"));
                            status.setText(jsonObject.getString("last_air_date"));

                            String tag = jsonObject.getString("tagline");
                            if(tag == "'"){
                                tagline.setText("No data available");
                            }
                            else {
                                tagline.setText(jsonObject.getString("tagline"));
                            }
                            release_date.setText(jsonObject.getString("first_air_date"));

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

    @Override
    public void addLifeCycleCallBack(YouTubePlayerView youTubePlayerView) {
        getLifecycle().addObserver(youTubePlayerView);
    }
}