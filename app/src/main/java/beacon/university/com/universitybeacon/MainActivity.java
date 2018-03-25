package beacon.university.com.universitybeacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //setting proximity manager : Keeps looking for beacon
    private ProximityManager proximityManager;
    TextView tvBeaconCount;
    Button btSearch;
    ArrayList<String> list_of_beacons;
    int beacon_img;
    ListView lvBeaconList;
    Context context = this;
    String API_KEY="INSERT_API_KEY_HERE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_of_beacons = new ArrayList<String>();
//        list_of_beacons.add("one");
//        list_of_beacons.add("two");
//        list_of_beacons.add("three");
        beacon_img = R.drawable.beacon_notif_img;
        tvBeaconCount = (TextView) findViewById(R.id.tvBeaconCount);
        tvBeaconCount.setText(Integer.toString(list_of_beacons.size()));
        lvBeaconList = (ListView) findViewById(R.id.lvBeaconList);
        btSearch = (Button) findViewById(R.id.btSearch);
        //initializing sdk using API key
        KontaktSDK.initialize(API_KEY);
        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
        proximityManager.setEddystoneListener(createEddystoneListener());



    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
        Log.i("Sample", "Scanning start");
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
                Log.e("Sample", "Eddystone discovered: " + eddystone.toString());
                Log.e("Sample", "namespace discovered: " + namespace.toString());
                generate_notification();
                list_of_beacons.add(eddystone.getUniqueId());
                lvBeaconList.setAdapter(new CustomAdapterBeaconList(context,list_of_beacons,beacon_img));

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
