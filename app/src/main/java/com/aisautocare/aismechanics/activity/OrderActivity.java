package com.aisautocare.aismechanics.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aisautocare.aismechanics.GlobalVar;
import com.aisautocare.aismechanics.R;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private Button declineButton, acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.order_map_fragment);
        mapFragment.getMapAsync(this);

        declineButton = (Button) findViewById(R.id.order_decline_button);
        acceptButton = (Button) findViewById(R.id.order_accept_button);

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

        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng(-6.8890725, 107.6173804);
        LatLng end = new LatLng(-6.8983939, 107.6198499);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15.0f));
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.addMarker(new MarkerOptions().position(end).title("Lokasi Kendaraan anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));

        final Marker customerLoc = mMap.addMarker(new MarkerOptions().position(start).title("Lokasi Keberangkatan Montir"));
        GoogleDirection.withServerKey("AIzaSyBDv7B62-bLvjbdWZCXyIl4dxiLmSR4vB0")
                .from(start)
                .to(end)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            List<Step> stepList = leg.getStepList();

                            final ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(OrderActivity.this, directionPositionList, 5, Color.RED);
                            mMap.addPolyline(polylineOptions);
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(directionPositionList.get(0), 10));
                            Handler handler = new Handler();
                            Info distanceInfo = leg.getDistance();
                            final Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            final String duration = durationInfo.getValue();
                            GlobalVar.waktuTempuh = Integer.valueOf(durationInfo.getValue());
                            System.out.println("Jarak dan waktu " + distance + " " + duration);
//                            timer.addView(new CircularCountdown(TrackEmployeeActivity.this));
//                            arriveButton.setVisibility(View.VISIBLE);
                            animateMarker(mMap, customerLoc, directionPositionList, false, Integer.valueOf(durationInfo.getValue()));

                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    private static void animateMarker(GoogleMap myMap, final Marker marker, final List<LatLng> directionPoint,
                                      final boolean hideMarker, int duration) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = myMap.getProjection();
        //final long duration = 720000;
        duration = (duration) * 1000;

        final Interpolator interpolator = new LinearInterpolator();

        final int finalDuration = duration;
        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / finalDuration);
                if (i < directionPoint.size())
                    marker.setPosition(directionPoint.get(i));
                i++;

                handler.postDelayed(this, finalDuration / directionPoint.size());
//                if (t < 1.0) {
//                    // Post again 16ms later.
//
//                } else {
//                    handler.postDelayed(this,  (finalDuration *60)*1000/directionPoint.size());
//                    if (hideMarker) {
//                        marker.setVisible(false);
//                    } else {
//                        marker.setVisible(true);
//                    }
//                }
            }
        });
    }
}
