package com.toiler.enigmasystems.mapMarkerAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.toiler.enigmasystems.R;
import com.toiler.enigmasystems.markerInfoModel.InfoWindowData;

/**
 * Created by Arm_AVI on 8/30/2018.
 */

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.map_custom_infowindow, null);

        LatLng latLng = arg0.getPosition();
        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        tvLat.setText("Latitude:" + latLng.latitude);
        tvLng.setText("Longitude:"+ latLng.longitude);
        return v;
    }
}
