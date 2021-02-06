package com.codingburg.mid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingburg.mid.R;
import com.codingburg.mid.activity.MovieDetailsActivity;
import com.codingburg.mid.model.MovieList;
import com.codingburg.mid.model.SlideModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {
    private static final String IMAGE_PRODUCTS = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private List<SlideModel> mSliderItems;

    public SliderAdapterExample(Context context, List<SlideModel> productList) {
        this.context = context;
        this.mSliderItems = productList;
    }

    public void renewItems(List<SlideModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SlideModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SlideModel sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getTitle());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.id.setText(sliderItem.getId());
        viewHolder.vote.setText(sliderItem.getVote_count());
        viewHolder.rating.setText(sliderItem.getVote_average());
        viewHolder.title.setText(sliderItem.getTitle());


        Glide.with(viewHolder.itemView)
                .load(IMAGE_PRODUCTS + sliderItem.getBackdrop_path())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                intent.putExtra("id", viewHolder.id.getText().toString());
                intent.putExtra("title", viewHolder.title.getText().toString());
                intent.putExtra("vote", viewHolder.vote.getText().toString());
                intent.putExtra("rating", viewHolder.rating.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder implements View.OnClickListener {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription, id, vote, rating, title;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            id = itemView.findViewById(R.id.id);
            vote = itemView.findViewById(R.id.vote);
            rating = itemView.findViewById(R.id.rating);
            title = itemView.findViewById(R.id.title);

            this.itemView = itemView;
        }

        @Override
        public void onClick(View v) {

        }
    }

}