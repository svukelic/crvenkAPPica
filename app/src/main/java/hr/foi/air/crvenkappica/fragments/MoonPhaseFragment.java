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
import android.widget.ListView;
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

public class MoonPhaseFragment extends Fragment{

    private ListView listView;
    private ProgressDialog progressdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_moon,container,false);

        listView = (ListView) view.findViewById(R.id.listViewMoon);

        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setTitle("Dohvat");
        progressdialog.setMessage("Učitavam");
        progressdialog.setIndeterminate(false);
        progressdialog.setCancelable(false);
        progressdialog.show();

        String hash = "";
        String type = "";

        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        WebParams paramsLogin = new WebParams();
        paramsLogin.adresa = "http://api.usno.navy.mil/moon/";
        paramsLogin.params = "?date=" + date + "&nump=24";
        paramsLogin.service = "phase";
        paramsLogin.listener = response;

        new WebRequest().execute(paramsLogin);

        return view;
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {

            progressdialog.hide();

            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("phasedata");

                String[] phase = new String[jArray.length()];
                String[] date = new String[jArray.length()];
                String[] time = new String[jArray.length()];

                String[] list = new String[jArray.length()];

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    phase[i] = json_data.getString("phase");
                    date[i] = json_data.getString("date");
                    time[i] = json_data.getString("time");

                    list[i] = phase[i] + "\n" + date[i]  + "\n" + time[i];
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
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }

        }
    };

}
