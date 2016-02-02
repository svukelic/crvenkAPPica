package hr.foi.air.crvenkappica.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.news.NewsWebView;

public class LovistaMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_lovista_map, container, false);

        String url = "http://www.lovac.info/lovacki-portal-lovac-home/karte-lovista-hrvatske.html";

        Intent intent = new Intent(getActivity(),NewsWebView.class);
        intent.putExtra("URL",url);
        startActivity(intent);

        return view;
    }

}
