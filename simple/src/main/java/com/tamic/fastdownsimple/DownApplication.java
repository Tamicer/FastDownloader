package com.tamic.fastdownsimple;

import android.app.Application;

import com.tamic.fastdownsimple.down.DLDownloadListener;
import com.tamic.fastdownsimple.down.DLNormalCallback;
import com.tamic.fastdownsimple.down.DownloadInit;
import com.tamic.fastdownsimple.widget.DownloadAdapter;
import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.client.Type;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.RxDownloadManager;


/**
 * Created by Tamic on 2016-12-22.
 */
public class DownApplication extends Application {

    public static DownloadAdapter downloadAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

        DownloadInit.init(getBaseContext());

        new Download.ConfigBuilder<>()
                .addMaxCount(5)
                .downloadListener(new DLDownloadListener(this.getBaseContext()))
                .baseClient(DLClientFactory.createClient(Type.NORMAL, getBaseContext()))
                .newbuild(this, downloadAdapter);
       /* RxDownloadManager manager = RxDownloadManager.getInstance();
        manager.init(getBaseContext(), null);
        manager.setContext(getBaseContext());
        manager.setListener(new DLDownloadListener(getBaseContext()));


        DLNormalCallback normalCallback = new DLNormalCallback();
        if (manager.getClient() != null) {
            manager.getClient().setCallback(normalCallback);
        }*/






    }
}
