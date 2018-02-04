package com.western.game.center.westerngamecenter.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Time.ExampleTimer;
import com.western.game.center.westerngamecenter.Time.Timer;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

public class TimerService extends Service {


    public static final String TAG = "===/===>";

    private BroadcastReceiver receiver ;

    Timer timer []  ;
    ActiveUser   activeUser ;

    DataBase_Operation db ;

    int tag_id ;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer[40] ;
        tag_id = -1 ;
        db = App.getDataBaseOperation();

        Log.i(TAG, "onCreate: service");
        receive();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        switch (intent.getIntExtra("mode" , 0 )){

            case 0 : // start new one

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                start_new_active(activeUser);

                break;

            case 1 : // pause

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                onPause(activeUser.Tag_Num);
                break;

            case 2 : // resume

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                onResume(activeUser.Tag_Num);
                break;



        }






        return START_STICKY;
    }

    private void start_new_active (final ActiveUser activeUser){

        tag_id++ ;
        Log.i(TAG, "start_new_active: " + tag_id + " id : " + activeUser.Username_id + "  UID  : " + activeUser.active_UID);
        if (activeUser == null) {
            Log.i(TAG, "onStartCommand: nuuulllll");
            tag_id -- ;
        }else {

            activeUser.isRunning = true ;
            db.Update_Active_User(activeUser , 2);
            activeUser.Tag_Num = tag_id ;
            db.Update_Active_User(activeUser , 3);
            timer[tag_id] = new ExampleTimer(1000, activeUser.Remaining_Time*60*1000) {
                @Override
                protected void onTick() {
                    super.onTick();

                    Log.i(TAG, "onTick:  "  + tag_id + "  " + timer[tag_id].isRunning() + "   " + timer[tag_id].getElapsedTime() + "  " + timer[tag_id].getRemainingTime());

                }

                @Override
                protected void onFinish() {
                    super.onFinish();
                    Log.i(TAG, "onFinish: " );
                    activeUser.isRunning = false ;
                    db.Update_Active_User(activeUser , 2);

                }
            };
            timer[tag_id].start();

        }
    }

    public void onPause (int tagId){
        Log.i(TAG, "onPause:  "  + tag_id + "  " + timer[tag_id].isRunning() + "   " + timer[tag_id].getElapsedTime() + "  " + timer[tag_id].getRemainingTime());
            timer[tagId].pause();
        }

    public void onResume(int tagId){
        timer[tagId].resume();
    }

    private void receive (){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                    for (int j = 0; j <= tag_id ; j ++  ){
                         activeUser = new ActiveUser();
                         activeUser = db.Search_ActiveUser(j , 0);
                         activeUser.Remaining_Time = timer[j].getRemainingTime() ;
                        Log.i(TAG, "onReceive: " + timer[j].getRemainingTime());
                         db.Update_Active_User(activeUser , 0 ) ;
                         activeUser.Elapsed_time  = timer[j].getElapsedTime();
                         db.Update_Active_User(activeUser , 1) ;

                    }



            }
        };

        LocalBroadcastManager.getInstance(TimerService.this).registerReceiver(receiver , new IntentFilter("onTime_Action"));
    }


}
