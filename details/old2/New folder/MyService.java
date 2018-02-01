package com.project.avanikan_pc_003.taskslist.western;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    long time ;
    String name  , timer_name ;
    String [] names  ;
    CountDownTimer[] timers ;
    int i ;

    public static final String TAG = "===>" ;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        names = new String[50];
        timers = new CountDownTimer[50] ;

        Log.i(TAG, "onCreate: service");
    }

    @Override
    public int onStartCommand(Intent intent,  int flags  , int startId) {

        time = intent.getLongExtra("time" , 10000);
        i = intent.getIntExtra("i" , 0 );
        names[i] = intent.getStringExtra("name");

             timers[i]=   new CountDownTimer(time , 1000) {

                public void onTick(long millisUntilFinished) {

                    Log.i(TAG, "onTick: " + millisUntilFinished + names [i]);
                    Log.i(TAG, "onTick: " + names[i]);
                }

                public void onFinish() {
                    Toast.makeText(MyService.this, names[i] + "    " + "finished !!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG,  names[i] + String.valueOf(time) + "onFinish: ");

                }

            }.start();

        Log.i(TAG, "onStartCommand: "+ i);




        return START_STICKY ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroyed");
    }
}


