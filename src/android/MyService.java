package cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.heywatchme.tackgps.R;

import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MyService extends Service {
    private FusedLocationProviderClient fusedLocationClient;
    public static final long INTERVAL=10000;//variable to execute services every 10 second
    private Handler mHandler=new Handler(); // run on another Thread to avoid crash
    private Timer mTimer=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.


        if(mTimer!=null)
            mTimer.cancel();
        else
            mTimer=new Timer(); // recreate new timer
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,INTERVAL);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast at every 10 second

                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                    String location= String.valueOf(fusedLocationClient.getLastLocation());
                    Toast.makeText(getApplicationContext(), location, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
