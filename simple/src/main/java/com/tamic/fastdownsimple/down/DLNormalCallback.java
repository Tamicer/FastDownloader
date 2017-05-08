package com.tamic.fastdownsimple.down;

import android.os.Handler;
import android.os.Looper;

import com.tamic.rx.fastdown.RxConstants;
import com.tamic.rx.fastdown.DLToastManager;
import com.tamic.rx.fastdown.callback.IDLCallback;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.listener.ICompleteListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Normal Callback
 */
public class DLNormalCallback implements IDLCallback {

    private List<ICompleteListener> mListeners;
    public DLNormalCallback() {
        mListeners = new ArrayList<>();
    }

    public void addListener(ICompleteListener aListener) {
        if (!mListeners.contains(aListener)) {
            mListeners.add(aListener);
        }
    }

    public void removeListener(ICompleteListener aListener) {
        mListeners.remove(aListener);
    }



    @Override
    public void onStart(String tag, long fileLength, long downloaded, String savePath, String filenNme) {

    }

    @Override
    public void onSuccess(String tag, long fileLength, long downloaded, String savePath, final String filenNme, long aSpeed, String aAppiconName) {
        //install
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                RxDownLoadCenter.getInstance(null).sendShowToastMessage(filenNme + "下载已完成",
                        DLToastManager.TYPE_CLICKABLE, filenNme);
            }
        });
    }

    @Override
    public void onAppSuccess(String tag, long fileLength, long downloaded, String savePath, final String filenNme, long aSpeed, String aAppiconName, int downloadType, int appType) {

        //install
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                RxDownLoadCenter.getInstance(null).sendShowToastMessage(filenNme + "下载已完成",
                        DLToastManager.TYPE_CLICKABLE, filenNme);
            }
        });
    }

    @Override
    public void onFail(String tag, long downloaded, String aFilepath, String aFilename,
                       String aErrinfo) {
        try {
            if (mListeners != null) {
                for (ICompleteListener listener : mListeners) {
                    if (listener.onFail(tag, downloaded, aFilepath, aFilename, aErrinfo)) {
                        continue;
                    }
                }
            }
            if (aErrinfo.equals(RxConstants.ERROR_INVALID_FILE)) {
                RxDownLoadCenter.getInstance(null).sendShowToastMessage("SD卡异常，请稍后重试",
                        DLToastManager.TYPE_CLICKABLE, aFilename);
            } else if (aErrinfo.equals(RxConstants.ERROR_INSUFFICIENT_STORAGE)) {
                RxDownLoadCenter.getInstance(null).sendShowToastMessage("SD卡容量不足",
                        DLToastManager.TYPE_CLICKABLE, aFilename);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(String tag, long aFilelength, long downloaded, String aFilepath,
                         String aFilename) {
        try {
            if (mListeners != null) {
                for (ICompleteListener listener : mListeners) {
                    if (listener.onCancel(tag, aFilelength, downloaded, aFilepath, aFilename)) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(String tag, long aFilelength, long downloaded, String aFilepath,
                        String aFilename) {
        try {
            if (mListeners != null) {
                for (ICompleteListener listener : mListeners) {
                    if (listener.onPause(tag, aFilelength, downloaded, aFilepath, aFilename)) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloading(String key, long filelength, long downloaded, long speed, String filename, int downloadType) {
        RxDownLoadCenter.getInstance(null).sendShowToastMessage("正在下载中..." + downloaded,
                DLToastManager.TYPE_CLICKABLE, filename);
    }

    @Override
    public void onRefresh(List<DownLoadInfo> infos) {

    }


}
