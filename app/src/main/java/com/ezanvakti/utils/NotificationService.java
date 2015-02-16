package com.ezanvakti.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.ezanvakti.MainActivity;
import com.ezanvakti.R;
import com.ezanvakti.db.model.Vakit;

import java.util.Calendar;
import java.util.Date;

public class NotificationService extends Service {
    private Vakit mVakit;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Intent alarmMsgIntent;
    private static final int NOTIFICATION_ID = 430;
    public static final String NOTIFY_VAKIT = "notify_vakit";
    private Date lastNotificationTime = new Date();

    // TODO: Use shared prefs and customzie each tim's notification time
    private static int NOTIFY_BEFORE = 5; // min

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mVakit = DBUtils.getTodaysVakit();
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmMsgIntent = new Intent(this, NotificationService.class);
        alarmMsgIntent.setAction(NOTIFY_VAKIT);
        setNextAlarm();
    }

    public void setNextAlarm() {
        setNextAlarm(VakitUtils.getNextVakit(mVakit));
    }

    public void setNextAlarm(Date nextVakit) {
        if(nextVakit != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(nextVakit);
            c.add(Calendar.MINUTE, -NOTIFY_BEFORE);


            alarmIntent = PendingIntent.getService(this,0,alarmMsgIntent,0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);
            } else {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);
            }
            Log.i("NotificationService","setNextAlarm. Alarm set: "+VakitUtils.HHMM.format(c.getTime()));
        }
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if(intent != null && NOTIFY_VAKIT.equals(intent.getAction())) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE,NOTIFY_BEFORE);
            if(c.getTime().before(lastNotificationTime)) // avoid flood
                notifyNextVakit();
            setNextAlarm(VakitUtils.getNextVakit(VakitUtils.getNextVakit(mVakit),mVakit));
        } else {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setNextAlarm();
                }
            }).start();
        }


        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void notifyNextVakit() {
        String label = getResources().getString(mVakit.getVakitStringResource(VakitUtils.getNextVakitIndex(mVakit)));
        String time = VakitUtils.HHMM.format(VakitUtils.getNextVakit(mVakit));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(label)
                        .setContentText(time)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
