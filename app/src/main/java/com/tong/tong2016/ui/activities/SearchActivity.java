package com.tong.tong2016.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.DateHeaderAdapter;
import com.tong.tong2016.adapter.QuestionCardAdapter;
import com.tong.tong2016.bean.json.NewArticle;
import com.tong.tong2016.bean.json.Search;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.config.TongService;
import com.tong.tong2016.ui.widget.SimpleSearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseNavActivity {

    @ViewById
    SimpleSearchView ssv;
    @ViewById
    RecyclerView rv;

    ProgressDialog dialog;
    QuestionCardAdapter mAdapter;
    DateHeaderAdapter headerAdapter;
    List<NewArticle> news = new ArrayList<>();
    Call<Search> call;

    @AfterViews
    void init(){
        initBackup();
        initDialog();
        ssv.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.show();
                ssv.clearFocus();
                getSearch(query);
                return true;
            }
        });
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        mAdapter = new QuestionCardAdapter(this,news);
        headerAdapter = new DateHeaderAdapter(news);

        StickyHeadersItemDecoration header = new StickyHeadersBuilder()
                .setAdapter(mAdapter)
                .setRecyclerView(rv)
                .setStickyHeadersAdapter(headerAdapter)
                .build();

        rv.setAdapter(mAdapter);
        rv.addItemDecoration(header);
    }

    @Background(id = "search")
    void getSearch(String query){
        try {
            TongService service = TongApplication.getNetService();
            TongApplication.getIntercepter().setHost(Constants.Urls.SEARCH_HOST);
            call = service.getSearch(query);
            Search search = call.execute().body();
            news = search.getNews();
            updateSearch(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @UiThread
    void updateSearch(List<NewArticle> newArticles){
        if(newArticles!=null&&newArticles.size()>0){
            headerAdapter.setNewArticleList(news);
            mAdapter.updateNewsList(news);
        }else{
            showToast("没有搜索的内容");
        }
    }

    private void initDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在搜索...");
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if(call!=null&&!call.isCanceled()){
                    call.cancel();
                }
            }
        });
    }
}
