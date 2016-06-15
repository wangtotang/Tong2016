package com.tong.tong2016.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.tong.tong2016.R;
import com.tong.tong2016.bean.json.NewArticle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DateHeaderAdapter implements StickyHeadersAdapter<DateHeaderAdapter.HeaderViewHolder> {
    private List<NewArticle> newArticleList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
    private DateFormat dateFormat = DateFormat.getDateInstance();

    public DateHeaderAdapter(List<NewArticle> newArticleList) {
        this.newArticleList = newArticleList;
    }

    public void setNewArticleList(List<NewArticle> newArticleList) {
        this.newArticleList = newArticleList;
    }

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.view_date_header, parent, false);

        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HeaderViewHolder viewHolder, int position) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(simpleDateFormat.parse(newArticleList.get(position).getDate()));
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        } catch (ParseException ignored) {

        }

        viewHolder.title.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public long getHeaderId(int position) {
        return newArticleList.get(position).getDate().hashCode();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
