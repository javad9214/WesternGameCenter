package com.project.avanikan_pc_003.taskslist.western;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.project.avanikan_pc_003.taskslist.R;

public class MainWestern extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_western);
        Button button = (Button) findViewById(R.id.btn_show);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Animation Dialog");
        builder.setMessage("something ...");
        builder.setNegativeButton("Previous", null);
        builder.setPositiveButton("Next" , null);

        LayoutInflater inflater = getLayoutInflater();
        final View dialog1 = inflater.inflate(R.layout.dialog_test_content  ,null);
//        final View dialog2 = inflater.inflate(R.layout.dialog_edit_deadline , null);


        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button theButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                theButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.setView(dialog1);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;

                        fragmentTransaction.setCustomAnimations(R.anim.slide_left , R.anim.slide_right);
                        //fragmentTransaction.add(getResources().getIdentifier(), new dialog_fragmrnt());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        });




        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme ;
        dialog.show();
    }




}
