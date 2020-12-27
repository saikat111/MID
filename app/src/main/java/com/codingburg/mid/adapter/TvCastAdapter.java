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
import com.codingburg.mid.model.Cast;
import com.codingburg.mid.model.TvCast;

import java.util.List;

public class TvCastAdapter extends RecyclerView.Adapter<TvCastAdapter.ProductViewHolder> {
    private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";
    private Context mCtx;
    private List<TvCast> productList;

    public TvCastAdapter(Context mCtx, List<TvCast> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public TvCastAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cast, null);
        return new TvCastAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvCastAdapter.ProductViewHolder holder, int position) {
        TvCast movieData = productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(IMAGE_PRODUCTS + movieData.getProfile_path())
                .into(holder.poster);

        holder.name.setText(movieData.getName());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView poster;

        public ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            poster = itemView.findViewById(R.id.poster);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
