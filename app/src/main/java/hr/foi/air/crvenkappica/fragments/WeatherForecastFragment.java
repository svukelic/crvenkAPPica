package hr.foi.air.crvenkappica.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.calendar.CalendarAdapter;
import hr.foi.air.crvenkappica.calendar.CalendarItem;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class WeatherForecastFragment extends Fragment {

    private EditText etSearch;
    private Button btnSearch, btnList, btnTrend;
    private ProgressDialog progressdialog;
    private String searchString;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CalendarItem> items;
    private CalendarItem calendarItem;
    private CalendarAdapter adapter;
    private String weatherCode;
    private GraphView graph;
    private Date d1, d2, d3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_weatherforecast,container,false);

        btnSearch = (Button) view.findViewById(R.id.buttonSearchWeather);
        btnList = (Button) view.findViewById(R.id.buttonList);
        btnTrend = (Button) view.findViewById(R.id.buttonTrend);

        graph = (GraphView) view.findViewById(R.id.graph);

        /*Calendar calendar = Calendar.getInstance();
        d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        d3 = calendar.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(2, 3),
        });
        graph.addSeries(series);*/

        recyclerView = (RecyclerView) view.findViewById(R.id.weather_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //listener za klik na search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etSearch = (EditText) view.findViewById(R.id.etSearchGrad);
                searchString = etSearch.getText().toString();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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

        graph.setVisibility(View.INVISIBLE);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph.getHandler().post(new Runnable() {
                    public void run() {
                        graph.setVisibility(View.INVISIBLE);
                    }
                });

                recyclerView.getHandler().post(new Runnable() {
                    public void run() {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

        btnTrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graph.getHandler().post(new Runnable() {
                    public void run() {
                        graph.setVisibility(View.VISIBLE);
                    }
                });

                recyclerView.getHandler().post(new Runnable() {
                    public void run() {
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                });

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
            items = new ArrayList<CalendarItem>();

            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("list");

                String[] lista = new String[jArray.length()];
                Date[] datum = new Date[jArray.length()];

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    JSONObject main = json_data.getJSONObject("main");
                    JSONArray weatherArray = json_data.getJSONArray("weather");
                    JSONObject weather = weatherArray.getJSONObject(0);

                    weatherCode = weather.getString("icon");

                    lista[i] = "Datum: " + json_data.getString("dt_txt") + "\n" +
                            "Vrijeme: " + weather.getString("main") + "\n" +
                            "Temperatura: " + main.getString("temp") + " C" + "\n" +
                            "Pritisak: " + main.getString("pressure") + " hPa" + "\n" +
                            "Vlažnost: " + main.getString("humidity") +"%";

                    calendarItem = new CalendarItem();
                    calendarItem.setLink("http://openweathermap.org/img/w/" + weatherCode + ".png");
                    calendarItem.setName(lista[i]);

                    items.add(calendarItem);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        datum[i] = format.parse(json_data.getString("dt_txt"));
                    } catch (ParseException e) {
                    }

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(datum[i], Double.parseDouble(main.getString("temp"))),
                    });
                    graph.addSeries(series);
                }

                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

                graph.getViewport().setMinX(datum[0].getTime());
                graph.getViewport().setMaxX(datum[jArray.length()-1].getTime());
                graph.getViewport().setXAxisBoundsManual(true);

                adapter = new CalendarAdapter(items,getActivity());
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };

}
