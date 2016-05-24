package com.example.gerard.directionsgooglemaps;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Models.Route;
import Utilities.Directions;
import Utilities.MapsActivityInterface;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsActivityInterface {

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText originAddressInput;
    private EditText destinationAddressInput;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Starts map service
        mapFragment.getMapAsync(this);

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        originAddressInput = (EditText) findViewById(R.id.etOrigin);
        destinationAddressInput = (EditText) findViewById(R.id.etDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerDirectionRequest();
            }
        });
    }

    private void triggerDirectionRequest() {
        String origin = originAddressInput.getText().toString();
        String destination = destinationAddressInput.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new Directions(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in BCIT and move the camera
//        LatLng bcit = new LatLng(49.2485, -123.0014);
//        LatLng ubc = new LatLng(49.2606, -123.2460);
//        LatLng sfu = new LatLng(49.2769, -122.9148);
//        LatLng kwantlen = new LatLng(49.133, -122.8711);
//
//
//
//        mMap.addMarker(new MarkerOptions()
//                .position(bcit)
//                .title("Marker in BCIT"));
//                //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boy));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(bcit)
//                .title("Marker in UBC"));
//                //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mediumgirl));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(bcit)
//                .title("Marker in SFU"));
//                //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ponygirl));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(bcit)
//                .title("Marker in Kwantlen"));
//                //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.orangeboy));
//
//        mMap.addPolyline(new PolylineOptions().add(
//                    bcit,
//                    ubc,
//                    sfu,
//                    kwantlen)
//                .width(10)
//                .color(Color.RED)
//        );
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bcit, 18));
//        //mMap.setMapType(GoogleMap.INSERT_TYPE_HERE), MAP_TYPE_NORMAL, MAP_TYPE_SATELLIE, MAP_TYPE_TERRAIN
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bcit = new LatLng(49.2485, -123.0014);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bcit, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("BCIT")
                .position(bcit)));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void clearPrevious() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void addNewRoutes(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.getDurationText());
            ((TextView) findViewById(R.id.tvDistance)).setText(route.getDistanceText());

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
