package maks.dev.diplom.Activities.ActivityGraphics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Adapters.ActivityGraphics.ViewPagerAdapter;
import maks.dev.diplom.Fragments.ActivityGraphics.GraphicLinear;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphis);
        initItems();
        buildToolbar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initItems() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        currencyOne = (HashMap) getIntent().getSerializableExtra("firstCurrency");
        currencyTwo = (HashMap) getIntent().getSerializableExtra("secondCurrency");
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(currencyOne.get("name") + " -> " + currencyTwo.get("name"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_btnsubmit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnDone:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GraphicLinear(), getString(R.string.linear));
//        adapter.addFragment(new GraphicDiagram(), getString(R.string.diagram));
        viewPager.setAdapter(adapter);
    }
}
