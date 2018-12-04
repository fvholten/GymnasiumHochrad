package de.hochrad.hochradapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.kobakei.ratethisapp.RateThisApp;
import com.kobakei.ratethisapp.RateThisApp.Config;
import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.fragments.*;
import de.hochrad.hochradapp.fragments.SubstitutionScheduleFragment.DetailView;
import de.hochrad.hochradapp.service.Service;

public class AppActivity extends AppCompatActivity implements DetailView, OnNavigationItemSelectedListener {
    private int current_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        startService(new Intent(this, Service.class));
        rateApp();
        ((BottomNavigationView) findViewById(R.id.bottom_navigation)).setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new NewsOfDayFragment()).commit();
        this.current_id = R.id.news_of_day;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        this.current_id = item.getItemId();
        int i = this.current_id;
        if (i == R.id.about_menu_item) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new AboutFragment()).commit();
            return true;
        } else if (i != R.id.options_menu_item) {
            return super.onOptionsItemSelected(item);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SettingsFragment()).commit();
            return true;
        }
    }

    private void rateApp() {
        Config config = new Config(15, 30);
        config.setTitle(R.string.my_own_title_rateme);
        config.setMessage(R.string.my_own_message_rateme);
        RateThisApp.init(config);
        RateThisApp.onStart(this);
        RateThisApp.showRateDialogIfNeeded(this);
    }

    public void openDetails(Class toActivity, Bundle bundle) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (this.current_id == item.getItemId()) {
            return false;
        }
        this.current_id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int i = this.current_id;
        if (i == R.id.news_of_day) {
            fragmentTransaction.replace(R.id.main_view, new NewsOfDayFragment()).commit();
            return true;
        } else if (i == R.id.school_hours) {
            fragmentTransaction.replace(R.id.main_view, new SchoolHoursFragment()).commit();
            return true;
        } else if (i != R.id.substitutions_schedule) {
            fragmentTransaction.replace(R.id.main_view, new SubstitutionScheduleFragment()).commit();
            return true;
        } else {
            return false;
        }
    }
}
