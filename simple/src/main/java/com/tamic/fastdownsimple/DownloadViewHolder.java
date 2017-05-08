package com.tamic.fastdownsimple;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tamic.fastdownsimple.widget.AbstractViewHolder;
import com.tamic.rx.fastdown.DLMimeType;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.core.DownLoadBuilder;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.core.RxDownloadManager;
import com.tamic.rx.fastdown.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tamic.fastdownsimple.R.id.percent;


/**
 * DownloadViewHolder
 * Created by Tamic on 2016-12-27.
 */
public class DownloadViewHolder extends AbstractViewHolder<DownLoadInfo> {

    @BindView(R.id.img)
    public ImageView mImg;
    @BindView(percent)
    public TextView mPercent;
    @BindView(R.id.progress)
    public ProgressBar mProgress;
    @BindView(R.id.size)
    public TextView mSize;
    @BindView(R.id.filesize)
    public TextView mFlieSize;
    @BindView(R.id.speed)
    public TextView mSpeed;
    @BindView(R.id.status)
    public Button mStatus;

    DownLoadInfo data;
    private Context mContext;

    public DownloadViewHolder(ViewGroup parent) {
        super(parent, R.layout.reclye_download_item);
        ButterKnife.bind(this, itemView);
        mContext = parent.getContext();

    }

    @Override
    public void setData(DownLoadInfo data) {
        this.data = data;
        checkTypeUI(data);
        mProgress.setMax((int) data.mTotalbytes);
        mProgress.setProgress((int) data.mDownloadedbytes);
        mSpeed.setText(Formatter.formatFileSize(mContext, data.mSpeed) + "/s");
        mPercent.setText(data.mFilename);
        mSize.setText(Utils.formatFilesize(data.mDownloadedbytes) + "/" );
        mFlieSize.setText(Utils.formatFilesize(data.mTotalbytes));
        checkUI(data);
    }

    private void checkTypeUI(DownLoadInfo data) {

        switch (data.itemFileType()) {

            case DLMimeType.APK:
                mImg.setBackgroundResource(R.drawable.apk);
                break;

            case DLMimeType.JPEG:
                mImg.setBackgroundResource(R.drawable.image);
                break;

            case DLMimeType.BMP:
                mImg.setBackgroundResource(R.drawable.image);
                break;

            case DLMimeType.PNG:
                mImg.setBackgroundResource(R.drawable.image);
                break;

            case DLMimeType.PDF:
                mImg.setBackgroundResource(R.drawable.pdf);
                break;

            case DLMimeType.TXT:
                mImg.setBackgroundResource(R.drawable.txt);

                break;

            case DLMimeType.VIDEO:
                mImg.setBackgroundResource(R.drawable.video);

                break;

            case DLMimeType.OTHERS:
                mImg.setBackgroundResource(R.drawable.file);
                break;

            default:
                mImg.setBackgroundResource(R.drawable.file);
                break;
            // more Type ,  you need to add  。。。。。

        }

    }


    private void checkUI(DownLoadInfo data) {

        switch (data.mStatus) {
            case RUNNING:
                setSatus(R.drawable.status_down, "下载中");
                mSize.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                setSatus(R.drawable.status_right, "完成");
                mSpeed.setText("下载已完成");
                mProgress.setMax(100);
                mProgress.setProgress(100);
                mSize.setVisibility(View.INVISIBLE);

                break;

            case PAUSED:
                setSatus(R.drawable.status_pused, "继续");
                mSpeed.setText("已暂停，点击继续");
                break;

            case FAIL:
                setSatus(R.drawable.status_alert, "重试");
                mSpeed.setText("下载出错，请重试");
                mSize.setVisibility(View.INVISIBLE);
                break;

            case AUTOPAUSE:
                setSatus(R.drawable.status_down, "继续");
                mSpeed.setText("下载已自动暂停，点击继续");
                mSize.setVisibility(View.VISIBLE);
                break;

            case READY:
                setSatus(R.drawable.status_pused, "等待中");
                mSpeed.setText("任务正在排队中，请稍后....");
                mSize.setVisibility(View.INVISIBLE);
                break;

            default:
                setSatus(R.drawable.status_down, "开始");
                break;
        }
    }

    private void setSatus(@DrawableRes int resId, String tpis) {
       // mStatus.setText(tpis);
        mStatus.setBackgroundResource(resId);
    }

    @OnClick(R.id.status)
    public void onClick() {
        switch (data.mStatus) {
            case RUNNING:
                RxDownLoadCenter.getInstance(mContext).pausetask(data.mKey, true);
                Toast.makeText(mContext, "已暂停", Toast.LENGTH_SHORT).show();
            case READY:
                //点击暂停
                RxDownLoadCenter.getInstance(mContext).pausetask(data.mKey, true);
                Toast.makeText(mContext, "已暂停", Toast.LENGTH_SHORT).show();
                break;
            case PAUSED:
                RxDownLoadCenter.getInstance(mContext).resumeTask(data.mKey, true);

                break;
            case FAIL:
                RxDownLoadCenter.getInstance(mContext).addTask(data);
                break;
            case SUCCESS:
                Toast.makeText(mContext, "已下载完成，不要重复点啦", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
