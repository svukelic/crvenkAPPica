package hr.foi.air.crvenkappica.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


import hr.foi.air.crvenkappica.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OSMFragment extends Fragment {

    public OSMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_air_map_view, container, false);
        MapView map = (MapView) view.findViewById(R.id.map);
        GeoPoint startPoint = new GeoPoint(46.310834, 16.089587);
        GeoPoint point2 = new GeoPoint(46.336756, 16.147521);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(14);
        mapController.setCenter(startPoint);
        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Lovište Voća");
        startMarker.setSubDescription("Površina: 3886 ha, zajedničko lovište.");
        map.getOverlays().add(startMarker);
        Marker marker2 = new Marker(map);
        marker2.setPosition(point2);
        marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker2.setTitle("Lovište Vinica");
        marker2.setSubDescription("Površina: 2663 ha, zajedničko lovište.");
        map.getOverlays().add(startMarker);
        map.getOverlays().add(marker2);
        map.invalidate();
        return view;
    }

}
