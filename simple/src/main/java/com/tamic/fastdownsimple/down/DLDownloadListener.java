package com.tamic.fastdownsimple.down;

import android.content.Context;
import android.widget.Toast;

import com.tamic.rx.fastdown.PopupDialogParams;
import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.client.Type;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownloadManager;
import com.tamic.rx.fastdown.listener.IDownloadListener;
import com.tamic.rx.fastdown.listener.IUIDownHandler;
import com.tamic.rx.fastdown.util.Utils;

/**
 * Created by Tamic on 2015-12-22.
 */
public class DLDownloadListener implements IDownloadListener {

    private Context mContext;

    public DLDownloadListener(Context aContext) {
        mContext = aContext;
    }

    @Override
    public void showPopupDialog(PopupDialogParams params) {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void showToast(String toastContent) {
        Toast.makeText(mContext, toastContent, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openUrl(String aUrl) {
    }

    @Override
    public String getCookie(String aUrl) {
       // 你的Cookie
        return null;
    }

    @Override
    public String getUA() {
        // 你的设备UA
        return null;
    }

    @Override
    public void onInstallFrame(String aFileName) {
        Utils.openFile(Utils.getDefaultSavepath(Type.FRAME) + aFileName, RxDownloadManager.getInstance().getContext());
    }

    @Override
    public void onIemClick(DownLoadInfo info) {

    }

    @Override
    public IUIDownHandler onHander() {
        return null;
    }
}
