package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.login.LoginStatus;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

public class WeatherForecastFragment extends Fragment {

    private EditText etSearch;
    private Button btnSearch;
    private ProgressDialog progressdialog;
    private String searchString;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_weatherforecast,container,false);

        btnSearch = (Button) view.findViewById(R.id.buttonSearchWeather);
        listView = (ListView) view.findViewById(R.id.lvSearchWeather);

        //listener za klik na search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etSearch = (EditText) view.findViewById(R.id.etSearchGrad);
                searchString = etSearch.getText().toString();

                //if (!searchString.isEmpty()) {

                    progressdialog = new ProgressDialog(getActivity());
                    progressdialog.setTitle("Dohvat");
                    progressdialog.setMessage("Učitavam");
                    progressdialog.setIndeterminate(false);
                    progressdialog.setCancelable(false);
                    progressdialog.show();

                    String hash = "";
                    String type = "";
                    WebParams paramsProfil = new WebParams();
                    paramsProfil.adresa = "http://api.openweathermap.org/data/2.5/";
                    paramsProfil.params = "?q=" + searchString + ",hr&units=metric&mode=json&appid=b6c7cadd32cac640c6b85fc9610ee541";
                    paramsProfil.service = "forecast";
                    paramsProfil.listener = response;
                    new WebRequest().execute(paramsProfil);
                //}
            }
        });

        return view;
    }

    //dohvat odgovora
    //odgovor je u obliku JSON arraya, parsira se i ispisuje u list view
    //klikom na item koji predstavlja neki pronađeni profil u listviewu otvaraju se detalji tog profila
    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            progressdialog.hide();

            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("list");

                String[] lista = new String[jArray.length()];

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    JSONObject main = json_data.getJSONObject("main");
                    JSONArray weatherArray = json_data.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);

                    lista[i] = "Datum: " + json_data.getString("dt_txt") + "\n" +
                            "Vrijeme: " + weather.getString("main") + "\n" +
                            "Temperatura: " + main.getString("temp") + " C" + "\n" +
                            "Pritisak: " + main.getString("pressure") + " hPa" + "\n" +
                            "Vlažnost: " + main.getString("humidity") +"%";

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, lista);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        int itemPosition     = position;

                        String  itemValue    = (String) listView.getItemAtPosition(position);

                        Fragment fragment = new ProfilDetailsFragment(true);
                        FragmentManager fragmentManager = getFragmentManager();

                        /*LoginStatus.LoginInfo.setProfilSearch(itemValue);

                        fragmentManager.beginTransaction()
                                .replace(R.id.container352, fragment)
                                .commit();/*

                        /* Show Alert
                        Toast.makeText(getActivity(),
                                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                                .show();*/

                    }

                });

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };

}
