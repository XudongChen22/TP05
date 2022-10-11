package com.example.mebournists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mebournists.movie.MovieListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    ImageView homebackground;
    LinearLayout welcome, background, menu, cal, act, good;
    Animation frombottom;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //homepage background show
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

////        homebackground = (ImageView) findViewById(R.id.homebackground);
//        welcome = (LinearLayout) findViewById(R.id.welcome);
//        background = (LinearLayout) findViewById(R.id.background);
//        menu = (LinearLayout) findViewById(R.id.menu);
//
////        homebackground.animate().translationY(-2100).setDuration(800).setStartDelay(300);
//        welcome.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
//        background.startAnimation(frombottom);
//        menu.startAnimation(frombottom);


        //navigation bar
        bottomNavigationView = findViewById(R.id.navigationBar);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(),NewCalendarMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(),ActRecActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.recommendation:
                        startActivity(new Intent(getApplicationContext(), MovieListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //call calender function
        cal = (LinearLayout) findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCalendar();
            }
        });

        //call calender function
        act = (LinearLayout) findViewById(R.id.act);
        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity();
            }
        });


        findViewById(R.id.mov).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieListActivity.class));
            }
        });



    }

    public void openCalendar(){
        Intent intent = new Intent(this, NewCalendarMainActivity.class);
        startActivity(intent);
    }

    public void openActivity(){
        Intent intent = new Intent(this, ActRecActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}