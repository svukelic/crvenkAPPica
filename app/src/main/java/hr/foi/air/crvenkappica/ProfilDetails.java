package hr.foi.air.crvenkappica;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class ProfilDetails extends Fragment  {
    private TextView tvUsername;
    private TextView tvIme;
    private TextView tvPrezime;
    private TextView tvDob;
    private ProgressDialog progressdialog;
    private String userName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigacija,container,false);



        tvUsername = (TextView) view.findViewById(R.id.tvUsername);

        tvIme = (TextView) view.findViewById(R.id.tvIme);
        tvPrezime = (TextView) view.findViewById(R.id.tvPrezime);
        tvDob = (TextView) view.findViewById(R.id.tvDOB);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setTitle("Profil");
        progressdialog.setMessage("Loading profile");
        progressdialog.setIndeterminate(false);
        progressdialog.setCancelable(false);
        progressdialog.show();

        userName = LoginStatus.LoginInfo.getProfilSearch();

        tvUsername.setText("Username: " + userName);

        if (!userName.isEmpty()) {
            String hash = "";
            String type = "";
            WebParams paramsProfil = new WebParams();
            paramsProfil.params = "?UserName=" + userName;
            paramsProfil.service = "profil_dohvat.php";
            paramsProfil.listener = response;
            new WebRequest().execute(paramsProfil);
        }

        return view;
    }

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
