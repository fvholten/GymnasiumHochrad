package de.hochrad.hochradapp.activites;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;
import de.hochrad.hochradapp.loader.WochenplanLadenTaskCallBack;


public class WochenplanActivtiy extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        WochenplanLadenTaskCallBack {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        NavigationDrawer und ToolBar!!!!

        setContentView(R.layout.activity_wochenplan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        Dass, was auf dem Bildschirmentstehen soll

        ArrayAdapter<String> WochenplanAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        WochenplanAdapter.add("Kommt Bald!");

        GridView NachrichtenzumTagInhalt = (GridView) findViewById(R.id.WochenplanInhalt);
        NachrichtenzumTagInhalt.setAdapter(WochenplanAdapter);
//
//        new WochenplanLadenTask(this).execute();
    }

    @Override
    public void WochenplanLaden(String[] string) {
//      TODO
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.startseite) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.vertretungsplan) {
            startActivity(new Intent(this, VertretungsplanActivity.class));
            finish();
        } else if (id == R.id.einstellungen) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            finish();
        } else if (id == R.id.wochenplan) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.stundenzeiten) {
            startActivity(new Intent(this, StundenzeitenActivity.class));
            finish();
        } else if (id == R.id.ueber) {
            startActivity(new Intent(this, UeberActivity.class));
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
