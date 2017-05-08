package com.tamic.fastdownsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


import com.tamic.fastdownsimple.down.DLNormalCallback;
import com.tamic.rx.fastdown.callback.IDLCallback;
import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.Priority;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tamic.rx.fastdown.client.Type.FRAME;
import static com.tamic.rx.fastdown.client.Type.NORMAL;

/**
 * Created by Tamic on 2016-12-27.
 */
public class SingleSimpleActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.singledown)
    Button mSingle;
    @BindView(R.id.taskdown)
    Button mTaskdown;
    @BindView(R.id.newdown)
    Button mNewDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_simple);
        ButterKnife.bind(this, this);


    }


    @OnClick(R.id.singledown)
    void sinleDown() {

        new Download.Builder()
                .priority(Priority.HIGH)
                .name("simple.apk")
                .isImplicit(false)
                .savepath("可以指定目录")
                .type(FRAME)
                .channel(2000)
                .setCallback(new IDLCallback() {
                    @Override
                    public void onStart(String key, long fileLength, long downloaded, String savePath, String filenNme) {

                    }

                    @Override
                    public void onSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName) {

                    }

                    @Override
                    public void onAppSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName, int downloadType, int appType) {

                    }

                    @Override
                    public void onFail(String key, long downloaded, String savePath, String filenNme, String aErrinfo) {

                    }

                    @Override
                    public void onCancel(String key, long fileLength, long downloaded, String savePath, String filenNme) {

                    }

                    @Override
                    public void onPause(String key, long fileLength, long downloaded, String savePath, String filenNme) {

                    }

                    @Override
                    public void onDownloading(String key, long fileLength, long downloadLength, long speed, String fileName, int downloadType) {

                    }

                    @Override
                    public void onRefresh(List<DownLoadInfo> infos) {

                    }
                })

                .client(DLClientFactory.createClient(FRAME, this))
                .build(this).start();
    }

    @OnClick(R.id.taskdown)
    void callTaskDown() {

        new Download.Builder()
                .url("http://p2.gexing.com/kongjianpifu/20120713/1622/4fffdacf88244.jpg")
                .priority(Priority.MEDIUM)
                .isImplicit(false)
                .channel(1000)
                .client(DLClientFactory.createClient(NORMAL, this))
                .build(this)
                .start();

    }

    @OnClick(R.id.newdown)
    void callnewDown() {

        new Download.Builder()
                .url("http://p2.gexing.com/kongjianpifu/20120713/1622/4fffdacf88244.jpg")
                .priority(Priority.MEDIUM)
                .isImplicit(false)
                .channel(1000)
                .client(DLClientFactory.createClient(NORMAL, this))
                .build(this)
                .start();

    }

}
