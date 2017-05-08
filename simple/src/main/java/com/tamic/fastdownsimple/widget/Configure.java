package com.tamic.fastdownsimple.widget;

import android.view.View;

/**
 * Created by liuyongkui726 on 2016-12-21.
 */

public interface Configure {
    void configureEmptyView(View emptyView);

    void configureErrorView(View errorView);

    void configureLoadingView(View loadingView);

    void configureLoadMoreView(View loadMoreView);

    void configureNoMoreView(View noMoreView);

    void configureLoadMoreFailedView(View loadMoreFailedView);
}
