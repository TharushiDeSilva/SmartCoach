package com.example.tharushidesilva.smartcoach;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        MainActivity mainActivity = (MainActivity)context;
        if(mainActivity.curr_step_cnt < mainActivity.rec_step_cnt){
            EmailSender emailSender = new EmailSender();
            emailSender.sendMail("tharushid.14@cse.mrt.ac.lk",
                    "Below recommended step count!","User "+mainActivity.userName+
                            "has not achieved the recommended step count! "+"Current step count:"
                            +mainActivity.curr_step_cnt,mainActivity);
        }

        wl.release();
    }

//    public void setAlarm(Context context)
//    {
//        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(context, Alarm.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
//    }
//
//    public void cancelAlarm(Context context)
//    {
//        Intent intent = new Intent(context, Alarm.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
//    }
}
