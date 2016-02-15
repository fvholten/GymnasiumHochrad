package de.hochrad.hochradapp.activites;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.activites.einstellungen.EinstellungenActivity;
import de.hochrad.hochradapp.activites.vertretungsplan.VertretungsplanActivity;

public class UeberActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView HochradWebseite;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//               NavigationDrawer und ToolBar!!!!

        setContentView(R.layout.activity_ueber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//              AppInhalt

        HochradWebseite = (TextView) findViewById(R.id.Recht);
        HochradWebseite.setMovementMethod(LinkMovementMethod.getInstance());
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
            startActivity(new Intent(this, WochenplanActivtiy.class));
            finish();
        } else if (id == R.id.stundenzeiten) {
            startActivity(new Intent(this, StundenzeitenActivity.class));
            finish();
        } else if (id == R.id.ueber) {
            drawer.closeDrawer(GravityCompat.START);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
