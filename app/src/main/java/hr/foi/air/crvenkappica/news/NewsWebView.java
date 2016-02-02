package hr.foi.air.crvenkappica.news;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hr.foi.air.crvenkappica.R;

/**
 * Klasa za prikaz WebView-a.
 */
public class NewsWebView extends Activity {

    private String url;
    private WebView webView;

    /**
     * Poziva se pri kreiranju aktivnosti. Postavlja se layout koji sadrži webview.
     * Učitava se proslijeđeni url u webview.
     */
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
    
}

