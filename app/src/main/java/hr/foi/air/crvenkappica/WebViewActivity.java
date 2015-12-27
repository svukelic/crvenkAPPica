package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Klasa za prikaz WebView-a.
 */
public class WebViewActivity extends Activity {

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
        webView.loadUrl(url);
    }

    /**
     * Potrebno je naslijediti klasu WebViewClient te overrideati metodu
     * shouldOverrideUrlLoading (vratiti true) kako bi se omogućilo daljnje pretraživanje internetskih
     * stranica unutar WebView-a.
     */
    
    private class Browser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }
    }
}

