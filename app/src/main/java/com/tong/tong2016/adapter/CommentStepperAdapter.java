package com.tong.tong2016.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tong.tong2016.R;
import com.tong.tong2016.bean.db.Comment;

import java.util.List;

/**
 * Created by Martin on 2016/5/13.
 */
public class CommentStepperAdapter extends RecyclerView.Adapter<CommentStepperAdapter.ViewHolder> {

    private List<Comment> commentList;

    public CommentStepperAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void updateCommentList(List<Comment> commentList){
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public CommentStepperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentStepperAdapter.ViewHolder holder, int position) {
        if(position==0){
            holder.topBlank.setVisibility(View.VISIBLE);
        }else if(position==commentList.size()-1){
            holder.buttomBlank.setVisibility(View.VISIBLE);
        }else{
            holder.topBlank.setVisibility(View.GONE);
            holder.buttomBlank.setVisibility(View.GONE);
        }
        Comment comment = commentList.get(position);
        holder.tvItemCommentYear.setText("2016");
        holder.tvItemCommentDate.setText("06 - 06");
        holder.tvItemCommentTitle.setText(comment.getTitle());
        holder.tvItemCommentContent.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItemCommentYear;
        TextView tvItemCommentDate;
        TextView tvItemCommentTitle;
        TextView tvItemCommentContent;
        View topBlank,buttomBlank;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemCommentYear = (TextView) itemView.findViewById(R.id.tv_item_comment_year);
            tvItemCommentDate = (TextView) itemView.findViewById(R.id.tv_item_comment_date);
            tvItemCommentTitle = (TextView) itemView.findViewById(R.id.tv_item_commet_title);
            tvItemCommentContent = (TextView) itemView.findViewById(R.id.tv_item_comment_content);
            topBlank = itemView.findViewById(R.id.top_blank);
            buttomBlank = itemView.findViewById(R.id.buttom_blank);
        }

    }


}
