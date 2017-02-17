package com.aisautocare.aismechanics.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aisautocare.aismechanics.GlobalVar;
import com.aisautocare.aismechanics.R;
import com.aisautocare.aismechanics.model.Order;
import com.aisautocare.aismechanics.model.POSTResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OrderActivity.POSTOrder().execute("");
            }
        });

    }

    private String URLOrder = new GlobalVar().hostAPI + "/order";

    public class POSTOrder extends AsyncTask<String, Void, List<POSTResponse>> {

        private final String LOG_TAG = "asd";

        private List<POSTResponse> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject movieJson = new JSONObject(jsonStr);
            //JSONArray movieArray = movieJson.getJSONArray();
//            System.out.println("movie json : " + movieJson  );
//            System.out.println("itemsarray : " + movieArray  );
            // System.out.println(" Data JSON Items" + jsonStr);
            List<POSTResponse> results = new ArrayList<>();
            JSONObject berita = movieJson;
            POSTResponse beritaModel = new POSTResponse(berita);
            results.add(beritaModel);
            return results;
        }

        @Override
        protected List<POSTResponse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {

                Order order = new Order();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                order.setCustomer_id("2");
                order.setOrder_date(dateFormat.format(date));
                order.setOrder_time(timeFormat.format(date));
                order.setService_date("2003-02-01");
                order.setService_time("00:00:00");
                order.setService_location("123456");
                order.setLatitude("123456");
                order.setLongitude("123456");
                order.setArea_id("14");
                order.setIs_emergency("false");
                order.setLicense_plate("AB100CA");
                order.setRef_service_id("1");
                order.setStatus("1");
                order.setMethod("3");
                order.setPayment_status("1");
                Uri builtUri = Uri.parse(URLOrder).buildUpon()
                        .appendQueryParameter("customer_id", order.getCustomer_id())
                        .appendQueryParameter("order_date", order.getOrder_date())
                        .appendQueryParameter("order_time", order.getOrder_time())
                        .appendQueryParameter("service_date", order.getService_date())
                        .appendQueryParameter("service_time", order.getService_time())
                        .appendQueryParameter("service_location", order.getService_location())
                        .appendQueryParameter("latitude", order.getLatitude())
                        .appendQueryParameter("longitude", order.getLongitude())
                        .appendQueryParameter("area_id", order.getArea_id())
                        .appendQueryParameter("is_emergency", order.getIs_emergency())
                        .appendQueryParameter("license_plate", order.getLicense_plate())
                        .appendQueryParameter("ref_service_id", order.getRef_service_id())
                        .appendQueryParameter("status", order.getStatus())
                        .appendQueryParameter("method", order.getMethod())
                        .appendQueryParameter("payment_status", order.getPayment_status())
                        .build();

                URL url = new URL(builtUri.toString());


                //URL url = new URL(URLServiceRepair );

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getRepairDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<POSTResponse> responses) {

            if (responses != null) {
                //repairs.clear();
                //repairs.addAll(services);
                System.out.println("responses ketika set adapter : " + responses.toString());
                if (Integer.valueOf(responses.get(0).getApi_status()) == 1) {
                    finish();
//                    Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getApplicationContext().startActivity(intent);
                } else {
                    Log.e("AIS", "Error POST Order");
                    Log.e("AIS", "API Message : " + responses.get(0).getApi_message().toString());
                }
                //rcAdapter = new RecyclerViewAdapterBerita(getActivity(), movies);
                //adapter = new ServiceRecyclerViewAdapter();

                //rcAdapter.notifyDataSetChanged();

                //recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //swipeContainer.setRefreshing(false);

                //adapter.setLoaded();

                //pageBerita++;
            }
        }
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
