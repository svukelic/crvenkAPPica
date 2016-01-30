package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.login.LoginStatus;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

//ProfilDetailsFragment fragment, prikazuje detalje korisnika
public class ProfilDetailsFragment extends Fragment  {
    private TextView tvUsername;
    private TextView tvIme;
    private TextView tvPrezime;
    private TextView tvDob;
    private ProgressDialog progressdialog;
    private String userName;
    private Button buttonAlbum;

    public ProfilDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigacija, container, false);

        buttonAlbum = (Button)view.findViewById(R.id.button_album);
        userName = LoginStatus.LoginInfo.getProfilSearch();
        tvUsername = (TextView) view.findViewById(R.id.username);

        tvIme = (TextView)view.findViewById(R.id.ime);
        tvPrezime = (TextView) view.findViewById(R.id.prezime);
        tvDob = (TextView) view.findViewById(R.id.datum_rodenja);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setTitle("Profil");
        progressdialog.setMessage("Učitavam");
        progressdialog.setIndeterminate(false);
        progressdialog.setCancelable(false);
        progressdialog.show();

        tvUsername.setText(userName);


        //dohvat traženog profila
        if (!userName.isEmpty()) {

            WebParams paramsProfil = new WebParams();
            paramsProfil.adresa = WebSite.WebAdress.getAdresa();
            paramsProfil.params = "?UserName=" + userName;
            paramsProfil.service = "profil_dohvat.php";
            paramsProfil.listener = response;
            new WebRequest().execute(paramsProfil);
        }


        buttonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    //ispis dohvaćenog profila
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {

            progressdialog.hide();

            try {

                JSONObject jsonObject = new JSONObject(output);
                tvIme.setText(jsonObject.getString("Ime"));
                tvPrezime.setText(jsonObject.getString("Prezime"));
                tvDob.setText(jsonObject.getString("Dob"));

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };

}
