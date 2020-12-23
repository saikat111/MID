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
import com.codingburg.mid.activity.MovieDetailsActivity;
import com.codingburg.mid.R;
import com.codingburg.mid.model.MovieList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ProductViewHolder> {
    private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";
    String poster_path;
    private Context mCtx;
    private List<MovieList> productList;

    public MovieAdapter(Context mCtx, List<MovieList> productList) {
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
       MovieList movieData = productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(IMAGE_PRODUCTS + movieData.getPoster_path())
                .into(holder.poster);
        poster_path = movieData.getPoster_path();
        holder.title.setText(movieData.getTitle());
        holder.vote.setText(movieData.getVote_count() + " " + "Ratings");
        holder.rating.setText(movieData.getVote_average());
        holder.date.setText(movieData.getRelease_date());
        holder.id.setText(movieData.getId());
        holder.original_language.setText(movieData.getOriginal_language());
        holder.original_title.setText(movieData.getOriginal_title());
        holder.overview.setText(movieData.getOverview());
        holder.popularity.setText(movieData.getPopularity());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, rating, vote, date, id,original_language,original_title,overview,popularity;
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
            original_language = itemView.findViewById(R.id.original_language);
            original_title = itemView.findViewById(R.id.original_title);
            overview = itemView.findViewById(R.id.overview);
            popularity = itemView.findViewById(R.id.popularity);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra("id", id.getText().toString());
            intent.putExtra("title", title.getText().toString());
            intent.putExtra("vote", vote.getText().toString());
            intent.putExtra("rating", rating.getText().toString());
            v.getContext().startActivity(intent);
        }
    }
}