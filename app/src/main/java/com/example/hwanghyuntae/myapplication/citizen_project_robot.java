package com.example.hwanghyuntae.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class citizen_project_robot extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageSwitcher imageSwitcher;
    public int pagecount = 1;
    private View page1,page2,page3,page4;
    private LayoutInflater inflater;
    private Button nb;
    private ImageView iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_project_robot);
        iv3 = (ImageView) findViewById(R.id.imageView3);
        Button btn_robot = (Button) findViewById(R.id.button7);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout tv_layout =(LinearLayout) findViewById(R.id.tv_layout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        nb = (Button) findViewById(R.id.button9);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        page1 = (View) inflater.inflate(R.layout.content_citizen_project_robot_1, null);
        tv_layout.addView(page1);
        btn_robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagecount == 1) {
                    tv_layout.removeView(page1);
                    page2 = (View) inflater.inflate(R.layout.content_citizen_project_robot_2, null);
                    tv_layout.addView(page2);
                    pagecount++;
                } else if (pagecount == 2) {
                    tv_layout.removeView(page2);
                    page3 = (View) inflater.inflate(R.layout.content_citizen_project_robot_3, null);
                    tv_layout.addView(page3);
                    pagecount++;
                }else if (pagecount == 3) {
                    startActivity(new Intent(citizen_project_robot.this, Bluetooth.class));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void ControlAppButtononclick(View v)
    {
        startActivity(new Intent(citizen_project_robot.this, Bluetooth.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(citizen_project_robot.this, citizen_main.class));
        } else if (id == R.id.nav_calendar) {


        } else if (id == R.id.nav_group) {

        } else if (id == R.id.nav_live) {

        } else if (id == R.id.nav_presi_time) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_surveys) {

        } else if (id == R.id.nav_signout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
