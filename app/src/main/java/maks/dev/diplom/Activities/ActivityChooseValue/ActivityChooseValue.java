package maks.dev.diplom.Activities.ActivityChooseValue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class ActivityChooseValue
        extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private TextView tvTempSum;

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

    private void initItems() {
        TextView btnClear = (TextView) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnClear.setOnLongClickListener(this);
        tvTempSum = (TextView) findViewById(R.id.tvTempSum);
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

    private String getFilteredResult(String result) {
        if (Double.parseDouble(result) != 0) {
            return new DecimalFormat("#######.############").format(Double.parseDouble(result));
        } else {
            return "1";
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
                intent.putExtra("sum", getFilteredResult(tvTempSum.getText().toString()));
                intent.putExtra("name", getSupportActionBar() != null ? getSupportActionBar().getTitle() : "");
                intent.putExtra("value", getIntent().getStringExtra("value"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
