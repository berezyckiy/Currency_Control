package maks.dev.diplom.Activities.ActivityMain;

import android.content.Intent;
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

import java.util.Locale;

import maks.dev.diplom.Data.DB;
import maks.dev.diplom.Dialogs.DialogAbout;
import maks.dev.diplom.Dialogs.DialogDefaultSettings;
import maks.dev.diplom.Dialogs.DialogLanguage;
import maks.dev.diplom.Dialogs.DialogTheme;
import maks.dev.diplom.Fragments.ActivityMain.ChooseMainCurrency;
import maks.dev.diplom.Fragments.ActivityMain.ChooseYourCurrency;
import maks.dev.diplom.Fragments.ActivityMain.GraphicsFragment;
import maks.dev.diplom.Fragments.ActivityMain.MainFragment;
import maks.dev.diplom.Fragments.ActivityMain.SettingsFragment;
import maks.dev.diplom.Interface.DialogListener;
import maks.dev.diplom.R;
import maks.dev.diplom.utils.PreferenceUtils;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public static NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationTheme();
        setApplicationLocale();
        setContentView(R.layout.activity_main);
        initItems();
        setSupportActionBar(toolbar);
        addDrawerToggle();
        startMainFragment();
    }

    private void initItems() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.currency_exchange));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvView = (NavigationView) findViewById(R.id.nvView);
        nvView.setNavigationItemSelectedListener(this);
    }

    private void addDrawerToggle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.home_navigation_drawer_open, R.string.home_navigation_drawer_close) {

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
        setToolbarTitle(R.string.currency_exchange);
        setToolbarTitleTextColor(android.R.color.white);
        ft.replace(R.id.contentFrame, new MainFragment()).commit();
    }

    private void setApplicationTheme() {
        Integer theme = R.style.AppTheme;
        if (!PreferenceUtils.isContainsKey(this, "appTheme")) {
            PreferenceUtils.saveInteger(this, "appTheme", R.style.AppTheme);
        } else {
            theme = PreferenceUtils.getInteger(this, "appTheme", R.style.AppTheme);
        }
        setTheme(theme);
    }

    private void setApplicationLocale() {
        if (!PreferenceUtils.isContainsKey(this, "appLanguage")) {
            PreferenceUtils.saveString(this, "appLanguage", "en");
        } else {
            String mLang = PreferenceUtils.getString(this, "appLanguage", "");
            Locale mNewLocale = new Locale(mLang);
            Locale.setDefault(mNewLocale);
            android.content.res.Configuration configuration = new android.content.res.Configuration();
            configuration.locale = mNewLocale;
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }

    private void changeTheme(Integer themeId) {
        PreferenceUtils.saveInteger(this, "appTheme", themeId);
        finish();
        startActivity(new Intent(this, this.getClass()));
    }

    private void changeLocale(String mLang) {
        PreferenceUtils.saveString(this, "appLanguage", mLang);
        finish();
        startActivity(new Intent(this, this.getClass()));
    }

    public void showDialogTheme(View v) {
        new DialogTheme().show(getSupportFragmentManager(), "ThemeDialog");
    }

    public void showDialogLanguage(View v) {
        new DialogLanguage().show(getSupportFragmentManager(), "LanguageDialog");
    }

    public void showDialogSetDefaultSettings(View v) {
        new DialogDefaultSettings().show(getSupportFragmentManager(), "SetDefaultDialog");
    }

    public void showDialogAbout(View v) {
        new DialogAbout().show(getSupportFragmentManager(), "AboutDialog");
    }

    @Override
    public void onFinishThemeDialog(Integer chosenThemeId) {
        if (chosenThemeId != PreferenceUtils.getInteger(this, "appTheme", 0)) {
            changeTheme(chosenThemeId);
        }
    }

    @Override
    public void onFinishLanguageDialog(String language) {
        if (!language.equals(PreferenceUtils.getString(this, "appLanguage", ""))) {
            changeLocale(language);
        }
    }

    @Override
    public void onFinishSetDefaultDialog(String result) {
        if (result.equals("yes")) {
            DB db = new DB(this);
            db.open();
            db.delAllData();
            db.close();
            if (PreferenceUtils.isContainsKey(this, "appTheme") || PreferenceUtils.isContainsKey(this, "appLanguage")) {
                PreferenceUtils.saveInteger(this, "appTheme", R.style.AppTheme);
                PreferenceUtils.saveString(this, "appLanguage", "en");
                finish();
                startActivity(new Intent(this, this.getClass()));
            }
        }
    }

    private void setToolbarTitle(@NonNull Integer stringId) {
        toolbar.setTitle(getString(stringId));
    }

    private void setToolbarTitleTextColor(@NonNull Integer colorId) {
        toolbar.setTitleTextColor(getResources().getColor(colorId));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_currency_exchange:
                setToolbarTitle(R.string.currency_exchange);
                setToolbarTitleTextColor(android.R.color.white);
                ft.replace(R.id.contentFrame, new MainFragment()).commit();
                break;
            case R.id.nav_choose_main_currency:
                setToolbarTitle(R.string.choose_main_currency);
                setToolbarTitleTextColor(android.R.color.white);
                ft.replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                setToolbarTitle(R.string.choose_your_currencies);
                setToolbarTitleTextColor(android.R.color.white);
                ft.replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                setToolbarTitle(R.string.graphics);
                setToolbarTitleTextColor(android.R.color.white);
                ft.replace(R.id.contentFrame, new GraphicsFragment()).commit();
                break;
            case R.id.nav_settings:
                setToolbarTitle(R.string.settings);
                setToolbarTitleTextColor(android.R.color.white);
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
