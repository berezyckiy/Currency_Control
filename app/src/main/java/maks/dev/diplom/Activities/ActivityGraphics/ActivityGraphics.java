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
import maks.dev.diplom.Fragments.ActivityGraphics.GraphicDiagram;
import maks.dev.diplom.Fragments.ActivityGraphics.GraphicLinear;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/11/17.
 */
public class ActivityGraphics extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphis);
        HashMap currencyOne = (HashMap) getIntent().getSerializableExtra("firstCurrency");
        HashMap currencyTwo = (HashMap) getIntent().getSerializableExtra("secondCurrency");

        initItems();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(currencyOne.get("name") + " -> " + currencyTwo.get("name"));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initItems() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

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
            case R.id.btnSubmit:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GraphicLinear(), "Linear");
        adapter.addFragment(new GraphicDiagram(), "Diagram");
        viewPager.setAdapter(adapter);
    }
}
