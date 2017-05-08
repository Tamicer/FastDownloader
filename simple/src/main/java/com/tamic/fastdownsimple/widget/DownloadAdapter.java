package com.tamic.fastdownsimple.widget;

import android.util.Log;
import android.view.ViewGroup;

import com.tamic.fastdownsimple.DownloadViewHolder;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownloadManager;
import com.tamic.rx.fastdown.listener.IUIDownHandler;

import java.util.List;


/**
 * DownloadAdapter
 */
public class DownloadAdapter extends AbstractAdapter<DownLoadInfo, DownloadViewHolder> implements IUIDownHandler {

    private DownLoadInfo loadInfo;
    private DownloadViewHolder holder;

    @Override
    protected DownloadViewHolder onNewCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadViewHolder(parent);
    }

    @Override
    protected void onNewBindViewHolder(DownloadViewHolder holder, int position) {
        this.holder = holder;
        loadInfo = get(position);
        holder.setData(loadInfo);
    }

    @Override
    public void onBindViewHolder(DownloadViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty() || payloads.size() == 0) {
            onNewBindViewHolder(holder, position);
        } else {
            this.holder = holder;
            loadInfo = get(position);
            holder.setData(loadInfo);
        }
    }

    @Override
    public void notifyComplete(DownLoadInfo info) {
        holder.setData(loadInfo);
        notifyDataSetChanged();
    }

    @Override
    public void notifyRefresh(DownLoadInfo info) {

        if (holder == null) {
            return;
        }
        loadInfo = info;
        Log.d("nsygsb", loadInfo.toString());
        holder.setData(info);
        notifyDataSetChanged();
    }

    @Override
    public void notifyRefresh() {

    }

    @Override
    public void notifyNewTask(DownLoadInfo info) {
        Log.d("nsygsb", "a  new task>> :" + loadInfo.toString());
        getData().add(info);
        holder.setData(info);
        RxDownloadManager.getInstance().loadTask();
        notifyDataSetChanged();
    }


}
