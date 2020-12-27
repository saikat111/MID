package com.codingburg.mid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingburg.mid.R;
import com.codingburg.mid.activity.MovieDetailsActivity;
import com.codingburg.mid.activity.TvShowDetailsActivity;
import com.codingburg.mid.model.TvList;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ProductViewHolder> {
private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";

private Context mCtx;
private List<TvList> productList;

public TvShowAdapter(Context mCtx, List<TvList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        }

@Override
public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.movie_card, null);
        return new ProductViewHolder(view);
        }

@Override
public void onBindViewHolder(ProductViewHolder holder, int position) {
        TvList movieData = productList.get(position);

        //loading the image
        Glide.with(mCtx)
        .load(IMAGE_PRODUCTS + movieData.getPoster_path())
        .into(holder.poster);

        holder.title.setText(movieData.getName());
        holder.vote.setText(movieData.getVote_count() + " " + "votes");
        holder.rating.setText(movieData.getVote_average());
        holder.id.setText(movieData.getId());
        }

@Override
public int getItemCount() {
        return productList.size();
        }

class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, rating, vote, date, id;
    ImageView poster;

    public ProductViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        title = itemView.findViewById(R.id.title);
        rating = itemView.findViewById(R.id.rating);
        vote = itemView.findViewById(R.id.vote);
        date = itemView.findViewById(R.id.date);
        poster = itemView.findViewById(R.id.poster);
        id = itemView.findViewById(R.id.id);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), TvShowDetailsActivity.class);
        intent.putExtra("id", id.getText().toString());
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("vote", vote.getText().toString());
        intent.putExtra("rating", rating.getText().toString());
        v.getContext().startActivity(intent);
    }
}
}