package hr.foi.air.crvenkappica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by domagoj on 04.12.15..
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
        View view = inflater.inflate(R.layout.fragment_navigacija,container,false);
        Log.e("TUSAM", "TESTFRAGENT");
=======
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
>>>>>>> origin/master
        return view;
    }
}
