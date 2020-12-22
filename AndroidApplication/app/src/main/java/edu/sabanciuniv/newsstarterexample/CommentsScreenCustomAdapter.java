package edu.sabanciuniv.newsstarterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import edu.sabanciuniv.newsstarterexample.model.Comment;

public class CommentsScreenCustomAdapter extends RecyclerView.Adapter<CommentsScreenCustomAdapter.CommentsArrViewHolder>{
    Context context;
    List<Comment> commentsArr;

    public CommentsScreenCustomAdapter(List<Comment> commentsData, Context context) {
        this.commentsArr = commentsData;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsArrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_comments_row_layout, parent,false);

        return new CommentsArrViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsArrViewHolder holder, int position) {
        holder.comment.setText(commentsArr.get(position).getMessage());

        holder.author.setText(commentsArr.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return commentsArr.size();
    }

    class CommentsArrViewHolder extends RecyclerView.ViewHolder{
        TextView comment;
        TextView author;

        public CommentsArrViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);

            author = itemView.findViewById(R.id.author);
        }
    }
}
