package com.western.game.center.westerngamecenter.ActiveUsers;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Service.TimerService;
import com.western.game.center.westerngamecenter.Time.ExampleTimer;
import com.western.game.center.westerngamecenter.Time.Timer;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.List;

public class ActiveUsers_Recycler_Adapter extends RecyclerView.Adapter<ActiveUsers_Recycler_Adapter.Recycler_viewHolder> {



    List<ActiveUser> dataList ;
    public static final String TAG = "===>" ;
    LayoutInflater inflater ;
    private Context context  ;
    ActiveUser activeUser;
    int position ;
    View view1;

    Activity activity ;

    Recycler_viewHolder holder2 ;




    public ActiveUsers_Recycler_Adapter(List<ActiveUser> data , Context context  , View view1 , Activity activity ) {
        this.dataList = data;
        this.context = context;
        this.view1 = view1;
        this.activity = activity;

    }

    @Override
    public Recycler_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_user_recycler_content  , parent , false);
        Recycler_viewHolder recyclerView_holder = new Recycler_viewHolder(view , dataList ,context , view1 , activity );


        return recyclerView_holder ;
    }

    @Override
    public void onBindViewHolder(Recycler_viewHolder holder, int position) {

        this.position = position ;
        Recycler_viewHolder  holder1 = (Recycler_viewHolder) holder ;
        holder2 = holder1 ;
        activeUser = dataList.get(position);
        holder1.tx_Name.setText(String.valueOf(activeUser.NAME));
        holder1.tx_LastName.setText(String.valueOf(activeUser.LastName));
        holder1.tx_startTime.setText(String.valueOf(activeUser.startTime));
        holder1.tx_endTime.setText(String.valueOf(activeUser.endTime));
        holder1.tx_leftTime.setText(String.valueOf((long) activeUser.Remaining_Time /1000));
        set_tv_image(holder1 , activeUser.Tv_Num);
        Log.i(TAG, "onBindViewHolder: " + activeUser.NumJoyStick + " money : " + activeUser.Tv_Num);


        if (activeUser.isRunning){

            holder1.resume_flag = activeUser.isResuming ;
            holder1.pause_new_flag = activeUser.isPause ;
            if (activeUser.isResuming){
                holder1.start_continue(activeUser);
            }else if (activeUser.isPause){
                holder1.pause_continue(activeUser);
            }

        }


    }



    private void set_tv_image ( Recycler_viewHolder  holder   , int num){

        switch (num) {

            case 2 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_two);
                break;

            case 3 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_three);
                break;

            case 4 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_four);
                break;

            case 5 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_five);
                break;

            case 6 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_six);
                break;

            case 7 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_seven);
                break;

            case 8 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_eight);
                break;

            case 9 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_nine);
                break;

            case 10 :
                holder.tv_num_activated.setImageResource(R.drawable.ic_ten_green);
                break;
        }
    }

    public void delete(int position , ActiveUsers_Recycler_Adapter adapter){
        dataList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position , dataList.size());
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    public static class Recycler_viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tx_Name,tx_LastName , tx_leftTime , tx_startTime , tx_endTime   ;
        ProgressBar progressBar ;
        ImageView drop_down ;
        Button start_pause , stop ;
        ImageView delete  , tv_num_activated ;



        View view ;

        Snackbar mySnackbar ;

        Chronometer chronometer ;

        Context context ;

        long time ;

        List<ActiveUser> list ;

        ObjectAnimator animator ;

        Timer timer ;
        Timer timer1 []  ;
        ObjectAnimator animator1 [] ;
        int tag_id ;

        Activity activity ;

        TimerService timerService ;

        long fa ;

        boolean flag_dropdown = false  , start_flag = false  , pause_flag = false   , resume_flag = false , stop_flag = false   , pause_new_flag = false ;
        boolean chorno = false ;


        public Recycler_viewHolder(View itemView , final List<ActiveUser> list , Context context , View view , Activity activity ) {
            super(itemView);

            this.list = list ;
            this.context = context ;
            this.view = view ;
            this.activity = activity ;

            timer1 = new Timer[40] ;
            animator1 = new ObjectAnimator[40];
            tag_id = -1 ;

            timerService = new TimerService() ;

            tx_Name = (TextView) itemView.findViewById(R.id.name_active_user_recycler);
            tx_LastName = (TextView) itemView.findViewById(R.id.Last_name_active_user_recycler);
            tx_leftTime = (TextView) itemView.findViewById(R.id.leftTime);
            tx_startTime = (TextView) itemView.findViewById(R.id.startTime);
            tx_endTime = (TextView) itemView.findViewById(R.id.endTime);

            chronometer = (Chronometer) itemView.findViewById(R.id.chorno_extra);
            chronometer.setBase(SystemClock.elapsedRealtime());



            tv_num_activated = itemView.findViewById(R.id.tv_num_activated);

            progressBar = (ProgressBar) itemView.findViewById(R.id.active_user_progressBar);

            start_pause = (Button) itemView.findViewById(R.id.btn_start_pause_user);
            stop = itemView.findViewById(R.id.btn_stop_user);
            delete = (ImageView) itemView.findViewById(R.id.btn_delete_active_user);

            mySnackbar = Snackbar.make(view ,"Task Deleted ..." , Snackbar.LENGTH_LONG );

            animator = ObjectAnimator.ofInt(progressBar , "progress", 0 , 100);



            stop.setOnClickListener(this);
            start_pause.setOnClickListener(this);
            delete.setOnClickListener(this);





        }



        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {


            switch (v.getId()) {

                case R.id.btn_delete_active_user :

                    if (start_flag){
                        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
                        builder.setTitle("Warning");
                        builder.setMessage(  "Are you Sure To Stop And Delete This User ??");
                        builder.setNegativeButton("Cancel" , null);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBase_Operation db = App.getDataBaseOperation();
                                list.get(getLayoutPosition()).isRunning = false ;
                                db.Update_Active_User(list.get(getLayoutPosition()) , 2);
                                db.Delete_ActiveUser(list.get(getLayoutPosition()));
                                if (db.Show_Active_user().size() == 0){
                                    Intent service = new Intent(context , TimerService.class);
                                    context.stopService(service);
                                }

                                mySnackbar.setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "undo ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mySnackbar.show();
                            }
                        });
                        builder.show();
                    }else {

                        mySnackbar.setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "undo ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        mySnackbar.show();
                        DataBase_Operation db = App.getDataBaseOperation();
                        list.get(getLayoutPosition()).isRunning = false ;
                        db.Update_Active_User(list.get(getLayoutPosition()) , 2);
                        db.Delete_ActiveUser(list.get(getLayoutPosition()));
                        if (db.Show_Active_user().size() == 0){
                            Intent service = new Intent(context , TimerService.class);
                            context.stopService(service);
                        }



                    }


                    break;


                case R.id.btn_start_pause_user:

                    if (start_flag){

                        Log.i(TAG, "onClick:  start flag ");
                        pause_flag = true ;
                        start_flag = false ;
                        pause();

                    }else if (pause_flag){

                        Log.i(TAG, "onClick:  pause flag ");

                        pause_flag = false ;
                        resume_flag = true ;
                        resume();



                    }else if (resume_flag){

                        Log.i(TAG, "onClick:  resume flag ");

                        pause_flag = true ;
                        resume_flag = false ;
                        pause();

                    }else if (stop_flag){
                        Log.i(TAG, "onClick:  stop flag ");

                        Log.i(TAG, "onClick:  stop");

                    } else if (pause_new_flag){

                        Log.i(TAG, "onClick:  pause new flag ");

                        pause_flag = false ;
                        resume_flag = true ;
                        pause_new_flag = false ;
                        animator.start();
                        timer1[tag_id].start();
                        resume();


                    } else {

                        Log.i(TAG, "onClick:  else ");

                        start_flag = true ;
                        start_begin();

                    }

                    break;

                case R.id.btn_stop_user :

                    if (!chorno){
                        chronometer.start();
                        chorno = true ;

                    }else {
                        chronometer.stop();
                        chorno = false ;
                    }
                    break;

            }
        }

        private void start_begin (){

            start_pause.setText("pause");
            start_pause.setTextColor(Color.parseColor("#d50000"));

            tag_id ++ ;

            time = list.get(getLayoutPosition()).Remaining_Time;


            Intent service = new Intent(context , TimerService.class);
            service.putExtra("id" , list.get(getLayoutPosition()).Username_id) ;
            service.putExtra("mode" , 0 ) ;
            context.startService(service);

            animator.setDuration(time);
            animator.setCurrentPlayTime(list.get(getLayoutPosition()).Elapsed_time);
            animator.setInterpolator(new LinearInterpolator());

            timer1[tag_id] =  new ExampleTimer(1000 , time) {
                long leftTime = time /1000 ;
                @Override
                protected void onTick() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText(String.valueOf(leftTime--));
                        }
                    });
                }

                @Override
                protected void onFinish() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText("Finished ...");
                        }
                    });
                }
            };


            animator.start();
            timer1[tag_id].start();
        }

        private void start_continue (ActiveUser activeUser1){

            start_pause.setText("pause");
            start_pause.setTextColor(Color.parseColor("#d50000"));

            tag_id ++ ;

            time = activeUser1.Remaining_Time;



            animator.setDuration(time);
            animator.setCurrentPlayTime(activeUser1.Elapsed_time);
            animator.setInterpolator(new LinearInterpolator());

            timer1[tag_id] =  new ExampleTimer(1000 , time) {
                long leftTime = time /1000 ;
                @Override
                protected void onTick() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText(String.valueOf(leftTime--));
                        }
                    });
                }

                @Override
                protected void onFinish() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText("Finished ...");
                        }
                    });
                }
            };


            animator.start();
            timer1[tag_id].start();

        }

        private void pause_continue (ActiveUser activeUser1){

            start_pause.setText("resume");
            start_pause.setTextColor(Color.parseColor("#64dd17"));
            stop.setVisibility(View.VISIBLE);
            tag_id ++ ;

            time = activeUser1.Remaining_Time;



            animator.setDuration(time);
            animator.setCurrentPlayTime(activeUser1.Elapsed_time);
            animator.setInterpolator(new LinearInterpolator());

            timer1[tag_id] =  new ExampleTimer(1000 , time) {
                long leftTime = time /1000 ;
                @Override
                protected void onTick() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText(String.valueOf(leftTime--));
                        }
                    });
                }

                @Override
                protected void onFinish() {
                    tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            tx_leftTime.setText("Finished ...");
                        }
                    });
                }
            };
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void pause(){
            start_pause.setText("resume");
            start_pause.setTextColor(Color.parseColor("#64dd17"));
            stop.setVisibility(View.VISIBLE);

            Intent service = new Intent(context , TimerService.class);
            service.putExtra("mode" , 1) ;
            Log.i(TAG, "pause: " + list.get(getLayoutPosition()).Tag_Num);
            Log.i(TAG, "pause: " + list.get(getLayoutPosition()).NAME);
            service.putExtra("id" , list.get(getLayoutPosition()).Username_id) ;
            context.startService(service);

            animator.pause();
            timer1[tag_id].pause();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void resume(){
            start_pause.setText("pause");
            start_pause.setTextColor(Color.parseColor("#d50000"));
            stop.setVisibility(View.INVISIBLE);

            Intent service = new Intent(context , TimerService.class);
            service.putExtra("mode" , 2) ;
            service.putExtra("id" , list.get(getLayoutPosition()).Username_id) ;
            context.startService(service);

            animator.resume();
            timer1[tag_id].resume();
        }

        private void stop (){}




    }







}
