package com.tamic.fastdownsimple;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.client.Type;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.Priority;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.HttpUrl;


/**
 * DownLoadAddActivity
 */
public class DownLoadAddActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mUrlView;
    Button mGoDownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_add);
        ButterKnife.bind(this, this);
        // Set up the url form.
        mUrlView = (AutoCompleteTextView) findViewById(R.id.down_Loadurl);
        mGoDownButton = (Button) findViewById(R.id.down_in_button);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid url, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @OnClick(R.id.down_in_button)
    void attemptDown() {

        // Reset errors.
        mUrlView.setError(null);

        // Store values at the time of the login attempt.
        String url = mUrlView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid url address.
        if (TextUtils.isEmpty(url)) {
            mUrlView.setError(getString(R.string.error_field_required));
            Toast.makeText(this, R.string.error_field_required, Toast.LENGTH_SHORT).show();
            focusView = mUrlView;
            cancel = true;
            focusView.requestFocus();
            return;
        }
        // Check for a valid url address.

        if (HttpUrl.parse(url) == null || TextUtils.isEmpty(HttpUrl.parse(url).url().toString())) {
            mUrlView.setError(getString(R.string.error_field_required));
            Toast.makeText(this, R.string.error_invalid_url, Toast.LENGTH_SHORT).show();
            focusView = mUrlView;
            cancel = true;
            focusView.requestFocus();
            return;
        }


        // perform the down attempt.
        new Download.Builder()
                .url(url)
                .priority(Priority.LOW)
                .type(Type.NORMAL)
               // .client(DLClientFactory.createClient(DLClientFactory.NORMAL, this))
                .build(this)
                .start();

    }

}

