package rffsystems.es.pillreminder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class AlarmScreen extends Activity {
    public final String TAG = this.getClass().getSimpleName();

    public String MESSAGE_PILL_DETAILS_NAME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_NAME";
    public String MESSAGE_PILL_DETAILS_TIME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_TIME";
    public String MESSAGE_PILL_DETAILS_DOSIS = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_DOSIS";
    public String MESSAGE_PILL_DETAILS_ID = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_ID";

    private PowerManager.WakeLock mWakeLock;
    
    // an alarm can use a ringtone. 
    // to play a rintone mplayer may be used.
    //private MediaPlayer mPlayer;      


    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setup layout
        setContentView(R.layout.activity_alarm_screen);
        
        //get values from pending intent from alarm
        String pill_name = getIntent().getStringExtra(MESSAGE_PILL_DETAILS_NAME);
        String pill_time = getIntent().getStringExtra(MESSAGE_PILL_DETAILS_TIME);
        
        //set pill values into screen
        TextView tvPillName = (TextView) findViewById(R.id.alarm_screen_name);
        TextView tvPillTime = (TextView) findViewById(R.id.alarm_screen_time);
        
        tvPillName.setText(pill_name);
        tvPillTime.setText(pill_time);

        Button takeButton = (Button) findViewById(R.id.alarm_screen_button_take);
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPlayer.stop();
                finish();
            }

        });

        //Ensure wakelock release
        Runnable releaseWakelock = new Runnable() {


            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };


        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
        
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();


        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }


        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }


    }


    @Override
    protected void onPause() {
        super.onPause();


        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
