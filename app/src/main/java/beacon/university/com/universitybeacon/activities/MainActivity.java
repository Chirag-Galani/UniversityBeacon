package beacon.university.com.universitybeacon.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.SpaceListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.ArrayList;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.adapter.BeaconListAdapter;

public class MainActivity extends AppCompatActivity {

    //setting proximity manager : Keeps looking for beacon
    private ProximityManager proximityManager;
    private static TextView tvBeaconCount;
    private static int att_img = R.drawable.attendance_icon;
    private static int dis_img = R.drawable.discount_icon;
    private static int info_img = R.drawable.information_icon;
    ArrayList<String> list_of_beacons;
    //unique beacon ids
    ArrayList<String> list_of_beacons_ids;
    ArrayList<Integer> beacon_img;
    ArrayList<String> beacon_urls;
    BeaconListAdapter beaconListAdapter;
    ListView lvBeaconList;
    Context context = this;
    ProgressDialog progress;
    String API_KEY="HfGOIwmtzGgivhSQqrcOdbocfxUwfiNJ";
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_of_beacons = new ArrayList<String>();
        list_of_beacons_ids = new ArrayList<String>();
        beacon_urls = new ArrayList<String>();
        beacon_img = new ArrayList<Integer>();
        tvBeaconCount = findViewById(R.id.tvBeaconCount);
        tvBeaconCount.setText(Integer.toString(list_of_beacons.size()));
        lvBeaconList = findViewById(R.id.lvBeaconList);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        //setup for loading screen
        progress = new ProgressDialog(this);
        progress.setTitle("Hold on !");
        progress.setMessage("Searching for Beacons nearby...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //initializing SDK using API key
        lvBeaconList = findViewById(R.id.lvBeaconList);
        //initializing sdk using API key
        KontaktSDK.initialize(API_KEY);
        beaconListAdapter = new BeaconListAdapter(context, list_of_beacons, beacon_img, beacon_urls, userEmail);
        lvBeaconList.setAdapter(beaconListAdapter);
        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
        proximityManager.setEddystoneListener(createEddystoneListener());
        proximityManager.setSpaceListener(new SpaceListener() {
            @Override
            public void onRegionEntered(IBeaconRegion region) {

            }

            @Override
            public void onRegionAbandoned(IBeaconRegion region) {

            }

            @Override
            public void onNamespaceEntered(IEddystoneNamespace namespace) {

            }

            @Override
            public void onNamespaceAbandoned(IEddystoneNamespace namespace) {
                list_of_beacons_ids.clear();
                list_of_beacons.clear();
                beacon_urls.clear();
                beaconListAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                Log.e("Sample", "IBeacon discovered: " + ibeacon.toString());
            }
        };
    }

    private EddystoneListener createEddystoneListener() {
        return new SimpleEddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
//                Log.e("Sample", "Eddystone discovered: " + eddystone.toString());
//                Log.e("Sample", "namespace discovered: " + namespace.toString());
                //list_of_beacons.add(eddystone.getUniqueId());
                String beacon_name = eddystone.getName();
                String beacon_id = eddystone.getUniqueId();
                if (!list_of_beacons_ids.contains(beacon_id)){
                    generate_notification();
                    list_of_beacons_ids.add(beacon_id);
                    list_of_beacons.add(beacon_name);
                    beacon_urls.add(eddystone.getUrl());
                    String beacon_type = beacon_name.split(" ")[1];
                    switch (beacon_type){
                        case "ATT" :
                            beacon_img.add(att_img);
                            break;
                        case "INFO" :
                            beacon_img.add(info_img);
                            break;
                        case "CAFE":
                            beacon_img.add(dis_img);
                            break;
                    }
                    progress.dismiss();
                    beaconListAdapter.notifyDataSetChanged();
                    tvBeaconCount.setText(Integer.toString(list_of_beacons.size()));
                }
//                Log.e("Error","Error");
            }
        };
    }

    //function to setup notification
    public void generate_notification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "beacon_discovered_channel";
        //Android Oreo and above needs notification to be setup in a notification channnel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Beacon_Channel", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Notifications for discovered Beacons");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.beacon_notif_img)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Check Application")
                .setContentText("Beacon discovered nearby")
                .setContentInfo("Open Application for more details");
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
