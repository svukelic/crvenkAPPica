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

import hr.foi.air.crvenkappica.login.LoginPreference;
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
    private Button b;
    private LoginPreference loginPreference;
    private boolean loggedIn;
    private boolean search = false;

    public ProfilDetailsFragment(boolean Search){
        search = Search;
    }
    public ProfilDetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigacija,container,false);

        loginPreference = new LoginPreference(getActivity());
        loggedIn = loginPreference.CheckLoggedIn();

        if(loggedIn && !search){
            userName = loginPreference.GetUsername();
        }
        else{
            userName = LoginStatus.LoginInfo.getProfilSearch();
        }

        tvUsername = (TextView) view.findViewById(R.id.tvUsername);

        tvIme = (TextView) view.findViewById(R.id.tvIme);
        tvPrezime = (TextView) view.findViewById(R.id.tvPrezime);
        tvDob = (TextView) view.findViewById(R.id.tvDOB);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setTitle("Profil");
        progressdialog.setMessage("Učitavam");
        progressdialog.setIndeterminate(false);
        progressdialog.setCancelable(false);
        progressdialog.show();

        tvUsername.setText("Korisničko ime: " + userName);


        //dohvat traženog profila
        if (!userName.isEmpty()) {
            String hash = "";
            String type = "";
            WebParams paramsProfil = new WebParams();
            paramsProfil.adresa = WebSite.WebAdress.getAdresa();
            paramsProfil.params = "?UserName=" + userName;
            paramsProfil.service = "profil_dohvat.php";
            paramsProfil.listener = response;
            new WebRequest().execute(paramsProfil);
        }
        return view;
    }

    //ispis dohvaćenog profila
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            //System.out.println(output);
            progressdialog.hide();
            try {
                JSONObject jsonObject = new JSONObject(output);
                tvIme.setText("Ime: " + jsonObject.getString("Ime"));
                tvPrezime.setText("Prezime: " + jsonObject.getString("Prezime"));
                tvDob.setText("DOB: " + jsonObject.getString("Dob"));
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };
}
