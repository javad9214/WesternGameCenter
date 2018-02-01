package com.western.game.center.westerngamecenter.ActiveUsers;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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

public class ActiveUsers_Recycler_Adapter extends RecyclerView.Adapter<ActiveUsers_Recycler_Adapter.Recycler_viewHolder> implements View.OnClickListener {



    List<ActiveUser> dataList ;
    public static final String TAG = "===>" ;
    LayoutInflater inflater ;
    private Context context  ;
    ActiveUser user ;
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
        user = dataList.get(position);
        holder1.tx_Name.setText(String.valueOf(user.NAME));
        holder1.tx_LastName.setText(String.valueOf(user.LastName));
        holder1.tx_startTime.setText(String.valueOf(user.startTime));
        holder1.tx_endTime.setText(String.valueOf(user.endTime));
        holder1.tx_money.setText("Money : " + String.valueOf(user.money) + " T");
        holder1.tx_leftTime.setText(String.valueOf((long) user.Remaining_Time));
        holder1.tx_numJoystick.setText( "GamePads : " + String.valueOf(user.NumJoyStick));
        holder1.tx_money.setVisibility(View.GONE);
        holder1.tx_numJoystick.setVisibility(View.GONE);

        if (user.isRunning){

            holder1.start_pause.setText("stop");
            holder1.start_pause.setTextColor(Color.parseColor("#d50000"));
            holder1.start_flag = true ;

            holder1.time = user.Remaining_Time;

            holder1.animator.setDuration( holder1.time);
            Log.i(TAG, "onBindViewHolder: " + holder1.time);
            holder1. animator.setInterpolator(new LinearInterpolator());
            Log.i(TAG, "onBindViewHolder: " + user.Elapsed_time);
            holder1.animator.setCurrentPlayTime(user.Elapsed_time);


            holder1. timer =  new ExampleTimer(1000 , holder1.time) {
                long leftTime =  holder2.time / 1000  ;
                @Override
                protected void onTick() {
                    holder2.tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            holder2 .tx_leftTime.setText(String.valueOf(leftTime--));
                        }
                    });
                }

                @Override
                protected void onFinish() {
                    holder2. tx_leftTime.post(new Runnable() {
                        @Override
                        public void run() {
                            holder2.tx_leftTime.setText("Finished ...");
                        }
                    });
                }
            };


            holder1. animator.start();
            holder1. timer.start();
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

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case  R.id.drop_down_btn_active_users :



                if (holder2.flag_dropdown){
                    holder2.tx_numJoystick.setVisibility(View.GONE);
                    holder2.tx_money.setVisibility(View.GONE);
                    holder2.drop_down.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                    holder2.flag_dropdown = false ;
                }else {
                    holder2.tx_numJoystick.setVisibility(View.VISIBLE);
                    holder2.tx_money.setVisibility(View.VISIBLE);
                    holder2.drop_down.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                    holder2.flag_dropdown = true ;
                }

                break;

        }
    }

    public static class Recycler_viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       TextView tx_Name,tx_LastName , tx_leftTime , tx_startTime , tx_endTime  , tx_money , tx_numJoystick ;
        ProgressBar progressBar ;
        ImageView drop_down ;
        Button start_pause , stop ;
        ImageView delete ;



        View view ;

        Snackbar mySnackbar ;

        Context context ;

        long time ;

        List<ActiveUser> list ;

        ObjectAnimator animator ;

        Timer timer ;

        Activity activity ;

        TimerService timerService ;


        boolean flag_dropdown = false  , start_flag = false  , pause_flag = false ;


        public Recycler_viewHolder(View itemView , final List<ActiveUser> list , Context context , View view , Activity activity ) {
            super(itemView);

            this.list = list ;
            this.context = context ;
            this.view = view ;
            this.activity = activity ;

            timerService = new TimerService() ;

            tx_Name = (TextView) itemView.findViewById(R.id.name_active_user_recycler);
            tx_LastName = (TextView) itemView.findViewById(R.id.Last_name_active_user_recycler);
            tx_leftTime = (TextView) itemView.findViewById(R.id.leftTime);
            tx_startTime = (TextView) itemView.findViewById(R.id.startTime);
            tx_endTime = (TextView) itemView.findViewById(R.id.endTime);
            tx_money = (TextView) itemView.findViewById(R.id.releaseMoney);
            tx_numJoystick = (TextView) itemView.findViewById(R.id.joystickNum);

            progressBar = (ProgressBar) itemView.findViewById(R.id.active_user_progressBar);

            drop_down = (ImageView) itemView.findViewById(R.id.drop_down_btn_active_users);

            start_pause = (Button) itemView.findViewById(R.id.btn_start_pause_user);
            stop = itemView.findViewById(R.id.btn_stop_user);
            delete = (ImageView) itemView.findViewById(R.id.btn_delete_active_user);

            mySnackbar = Snackbar.make(view ,"Task Deleted ..." , Snackbar.LENGTH_LONG );

            animator = ObjectAnimator.ofInt(progressBar , "progress", 0 , 100);



            drop_down.setOnClickListener(this);
            start_pause.setOnClickListener(this);
            delete.setOnClickListener(this);




        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {


            switch (v.getId()) {

                case R.id.drop_down_btn_active_users:
                    if (flag_dropdown){
                        tx_numJoystick.setVisibility(View.GONE);
                        tx_money.setVisibility(View.GONE);
                        drop_down.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                        flag_dropdown = false ;
                    }else {
                        tx_numJoystick.setVisibility(View.VISIBLE);
                        tx_money.setVisibility(View.VISIBLE);
                        drop_down.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                        flag_dropdown = true ;
                    }
                    break;


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
                                db.Delete_ActiveUser(list.get(getLayoutPosition()));

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
                        db.Delete_ActiveUser(list.get(getLayoutPosition()));

                    }


                    break;


                case R.id.btn_start_pause_user:

                    Log.i(TAG, "onClick: " + getLayoutPosition());

                    if (!start_flag){

                            start_begin();
                            start_flag = true ;


                    }else if (start_flag && !pause_flag){

                        pause();
                        pause_flag = true ;

                    }else if (pause_flag){

                        resume();
                        pause_flag = false ;


                     }

                    break;

            }
        }

        private void start_begin (){

            start_pause.setText("pause");
            start_pause.setTextColor(Color.parseColor("#d50000"));


            time = list.get(getLayoutPosition()).Remaining_Time;

            Intent service = new Intent(context , TimerService.class);
            service.putExtra("id" , list.get(getLayoutPosition()).Username_id) ;
            service.putExtra("mode" , 0 ) ;
            context.startService(service);

            animator.setDuration(time*60*1000);
            animator.setCurrentPlayTime(list.get(getLayoutPosition()).Elapsed_time);
            animator.setInterpolator(new LinearInterpolator());

            timer =  new ExampleTimer(1000 , time*60*1000) {
                long leftTime = time *60 ;
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
            timer.start();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void pause(){
            start_pause.setText("resume");
            start_pause.setTextColor(Color.parseColor("#64dd17"));
            stop.setVisibility(View.VISIBLE);

            Intent service = new Intent(context , TimerService.class);
            service.putExtra("mode" , 1) ;
            Log.i(TAG, "pause: " + list.get(getLayoutPosition()).Tag_Num);
            //service.putExtra("tag_id" , list.get(getLayoutPosition()).Tag_Num) ;
            context.startService(service);

            animator.pause();
            timer.pause();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void resume(){
            start_pause.setText("pause");
            start_pause.setTextColor(Color.parseColor("#d50000"));
            stop.setVisibility(View.INVISIBLE);

            Intent service = new Intent(context , TimerService.class);
            service.putExtra("mode" , 2) ;
            service.putExtra("tag_id" , list.get(getLayoutPosition()).Tag_Num) ;
            context.startService(service);

            animator.resume();
            timer.resume();
        }

        private void stop (){}




    }





}
