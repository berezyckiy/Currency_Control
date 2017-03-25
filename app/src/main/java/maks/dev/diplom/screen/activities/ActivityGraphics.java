package maks.dev.diplom.screen.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import maks.dev.diplom.screen.adapters.activity_graphics.ViewPagerAdapter;
import maks.dev.diplom.screen.fragments.activity_graphics.GraphicLinear;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/11/17.
 */
public class ActivityGraphics
        extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HashMap currencyOne;
    private HashMap currencyTwo;
    private Integer currentTheme;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationTheme();
        setContentView(R.layout.activity_graphis);
        initItems();
        buildToolbar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        changeVisualisation();
    }

    private void setApplicationTheme() {
        currentTheme = Integer.parseInt(getIntent().getStringExtra("theme"));
        setTheme(currentTheme);
    }

    private void initItems() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(Color.BLACK, Color.BLACK);
        currencyOne = (HashMap) getIntent().getSerializableExtra("firstCurrency");
        currencyTwo = (HashMap) getIntent().getSerializableExtra("secondCurrency");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void buildToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(currencyOne.get("name") + " -> " + currencyTwo.get("name"));
        }
    }

    private void changeVisualisation() {
        if (currentTheme != R.style.AppTheme) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.background_theme_inversion));
            tabLayout.setBackgroundColor(getResources().getColor(R.color.background_theme_inversion));
            viewPager.setBackgroundColor(Color.WHITE);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            toolbar.setTitleTextColor(Color.WHITE);
        } else {
            viewPager.setBackgroundColor(getResources().getColor(R.color.graphic_background));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemDone = menu.findItem(R.id.btnDone);
        if (currentTheme != R.style.AppTheme) {
            itemDone.setIcon(R.mipmap.ic_menu_done_white);
        } else {
            itemDone.setIcon(R.mipmap.ic_menu_done);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graphics_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnDone:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GraphicLinear(), getString(R.string.linear));
        viewPager.setAdapter(adapter);
    }
}
