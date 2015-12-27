package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mario on 21/12/2015.
 */
public class CustomAsyncTask extends AsyncTask<Void,Void, ArrayList<ImageItem>> {
    //String lista[] = {"http://www.redtesseract.sexy/crvenkappica/images/Screenshot_2015-09-23-12-59-54.png" }  ;
    private String lista[];
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Context context;
    private OnTaskCompleted listener;
    public CustomAsyncTask(String b[], Context c, OnTaskCompleted l){
        lista = b;
        context = c;
        listener = l;
    }
    @Override
    protected ArrayList<ImageItem> doInBackground(Void... params) {
        System.out.println("Alo:");
        ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        Bitmap mIcon11[] = new Bitmap[lista.length];
        for (int i=0;i<lista.length;i++){
            System.out.println("U petlji sam.");
            try{
                mIcon11[i] =  getBitmapFromURL(lista[i]);
                imageItems.add(new ImageItem(mIcon11[i]));
                System.out.println("Jesam, valjda.");
            }
            catch(Exception e){}
        }
        return imageItems;
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ImageItem> imageItems) {
        //super.onPostExecute(imageItems);
        listener.onTaskCompleted2(imageItems);
    }

    public class SanInputStream extends FilterInputStream {
        public SanInputStream(InputStream in) {
            super(in);
        }
        public long skip(long n) throws IOException {
            long m = 0L;
            while (m < n) {
                long _m = in.skip(n-m);
                if (_m == 0L) break;
                m += _m;
            }
            return m;
        }
    }
}
