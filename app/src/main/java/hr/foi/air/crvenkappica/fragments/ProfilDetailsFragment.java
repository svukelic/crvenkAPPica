package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView tvStatus;
    private EditText etStatus;
    private ProgressDialog progressdialog;
    private String userName, id_korisnik;
    private Button buttonAlbum;
    private Button buttonStatus;

    private ProgressDialog dialog;


    public ProfilDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigacija, container, false);

        buttonAlbum = (Button)view.findViewById(R.id.button_album);
        buttonStatus = (Button)view.findViewById(R.id.button_status);
        userName = LoginStatus.LoginInfo.getProfilSearch();
        tvUsername = (TextView) view.findViewById(R.id.username);

        tvIme = (TextView)view.findViewById(R.id.ime);
        tvPrezime = (TextView) view.findViewById(R.id.prezime);
        tvDob = (TextView) view.findViewById(R.id.datum_rodenja);
        tvStatus = (TextView) view.findViewById(R.id.status);
        etStatus = (EditText) view.findViewById(R.id.etStatus);

        etStatus.setVisibility(View.INVISIBLE);
        buttonStatus.setVisibility(View.INVISIBLE);

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
        buttonAlbum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("ID", id_korisnik);
                System.out.println("ID korisnika profil: "+ id_korisnik);
                AlbumFragment albumFragment = new AlbumFragment();
                albumFragment.setArguments(data);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.container352, albumFragment)
                        .commit();
            }
        });

        tvStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (LoginStatus.LoginInfo.getProfilSearch().equals(LoginStatus.LoginInfo.getLoginName())) {

                    etStatus.getHandler().post(new Runnable() {
                        public void run() {
                            etStatus.setVisibility(View.VISIBLE);
                        }
                    });

                    tvStatus.getHandler().post(new Runnable() {
                        public void run() {
                            tvStatus.setVisibility(View.INVISIBLE);
                        }
                    });

                    buttonStatus.getHandler().post(new Runnable() {
                        public void run() {
                            buttonStatus.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        etStatus.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (LoginStatus.LoginInfo.getProfilSearch().equals(LoginStatus.LoginInfo.getLoginName())) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        StatusUpdate();

                        return true;
                    }
                }
                return false;
            }
        });

        buttonStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                StatusUpdate();

            }
        });

        return view;
    }

    //ispis dohvaćenog profila
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {

            progressdialog.hide();
            //dialog.hide();

            try {
                final JSONObject jsonObject = new JSONObject(output);
                tvIme.setText(jsonObject.getString("Ime"));
                tvPrezime.setText(jsonObject.getString("Prezime"));
                tvDob.setText(jsonObject.getString("Dob"));
                if(jsonObject.getString("Status").isEmpty())
                    tvStatus.setText("Nema statusa");
                else tvStatus.setText(jsonObject.getString("Status"));

                id_korisnik = jsonObject.getString("id");
                //System.out.println("ID korisnika: " + jsonObject.getString("id"));
            } catch (JSONException e) {
                //Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };

    void StatusUpdate(){

        WebParams webParamsReg = new WebParams();
        webParamsReg.adresa = WebSite.WebAdress.getAdresa();
        webParamsReg.service = "status_update.php";
        webParamsReg.params = "?Username=" + LoginStatus.LoginInfo.getLoginName() + "&Status=" + etStatus.getText();
        webParamsReg.listener = response;
        new WebRequest().execute(webParamsReg);

        tvStatus.setText(etStatus.getText());

        etStatus.getHandler().post(new Runnable() {
            public void run() {
                etStatus.setVisibility(View.INVISIBLE);
            }
        });

        tvStatus.getHandler().post(new Runnable() {
            public void run() {
                tvStatus.setVisibility(View.VISIBLE);
            }
        });

        buttonStatus.getHandler().post(new Runnable() {
            public void run() {
                buttonStatus.setVisibility(View.INVISIBLE);
            }
        });
    }

}
