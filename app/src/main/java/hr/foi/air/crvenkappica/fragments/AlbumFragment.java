package hr.foi.air.crvenkappica.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import hr.foi.air.crvenkappica.camera.CameraManager;
import hr.foi.air.crvenkappica.camera.PictureItem;
import hr.foi.air.crvenkappica.images.CustomAsyncTask;
import hr.foi.air.crvenkappica.images.GridViewAdapter;
import hr.foi.air.crvenkappica.images.ImageItem;
import hr.foi.air.crvenkappica.images.ImagePicker;
import hr.foi.air.crvenkappica.JSONParser;
import hr.foi.air.crvenkappica.login.LoginPreference;
import hr.foi.air.crvenkappica.login.LoginStatus;
import hr.foi.air.crvenkappica.OnTaskCompleted;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

/**
 * Created by Mario on 20/12/2015.
 */
/*
Fragment za album korisnika, omogućava upload slika na server, te prikazuje koje slike je korisnik
uploadao. Nasljeđuje Fragment klasu te implementira OnTaskCompleted interface
 */
public class AlbumFragment extends Fragment implements OnTaskCompleted, PictureItem {
    private static final int PICK_IMAGE_ID = 234;
    private Button b;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String selectedImagePath;
    private Fragment f;
    private String[] lista;
    private OnTaskCompleted o;
    private static String upLoadServerUri = "http://www.redtesseract.sexy/crvenkappica/upload_images.php";
    int serverResponseCode = 0;
    private LoginPreference loginPreference;
    private boolean loggedIn;
    private String userId;
    CameraManager cm = null;

    /**
     * Kreira te vraća view pripadnog fragmenta.
     * Poziva se webrequest koji dohvaća listu slika koje je korisnik uploadao.
     *
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loginPreference = new LoginPreference(getActivity());
        loggedIn = loginPreference.CheckLoggedIn();
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
        webParamsReg.adresa = WebSite.WebAdress.getAdresa();
        webParamsReg.service = "image_list.php";
        if(loggedIn){
            userId = loginPreference.GetUserId();
        }
        else{
            userId = LoginStatus.LoginInfo.getLoginID();
        }
        webParamsReg.params = "?id=" + userId;
        webParamsReg.listener = response2;
        new WebRequest().execute(webParamsReg);
        o = this;
        f= this;
        cm = CameraManager.getInstance();
        return view;
    }
    //Pri kliku na button, otvara nam se prozor na kojem biramo s kojeg "servisa" zelimo odabrati slike: kamera, galerija...
   //pokrecemo aktivnost kako bi dobili rezultat: u nasem slucaju dobivamo sliku
    public void onPickImage(View view){
       // Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity().getApplicationContext());
      //  startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        //CameraManager cm = CameraManager.getInstance();
        cm.setDependencies(getActivity().getApplicationContext());
        Intent chooseImageIntent = cm.getPickImageIntent();
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
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(f).attach(f).commit();
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
        try {
            switch(requestCode) {
                case PICK_IMAGE_ID:
                    Bitmap bitmap = cm.getImageFromResult(resultCode, data);
                    String id = userId;
                    Uri selectedImageUri = cm.getImageUri(bitmap);
                    selectedImagePath = cm.getPath(selectedImageUri);
                    File file = cm.returnFile(selectedImagePath);
                    String nesto = file.getName();
                    ImageItem i = new ImageItem();
                    i.setId(id);
                    i.setTitle(nesto);
                    JSONParser j = new JSONParser(i);
                    new Thread(new Runnable() {
                        public void run() {
                            cm.uploadFile(selectedImagePath);
                        }
                    }).start();
                    WebParams webParamsReg = new WebParams();
                    webParamsReg.adresa = WebSite.WebAdress.getAdresa();
                    webParamsReg.service = "image_db.php";
                    webParamsReg.params = j.getString();
                    webParamsReg.listener = response;
                    new WebRequest().execute(webParamsReg);
                    Toast.makeText(getActivity(), "Image chosen.", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Please choose image again.", Toast.LENGTH_LONG).show();
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
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity().getApplicationContext(),R.layout.grid_item_layout,result);
        gridView.setAdapter(gridAdapter);
        gridView.refreshDrawableState();
    }
    @Override
    public String getPicturePath() {
        return selectedImagePath;
    }


}
