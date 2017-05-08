package com.tamic.fastdownsimple.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tamic on 2016-12-21.
 */

public class SectionItemImpl implements SectionItem{
    private View mView;

    public SectionItemImpl() {
    }

    public SectionItemImpl(View view) {
        mView = view;
    }

    @Override
    public View createView(ViewGroup parent) {
        return mView;
    }

    @Override
    public void onBind() {

    }

}
