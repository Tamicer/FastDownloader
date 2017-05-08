package com.tamic.fastdownsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tamic.fastdownsimple.down.DLFrameCallback;
import com.tamic.fastdownsimple.down.DLNormalCallback;
import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.Priority;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tamic.rx.fastdown.client.Type.FRAME;
import static com.tamic.rx.fastdown.client.Type.NORMAL;

public class DownloadSimpleActivity extends AppCompatActivity {


    // UI references.
    @BindView(R.id.downpk)
    Button downApk;
    @BindView(R.id.downdf)
    Button downPdf;
    @BindView(R.id.downImag)
    Button downImage;

    String url = "http://wifiapi02.51y5.net/wifiapi/rd.do?f=wk00003&b=gwanz02&rurl=http%3A%2F%2Fdl.lianwifi.com%2Fdownload%2Fandroid%2FWifiKey-3091-guanwang.apk";
    private String url_sougou = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201512/03/SogouInput_android_v7.11_sweb.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_simple);
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.downdf)
    void calldownPdf() {
        new Download.Builder()
                .url("http://downloads2.txt99.com/d/file/p/txt/2016/%E5%BD%B1%E5%B8%9D%E7%9A%84%E8%80%81%E5%A9%86.txt")
                .priority(Priority.HIGH)
                .isImplicit(false)
                .channel(3000)
                .client(DLClientFactory.createClient(NORMAL, this))
                .setCallback(new DLFrameCallback())
                .build(this)
                .start();
    }

    @OnClick(R.id.downpk)
    void calldownApK() {

        new Download.Builder()
                .url(url)
                .priority(Priority.HIGH)
                .name("simple.apk")
                .isImplicit(false)
                .type(FRAME)
                .channel(2000)
                .setCallback(new DLNormalCallback())
                .build(this).start();

    }

    @OnClick(R.id.downImag)
    void calldownImage() {
        //http://cdn2.parllay.cn/2016102217/gr7s7ygyd5sg0.mp4
      //  "http://p2.gexing.com/kongjianpifu/20120713/1622/4fffdacf88244.jpg"
        new Download.Builder()
                .url("http://cdn2.parllay.cn/2016102217/gr7s7ygyd5sg0.mp4")
                .priority(Priority.MEDIUM)
                .isImplicit(false)
                .channel(1000)
                .client(DLClientFactory.createClient(NORMAL, this))
                .build(this)
                .start();

    }

    @OnClick(R.id.downloadSogou)
    void calldownSogou() {

        String key = new Download.Builder()
                .tag("key")
                .url(url_sougou)
                .priority(Priority.HIGH)
                .isImplicit(false)
                .client(DLClientFactory.createClient(NORMAL, this))
                .build(this)
                .start();

    }
}
