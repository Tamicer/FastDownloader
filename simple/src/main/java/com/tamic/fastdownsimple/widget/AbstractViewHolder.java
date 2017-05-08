package com.tamic.fastdownsimple.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import com.tamic.rx.fastdown.widget.DownItemType;


/**
 * Created by liuyongkui726 on 2016-12-21.
 */

public abstract class AbstractViewHolder<T extends DownItemType> extends RecyclerView.ViewHolder {

    public AbstractViewHolder(View itemView) {
        super(itemView);
    }

    public AbstractViewHolder(ViewGroup parent, int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    public abstract void setData(T data);
}
