package hr.foi.air.crvenkappica.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.osmdroid.api.IMapController;
import org.osmdroid.api.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


import hr.foi.air.crvenkappica.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AirMapViewFragment extends Fragment {

    public AirMapViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_air_map_view, container, false);
        MapView map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        org.osmdroid.bonuspack.overlays.Marker startMarker = new org.osmdroid.bonuspack.overlays.Marker(map);
        IMapController mapController = map.getController();
        mapController.setZoom(14);
        GeoPoint startPoint = new GeoPoint(46.307727, 16.338072);
        mapController.setCenter(startPoint);


        startMarker.setPosition(startPoint);
        startMarker.setAnchor(org.osmdroid.bonuspack.overlays.Marker.ANCHOR_CENTER, org.osmdroid.bonuspack.overlays.Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        map.invalidate();


        return view;
    }

}
