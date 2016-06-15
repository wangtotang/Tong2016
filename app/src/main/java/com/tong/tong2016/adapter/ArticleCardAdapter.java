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
import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;
import com.tong.tong2016.ui.activities.ArticleActivity_;

import java.util.List;

/**
 * Created by Martin on 2016/5/13.
 */
public class ArticleCardAdapter extends RecyclerView.Adapter<ArticleCardAdapter.ViewHolder> {

    private Context context;
    private List<News> newsList;
    private OnRecyclerViewItemClickListener listener;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    public ArticleCardAdapter(Context context,List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public void updateNewsList(List<News> newsList) {
        setNewsList(newsList);
        notifyDataSetChanged();
    }

    @Override
    public ArticleCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleCardAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);
        if(news.getImage()==null){
            holder.ivItemArticlePicture.setVisibility(View.GONE);
        }else{
            imageLoader.displayImage(news.getImage(), holder.ivItemArticlePicture, Constants.options, animateFirstListener);
        }
        holder.tvItemArticleTitle.setText(news.getQuestion());
        holder.tvItemArticleContent.setText(news.getTitle());
        setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Intent intent = new Intent(context,ArticleActivity_.class);
                intent.putExtra("ids",newsList.get(position).getIds());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
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
