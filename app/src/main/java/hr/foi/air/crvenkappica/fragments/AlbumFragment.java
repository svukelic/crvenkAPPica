package hr.foi.air.crvenkappica.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.util.ArrayList;

import hr.foi.air.crvenkappica.core.FileMan;
import hr.foi.air.crvenkappica.cam.ImageFromResultCamera;
import hr.foi.air.crvenkappica.cam.PickImageIntentCamera;
import hr.foi.air.crvenkappica.core.OnImageReturn;
import hr.foi.air.crvenkappica.gal.ImageFromResultGallery;
import hr.foi.air.crvenkappica.gal.PickImageIntentGallery;
import hr.foi.air.crvenkappica.images.CustomAsyncTask;
import hr.foi.air.crvenkappica.images.GridViewAdapter;
import hr.foi.air.crvenkappica.images.ImageItem;
import hr.foi.air.crvenkappica.JSONParser;
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
public class AlbumFragment extends Fragment implements OnTaskCompleted {
    private static final int CAMERA = 1;
    private static final int ALBUM = 2;
    private Button b, b2;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String selectedImagePath;
    private Fragment f;
    private String[] lista;
    private OnTaskCompleted o;
    private String userId;
    /**
     * Kreira te vraća view pripadnog fragmenta.
     * Poziva se webrequest koji dohvaća listu slika koje je korisnik uploadao.
     *
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_album,container,false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        b = (Button) view.findViewById(R.id.kamera);
        b2 = (Button) view.findViewById(R.id.album);
        //Listener za click na button
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageFragment imageFragment = new ImageFragment();
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                Bundle data = new Bundle();
                data.putString("title", item.getTitle());
                data.putParcelable("image", item.getImage());
                imageFragment.setArguments(data);
                getFragmentManager().beginTransaction()
                        .add(R.id.container352, imageFragment).addToBackStack(null)
                        .commit();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickCamera(view);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickAlbum(view);
            }
        });
        WebParams webParamsReg = new WebParams();
        webParamsReg.adresa = WebSite.WebAdress.getAdresa();
        webParamsReg.service = "image_list.php";
        userId = LoginStatus.LoginInfo.getLoginID();
        //Za dohvat drugog profila...
        Bundle bundle = getArguments();
        if(bundle != null){
            String id_user = bundle.getString("ID", null);
            webParamsReg.params = "?id=" + id_user;
            b.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }
        else{
            webParamsReg.params = "?id=" + userId;
        }
        webParamsReg.listener = response2;
        new WebRequest().execute(webParamsReg);
        o = this;
        f= this;

        return view;
    }
    //Pri kliku na button, otvara nam se prozor na kojem biramo s kojeg "servisa" zelimo odabrati slike: kamera, galerija...
   //pokrecemo aktivnost kako bi dobili rezultat: u nasem slucaju dobivamo sliku
    public void onPickCamera(View view){

        Intent chooseImageIntent = PickImageIntentCamera.getPickImageIntent(getActivity().getApplicationContext());
        startActivityForResult(chooseImageIntent, CAMERA);
    }
    public void onPickAlbum(View view){
        //
        Intent chooseImageIntent = PickImageIntentGallery.getPickImageIntent(getActivity().getApplicationContext());
        startActivityForResult(chooseImageIntent, ALBUM);
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
                if(lista.length <1){
                    //Toast.makeText(getActivity().getApplicationContext(), "Korisnik nema slika u albumu.", Toast.LENGTH_LONG).show();
                }
                else{

                    for(int i=0; i<jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        lista[i] = json_data.getString("Link");
                    }
                    new CustomAsyncTask(lista,getContext(),o).execute();
                }

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
                Toast.makeText(getActivity().getApplicationContext(), "Uspješan upload.", Toast.LENGTH_LONG).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(f).attach(f).commit();
            }
            if (output.equals("greska prilikom upisa")) {
                Toast.makeText(getActivity().getApplicationContext(), "Greška prilikom uploada.", Toast.LENGTH_LONG).show();
            }
            if(output == null || output.isEmpty()) Toast.makeText(getActivity().getApplicationContext(), "Problem sa internetskom vezom.", Toast.LENGTH_LONG).show();
        }
    };
    //Nakon sto smo odabrali sliku (s kamere, iz galerije) istu ćemo dobiti kao rezultat u kao Intent data parametar
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        OnImageReturn onImageReturn = null;
        try {
            switch(requestCode) {
                case CAMERA:
                    onImageReturn = new ImageFromResultCamera();
                    break;
                case ALBUM:
                    onImageReturn = new ImageFromResultGallery();
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
            selectedImagePath = onImageReturn.GetPath(getActivity().getApplicationContext(), resultCode, data);

            if(!selectedImagePath.isEmpty()){
                String id = userId;
                File file = FileMan.returnFile(selectedImagePath);
                ImageItem i = new ImageItem();
                i.setId(id);
                i.setTitle(file.getName());
                JSONParser j = new JSONParser(i);
                new Thread(new Runnable() {
                    public void run() {
                        FileMan.uploadFile(getActivity().getApplicationContext(), selectedImagePath);
                    }
                }).start();
                WebParams webParamsReg = new WebParams();
                webParamsReg.adresa = WebSite.WebAdress.getAdresa();
                webParamsReg.service = "image_db.php";
                webParamsReg.params = j.getString();
                webParamsReg.listener = response;
                new WebRequest().execute(webParamsReg);
                Toast.makeText(getActivity(), "Slika odabrana.", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Molimo odaberite sliku ponovo.", Toast.LENGTH_LONG).show();
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


}
