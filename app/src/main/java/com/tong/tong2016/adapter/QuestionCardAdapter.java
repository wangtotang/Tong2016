package com.tong.tong2016.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tong.baselibrary.common.listener.OnRecyclerViewItemClickListener;
import com.tong.tong2016.R;
import com.tong.tong2016.bean.json.NewArticle;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;
import com.tong.tong2016.ui.activities.QuestionActivity_;

import java.util.List;

/**
 * Created by Martin on 2016/5/13.
 */
public class QuestionCardAdapter extends RecyclerView.Adapter<QuestionCardAdapter.ViewHolder> {

    private Context context;
    private List<NewArticle> newArticleList;
    private OnRecyclerViewItemClickListener listener;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    public QuestionCardAdapter(Context context, List<NewArticle> newArticleList) {
        this.context = context;
        this.newArticleList = newArticleList;
        setHasStableIds(true);
    }

    public void setNewArticleList(List<NewArticle> newArticleList) {
        this.newArticleList = newArticleList;
    }

    public void updateNewsList(List<NewArticle> newArticleList) {
        setNewArticleList(newArticleList);
        notifyDataSetChanged();
    }

    @Override
    public QuestionCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionCardAdapter.ViewHolder holder, int position) {
        NewArticle newArticle = newArticleList.get(position);
        if(newArticle.getThumbnailUrl()==null){
            holder.ivItemArticlePicture.setVisibility(View.GONE);
        }else{
            imageLoader.displayImage(newArticle.getThumbnailUrl(), holder.ivItemArticlePicture, Constants.options, animateFirstListener);
        }
        holder.tvItemArticleTitle.setText(newArticle.getQuestions().get(0).getTitle());
        holder.tvItemArticleContent.setText(newArticle.getDailyTitle());
        setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(context,QuestionActivity_.class);
                intent.putExtra("url",newArticleList.get(position).getQuestions().get(0).getUrl());
                intent.putExtra("image_url",newArticleList.get(position).getThumbnailUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newArticleList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvItemArticleTitle;
        TextView tvItemArticleContent;
        ImageView ivItemArticlePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemArticleTitle = (TextView) itemView.findViewById(R.id.tv_item_article_title);
            tvItemArticleContent = (TextView) itemView.findViewById(R.id.tv_item_article_content);
            ivItemArticlePicture = (ImageView) itemView.findViewById(R.id.iv_item_article_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           if(listener!=null){
               listener.OnItemClick(view,getLayoutPosition());
           }
        }
    }


}
