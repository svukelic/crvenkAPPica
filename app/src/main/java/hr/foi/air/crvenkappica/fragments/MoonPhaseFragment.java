package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Date;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.calendar.CalendarAdapter;
import hr.foi.air.crvenkappica.calendar.CalendarItem;
import hr.foi.air.crvenkappica.calendar.MoonLinks;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class MoonPhaseFragment extends Fragment{

    private ListView listView;
    private ProgressDialog progressdialog;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CalendarItem> items;
    private CalendarItem calendarItem;
    private CalendarAdapter adapter;
    private String moonIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_moon,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.moon_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

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
            items = new ArrayList<CalendarItem>();

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

                    String phaseType = "new";

                    if(phase[i].equals("Full Moon")) phaseType = "full";
                    if(phase[i].equals("New Moon")) phaseType = "new";
                    if(phase[i].equals("First Quarter")) phaseType = "first";
                    if(phase[i].equals("Last Quarter")) phaseType = "last";

                    moonIcon = MoonLinks.MoonIcons.getLink(phaseType);

                    list[i] = phase[i] + "\n" + date[i]  + "\n" + time[i];

                    calendarItem = new CalendarItem();
                    calendarItem.setLink(moonIcon);
                    calendarItem.setName(list[i]);

                    items.add(calendarItem);
                }

                adapter = new CalendarAdapter(items,getActivity());
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }

        }
    };

}
