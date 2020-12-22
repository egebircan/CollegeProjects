package edu.sabanciuniv.newsstarterexample;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import android.widget.TextView;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import edu.sabanciuniv.newsstarterexample.model.News;

public class NewsScreenCustomAdapter extends RecyclerView.Adapter<NewsScreenCustomAdapter.NewsViewHolder> {
    NewsClickListener listener;
    List<News> allNewsArr;
    Context context;




    public NewsScreenCustomAdapter(List<News> allNewsArr, Context context, NewsClickListener listener) {
        this.listener = listener;

        this.context = context;
        this.allNewsArr = allNewsArr;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_news_row_layout,parent,false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.newsNameTextListView.setText(allNewsArr.get(position).getTitle());
        holder.newsDateTextListView.setText(new SimpleDateFormat("dd/MM/yyy").format(allNewsArr.get(position).getNewsDate()));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newsClicked(allNewsArr.get(position));
            }
        });

        if(allNewsArr.get(position).getBitmap() == null) {
            //validation
            new FetchImage(holder.imageListView).execute(allNewsArr.get(position));
        }
        else {
            //else??
            holder.imageListView.setImageBitmap(allNewsArr.get(position).getBitmap());
        }


    }

    @Override
    public int getItemCount()
    {
        return allNewsArr.size();
    }

    public interface NewsClickListener {
        public void newsClicked(News news);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        //class init
        TextView newsNameTextListView;
        TextView newsDateTextListView;
        ConstraintLayout root;
        ImageView imageListView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //settings
            newsDateTextListView = itemView.findViewById(R.id.txtlistnewsdate);
            newsNameTextListView = itemView.findViewById(R.id.txtlistnewsname);
            root = itemView.findViewById(R.id.container);
            imageListView = itemView.findViewById(R.id.imglistnews);


        }
    }

}
