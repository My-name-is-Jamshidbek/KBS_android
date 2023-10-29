// MainActivity.java
package com.example.rgb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import com.example.rgb.utility.NetworkChangeListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements NetworkChangeListener.NetworkChangeCallback {
    private WebView mywebView;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView = (WebView) findViewById(R.id.webview);
        mywebView.setWebViewClient(new mywebClient());
        mywebView.loadUrl("http://192.168.4.22/");
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    public void onNetworkConnected() {
        // Reload the WebView when the network is connected
        mywebView.loadUrl("http://192.168.4.22/");
    }
}
