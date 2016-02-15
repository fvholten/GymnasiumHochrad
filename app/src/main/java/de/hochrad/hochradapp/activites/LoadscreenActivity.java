package de.hochrad.hochradapp.activites;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;

public abstract class LoadscreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        NavigationDrawer und ToolBar!!!!

        setContentView(R.layout.activitiy_loadscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newIntent(LoadscreenActivity.this));
            }
        });
    }

    public abstract Intent newIntent(LoadscreenActivity activity);


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
            startActivity(new Intent(this, WochenplanActivtiy.class));
            finish();
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
