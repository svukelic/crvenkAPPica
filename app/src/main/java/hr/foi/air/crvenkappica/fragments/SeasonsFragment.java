package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.calendar.CalendarAdapter;
import hr.foi.air.crvenkappica.calendar.CalendarItem;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;


public class SeasonsFragment extends Fragment {

    private Spinner spinner;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ArrayList<CalendarItem> items;
    private LinearLayoutManager layoutManager;
    private CalendarItem calendarItem;
    private CalendarAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<CalendarItem>();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calendar_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.prey_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        spinner = (Spinner) view.findViewById(R.id.months_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.months_array, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Hunting seasons");
                progressDialog.setMessage("Downloading data...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.show();

                WebParams webParams = new WebParams();
                webParams.adresa = WebSite.WebAdress.getAdresa();
                webParams.params = "?sezona=" + String.valueOf(position+1);
                webParams.service = "sezone_lovine.php";
                webParams.listener = response;
                new WebRequest().execute(webParams);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            progressDialog.hide();
            items = new ArrayList<CalendarItem>();

            try{

                JSONArray jsonArray = new JSONArray(output);

                for (int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    calendarItem = new CalendarItem();
                    calendarItem.setLink(jsonObject.getString("Link"));
                    calendarItem.setName(jsonObject.getString("Naziv"));

                    items.add(calendarItem);
                }

                adapter = new CalendarAdapter(items,getActivity());
                recyclerView.setAdapter(adapter);

            }catch (JSONException ex){
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };
}
