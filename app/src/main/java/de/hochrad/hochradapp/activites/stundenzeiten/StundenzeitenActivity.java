package de.hochrad.hochradapp.activites.stundenzeiten;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.NavigationDrawerNavigate;

public class StundenzeitenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView stundenzeiten;
    ArrayAdapter<String> Stundenzeitenadapter;
    NavigationDrawerNavigate navigationDrawerNavigate;
    Context context = this;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        NavigationDrawer und ToolBar!!!!

        setContentView(R.layout.activity_stundenzeiten);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        App Inhalt
        String[] zeitenArray = getResources().getStringArray(R.array.Zeiten);
        Stundenzeitenadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, zeitenArray);
        stundenzeiten = (ListView) findViewById(R.id.stundenzeiten);
        stundenzeiten.setAdapter(Stundenzeitenadapter);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerNavigate = new NavigationDrawerNavigate(id, context);
        if (id != R.id.stundenzeiten)
            finish();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

