package com.example.hwanghyuntae.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

public class citizen_project_robot extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {
    private ImageSwitcher imageSwitcher;
    public int pagecount = 1;
    private View page1, page2, page3, page4;
    private LayoutInflater inflater;
    private Button nb;
    private ImageView iv3;
    private ViewFlipper flipper;
    boolean isImageFitToScreen;
    private String imageurl[] = { "http://eeweb.poly.edu/~yao/EL5123/image/lena_gray.bmp" };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean zoomOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_project_robot);
        iv3 = (ImageView) findViewById(R.id.imageView3);
        Button btn_robot = (Button) findViewById(R.id.button7);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout tv_layout = (LinearLayout) findViewById(R.id.tv_layout);
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


                    //ViewFlipper 객체 참조
                    flipper = (ViewFlipper) findViewById(R.id.viewFlipper);

                    for (int i = 0; i < 7; i++) {

                        ImageView img = new ImageView(citizen_project_robot.this);
                        img.setOnTouchListener(citizen_project_robot.this);
                        img.setClickable(true);
                        new DownloadImageTask(img)
                                .execute(imageurl[0]);
                        flipper.addView(img);
                    }

                    Animation showIn = AnimationUtils.loadAnimation(citizen_project_robot.this, android.R.anim.slide_in_left);
                    flipper.setInAnimation(showIn);
                    flipper.setOutAnimation(citizen_project_robot.this, android.R.anim.slide_out_right);

                } else if (pagecount == 2) {
                    tv_layout.removeView(page2);
                    page3 = (View) inflater.inflate(R.layout.content_citizen_project_robot_3, null);
                    tv_layout.addView(page3);
                    pagecount++;


                } else if (pagecount == 3) {
                    startActivity(new Intent(citizen_project_robot.this, Bluetooth.class));
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    //onClick속성이 지정된 View가 클릭되었을 때 자동으로 호출되는 메소드.
    public void mOnClick(View v) {

        switch (v.getId()) {
            case R.id.button8:
                flipper.showPrevious();//이전 View로 교체
                break;
            case R.id.button9:
                flipper.showNext();//다음 View로 교체
                break;
        }
    }


    public void ControlAppButtononclick(View v) {
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "citizen_project_robot Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hwanghyuntae.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "citizen_project_robot Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hwanghyuntae.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof ImageView) {
        }
        return false;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
