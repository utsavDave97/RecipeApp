package com.example.recipeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * @author utsav
 * @date 24th Feb 2019
 *
 * This class is the MainActivity class. Inside this class all the fragments are opened.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        CreateYourOwn.OnFragmentInteractionListener,
        Step1.OnFragmentInteractionListener,
        Step2.OnFragmentInteractionListener,
        Step3.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener,
        CalorieCalculatorFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        YourRecipe.OnFragmentInteractionListener,
        EachRecipe.OnFragmentInteractionListener
{
    //Declaring the FragmentManager
    FragmentManager fm;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Disable the default title of Action Bar
        //This would enable the custom Title on Action Bar
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().show();

        //Initializing the FragmentManager
        fm = getSupportFragmentManager();

        //If the savedInstanceState is null, the App would launch with Main fragment
        if(savedInstanceState == null)
        {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new MainFragment());
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //This would by default launch the app with Home Icon in checked state
        navigationView.setCheckedItem(R.id.nav_home);
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
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_home)
        {
            // Handle the home action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new MainFragment());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_create)
        {
            // Handle the Create Your Own action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new CreateYourOwn());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_calculator)
        {
            // Handle the Calorie Calculator action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new CalorieCalculatorFragment());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_your_recipes)
        {
            // Handle the Your Recipes action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new YourRecipe());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_favorites)
        {
            // Handle the Favorites action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new FavoritesFragment());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_settings)
        {
            // Handle the Settings action
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.content,new SettingsFragment());
            transaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if(fragments != null)
        {
            for (Fragment f: fragments)
            {
                f.onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
