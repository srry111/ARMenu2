package my.edu.tarc.armenu;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

import my.edu.tarc.armenu.Adapter.ViewPagerAdapter;
import my.edu.tarc.armenu.Favourite.favouriteActivity;
import my.edu.tarc.armenu.Food.CrabFood;
import my.edu.tarc.armenu.Food.Streak;
import my.edu.tarc.armenu.Foodlist.Beverage;
import my.edu.tarc.armenu.Foodlist.Dessert;
import my.edu.tarc.armenu.Foodlist.Disher;
import my.edu.tarc.armenu.Foodlist.FastFood;
import my.edu.tarc.armenu.Foodlist.Soup;
import my.edu.tarc.armenu.Foodlist.food;
import my.edu.tarc.armenu.Login.loginFunction;
import my.edu.tarc.armenu.Register.SharePreferenceManager;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton imgbuttonf2;
    ImageButton imgbutton3,foodbt,beveragebt;
    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private int NUM_PAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter vpa = new ViewPagerAdapter(this);
        viewPager.setAdapter(vpa);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textView6);
        Intent intent = getIntent();
        
        String name1 = SharePreferenceManager.getmInstance(this).getUserEmail();
        nav_user.setText("Welcome , " +name1);

        imgbuttonf2 = (ImageButton)findViewById(R.id.imgbtnf2);
        imgbuttonf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), CrabFood.class));
            }
        });

        imgbutton3 = (ImageButton)findViewById(R.id.imgbtnf1);
        imgbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Streak.class));
            }
        });
        foodbt = (ImageButton)findViewById(R.id.foodbtn);
        foodbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), food.class));
            }
        });
        beveragebt = (ImageButton)findViewById(R.id.beveragebtn);
        beveragebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Beverage.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.disher:
                startActivity(new Intent(getApplicationContext(), Disher.class));
                break;
            case R.id.soup:
                startActivity(new Intent(getApplicationContext(), Soup.class));
                break;
            case R.id.viewfavour:
                startActivity(new Intent(getApplicationContext(),favouriteActivity.class));
                break;
            case R.id.fastfood:
                startActivity(new Intent(getApplicationContext(), FastFood.class));
                break;
            case R.id.beverage:
                startActivity(new Intent(getApplicationContext(), Beverage.class));
                break;
            case R.id.dessert:
                startActivity(new Intent(getApplicationContext(), Dessert.class));
                break;
            case R.id.viewlocation:
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                //startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                break;
            case R.id.Logout:
                SharePreferenceManager.getmInstance(getApplicationContext()).logout();
                Intent logoutInt = new Intent(MainActivity.this,loginFunction.class);
                startActivity(logoutInt);
                finish();



        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
