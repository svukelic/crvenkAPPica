package hr.foi.air.crvenkappica;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;

public class ProfilSearchFragment extends Fragment {

    private EditText etSearch;
    private Button btnSearch;
    private ProgressDialog progressdialog;
    private String searchString;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search,container,false);

        btnSearch = (Button) view.findViewById(R.id.buttonSearch);
        listView = (ListView) view.findViewById(R.id.lvSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etSearch = (EditText) view.findViewById(R.id.etSearch);
                searchString = etSearch.getText().toString();

                if (!searchString.isEmpty()) {

                    progressdialog = new ProgressDialog(getActivity());
                    progressdialog.setTitle("Pretraga");
                    progressdialog.setMessage("Pretražujem profile");
                    progressdialog.setIndeterminate(false);
                    progressdialog.setCancelable(false);
                    progressdialog.show();

                    String hash = "";
                    String type = "";
                    WebParams paramsProfil = new WebParams();
                    paramsProfil.params = "?ime=" + searchString;
                    paramsProfil.service = "profil_search.php";
                    paramsProfil.listener = response;
                    new WebRequest().execute(paramsProfil);
                }
            }
        });

        return view;
    }

    AsyncResponse response = new AsyncResponse() {
        @Override
        public void processFinish(String output) {
            //System.out.println(output);
            progressdialog.hide();
            Toast.makeText(getActivity(), output, Toast.LENGTH_LONG).show();
            //final ArrayList<String> list = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("list");

                String[] lista = new String[jArray.length()];

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    lista[i] = json_data.getString("Username");
                    //list.add(json_data.getString("Username"));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, lista);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition     = position;

                        // ListView Clicked item value
                        String  itemValue    = (String) listView.getItemAtPosition(position);

                        Fragment fragment = new ProfilDetails();
                        FragmentManager fragmentManager = getFragmentManager();

                        //Bundle bundle = new Bundle();
                        //bundle.putString("Username", itemValue);
                        //fragment.setArguments(bundle);
                        LoginStatus.LoginInfo.setProfilSearch(itemValue);

                        fragmentManager.beginTransaction()
                                .replace(R.id.container352, fragment)
                                .commit();

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
