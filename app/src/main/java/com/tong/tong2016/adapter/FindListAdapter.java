package com.tong.tong2016.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tong.baselibrary.common.widget.CircleImageView;
import com.tong.tong2016.R;
import com.tong.tong2016.bean.event.ThemesEvent;
import com.tong.tong2016.bean.json.Other;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;
import com.tong.tong2016.storage.TongPreferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Martin on 2016/5/13.
 */
public class FindListAdapter extends RecyclerView.Adapter<FindListAdapter.ViewHolder> {

    private List<Other> others;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private TongPreferences preferences;

    public FindListAdapter(Context context,List<Other> others) {
        this.others = others;
        preferences = TongPreferences.getPreferencesUtil(context);
    }

    public void updateThemes(List<Other> others){
        this.others = others;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position+1==others.size()){
            holder.line.setVisibility(View.GONE);
        }else{
            holder.line.setVisibility(View.VISIBLE);
        }
        final Other other = others.get(position);
        boolean checked = preferences.themeById(other.getId());
        imageLoader.displayImage(other.getThumbnail(), holder.civFindAvatar, Constants.options, animateFirstListener);
        holder.tvItemName.setText(other.getName().replace("日报",""));
        holder.tvItemDescribe.setText(other.getDescription());
        if(other.getId().equals("12")){
            holder.btnItemFind.setText("已关注");
            holder.btnItemFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {}
            });
        }else{
            holder.btnItemFind.setText(checked?"已关注":"关注");
            holder.btnItemFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean mc = preferences.themeById(other.getId());
                    ((Button)view).setText(mc?"关注":"已关注");
                    preferences.setThemeById(other.getId(),!mc);
                    EventBus.getDefault().post(new ThemesEvent("update"));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return others.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView civFindAvatar;
        TextView tvItemName;
        TextView tvItemDescribe;
        View line;
        Button btnItemFind;


        public ViewHolder(View itemView) {
            super(itemView);
            civFindAvatar = (CircleImageView) itemView.findViewById(R.id.civ_find_avatar);
            tvItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvItemDescribe = (TextView) itemView.findViewById(R.id.tv_item_describe);
            line = itemView.findViewById(R.id.line);
            btnItemFind = (Button) itemView.findViewById(R.id.btn_item_find);
        }

    }

}
