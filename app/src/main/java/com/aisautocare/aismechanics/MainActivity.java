package com.aisautocare.aismechanics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aisautocare.aismechanics.activity.HistoryActivity;
import com.aisautocare.aismechanics.activity.LoginActivity;
import com.aisautocare.aismechanics.activity.OrderActivity;
import com.aisautocare.aismechanics.activity.ProfileActivity;
import com.aisautocare.aismechanics.activity.RegisterActivity;
import com.aisautocare.aismechanics.app.Config;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView status, workshopName, balance, topUp;
    private Switch statusSwitch;
    private CircleImageView workshopImage;
    private RatingBar averageRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // remove title
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Set Navigation Drawer */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navItemSelect);

        status = (TextView) findViewById(R.id.status_text_view);
        statusSwitch = (Switch) findViewById(R.id.status_switch);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
//                startActivity(intent);
//            }
//        }, 5000);

        workshopImage = (CircleImageView) findViewById(R.id.workshop_image);
        workshopName = (TextView) findViewById(R.id.workshop_name_text_view);
        averageRate = (RatingBar) findViewById(R.id.workshop_average_rating);
        balance = (TextView) findViewById(R.id.workshop_balance_text_view);
        topUp = (TextView) findViewById(R.id.top_up_button);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("ais", "Firebase reg id: " + regId);

        //if (!TextUtils.isEmpty(regId));
        //txtRegId.setText("Firebase Reg Id: " + regId);
        //else
        //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {

        Intent intent;

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setCheckable(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (menuItem.getItemId()){
                case R.id.nav_profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_history:
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_login:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_register:
                    intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return true;
            }
        }
    };
}
