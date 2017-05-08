package com.tamic.fastdownsimple.down;

import com.tamic.rx.fastdown.DLToastManager;
import com.tamic.rx.fastdown.callback.IDLCallback;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.RxDownLoadCenter;
import com.tamic.rx.fastdown.core.RxDownloadManager;

import java.util.List;



/**
 * apk  Callback
 */
public class DLFrameCallback implements IDLCallback {


	@Override
	public void onStart(String tag, long fileLength, long downloaded, String savePath, String filenNme) {

	}

	@Override
	public void onSuccess(String tag, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName) {
		RxDownloadManager.getInstance().removeDialog();
		DownLoadInfo info = RxDownLoadCenter.getInstance(null).getSingleinfo(tag);
		if (info.isImplicit == 0) {
			RxDownLoadCenter.getInstance(null).sendShowToastMessage("升级包下载已完成",
					DLToastManager.TYPE_CLICKABLE, filenNme);
		}
	}


	@Override
	public void onAppSuccess(String atag, long aFilelength, long downloaded, final String aFilepath, final String aFilename, long aSpeed, String aAppiconName, int downloadType, int appType) {
		
		//
		RxDownloadManager.getInstance().removeDialog();
		DownLoadInfo info = RxDownLoadCenter.getInstance(null).getSingleinfo(atag);
		if (info.isImplicit == 1) {
			return;

		}
		RxDownLoadCenter.getInstance(null).sendShowToastMessage("安装包下载已完成",
				DLToastManager.TYPE_CLICKABLE, aFilename);
	}

	@Override
	public void onFail(String atag, long downloaded, String aFilepath, String aFilename,
			String aErrinfo) {
		RxDownloadManager.getInstance().removeDialog();
	}

	@Override
	public void onCancel(String tag, long fileLength, long downloaded, String savePath, String filenNme) {

	}

	@Override
	public void onPause(String tag, long fileLength, long downloaded, String savePath, String filenNme) {

	}


	@Override
	public void onDownloading(String atag, long aFilelength, long downloaded, long aSpeed, String aFilename, int downloadType) {

		RxDownLoadCenter.getInstance(null).sendShowToastMessage("下载中 >> + " + downloaded,
				DLToastManager.TYPE_CLICKABLE, aFilename);
	}


	@Override
	public void onRefresh(List<DownLoadInfo> infos) {

	}

}
