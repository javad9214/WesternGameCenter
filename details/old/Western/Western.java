package com.project.avanikan_pc_003.login.Western;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.avanikan_pc_003.login.R;

public class Western extends AppCompatActivity {

    TextView textView  , textView2;
    Button start ,  pause , resume ;
    Button start2 ,  pause2 , resume2 ;
    Timer timer , timer2 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western);

        textView = (TextView) findViewById(R.id.mtext);
        start = (Button) findViewById(R.id.btn_start);
        resume = (Button) findViewById(R.id.btn_pause);
        pause = (Button) findViewById(R.id.btn_resume);

        textView2 = (TextView) findViewById(R.id.mtext2);
        start2 = (Button) findViewById(R.id.btn_start2);
        resume2 = (Button) findViewById(R.id.btn_pause2);
        pause2 = (Button) findViewById(R.id.btn_resume2);

        timer  = new ExampleTimer(1000 , 30000){

          int k = 30 ;
           @Override
           protected void onTick() {
               super.onTick();


               textView.post(new Runnable() {
                                 public void run() {
                                     textView.setText(String.valueOf(k--));
                                 }
                             });


           }

           @Override
           protected void onFinish() {
               super.onFinish();

               textView.post(new Runnable() {
                   public void run() {
                       textView.setText("finished ...");
                   }
               });

           }
       };




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pause();

            }
        });


        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    timer.resume();


            }
        });


        timer2  = new ExampleTimer(1000 , 20000){

            int p = 20 ;
            @Override
            protected void onTick() {
                super.onTick();


                textView.post(new Runnable() {
                    public void run() {
                        textView2.setText(String.valueOf(p--));
                    }
                });


            }

            @Override
            protected void onFinish() {
                super.onFinish();


                textView2.post(new Runnable() {
                    public void run() {
                        textView2.setText("finished ...");
                    }
                });
            }
        };



        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer2.start();


            }
        });

        pause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer2.pause();

            }
        });


        resume2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer2.resume();


            }
        });





    }






}
