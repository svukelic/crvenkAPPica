package hr.foi.air.crvenkappica.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.crvenkappica.login.LoginStatus;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.web.AsyncResponse;
import hr.foi.air.crvenkappica.web.WebParams;
import hr.foi.air.crvenkappica.web.WebRequest;
import hr.foi.air.crvenkappica.web.WebSite;

//Fragment za pretragu profila
public class ProfilSearchFragment extends Fragment {

    private SearchView searchView;
    private ListView listView;
    List<String> listUsers;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search,container,false);

        listView = (ListView) view.findViewById(R.id.lvSearch);
        searchView = (SearchView) view.findViewById(R.id.searchView);

        WebParams paramsProfil = new WebParams();
        paramsProfil.adresa = WebSite.WebAdress.getAdresa();
        paramsProfil.params = "";
        paramsProfil.service = "profil_sve.php";
        paramsProfil.listener = response;
        new WebRequest().execute(paramsProfil);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0)
                    return setFilteredList(newText);

                setDefaultList();
                return false;
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

            try {
                JSONObject jsonObject = new JSONObject(output);
                JSONArray jArray = jsonObject.getJSONArray("list");

                listUsers = new ArrayList<String>();

                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    listUsers.add(json_data.getString("Username"));
                }

                setDefaultList();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String  itemValue = (String) listView.getItemAtPosition(position);

                        Fragment fragment = new ProfilDetailsFragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        LoginStatus.LoginInfo.setProfilSearch(itemValue);

                        fragmentManager.beginTransaction()
                                .replace(R.id.container352, fragment)
                                .commit();
                    }

                });

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    };

    public boolean setFilteredList(String searchString){

        searchString =  searchString.toLowerCase();
        ArrayList<String> filterList = new ArrayList<String>();


        for(String item:listUsers){
            if(item.toLowerCase().contains(searchString)){
                filterList.add(item);
            }
        }

        adapter = new ArrayAdapter<String>(getActivity(),
                  android.R.layout.simple_list_item_1, android.R.id.text1, filterList);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return true;
    }

    public void setDefaultList(){
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listUsers);

        listView.setAdapter(adapter);
    }
}
