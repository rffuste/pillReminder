package rffsystems.es.pillreminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Rubén on 23/12/2014.
 */
public class AlarmManagerHelper extends BroadcastReceiver {

    public static String MESSAGE_PILL_DETAILS_NAME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_NAME";
    public static String MESSAGE_PILL_DETAILS_TIME = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_TIME";
    public static String MESSAGE_PILL_DETAILS_DOSIS = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_DOSIS";
    public static String MESSAGE_PILL_DETAILS_ID = "rffsystems.es.pillreminder.MESSAGE_PILL_DETAILS_ID";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context) {
        cancelAlarms(context);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

       DbManager dbHelper = new DbManager(context);
        
        List<Pill> alarms = dbHelper.getAllPills();
        
        for (Pill pill:alarms) {
            PendingIntent pIntent = createPendingIntent(context, pill);
            
            //get time and hour from pill
            String pill_time = pill.getPillTime();
            
            int selectedHour=Utils.getHour(pill_time);
            int selectedMin =Utils.getMinutes(pill_time);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,selectedHour);
            calendar.set(Calendar.MINUTE, selectedMin);
            calendar.set(Calendar.SECOND, 00);

            //24*60*60*1000 implica que l'alarma s'executa cada dia.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pIntent);
        }
        

    }



    private static PendingIntent createPendingIntent(Context context, Pill p) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(MESSAGE_PILL_DETAILS_ID, p.getId());
        intent.putExtra(MESSAGE_PILL_DETAILS_NAME, p.getPillName());
        intent.putExtra(MESSAGE_PILL_DETAILS_TIME, p.getPillTime());
        intent.putExtra(MESSAGE_PILL_DETAILS_DOSIS, p.getPillDosis());

        return PendingIntent.getService(context, (int) p.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelAlarms(Context context) {
        DbManager dbHelper = new DbManager(context);

        List<Pill> alarms =  dbHelper.getAllPills();

        if (alarms != null) {
            for (Pill alarm : alarms) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);

            }
        }
    }
}