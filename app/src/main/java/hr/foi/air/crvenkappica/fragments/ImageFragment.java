package hr.foi.air.crvenkappica.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hr.foi.air.crvenkappica.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.image_viewer,container,false);
        Bundle data = getArguments();
        String title = data.getString("title");
        Bitmap bitmap = data.getParcelable("image");
        TextView titleText = (TextView) view.findViewById(R.id.title);
        titleText.setText(title);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
        return view;
    }

}
