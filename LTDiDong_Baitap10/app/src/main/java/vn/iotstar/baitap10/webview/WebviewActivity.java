package vn.iotstar.baitap10.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstar.baitap10.databinding.ActivityWebviewBinding;

public class WebviewActivity extends AppCompatActivity {
    private ActivityWebviewBinding binding;

    @SuppressLint({"SetJavaScriptEnabled", "WebViewApiAvailability"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // WebView settings
        binding.webview2.getSettings().setLoadWithOverviewMode(true);
        binding.webview2.getSettings().setUseWideViewPort(true);
        binding.webview2.getSettings().setJavaScriptEnabled(true);
        binding.webview2.setWebViewClient(new WebViewClient());
        binding.webview2.getSettings().setBuiltInZoomControls(true);
        binding.webview2.getSettings().setDomStorageEnabled(true);
        binding.webview2.getSettings().setDatabaseEnabled(true);
        binding.webview2.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.webview2.setWebChromeClient(new WebChromeClient());

        // Load URL
        binding.webview2.loadUrl("http://iotstar.vn");
    }
}
