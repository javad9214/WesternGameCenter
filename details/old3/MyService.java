package com.project.avanikan_pc_003.taskslist.western;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.project.avanikan_pc_003.taskslist.DataBase.DataBaseOperation;

public class MyService extends Service {



    public static final String TAG = "===>" ;

    CountDownTimer timer [] ;

    long tick [] ;

    int i = 0 ;
    int k ;

    long left_time ;
    boolean flag = false ;

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

        timer = new CountDownTimer[10];
        tick = new long[10];
        tick[0] = 0 ;
        Log.i(TAG, "onCreate: ");

    }

    @Override
    public int onStartCommand(Intent intent,  int flags  , int startId) {

        Log.i(TAG, "onStartCommand: " + i);
        if (flag){

            for (int l = 0 ; l < i ; l++){
                timer[l].cancel();
                SendMessage();
                Log.i(TAG, "onStartCommand:  on  ticking " + tick[l]);
            }


            i++ ;
            flag = false ;
        }

        for (  k = 0 ; k <= i ; k++){

            left_time = 30000 - tick[k] ;
            timer[k] =
                    new CountDownTimer(left_time , 1000) {
                        @Override
                        public void onTick(long l) {
                            tick[k] = l ;
                            Log.i(TAG, "onTick: " + l);
                        }

                        @Override
                        public void onFinish() {

                            Log.i(TAG, "onFinish: " + left_time );
                        }
                    }.start();
        }

        flag = true ;


        return START_STICKY ;
    }

    private void SendMessage () {
        Intent intent = new Intent("onTime_Action");

        intent.putExtra("sample" , "first line");
        intent.putExtra("tick" , tick);
        for (int g = 0 ; g  < 10 ; g++){

            Log.i("===========", "SendMessage: " + tick[g]);
            intent.putExtra("onTick" , tick[g] );
        }
        LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroyed");
    }



}


