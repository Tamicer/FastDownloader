package com.tamic.fastdownsimple.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tamic.fastdownsimple.R;

/**
 * WaitingDialog
 */
public class WaitingDialog extends Dialog {
	private static final int TEXT_SIZE = 16;
	private static final int MESSAGE_PADDING = 12;
	private String mMessage;
	private Context mContext;

	/**
	 * constructor
	 * @param context context
	 */
	public WaitingDialog(Context context) {
		super(context, R.style.BdWaitingDialog);

		mContext = context;
	}

	public static WaitingDialog show(Context context, CharSequence message) {
		return show(context, message, false);
	}


	public static WaitingDialog show(Context context, CharSequence message, boolean cancelable) {
		return show(context, message, cancelable, null);
	}

	public static WaitingDialog show(Context context, CharSequence message, boolean cancelable,
									 OnCancelListener aCancelListener) {
		WaitingDialog dialog = new WaitingDialog(context);
		dialog.setMessage(message);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(aCancelListener);
		dialog.show();
		return dialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new WaitingView(mContext));
	}

	/**
	 * set a msg
	 * @param message msg in String
	 */
	public void setMessage(String message) {
		mMessage = message;
	}

	/**
	 * set a msg
	 * @param message msg in CharSequence
	 */
	public void setMessage(CharSequence message) {
		mMessage = message.toString();
	}

	/**
	 * set a msg
	 * @param resId msg in res id
	 */
	public void setMessage(int resId) {
		mMessage = mContext.getResources().getString(resId);
	}

	/**
	 * waiting content view in waiting dialog
	 */
	private class WaitingView extends LinearLayout {
		/** text size */
		private int mTextSize;
		/** msg padding */
		private int mMessagePadding;
		/** screen width */
		private int mScreenWidth;
		/** context */
		private Context mContext;

		/**
		 * constructor
		 * @param context context
		 */
		public WaitingView(Context context) {
			super(context);

			mContext = context;

			loadConfiguration();

			layoutDesign();
		}

		/**
		 * load configuration
		 */
		private void loadConfiguration() {
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
			float density = dm.density;
			mScreenWidth = dm.widthPixels;

			mTextSize = (int) (TEXT_SIZE * Math.sqrt(density));
			mMessagePadding = (int) (MESSAGE_PADDING * density);
		}


		@SuppressLint("InlinedApi")
		private void layoutDesign() {
			setOrientation(LinearLayout.VERTICAL);
			FrameLayout.LayoutParams lParams0 = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			setLayoutParams(lParams0);
			setBackgroundColor(Color.TRANSPARENT);

			ProgressBar progressBar = new ProgressBar(mContext);
			progressBar.setBackgroundColor(Color.TRANSPARENT);
			LayoutParams lParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			lParams1.gravity = Gravity.CENTER;
			progressBar.setLayoutParams(lParams1);
			addView(progressBar);
			TextView textView = new TextView(mContext);
			textView.setText(mMessage);
			LayoutParams lParams2 = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			lParams2.gravity = Gravity.CENTER;
			lParams2.width = mScreenWidth;
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(lParams2);
			textView.setBackgroundColor(Color.TRANSPARENT);
			textView.setPadding(mMessagePadding, mMessagePadding, mMessagePadding, mMessagePadding);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
			textView.setTextColor(0xffe5e5e5);
			addView(textView);
		}
	}

}
