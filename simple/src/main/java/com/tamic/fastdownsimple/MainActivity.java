package com.tamic.fastdownsimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.tamic.rx.fastdown.core.RxDownloadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tamic on 2016-12-27.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.downloadList)
    Button openList;
    @BindView(R.id.downloadMore)
    Button openMore;
    @BindView(R.id.downloadbase)
    Button openBase;
    @BindView(R.id.downMode)
    Button openMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.downloadList)
    void openList() {
        startActivity(new Intent(this, DownLoadListActivity.class));
    }

    @OnClick(R.id.downloadMore)
    void openMore() {
        startActivity(new Intent(this, DownLoadAddActivity.class));
    }

    @OnClick(R.id.downloadbase)
    void openBase() {
        startActivity(new Intent(this, DownloadSimpleActivity.class));
    }

    @OnClick(R.id.downMode)
    void openMode() {
        startActivity(new Intent(this, SingleSimpleActivity.class));
    }

    @OnClick(R.id.loadCache)
    void loadCache() {
        //非必须 ，扫描存在文件，也可以只在首次安装进行扫描
        RxDownloadManager.getInstance().scanDefaultFolder();
    }

    @OnClick(R.id.statusCation)
    void removeTasks() {
        RxDownloadManager.getInstance().removeTasks(true);
    }


}
