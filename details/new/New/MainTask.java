package com.project.avanikan_pc_003.taskslist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.avanikan_pc_003.taskslist.Adapter.Task_Recycler_Adapter;
import com.project.avanikan_pc_003.taskslist.Data.CalendarTool;
import com.project.avanikan_pc_003.taskslist.Data.Tasks;
import com.project.avanikan_pc_003.taskslist.DataBase.DataBaseOperation;
import com.project.avanikan_pc_003.taskslist.western.Custom_dialog;
import com.project.avanikan_pc_003.taskslist.western.News.TypefaceSpan;


import java.util.ArrayList;

public class MainTask extends AppCompatActivity {



    EditText editText_task , editText_deadline_day ,  editText_deadline_month ,  editText_deadline_year  ;
    public static final String TAG = "===>" ;
    RecyclerView recyclerView , recyclerView_checkedTask ;
    Task_Recycler_Adapter adapter ;
    ArrayList<Tasks> list = new ArrayList<>() ;
    Toolbar toolbar ;
    boolean edit_menu_flag = false ;

    ImageView imageView_close_toolbar ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerTasks);
        recyclerView_checkedTask = (RecyclerView) findViewById(R.id.recycler_checkedTasks);




        SpannableString s = new SpannableString("Western Game Center");
        s.setSpan(new TypefaceSpan(this, "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(s);

        getTaskFromDB();
        checked_items();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (edit_menu_flag){
            getMenuInflater().inflate(R.menu.menu_second_toolbar, menu);
            return true;
        }else {
            getMenuInflater().inflate(R.menu.menu_main_task, menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case R.id.action_settings :
                Toast.makeText(this, "western game center", Toast.LENGTH_SHORT).show();
                return true;


            case android.R.id.home :
                Toast.makeText(this, "western game center", Toast.LENGTH_SHORT).show();
                return true ;


            case R.id.action_delete :
                break;

            case R.id.action_edit :
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void newTask(View view) {

        LayoutInflater inflater = getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.dialog_add_newtask  ,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        editText_task = dialog.findViewById(R.id.edit_add_task);
        editText_deadline_day = dialog.findViewById(R.id.edit_deadline_day);
        editText_deadline_month = dialog.findViewById(R.id.edit_deadline_month);
        editText_deadline_year = dialog.findViewById(R.id.edit_deadline_year);

        final CalendarTool calendarTool = new CalendarTool();
        editText_deadline_year.setHint(String.valueOf(calendarTool.getIranianYear()));
        editText_deadline_month.setHint(String.valueOf(calendarTool.getIranianMonth()));
        editText_deadline_day.setHint(String.valueOf(calendarTool.getIranianDay()));

        builder.setCancelable(false);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Tasks tasks = new Tasks();

                if (editText_task.getText().toString().matches("")){
                   tasks.task = "New Task " ;

                }else {
                    tasks.task = editText_task.getText().toString();
                }
                if (editText_deadline_day.getText().toString().matches("") ){
                    tasks.deadline_day = calendarTool.getIranianDay();
                }else {
                    tasks.deadline_day = Integer.parseInt(editText_deadline_day.getText().toString());
                }

                if (editText_deadline_month.getText().toString().matches("")){
                    tasks.deadline_month = calendarTool.getIranianMonth();
                }else {

                    tasks.deadline_month = Integer.parseInt(editText_deadline_month.getText().toString());
                }

                if (editText_deadline_year.getText().toString().matches("")){
                    tasks.deadline_year = calendarTool.getIranianYear();
                }else {
                    tasks.deadline_year = Integer.parseInt(editText_deadline_year.getText().toString());
                }


                tasks.status = 0 ;
                list.add(tasks);
                Toast.makeText(MainTask.this, "new task was created ", Toast.LENGTH_SHORT).show();
                DataBaseOperation db = App.getDataBaseOperation();
                db.addTask(tasks);
                getTaskFromDB();

            }
        });
        builder.setNegativeButton("Cancel", null ) ;

        builder.show();


    }

    public void getTaskFromDB(){


        try{
            Log.i(TAG, "getTaskFromDB: ");
            DataBaseOperation db = App.getDataBaseOperation();
            ArrayList<Tasks> arrayList = new ArrayList<>();
            arrayList.clear();
            arrayList  = db.get_tasks_form_db();
            adapter = new Task_Recycler_Adapter(arrayList , MainTask.this , MainTask.this);
            recyclerView.setAdapter(adapter);
            GridLayoutManager glm_1 = new GridLayoutManager(this , 1);
            glm_1.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(glm_1);

        }catch (NullPointerException e ){
            e.printStackTrace();
            Log.i(TAG, "getTaskFromDB: " + e.toString());
        }


    }

    public void getTaskFromDB(Activity activity , Context context){



        RecyclerView recyclerView ;
        Task_Recycler_Adapter adapter ;
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerTasks);

        try{
            Log.i(TAG, "getTaskFromDB: ");
            DataBaseOperation db = App.getDataBaseOperation();
            ArrayList<Tasks> arrayList = new ArrayList<>();
            arrayList.clear();
            arrayList  = db.get_tasks_form_db();
            adapter = new Task_Recycler_Adapter(arrayList , context ,activity);
            recyclerView.setAdapter(adapter);
            GridLayoutManager glm_1 = new GridLayoutManager(this , 1);
            glm_1.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(glm_1);

        }catch (NullPointerException e ){
            e.printStackTrace();
            Log.i(TAG, "getTaskFromDB: " + e.toString());
        }


    }

    public void checked_items(){

        try{
            Log.i(TAG, "getTaskFromDB: ");
            DataBaseOperation db = App.getDataBaseOperation();
            ArrayList<Tasks> arrayList = new ArrayList<>();
            arrayList.clear();
            arrayList  = db.get_checked_tasks_form_db();
            adapter = new Task_Recycler_Adapter(arrayList , MainTask.this , MainTask.this);
            recyclerView_checkedTask.setAdapter(adapter);
            GridLayoutManager glm_1 = new GridLayoutManager(this , 1);
            glm_1.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView_checkedTask.setLayoutManager(glm_1);

        }catch (NullPointerException e ){
            e.printStackTrace();
            Log.i(TAG, "getTaskFromDB: " + e.toString());
        }


    }

    public void checked_items(Activity activity , Context context){


        RecyclerView recyclerView_checkedTask ;
        Task_Recycler_Adapter adapter ;
        recyclerView_checkedTask = (RecyclerView) activity.findViewById(R.id.recycler_checkedTasks);

        try{
            Log.i(TAG, "getTaskFromDB: ");
            DataBaseOperation db = App.getDataBaseOperation();
            ArrayList<Tasks> arrayList = new ArrayList<>();
            arrayList.clear();
            arrayList  = db.get_checked_tasks_form_db();
            adapter = new Task_Recycler_Adapter(arrayList , context , activity);
            recyclerView_checkedTask.setAdapter(adapter);
            GridLayoutManager glm_1 = new GridLayoutManager(this , 1);
            glm_1.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView_checkedTask.setLayoutManager(glm_1);

        }catch (NullPointerException e ){
            e.printStackTrace();
            Log.i(TAG, "getTaskFromDB: " + e.toString());
        }


    }

    public void dialogTest(View view) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        transaction.addToBackStack(null);
        Custom_dialog custom_dialog = new Custom_dialog();
        custom_dialog.show(transaction , "dialog");


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void change_toolbar(View view) {
        LayoutInflater inflater = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.second_toolbar , null);
        edit_menu_flag = true ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true) ;
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.CYAN));

        final Window window = getWindow() ;
        window.setStatusBarColor(Color.rgb(3 , 194 ,194));
        Log.i(TAG, "change_toolbar: " + window.getStatusBarColor());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (v != null) {
                v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        invalidateOptionsMenu();

        imageView_close_toolbar = (ImageView) findViewById(R.id.image_close_toolbar);
        imageView_close_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowCustomEnabled(false) ;
                setSupportActionBar(toolbar);
                getSupportActionBar().setBackgroundDrawable(null);
                window.setStatusBarColor(Color.rgb(48 , 63 ,159));

                edit_menu_flag = false ;
                invalidateOptionsMenu();
            }
        });

    }



}
