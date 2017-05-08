package com.tamic.fastdownsimple.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuyongkui726 on 2016-12-21.
 */

public interface SectionItem {

    View createView(ViewGroup parent);

    void onBind();
}