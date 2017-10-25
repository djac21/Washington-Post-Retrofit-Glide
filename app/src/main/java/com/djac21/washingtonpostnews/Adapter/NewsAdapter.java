package com.djac21.washingtonpostnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.bumptech.glide.Glide;
import com.djac21.washingtonpostnews.DetailsActivity;
import com.djac21.washingtonpostnews.R;
import com.djac21.washingtonpostnews.Model.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MovieViewHolder> {

    private List<NewsModel> news;
    private int rowLayout;
    private Context context;

    public NewsAdapter(List<NewsModel> news, int rowLayout, Context context) {
        this.news = news;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.author.setText(news.get(position).getAuthor());
        holder.date.setText(news.get(position).getDate());
        Glide.with(context)
                .load(news.get(position).getImage())
                .placeholder(R.drawable.ic_loading)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        TextView title;
        TextView author;
        TextView date;
        ImageView image;

        public MovieViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.layout);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            date = view.findViewById(R.id.date);
            image = view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("title", news.get(position).getTitle());
            intent.putExtra("image", news.get(position).getImage());
            intent.putExtra("description", news.get(position).getDescription());
            intent.putExtra("author", news.get(position).getAuthor());
            intent.putExtra("date", news.get(position).getDate());
            intent.putExtra("url", news.get(position).getUrl());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}