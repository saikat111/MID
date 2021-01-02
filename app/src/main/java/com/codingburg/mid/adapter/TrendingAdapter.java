package com.codingburg.mid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingburg.mid.R;
import com.codingburg.mid.model.TrendingList;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ProductViewHolder> {
    private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";

    private final Context mCtx;
    private final List<TrendingList> productList;

    public TrendingAdapter(Context mCtx, List<TrendingList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public TrendingAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.glide_movie_list, null);
        return new TrendingAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrendingAdapter.ProductViewHolder holder, int position) {
        TrendingList movieData = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(IMAGE_PRODUCTS + movieData.getPoster_path())
                .into(holder.poster);

     /*   holder.title.setText(movieData.getTitle());*/
        holder.vote.setText(movieData.getVote_count() + " " + "votes");
        holder.rating.setText(movieData.getVote_average());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView title, rating, vote, date;
        ImageView poster;

        public ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            vote = itemView.findViewById(R.id.vote);
            date = itemView.findViewById(R.id.date);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}