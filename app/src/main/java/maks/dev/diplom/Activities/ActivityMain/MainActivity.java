package maks.dev.diplom.Activities.ActivityMain;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import maks.dev.diplom.Fragments.ChooseMainCurrency;
import maks.dev.diplom.Fragments.ChooseYourCurrency;
import maks.dev.diplom.Fragments.GraphicsFragment;
import maks.dev.diplom.Fragments.MainFragment;
import maks.dev.diplom.Fragments.SettingsFragment;
import maks.dev.diplom.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public static NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initItems();
        setSupportActionBar(toolbar);
        nvView.setNavigationItemSelectedListener(this);
        addDrawerToggle();
        startMainFragment();
    }

    private void initItems() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nvView);
    }

    private void addDrawerToggle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.home_navigation_drawer_open, R.string.home_navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void startMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        toolbar.setTitle("Currency exchange");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        ft.replace(R.id.contentFrame, new MainFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_currency_exchange:
                toolbar.setTitle("Currency exchange");
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                ft.replace(R.id.contentFrame, new MainFragment()).commit();
                break;
            case R.id.nav_choose_main_currency:
                toolbar.setTitle("Choose main currency");
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                ft.replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                toolbar.setTitle("Choose your currencies");
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                ft.replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                toolbar.setTitle("Graphics");
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                ft.replace(R.id.contentFrame, new GraphicsFragment()).commit();
                break;
            case R.id.nav_settings:
                toolbar.setTitle("Settings");
                toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
                ft.replace(R.id.contentFrame, new SettingsFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
