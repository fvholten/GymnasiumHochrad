package de.hochrad.hochradapp.activites.startseite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.kobakei.ratethisapp.RateThisApp;

import java.util.List;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.StundenzeitenActivity;
import de.hochrad.hochradapp.activites.UeberActivity;
import de.hochrad.hochradapp.activites.WochenplanActivtiy;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;
import de.hochrad.hochradapp.hilfsfunktionen.ConnectionTest;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTask;
import de.hochrad.hochradapp.loader.NachrichtenDesTagesLadenTaskCallBack;
import de.hochrad.hochradapp.loader.WochennummerLadenTask;
import de.hochrad.hochradapp.loader.WochennummerLadenTaskCallBack;
import de.hochrad.hochradapp.service.Service;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        WochennummerLadenTaskCallBack,
        NachrichtenDesTagesLadenTaskCallBack {

    Context context = this;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RateThisApp.Config config = new RateThisApp.Config(5, 7);
        config.setTitle(R.string.my_own_title_rateme);
        config.setMessage(R.string.my_own_message_rateme);
        RateThisApp.init(config);
        RateThisApp.onStart(context);
        RateThisApp.showRateDialogIfNeeded(context);
// NavigationDrawer und ToolBar!!!!
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startService(new Intent(context, Service.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Dass, was auf dem Bildschirmentstehen soll
        if (!(ConnectionTest.isOnline(context))) {
            startActivity();
        }
        new WochennummerLadenTask(false, 0, 0, this).execute();
    }

    public void WochennummerLaden(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, Integer wochennummer) {
        if (wochennummer == null) {
            startActivity();
        } else {
            new NachrichtenDesTagesLadenTask(wochennummer, this).execute();
        }
    }

    public void NachrichtenDesTages(List<String> newsDerWoche) {
        if (newsDerWoche == null) {
            startActivity();
        } else {
            ArrayAdapter<String> NachrichtenzumTagInhaltAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

            NachrichtenzumTagInhaltAdapter.addAll(newsDerWoche);
            GridView NachrichtenzumTagInhalt = (GridView) findViewById(R.id.NachrichtenzumTagInhalt);
            NachrichtenzumTagInhalt.setAdapter(NachrichtenzumTagInhaltAdapter);
        }
    }

    public void startActivity() {
        startActivity(new Intent(context, MainActivityLoadscreenActivity.class));
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.startseite) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.vertretungsplan) {
            startActivity(new Intent(context, VertretungsplanActivity.class));
            finish();
        } else if (id == R.id.einstellungen) {
            startActivity(new Intent(context, EinstellungenActivity.class));
            finish();
        } else if (id == R.id.wochenplan) {
            startActivity(new Intent(context, WochenplanActivtiy.class));
            finish();
        } else if (id == R.id.stundenzeiten) {
            startActivity(new Intent(context, StundenzeitenActivity.class));
            finish();
        } else if (id == R.id.ueber) {
            startActivity(new Intent(context, UeberActivity.class));
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
