package com.project.avanikan_pc_003.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class test_accordion extends AppCompatActivity {

    LinearLayout name , phone , line1 , line , totalMoney , join , line2 , line3 ; ;
    boolean flag = true  , flag2 = true ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.westerngamecenter_searchresult_content);


        name = (LinearLayout) findViewById(R.id.linear_name);
        phone = (LinearLayout) findViewById(R.id.linear_phone);
        line = (LinearLayout) findViewById(R.id.linear_line);
        line1 = (LinearLayout) findViewById(R.id.linear_line1);
        line2 = (LinearLayout) findViewById(R.id.linear_line2);
        line3 = (LinearLayout) findViewById(R.id.linear_line3);
        join = (LinearLayout) findViewById(R.id.linear_join);
        totalMoney = (LinearLayout) findViewById(R.id.linear_total_money);
        name.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        line3.setVisibility(View.GONE);
        join.setVisibility(View.GONE);
        totalMoney.setVisibility(View.GONE);

        final ImageView imageView = (ImageView) findViewById(R.id.drop_down_btn_first);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("===>", "onClick: ");

                if (flag){
                    name.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                    flag = false ;
                }else {
                    name.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    line1.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                    flag = true ;
                }

            }
        });


        final ImageView imageView1 = (ImageView) findViewById(R.id.drop_down_btn_second);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag2){

                    line2.setVisibility(View.VISIBLE);
                    line3.setVisibility(View.VISIBLE);
                    join.setVisibility(View.VISIBLE);
                    totalMoney.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                    flag2 = false ;
                }else {
                    line2.setVisibility(View.GONE);
                    line3.setVisibility(View.GONE);
                    join.setVisibility(View.GONE);
                    totalMoney.setVisibility(View.GONE);
                    imageView1.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                    flag2 = true ;
                }
            }
        });
    }
}
