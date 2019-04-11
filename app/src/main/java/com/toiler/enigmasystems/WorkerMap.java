package com.toiler.enigmasystems;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.toiler.enigmasystems.workermodel.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WorkerMap extends FragmentActivity implements OnMapReadyCallback {

    HashMap<String, HashMap> extraMarkerInfo = new HashMap<String, HashMap>();
    private GoogleMap mMap;
    public static final String KEY_PHONE = "phone";

//    String url_get_info = "http://www.armapprise.com/worker_api/meson.php";
      String url_get_info;

//    String url_show_marker_info = "http://www.armapprise.com/map_marker_info.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                addMarker(place);
            }

            @Override
            public void onError(Status status) {

            }
        });

        url_get_info = getIntent().getExtras().getString("mapUrl");

    }
     /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Current Location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }


        //Dev Location
        BitmapDrawable bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.dot_location);
        Bitmap bDev = bitmapdraw.getBitmap();
        Bitmap placeMarkerImage = Bitmap.createScaledBitmap(bDev, 60, 60, false);

        //Worker Location
        BitmapDrawable bitmapdrawWorker = (BitmapDrawable)getResources().getDrawable(R.drawable.worker_marker);
        Bitmap bWorker = bitmapdrawWorker.getBitmap();
        final Bitmap workerMarkerMapMarker = Bitmap.createScaledBitmap(bWorker, 150, 150, false);

        // Add a marker in Developer and move the camera
        LatLng wari = new LatLng(23.7174, 90.4178);
        mMap.addMarker(new MarkerOptions().position(wari).icon(BitmapDescriptorFactory.fromBitmap(placeMarkerImage)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wari));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        StringRequest stringRequest = new StringRequest(url_get_info, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        final String name = jsonObject.getString("name");
                        String designation = jsonObject.getString("designation");
                        String area = jsonObject.getString("area_name");
                        String city = jsonObject.getString("city");
                        final String phone = jsonObject.getString("phone");
                        String experience = jsonObject.getString("experience");
                        String status = jsonObject.getString("status");
                        String lati = jsonObject.getString("latitude");
                        String longi = jsonObject.getString("longitude");

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(lati), Double.parseDouble(longi)))
                                .title( name + "\n"+ getResources().getString(R.string.designation_map_info)+" "+ designation + "\n" + getResources().getString(R.string.area_map_info)+" "+ area + "\n" +getResources().getString(R.string.city_map_info)+" "+ city+ "\n" +getResources().getString(R.string.working_experience_map_info)+" "+ experience +"\n"+status)
                                .icon(BitmapDescriptorFactory.fromBitmap(workerMarkerMapMarker))
                                .snippet(phone));

                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker arg0) {

                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {

                                Context mContext =  getApplicationContext();
                                LinearLayout info = new LinearLayout(mContext);
                                info.setOrientation(LinearLayout.VERTICAL);

                                TextView title = new TextView(mContext);
                                title.setTextColor(Color.BLACK);
                                title.setGravity(Gravity.CENTER);
                                title.setTypeface(null, Typeface.BOLD);
                                title.setText(marker.getTitle());

                                TextView snippet = new TextView(mContext);
                                snippet.setTextColor(Color.GRAY);


                                snippet.setText(marker.getSnippet());

                                info.addView(title);
                                info.addView(snippet);

                                return info;
                            }
                        });


                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(final Marker marker) {
                                String details = marker.getTitle();
                                final String phone = marker.getSnippet();

                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(WorkerMap.this);
                                View mView = getLayoutInflater().inflate(R.layout.worker_info_map_marker, null);

                                final TextView phoneWorkerMap, nameWorkerMap;
                                phoneWorkerMap = (TextView) mView.findViewById(R.id.phone_map_warker_info);
                                nameWorkerMap = (TextView) mView.findViewById(R.id.name_map_warker_info);

                                phoneWorkerMap.setText(phone);
                                nameWorkerMap.setText(details);

                                FloatingActionButton phoneCall = (FloatingActionButton) mView.findViewById(R.id.phone_call_map_warker_info);
                                FloatingActionButton messageBtn = (FloatingActionButton) mView.findViewById(R.id.message_map_warker_info);

                                mBuilder.setView(mView);
                                final AlertDialog dialog = mBuilder.create();
                                dialog.show();
                                phoneCall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
//                                        Toast.makeText(getApplicationContext(),phoneWorkerMap.getText().toString(),Toast.LENGTH_SHORT).show();
                                        String validNumber = "^[+]?[0-9]{8,15}$";
                                        String number = phoneWorkerMap.getText().toString();
                                        if (number.matches(validNumber)) {
                                            callIntenStart("tel:"+number);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect Phone Number !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                messageBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String validNumber = "^[+]?[0-9]{8,15}$";
                                        String number = phoneWorkerMap.getText().toString();
                                        if (number.matches(validNumber)) {
                                            String mblNumVar = phoneWorkerMap.getText().toString();
                                            Intent smsMsgAppVar = new Intent(Intent.ACTION_VIEW);
                                            smsMsgAppVar.setData(Uri.parse("sms:" +  mblNumVar));
                                            smsMsgAppVar.putExtra("sms_body", getResources().getString(R.string.user_message));
                                            startActivity(smsMsgAppVar);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Incorrect Phone Number !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                return false;
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Anything you want
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        mMap.setMyLocationEnabled(true);

    }

//    public void worker_info_dialog(final String worker_map_phone, String worker_map_name){
//        LayoutInflater layoutInflater = LayoutInflater.from(WorkerMap.this);
//        View promptView = layoutInflater.inflate(R.layout.worker_info_map_marker, null);
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerMap.this);
//        alertDialogBuilder.setView(promptView);
//
////        final EditText tempPasswordTaker = (EditText) promptView.findViewById(R.id.tempPasswordTaker);
//        final TextView phoneWorkerMap, nameWorkerMap;
//
//        phoneWorkerMap = (TextView) promptView.findViewById(R.id.phone_map_warker_info);
//        nameWorkerMap = (TextView) promptView.findViewById(R.id.name_map_warker_info);
//        FloatingActionButton callBtn = (FloatingActionButton) findViewById(R.id.phone_call_map_warker_info);
//
//        phoneWorkerMap.setText(worker_map_phone);
//        nameWorkerMap.setText(worker_map_name);
//
//        callBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),worker_map_phone,Toast.LENGTH_SHORT).show();
//                String validNumber = "^[+]?[0-9]{8,15}$";
//                String number = phoneWorkerMap.getText().toString();
//                if (number.matches(validNumber)) {
//                    callIntenStart("tel:"+number);
//                } else {
//                    Toast.makeText(getApplicationContext(), "no phone number available", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        alertDialogBuilder.setCancelable(false)
//                .setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                })
//                .setNegativeButton(getResources().getString(R.string.cancel_btn_text),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        // create an alert dialog
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }

    void callIntenStart(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void addMarker(Place p){

        BitmapDrawable bitmapdraw =(BitmapDrawable)getResources().getDrawable(R.drawable.dot_location);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap placeMarkerImage = Bitmap.createScaledBitmap(b, 60, 60, false);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(p.getLatLng());
        markerOptions.title(p.getName()+"");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(placeMarkerImage));

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

    }

}
