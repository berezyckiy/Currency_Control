package maks.dev.diplom.Activities.ActivityChooseValue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class ActivityChooseValue
        extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private TextView tvTempValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_value);
        initItems();
        buildToolbar();
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("value", tvTempValue.getText().toString());
                intent.putExtra("name",  getSupportActionBar() != null ? getSupportActionBar().getTitle() : "");
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initItems() {
        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnClear.setOnLongClickListener(this);
        tvTempValue = (TextView) findViewById(R.id.tvTempValue);
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
        printData(".");
    }

    public void onClickButtonZero(View view) {
        printData("0");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                if (!tvTempValue.getText().equals("0")) {
                    String tmp = tvTempValue.getText().toString();
                    if (tmp.length() == 1) {
                        tvTempValue.setText("0");
                    } else {
                        tvTempValue.setText(tmp.substring(0, tmp.length() - 1));
                    }
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (!tvTempValue.getText().equals("0")) {
            tvTempValue.setText("0");
        }
        return true;
    }

    private void printData(String btnNumber) {
        if (tvTempValue.getText().equals("0")) {
            tvTempValue.setText(btnNumber);
        } else {
            tvTempValue.setText(tvTempValue.getText().toString().concat(btnNumber));
        }
    }

}
