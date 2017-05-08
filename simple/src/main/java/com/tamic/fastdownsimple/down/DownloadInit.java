package com.tamic.fastdownsimple.down;

import android.content.Context;

import com.tamic.fastdownsimple.widget.DownloadAdapter;
import com.tamic.rx.fastdown.RxConstants;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.core.RxDownloadManager;

/**
 * 下载模块初始化
 *  Created By Tamic On 2015-8-15
 */
public final class DownloadInit {

	/**
	 * private constructor
	 */
	private DownloadInit() {
	}


    public static void init(Context aContext) {
        //设置点击栏目知想打开的页面
        RxConstants.CLASSNAME = "MainActivty";

        RxDownloadManager manager = RxDownloadManager.getInstance();
        manager.init(aContext, new DownloadAdapter());
        manager.setContext(aContext);
        manager.setListener(new DLDownloadListener(aContext));
        DLNormalCallback normalCallback = new DLNormalCallback();
        if (manager.getClient() != null) {
            manager.getClient().setCallback(normalCallback);
        }
        RxDownLoadCenter.getInstance(aContext).loadTask();
    }
}
