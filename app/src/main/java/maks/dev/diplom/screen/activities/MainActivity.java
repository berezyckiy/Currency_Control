package maks.dev.diplom.screen.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
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
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

import maks.dev.diplom.R;
import maks.dev.diplom.data.db.DB;
import maks.dev.diplom.interfaces.DialogListener;
import maks.dev.diplom.screen.dialogs.DialogAbout;
import maks.dev.diplom.screen.dialogs.DialogDefaultSettings;
import maks.dev.diplom.screen.dialogs.DialogLanguage;
import maks.dev.diplom.screen.dialogs.DialogTheme;
import maks.dev.diplom.screen.fragments.activity_main.ChooseMainCurrency;
import maks.dev.diplom.screen.fragments.activity_main.ChooseYourCurrency;
import maks.dev.diplom.screen.fragments.activity_main.GraphicsFragment;
import maks.dev.diplom.screen.fragments.activity_main.MainFragment;
import maks.dev.diplom.screen.fragments.activity_main.SettingsFragment;
import maks.dev.diplom.utils.PreferenceUtils;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogListener, MainFragment.CollapseListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nvView;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FrameLayout collapsingFrameLayout;
    private AppBarLayout.OnOffsetChangedListener mListener;
    private Integer currentTheme;
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
        final Integer currentTheme = PreferenceUtils.getInteger(this, "appTheme", R.style.AppTheme);
        final Integer defaultTheme = R.style.AppTheme;
        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolbarLayout.getHeight() + verticalOffset < collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    tvToolbar.setAlpha(1);
                    if (!currentTheme.equals(defaultTheme)) {
                        toolbar.setBackgroundColor(getResources().getColor(R.color.background_theme_inversion));
                    } else {
                        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                } else {
                    tvToolbar.setAlpha(0);
                    toolbar.setBackgroundColor(0);
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
        currentTheme = PreferenceUtils.getInteger(this, "appTheme", R.style.AppTheme);
        setTheme(theme);
    }

    private void setApplicationLocale() {
        if (!PreferenceUtils.isContainsKey(this, "appLanguage")) {
            PreferenceUtils.saveString(this, "appLanguage", String.valueOf(Locale.getDefault()));
        } else {
            String mLang = PreferenceUtils.getString(this, "appLanguage", "");
            Locale mNewLocale = new Locale(mLang);
            Resources resources = getResources();
            Configuration configuration = new Configuration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(mNewLocale);
            } else {
                configuration.locale = mNewLocale;
            }
            resources.updateConfiguration(configuration, displayMetrics);
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

    private void changeVisualisation(String actionOfCollapse) {
        FrameLayout content = (FrameLayout) findViewById(R.id.contentFrame);
        content.setBackgroundColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.background_theme_inversion));
        toolbar.setTitleTextColor(Color.WHITE);
        if (actionOfCollapse.equals("enable")) {
            ImageView img = (ImageView) findViewById(R.id.imgViewBackground);
            img.setVisibility(View.VISIBLE);
        } else {
            ImageView img = (ImageView) findViewById(R.id.imgViewBackground);
            img.setVisibility(View.GONE);
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
            Cursor cursor = db.getAllData();
            if (cursor.moveToFirst()) {
                do {
                    cv = new ContentValues();
                    cv.put("isChecked", "true");
                    db.updateRec(cv, Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                } while (cursor.moveToNext());
            }
            db.close();
            PreferenceUtils.saveInteger(this, "appTheme", R.style.AppTheme);
            String mLang = String.valueOf(Locale.getDefault());
            PreferenceUtils.saveString(this, "appLanguage", mLang.toLowerCase());
            finish();
            startActivity(new Intent(this, this.getClass()));
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
                setToolbarTitle(getString(R.string.title_choose_main_currency));
                nvView.setCheckedItem(R.id.nav_choose_main_currency);
                ft.replace(R.id.contentFrame, new ChooseMainCurrency()).commit();
                break;
            case R.id.nav_choose_your_currency:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.choose_your_currencies));
                nvView.setCheckedItem(R.id.nav_choose_your_currency);
                ft.replace(R.id.contentFrame, new ChooseYourCurrency()).commit();
                break;
            case R.id.nav_graphics:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.graphics));
                nvView.setCheckedItem(R.id.nav_graphics);
                ft.replace(R.id.contentFrame, new GraphicsFragment()).commit();
                break;
            case R.id.nav_settings:
                enableToolbarTitle(true);
                setToolbarTitle(getString(R.string.settings));
                nvView.setCheckedItem(R.id.nav_settings);
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
    public void enableCollapse(final String base, final String rate, String baseFullName, final String value) {
        if (PreferenceUtils.getInteger(this, "appTheme", R.style.AppTheme) != R.style.AppTheme) {
            changeVisualisation("enable");
        }
        ImageView imgViewMain = (ImageView) findViewById(R.id.imgViewMain);
        TextView tvMainScreenBase = (TextView) findViewById(R.id.tvMainScreenBase);
        TextView tvMainScreenDate = (TextView) findViewById(R.id.tvMainScreenDate);
        TextView tvMainScreenRate = (TextView) findViewById(R.id.tvMainScreenRate);
        TextView tvMainScreenBaseFullName = (TextView) findViewById(R.id.tvMainScreenBaseFullName);
        CoordinatorLayout.LayoutParams defaultParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        float dpHeight = getResources().getDisplayMetrics().heightPixels / 3;
        defaultParams.height = (int) dpHeight;
        appBarLayout.setLayoutParams(defaultParams);
        collapsingFrameLayout.setVisibility(View.VISIBLE);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        imgViewMain.setBackgroundResource(PreferenceUtils.getImageIdOfCurrency(base));
        tvMainScreenBase.setText(base);
        tvMainScreenRate.setText(getFilteredSum(rate));
        tvMainScreenRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActivityChooseValue.class);
                intent.putExtra("name", base);
                intent.putExtra("value", value);
                intent.putExtra("theme", currentTheme.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        tvMainScreenDate.setText(getString(R.string.last_updated).concat(PreferenceUtils.getString(this, "date", "0")));
        tvMainScreenBaseFullName.setText(baseFullName);
        tvToolbar.setAlpha(0);
        tvToolbar.setText(base + " " + getFilteredSum(rate));
        tvToolbar.setVisibility(View.VISIBLE);
        appBarLayout.addOnOffsetChangedListener(mListener);
    }

    @Override
    public void disableCollapse() {
        if (PreferenceUtils.getInteger(this, "appTheme", R.style.AppTheme) != R.style.AppTheme) {
            changeVisualisation("disable");
        }
        CoordinatorLayout.LayoutParams customParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        appBarLayout.setLayoutParams(customParams);
        collapsingFrameLayout.setVisibility(View.GONE);
        tvToolbar.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        appBarLayout.removeOnOffsetChangedListener(mListener);
    }

    @Override
    public void disableTitle() {
        enableToolbarTitle(false);
        nvView.setCheckedItem(R.id.nav_currency_exchange);
    }

    @Override
    public void disableScrolling() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        params.setScrollFlags(0);
    }
}
