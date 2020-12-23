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
import com.codingburg.mid.model.ProductionCompanyData;

import java.util.List;

public class ProductionCompanyAdapter  extends RecyclerView.Adapter<ProductionCompanyAdapter.ProductViewHolder> {
    private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";
    String poster_path;
    private Context mCtx;
    private List<ProductionCompanyData> productList;

    public ProductionCompanyAdapter(Context mCtx, List<ProductionCompanyData> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductionCompanyAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.production, null);
        return new ProductionCompanyAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductionCompanyAdapter.ProductViewHolder holder, int position) {
        ProductionCompanyData movieData = productList.get(position);

        //loading the image
        Glide.with(mCtx).load(IMAGE_PRODUCTS + movieData.getLogo_path()).into(holder.logo);
        holder.name.setText(movieData.getName());
        holder.country.setText(movieData.getOrigin_country());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, country;
        ImageView logo;

        public ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
            logo = itemView.findViewById(R.id.logo);

        }

        @Override
        public void onClick(View v) {

        }
    }
}