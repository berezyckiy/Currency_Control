package maks.dev.diplom;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import maks.dev.diplom.Fragments.ChooseMainCurrency;
import maks.dev.diplom.Fragments.ChooseYourCurrency;
import maks.dev.diplom.Fragments.GrafficsFragment;
import maks.dev.diplom.Fragments.MainFragment;
import maks.dev.diplom.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , SwipeRefreshLayout.OnRefreshListener {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nvView;

    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initItems();
        setSupportActionBar(toolbar);
        nvView.setNavigationItemSelectedListener(this);
        addDrawerToggle();
        buildRecycleView();
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                TextView tv = (TextView) findViewById(R.id.tvMainScreen);
                tv.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    private void buildRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CurrencyFactory.Currency> currencyList = CurrencyFactory.getCurrencyList();
        mAdapter = new RecyclerViewAdapter(currencyList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initItems() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nvView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
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

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_currency_exchange:
                toolbar.setTitle("Currency exchange");
                ft.replace(R.id.contentFrame, new MainFragment()).commit();
                break;
            case R.id.nav_choose_main_currency:
                toolbar.setTitle("Choose main currency");
                ft.replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                toolbar.setTitle("Choose your currencies");
                ft.replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                toolbar.setTitle("Graphics");
                ft.replace(R.id.contentFrame, new GrafficsFragment()).commit();
                break;
            case R.id.nav_settings:
                toolbar.setTitle("Settings");
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
