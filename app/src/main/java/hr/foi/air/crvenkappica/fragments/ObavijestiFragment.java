package hr.foi.air.crvenkappica.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hr.foi.air.crvenkappica.images.ImageItem;
import hr.foi.air.crvenkappica.news.NewsAsyncTask;
import hr.foi.air.crvenkappica.news.NewsAdapter;
import hr.foi.air.crvenkappica.news.NewsOnClickListener;
import hr.foi.air.crvenkappica.news.NewsItem;
import hr.foi.air.crvenkappica.OnTaskCompleted;
import hr.foi.air.crvenkappica.R;
import hr.foi.air.crvenkappica.news.NewsWebView;


/**
 * Created by domagoj on 04.12.15..
 */

/**
 * Fragment za prikaz obavijesti o lovu.
 */
public class ObavijestiFragment extends Fragment implements OnTaskCompleted {

    private ArrayList<NewsItem> obavijestiList;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NewsAdapter adapter;
    private NewsItem obavijestiItem;

    /**
     * Poziva se pri kreiranju fragmentu.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.obavijestiList = new ArrayList<NewsItem>();
    }

    /**
     * Kreira te vraća view pripadnog fragmenta.
     * Kreira se i postavlja LinearLayoutManager za pozicioniranje itema unutar RecyclerView-a.
     * Poziva se aynctask za dohvat podataka za prikaz u obavijestima.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.obavijesti, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_obavijesti);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new NewsAsyncTask(getActivity(),this).execute();
        return view;
    }

    /**
     * Implementacija interface-a.
     * Podaci dobiveni od strane asynctask-a (lista) spremaju se kao pojedini objekti
     * klase NewsItem, pojedini objekti pohranjuju se u listu.
     * Naposljetku, kada je lista puna, postavlja se adapter za RecyclerView.
     */
    public void onTaskCompleted(final ArrayList<String> list) {
        for (int i = 0; i <= 5; i++) {
            obavijestiItem = new NewsItem();
            obavijestiItem.setDescription(list.get(i));
            obavijestiItem.setThumbnail(list.get(i + 6));
            obavijestiItem.setLink(list.get(i+12));
            obavijestiList.add(obavijestiItem);
        }
        adapter = new NewsAdapter(obavijestiList,getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskCompleted2(ArrayList<ImageItem> result) {

    }

    /**
     * Nakon što je aktivnost kreirana i svi podaci su prikazani, implementiran je listener
     * za dodir na pojedini item RecyclerView-a.
     * Dodirom na item otvara se WebView koji sadrži potpunu vijest.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView)recyclerView.findViewById(R.id.recycler_obavijesti);
        recyclerView.addOnItemTouchListener
                (new NewsOnClickListener(getActivity(), new NewsOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        obavijestiItem = obavijestiList.get(position);
                        String url = obavijestiItem.getLink();

                        Intent intent = new Intent(getActivity(),NewsWebView.class);
                        intent.putExtra("URL",url);
                        startActivity(intent);
                    }
                }));
    }
}