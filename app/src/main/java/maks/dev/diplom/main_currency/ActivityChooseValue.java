package maks.dev.diplom.main_currency;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import maks.dev.diplom.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class ActivityChooseValue
        extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private TextView tvTempSum;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private Integer currentTheme;
    private LinearLayout mainLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationTheme();
        setContentView(R.layout.activity_choose_value);
        initItems();
        buildToolbar();
        changeVisualisation();
    }

    private void buildToolbar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initItems() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        TextView btnClear = (TextView) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnClear.setOnLongClickListener(this);
        tvTempSum = (TextView) findViewById(R.id.tvTempSum);
        LinearLayout.LayoutParams defaultParams = (LinearLayout.LayoutParams) appBarLayout.getLayoutParams();
        float dpHeight = getResources().getDisplayMetrics().heightPixels / 3;
        defaultParams.height = (int) dpHeight;
        appBarLayout.setLayoutParams(defaultParams);
    }

    private void setApplicationTheme() {
        currentTheme = Integer.parseInt(getIntent().getStringExtra("theme"));
        setTheme(currentTheme);
    }

    private void changeVisualisation() {
        if (currentTheme != R.style.AppTheme) {
            appBarLayout.setBackgroundResource(R.drawable.ic_main_frame_background);
            mainLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar.setTitleTextColor(Color.WHITE);
        } else {
            appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mainLinearLayout.setBackgroundResource(R.drawable.ic_main_frame_background);
        }
    }


    public void onClickButton1(View view) {
        printData("1");
    }

    public void onClickButton2(View view) {
        printData("2");
    }

    public void onClickButton3(View view) {
        printData("3");
    }

    public void onClickButton4(View view) {
        printData("4");
    }

    public void onClickButton5(View view) {
        printData("5");
    }

    public void onClickButton6(View view) {
        printData("6");
    }

    public void onClickButton7(View view) {
        printData("7");
    }

    public void onClickButton8(View view) {
        printData("8");
    }

    public void onClickButton9(View view) {
        printData("9");
    }

    public void onClickButtonPoint(View view) {
        String tmpText = tvTempSum.getText().toString();
        if (!tmpText.contains(".")) {
            tvTempSum.setText(tmpText.concat("."));
        }
    }

    public void onClickButtonZero(View view) {
        printData("0");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                if (!tvTempSum.getText().equals("0")) {
                    String tmp = tvTempSum.getText().toString();
                    if (tmp.length() == 1) {
                        tvTempSum.setText("0");
                    } else {
                        tvTempSum.setText(tmp.substring(0, tmp.length() - 1));
                    }
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (!tvTempSum.getText().equals("0")) {
            tvTempSum.setText("0");
        }
        return true;
    }

    private void printData(String btnNumber) {
        if (tvTempSum.getText().equals("0")) {
            tvTempSum.setText(btnNumber);
        } else {
            tvTempSum.setText(tvTempSum.getText().toString().concat(btnNumber));
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
                intent.putExtra("sum", tvTempSum.getText().toString());
                intent.putExtra("name", getSupportActionBar() != null ? getSupportActionBar().getTitle() : "");
                intent.putExtra("value", getIntent().getStringExtra("value"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
