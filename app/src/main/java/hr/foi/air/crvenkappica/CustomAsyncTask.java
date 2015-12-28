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
/**
 * Klasa za rad s asynctaskom. Služi za dohvat slika te prikaz na gridview-u.
 */
public class CustomAsyncTask extends AsyncTask<Void,Void, ArrayList<ImageItem>> {
    private String lista[];
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Context context;
    private OnTaskCompleted listener;
    //Konstruktor prima listu (sadrzi linkove na slike), context, te listener
    public CustomAsyncTask(String b[], Context c, OnTaskCompleted l){
        lista = b;
        context = c;
        listener = l;
    }
    @Override
    /**
     * Metoda pozvana na background dretvi.
     * Nakon što su slike dohvaćene, vraća listu ImageItem-a.
     */
    protected ArrayList<ImageItem> doInBackground(Void... params) {
        ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        Bitmap mIcon11[] = new Bitmap[lista.length];
        for (int i=0;i<lista.length;i++){
            try{
                mIcon11[i] =  getBitmapFromURL(lista[i]);
                imageItems.add(new ImageItem(mIcon11[i]));
            }
            catch(Exception e){}
        }
        return imageItems;
    }
    //Metoda za dohvat slika s web adrese
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
    /**
     * Pozvana na UI dretvi nakon što su podaci dohvaćeni.
     * Argument metode je povratni tip podataka metode doInBackground.
     * Poziva se metoda onTaskCompleted2 iz interface-a.
     */
    @Override
    protected void onPostExecute(ArrayList<ImageItem> imageItems) {
        //super.onPostExecute(imageItems);
        listener.onTaskCompleted2(imageItems);
    }

}
