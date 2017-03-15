package maks.dev.diplom.Activities.ActivityMain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        implements NavigationView.OnNavigationItemSelectedListener, DialogListener, MainFragment.CollapseListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public static NavigationView nvView;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FrameLayout collapsingFrameLayout;
    private AppBarLayout.OnOffsetChangedListener mListener;
    private TextView tvToolbar;

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
        initAppBarOnOffsetChangedListener();
    }

    private void initItems() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        collapsingFrameLayout = (FrameLayout) findViewById(R.id.collapsingFrameLayout);
        tvToolbar = (TextView) findViewById(R.id.tvToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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


    private void initAppBarOnOffsetChangedListener() {
        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolbarLayout.getHeight() + verticalOffset < collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    tvToolbar.setAlpha(1);
                } else {
                    tvToolbar.setAlpha(0);
                }
            }
        };
    }

    private void enableToolbarTitle(boolean showTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
        }
    }

    private void startMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        enableToolbarTitle(false);
        collapsingToolbarLayout.setTitleEnabled(false);
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
            PreferenceUtils.saveString(this, "appLanguage", String.valueOf(Locale.getDefault()));
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
        this.recreate();
    }

    private void changeLocale(String mLang) {
        PreferenceUtils.saveString(this, "appLanguage", mLang);
        this.recreate();
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
            ContentValues cv;
            Cursor c = db.getAllData();
            if (c.moveToFirst()) {
                do {
                    cv = new ContentValues();
                    cv.put("isChecked", "true");
                    db.updateRec(cv, Integer.parseInt(c.getString(c.getColumnIndex("id"))));
                } while (c.moveToNext());
            }
            db.close();
            if (PreferenceUtils.isContainsKey(this, "appTheme") || PreferenceUtils.isContainsKey(this, "appLanguage")) {
                PreferenceUtils.saveInteger(this, "appTheme", R.style.AppTheme);
                PreferenceUtils.saveString(this, "appLanguage", String.valueOf(Locale.getDefault().getDisplayLanguage()));
                finish();
                startActivity(new Intent(this, this.getClass()));
            }
        }
    }

    private void setToolbarTitle(@NonNull String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_currency_exchange:
                startMainFragment();
                break;
            case R.id.nav_choose_main_currency:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.choose_main_currency));
                ft.replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.choose_your_currencies));
                ft.replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.graphics));
                ft.replace(R.id.contentFrame, new GraphicsFragment()).commit();
                break;
            case R.id.nav_settings:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.settings));
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

    private String getFilteredSum(String result) {
        if (Double.parseDouble(result) != 0) {
            return new DecimalFormat("#,###.##").format(Double.parseDouble(result));
        } else {
            return "1";
        }
    }

    @Override
    public void enableCollapse(String base, String rate, String baseFullName) {
        ImageView imgViewMain = (ImageView) findViewById(R.id.imgViewMain);
        TextView tvMainScreenBase = (TextView) findViewById(R.id.tvMainScreenBase);
        TextView tvMainScreenDate = (TextView) findViewById(R.id.tvMainScreenDate);
        TextView tvMainScreenRate = (TextView) findViewById(R.id.tvMainScreenRate);
        TextView tvMainScreenBaseFullName = (TextView) findViewById(R.id.tvMainScreenBaseFullName);
        CoordinatorLayout.LayoutParams defaultParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        defaultParams.height = 386;
        appBarLayout.setLayoutParams(defaultParams);
        collapsingFrameLayout.setVisibility(View.VISIBLE);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        imgViewMain.setBackgroundResource(PreferenceUtils.getImageIdOfCurrency(base));
        tvMainScreenBase.setText(base);
        tvMainScreenRate.setText(getFilteredSum(rate));
        tvMainScreenDate.setText(getString(R.string.last_updated).concat(PreferenceUtils.getString(this, "date", "0")));
        tvMainScreenBaseFullName.setText(baseFullName);
        tvToolbar.setAlpha(0);
        tvToolbar.setText(base + " " + getFilteredSum(rate));
        tvToolbar.setVisibility(View.VISIBLE);
        appBarLayout.addOnOffsetChangedListener(mListener);
    }

    @Override
    public void disableCollapse() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        CoordinatorLayout.LayoutParams customParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        appBarLayout.setLayoutParams(customParams);
        collapsingFrameLayout.setVisibility(View.GONE);
        tvToolbar.setVisibility(View.GONE);
        appBarLayout.removeOnOffsetChangedListener(mListener);
    }

    @Override
    public void disableTitle() {
        enableToolbarTitle(false);
    }

    @Override
    public void disableScrolling() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(0);
    }
}
