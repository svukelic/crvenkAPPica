package hr.foi.air.crvenkappica;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by domagoj on 04.12.15..
 */
public class NewsFeed {

    private Document doc;

    public NewsFeed(String url) throws IOException{
        doc = Jsoup.connect(url).get();
    }

    //Get all images for posts
    public ArrayList<String> getImages(){

        ArrayList<String> imageList = new ArrayList<>();

        //Get all elements with class .post-image
        Elements elements = doc.getElementsByClass("post-image");
        int index = 0;

        for(Element el : elements){
            String src = el.absUrl("src");
            imageList.add(index,src);
            index = index + 1;
        }

        return imageList;
    }

    public ArrayList<String> getText(){

        ArrayList<String> textList = new ArrayList<>();

        Elements elements = doc.select("div.post-content p");
        textList.add("test");
        for (Element e : elements){
            textList.add(e.text());
        }

        return  textList;
    }
}
