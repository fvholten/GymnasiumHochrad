package de.hochrad.hochradapp.activites.ueber;


import android.content.Context;
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

import de.hochrad.hochradapp.BuildConfig;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.NavigationDrawerNavigate;

public class UeberActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView HochradWebseite;
    NavigationDrawerNavigate navigationDrawerNavigate;
    Context context = this;


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

        String versionName = BuildConfig.VERSION_NAME;
        TextView textView = (TextView) findViewById(R.id.Version);
        textView.setText("Version: " + versionName);
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
        if (id != R.id.ueber)
            finish();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
