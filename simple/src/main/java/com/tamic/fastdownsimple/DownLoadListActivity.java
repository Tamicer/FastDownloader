package com.tamic.fastdownsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;

import com.tamic.fastdownsimple.widget.DownloadAdapter;
import com.tamic.fastdownsimple.widget.PracticalRecyclerView;
import com.tamic.rx.fastdown.client.Type;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.core.RxDownloadManager;
import com.tamic.rx.fastdown.listener.IUIDownHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DownLoadList
 * Created by Tamic on 2016-12-27.
 */
public class DownLoadListActivity extends AppCompatActivity implements IUIDownHandler {
    @BindView(R.id.content_main)
    RelativeLayout mContentMain;
    @BindView(R.id.recycler)
    PracticalRecyclerView mRecycler;
    DownloadAdapter mAdapter;

    List<DownLoadInfo> data = new ArrayList<>();
    HashSet<DownLoadInfo> set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowmload_list);
        ButterKnife.bind(this, this);

        mAdapter = new DownloadAdapter();

        RxDownloadManager.getInstance().setUiDownHandler(this);
        RxDownLoadCenter.getInstance(this).loadTask();


        //mAdapter = DownApplication.downloadAdapter;
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapterWithLoading(mAdapter);
        // Data references.
        List<DownLoadInfo> allinfo = RxDownLoadCenter.getInstance(this).getAllInfoWithType(Type.NORMAL);
        List<DownLoadInfo> allSuccessinfo = RxDownLoadCenter.getInstance(this).getAllSuccessInfo();

        if (!data.containsAll(allinfo)) {
            data.addAll(allinfo);
        }

        if (data.addAll(allSuccessinfo))  {
            data.addAll(allSuccessinfo);
        }

        mAdapter.getData().clear();
        mAdapter.addAll(data);
    }

    @Override
    public void notifyComplete(DownLoadInfo info) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyRefresh(DownLoadInfo downLoadInfo) {
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void notifyRefresh() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyNewTask(DownLoadInfo info) {
        // checkEmpty();
        RxDownloadManager.getInstance().loadTask();
        loadData(null);
        mAdapter.notifyDataSetChanged();

    }

    public void loadData(String tag) {
        List<DownLoadInfo> infos = RxDownLoadCenter.getInstance(this).getAllInfoWithType(Type.NORMAL);
        data.clear();
        for (DownLoadInfo info : infos) {
            data.add(info);
        }
        mAdapter.clearData();
        mAdapter.addAll(data);
        sort();
    }

    private void sort() {
        Collections.sort(data, new Comparator<DownLoadInfo>() {
            @Override
            public int compare(DownLoadInfo item1, DownLoadInfo item2) {
                return (int) (item2.mCreatedtime - item1.mCreatedtime);
            }
        });
    }


    /*private void unsubscribe() {
        List<DownLoadInfo> list = mAdapter.getData();
        for (DownLoadInfo each : list) {
            each.unsubscrbe();
        }
    }*/
}
