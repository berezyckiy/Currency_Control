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

public class ActivityChooseValue extends AppCompatActivity {

    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix,
            btnSeven, btnEight, btnNine, btnPoint, btnZero, btnClear;
    private TextView tvTempValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_value);
        buildToolbar();
        initItems();
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                intent.putExtra("name", getSupportActionBar().getTitle());
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initItems() {
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnPoint = (Button) findViewById(R.id.btnPoint);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnClear = (Button) findViewById(R.id.btnClear);
        tvTempValue = (TextView) findViewById(R.id.tvTempValue);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOne:
                printData("1");
                break;
            case R.id.btnTwo:
                printData("2");
                break;
            case R.id.btnThree:
                printData("3");
                break;
            case R.id.btnFour:
                printData("4");
                break;
            case R.id.btnFive:
                printData("5");
                break;
            case R.id.btnSix:
                printData("6");
                break;
            case R.id.btnSeven:
                printData("7");
                break;
            case R.id.btnEight:
                printData("8");
                break;
            case R.id.btnNine:
                printData("9");
                break;
            case R.id.btnPoint:
                printData(".");
                break;
            case R.id.btnZero:
                printData("0");
                break;
            case R.id.btnClear:
                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!tvTempValue.getText().equals("0")) {
                            String tmp = tvTempValue.getText().toString();
                            if (tmp.length() == 1) {
                                tvTempValue.setText("0");
                            } else {
                                tvTempValue.setText(tmp.substring(0, tmp.length() - 1));
                            }
                        }
                    }
                });
                btnClear.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!tvTempValue.getText().equals("0")) {
                            tvTempValue.setText("0");
                        }
                        return true;
                    }
                });
                break;
        }
    }

    private void printData(String btnNumber) {
        if (tvTempValue.getText().equals("0")) {
            tvTempValue.setText(btnNumber);
        } else {
            tvTempValue.setText(tvTempValue.getText().toString().concat(btnNumber));
        }
    }
}
