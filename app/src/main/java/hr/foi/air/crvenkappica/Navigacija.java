package hr.foi.air.crvenkappica;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Navigacija extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    //private ArrayList imageList;
    //private ArrayList textList;
    private int numberOfElements;

    private static final String url = "http://www.hls.com.hr/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigacija);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //getData();
        showData();
    }

    private void getData(){
        try{
            NewsFeed newsFeed = new NewsFeed(url);
            //textList = new ArrayList<String>(newsFeed.getText());
            //imageList = new ArrayList<String>(newsFeed.getImages());
           // numberOfElements = imageList.size();
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showData(){
        ArrayList<String> textList = new ArrayList<>();
        Document doc;
        TextView textView = (TextView)findViewById(R.id.testTv);
        try{
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div.post-content p");

            for (Element e : elements){
                textList.add(e.text());
                textView.setText(textList.get(0).toString());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*
        final TextView[] textViews = new TextView[numberOfElements];
        String text;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = (LinearLayout)findViewById(R.id.linear_layout);
        TextView textView = (TextView)findViewById(R.id.testTv);
        textView.setText(textList.get(0).toString());

        for(int i=0;i<numberOfElements;i++){
            final TextView rowTextView = new TextView(this);
            rowTextView.setLayoutParams(layoutParams);
            text = textList.get(i).toString();
            rowTextView.setText(text);
            layout.addView(rowTextView);
        }
        */
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new ObavijestiFragment())
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new TestFragment())
                        .commit();
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container352, new ObavijestiFragment())
                        .commit();
                break;
                default:
                    break;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigacija, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
