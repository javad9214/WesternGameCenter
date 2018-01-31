package com.project.avanikan_pc_003.login.western;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.constraint.solver.widgets.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.avanikan_pc_003.login.R;

public class Western extends AppCompatActivity {

    public static final String TAG = "===>" ;

    ProgressBar progressBar ;
    ObjectAnimator animator ;
    Button button  , button1 ;
    int mainTime ;
    int time  = 0 ;
    int innertime = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western);
        mainTime = 20 ;
        button = (Button) findViewById(R.id.start);
        button1 = (Button) findViewById(R.id.restart);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setMax(20);
//        progressBar.setProgress(0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            animator = ObjectAnimator.ofInt(progressBar , "progress", 0 , 100);
                animator.setDuration(10000); // 2 seconds
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                Log.i(TAG, "onClick: " + animator.getCurrentPlayTime());
                if (progressBar.getProgress() == 50){
                    Toast.makeText(Western.this, "half", Toast.LENGTH_SHORT).show();
                }
                animator.setCurrentPlayTime(5000);


                animator.addListener(new android.animation.Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(android.animation.Animator animator) {
                        if (progressBar.getProgress() == 50){
                            Toast.makeText(Western.this, "half", Toast.LENGTH_SHORT).show();
                        }

                        Log.i(TAG, "onAnimationStart: " + progressBar.getProgress());
                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animator) {
                        Toast.makeText(Western.this, "finished", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationCancel(android.animation.Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(android.animation.Animator animator) {
                        Log.i(TAG, "onAnimationRepeat: " + progressBar.getProgress());
                    }
                });
                Log.i(TAG, "onClick: " + animator.getCurrentPlayTime());


//
//                progress progress = new progress(getPercentage(mainTime));
//
//                progress.execute();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(0);
                time = 0 ;
            }
        });

    }

    public int getPercentage(int MainTime){
        MainTime =  MainTime  * 2 ;
        return MainTime ;
    }

    public class progress extends AsyncTask<Integer , Integer , String>{

        int threadTime ;

        public progress(int threadTime) {
            this.threadTime = threadTime ;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);


        }

        @Override
        protected String doInBackground(Integer... integers) {

            for (int i = 0 ; i<20 ; i++){

                    try {

                        Thread.sleep(1000);

                        Log.i(TAG, "doInBackground: thraed " + threadTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time++;
                    publishProgress((int) (time) );
                    Log.i(TAG, "doInBackground: " + time);

                }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(Western.this, "finished", Toast.LENGTH_SHORT).show();
        }



    }



}
