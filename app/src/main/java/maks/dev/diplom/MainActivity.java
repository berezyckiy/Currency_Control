package maks.dev.diplom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import maks.dev.diplom.Fragments.ChooseMainCurrency;
import maks.dev.diplom.Fragments.ChooseYourCurrency;
import maks.dev.diplom.Fragments.GrafficsFragment;
import maks.dev.diplom.Fragments.MainFragment;
import maks.dev.diplom.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout contentFrame;
    private NavigationView nvView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initItems();
        setSupportActionBar(toolbar);
        nvView.setNavigationItemSelectedListener(this);
    }

    private void initItems() {
        contentFrame = (LinearLayout) findViewById(R.id.contentFrame);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nvView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_currency_exchange:
                toolbar.setTitle("Currency exchange");
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new MainFragment()).commit();
                break;
            case R.id.nav_choose_main_currency:
                toolbar.setTitle("Choose main currency");
                toolbar.setBackgroundColor(Color.CYAN);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                toolbar.setTitle("Choose your currency");
                toolbar.setBackgroundColor(Color.GRAY);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                toolbar.setTitle("Graphics");
                toolbar.setBackgroundColor(Color.GREEN);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new GrafficsFragment()).commit();
                break;
            case R.id.nav_settings:
                toolbar.setTitle("Settings");
                toolbar.setBackgroundColor(Color.YELLOW);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new SettingsFragment()).commit();
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
