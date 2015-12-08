package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    private String url;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            url = extras.getString("URL");
        }

        webView = (WebView)findViewById(R.id.webView_hls);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);
    }

    private class Browser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
    }
}

