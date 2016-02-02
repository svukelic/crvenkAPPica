package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

public class LovistaFragment extends Fragment {

    private ListView listView;
    private ProgressDialog progressdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lovista,container,false);

        listView = (ListView) view.findViewById(R.id.lvLovista);

        Spinner dropdown = (Spinner) view.findViewById(R.id.spinner1);
        String[] items = new String[]{
                "Zagrebačka",
                "Krapinsko-zagorska",
                "Sisačko-moslavačka",
                "Karlovačka",
                "Varaždinska",
                "Koprivničko-križevačka",
                "Bjelovarsko-bilogorska",
                "Primorsko-goranska",
                "Ličko-senjska",
                "Virovitičko-podravska",
                "Požeško-slavonska",
                "Brodsko-posavska",
                "Zadarska",
                "Osječko-baranjska",
                "Šibensko-kninska",
                "Vukovarsko-srijemska",
                "Splitsko-dalmatinska",
                "Istarska",
                "Dubrovačko-neretvanska",
                "Međimurska"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), position, Toast.LENGTH_LONG).show();
                progressdialog = new ProgressDialog(getActivity());
                progressdialog.setTitle("Dohvat");
                progressdialog.setMessage("Učitavam");
                progressdialog.setIndeterminate(false);
                progressdialog.setCancelable(false);
                progressdialog.show();

                String hash = "";
                String type = "";
                WebParams params2 = new WebParams();
                params2.adresa = WebSite.WebAdress.getAdresa();
                params2.params = "?zupanija=" + (String) parent.getItemAtPosition(position);
                params2.service = "lovista.php";
                params2.listener = response;
                new WebRequest().execute(params2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {

            progressdialog.hide();

            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("list");

                String[] list = new String[jArray.length()];

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    list[i] = json_data.getString("Naziv");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, list);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        int itemPosition     = position;

                        String  itemValue    = (String) listView.getItemAtPosition(position);

                    }

                });

            } catch (JSONException e) {
                //Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }

        }
    };

}
