package hr.foi.air.crvenkappica;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * Created by domagoj on 04.12.15..
 */
public class ObavijestiFragment extends Fragment implements OnTaskCompleted {

    private ArrayList<Obavijesti_item> obavijestiList;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ObavijestiAdapter adapter;
    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.obavijestiList = new ArrayList<Obavijesti_item>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.obavijesti, container, false);
        this.view = view;
        new NewsFeed(getActivity(),this).execute();
        return view;
    }

    public void onTaskCompleted(final ArrayList<String> list, final Context context) {
        Obavijesti_item obavijestiItem;

        ArrayList<Obavijesti_item> arrayList = new ArrayList<Obavijesti_item>();

        for (int i = 0; i <= 5; i++) {
            obavijestiItem = new Obavijesti_item();
            obavijestiItem.setDescription(list.get(i));
            obavijestiItem.setThumbnail(list.get(i + 6));
            arrayList.add(obavijestiItem);
        }
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_obavijesti);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ObavijestiAdapter obavijestiAdapter = new ObavijestiAdapter(arrayList,context);
        recyclerView.setAdapter(obavijestiAdapter);
        obavijestiAdapter.notifyDataSetChanged();
        recyclerView.refreshDrawableState();
    }
}