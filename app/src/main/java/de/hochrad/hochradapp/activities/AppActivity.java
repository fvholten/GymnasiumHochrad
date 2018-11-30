package de.hochrad.hochradapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.kobakei.ratethisapp.RateThisApp;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.fragments.*;
import de.hochrad.hochradapp.service.Service;

public class AppActivity extends AppCompatActivity implements VertretungsplanFragment.DetailView{

    private int current_id = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        startService(new Intent(this, Service.class));

        rateApp();
        initNavigationAndToolbar();
    }

    private void rateApp() {
        RateThisApp.Config config = new RateThisApp.Config(5, 7);
        config.setTitle(R.string.my_own_title_rateme);
        config.setMessage(R.string.my_own_message_rateme);
        RateThisApp.init(config);
        RateThisApp.onStart(this);
        RateThisApp.showRateDialogIfNeeded(this);
    }

    private void initNavigationAndToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initNavigation(findViewById(R.id.nav_view), drawer);
    }

    private void initNavigation(NavigationView navigationView, final DrawerLayout drawer) {
        showFragment(current_id);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawer.closeDrawer(GravityCompat.START);

            if (current_id != item.getItemId()) {
                showFragment(item.getItemId());
            }
            return true;
        });
    }

    private void showFragment(int itemId) {
        current_id = itemId;

        //Set Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, chooseFragment(itemId)).commit();
    }

    private Fragment chooseFragment(int itemId) {
        switch (itemId) {
            case R.id.startseite:
                return new StartseiteFragment();
            case R.id.vertretungsplan:
                return new VertretungsplanFragment();
            case R.id.stundenzeiten:
                return new StundenzeitenFragment();
            case R.id.einstellungen:
                return new EinstellungenFragment();
            case R.id.ueber:
                return new UeberFragment();
            default:
                return new StartseiteFragment();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openDetails(Class toActivity, Bundle bundle) {
        Intent intent = new Intent(AppActivity.this, toActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
