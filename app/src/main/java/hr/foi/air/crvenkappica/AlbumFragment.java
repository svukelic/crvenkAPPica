package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

/**
 * Created by Mario on 20/12/2015.
 */
/*
Fragment za album korisnika, omogućava upload slika na server, te prikazuje koje slike je korisnik
uploadao. Nasljeđuje Fragment klasu te implementira OnTaskCompleted interface
 */
public class AlbumFragment extends Fragment implements OnTaskCompleted {
    private static final int PICK_IMAGE_ID = 234;
    private Button b;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String selectedImagePath;
    private String[] lista;
    private OnTaskCompleted o;
    private static String upLoadServerUri = "http://www.redtesseract.sexy/crvenkappica/upload_images.php";
    int serverResponseCode = 0;
    private Bitmap[] bitmap;



    /**
     * Kreira te vraća view pripadnog fragmenta.
     * Poziva se webrequest koji dohvaća listu slika koje je korisnik uploadao.
     *
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_album,container,false);
        b = (Button) view.findViewById(R.id.odabir);
        //Listener za click na button
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage(view);
            }
        });
        WebParams webParamsReg = new WebParams();
        webParamsReg.service = "image_list.php";
        webParamsReg.params = "?id=" +LoginStatus.LoginInfo.getLoginID();
        webParamsReg.listener = response2;
        new WebRequest().execute(webParamsReg);
        o = this;
        return view;
    }
    //Pri kliku na button, otvara nam se prozor na kojem biramo s kojeg "servisa" zelimo odabrati slike: kamera, galerija...
   //pokrecemo aktivnost kako bi dobili rezultat: u nasem slucaju dobivamo sliku
    public void onPickImage(View view){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
    /**
     "Listener" koji čeka odgovor web servisa i na temelju outputa parsira jsonobject
     kako bi dobili linkove slika koje je korisnik uploadao.
     Pokreće se customasynctask, koji postavlja pripadne slike u gridview
     */
    AsyncResponse response2 = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("List");
                lista = new String[jArray.length()];
                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    lista[i] = json_data.getString("Link");
                }
                new CustomAsyncTask(lista,getContext(),o).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    /**
     "Listener" koji čeka odgovor web servisa i na temelju outputa korisniku se ispisuje
     poruka za uspješan upload ili neuspješan upload slike na server.
     */
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            if (output.equals("uspjeh")) {
                Toast.makeText(getActivity().getApplicationContext(), "Image upload done.", Toast.LENGTH_LONG).show();
            }
            if (output.equals("greska prilikom upisa")) {
                Toast.makeText(getActivity().getApplicationContext(), "Error during image upload.", Toast.LENGTH_LONG).show();
            }
            if(output == null || output.isEmpty()) Toast.makeText(getActivity().getApplicationContext(), "Problem with internet connection", Toast.LENGTH_LONG).show();
        }
    };
    //Nakon sto smo odabrali sliku (s kamere, iz galerije) istu ćemo dobiti kao rezultat u kao Intent data parametar
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case PICK_IMAGE_ID:
                //nakon odabira slike, istu uploadamo i pokrećemo webservis za spremanje u bazu
                String id = LoginStatus.LoginInfo.getLoginID();
                Toast.makeText(getActivity(), "Image chosen.", Toast.LENGTH_LONG).show();
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                File file = new File(selectedImagePath);
                String nesto = file.getName();
                ImageItem i = new ImageItem();
                i.setId(id);
                i.setTitle(nesto);
                System.out.println(i.getId() +  i.getTitle());
                JSONParser j = new JSONParser(i);
                System.out.println(selectedImagePath);
                System.out.println(nesto);
                System.out.println(j.getString());
                new Thread(new Runnable() {
                    public void run() {
                        uploadFile(selectedImagePath);
                    }
                }).start();
                WebParams webParamsReg = new WebParams();
                webParamsReg.service = "image_db.php";
                webParamsReg.params = j.getString();
                webParamsReg.listener = response;
                new WebRequest().execute(webParamsReg);
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
        }
    }
    @Override
    public void onTaskCompleted(ArrayList<String> result) {
    }
    /**
     * Implementacija interface-a.
     * Podaci dobiveni od strane asynctask-a (lista imageitem-a) prikazuju se u gridview-u
     */
    public void onTaskCompleted2(ArrayList<ImageItem> result) {
       // Toast.makeText(getActivity().getApplicationContext(), "Slike bi trebale biti prikazane.", Toast.LENGTH_LONG);
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity().getApplicationContext(),R.layout.grid_item_layout,result);
        gridView.setAdapter(gridAdapter);
        gridView.refreshDrawableState();
    }
    //Metoda za dohvat putanje datoteke
    public String getPath(Uri uri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    //Metoda za upload datoteke (parametar je putanja datoteke)
    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Toast.makeText(getActivity(), "Error, choose picture again.", Toast.LENGTH_LONG).show();
            Log.e("uploadFile", "Source File not exist :" + sourceFile);
            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ fileName + "" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Image upload complete.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                //dialog.dismiss();
                ex.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Error.", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                //dialog.dismiss();
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Error, check logcat.", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                        + e.getMessage(), e);
            }
            return serverResponseCode;
        } // End else block
    }

}
