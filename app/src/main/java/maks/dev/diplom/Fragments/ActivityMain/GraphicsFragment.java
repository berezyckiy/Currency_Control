package maks.dev.diplom.Fragments.ActivityMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maks.dev.diplom.Activities.ActivityGraphics.ActivityGraphics;
import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */
public class GraphicsFragment extends Fragment implements View.OnClickListener {

    private TextView tvGraphicCurrencyFirst;
    private TextView tvGraphicCurrencySecond;
    private ImageView btnArrowLeftFirst;
    private ImageView btnArrowRightFirst;
    private ImageView btnArrowLeftSecond;
    private ImageView btnArrowRightSecond;
    private List<Map<String, Object>> currencyList;

    private View view;
    private int mapPosFirstTextView = 0;
    private int mapPosSecondTextView = 1;
    private boolean isDataInTextViewCorrect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_graphics, container, false);
        initItems();
        setDataTextView();
        return view;
    }

    private void initItems() {
        tvGraphicCurrencyFirst = (TextView) view.findViewById(R.id.tvGraphicCurrencyFirst);
        tvGraphicCurrencySecond = (TextView) view.findViewById(R.id.tvGraphicCurrencySecond);
        btnArrowLeftFirst = (ImageView) view.findViewById(R.id.btnArrowLeftFirst);
        btnArrowRightFirst = (ImageView) view.findViewById(R.id.btnArrowRightFirst);
        btnArrowLeftSecond = (ImageView) view.findViewById(R.id.btnArrowLeftSecond);
        btnArrowRightSecond = (ImageView) view.findViewById(R.id.btnArrowRightSecond);
        btnArrowLeftFirst.setOnClickListener(this);
        btnArrowRightFirst.setOnClickListener(this);
        btnArrowLeftSecond.setOnClickListener(this);
        btnArrowRightSecond.setOnClickListener(this);
        currencyList = MainFragment.currencyList;
        MainActivity.nvView.setCheckedItem(R.id.nav_graphics);
    }

    private void setDataTextView() {
        if (currencyList.size() == 0) {
            btnArrowLeftFirst.setVisibility(View.INVISIBLE);
            btnArrowRightFirst.setVisibility(View.INVISIBLE);
            btnArrowLeftSecond.setVisibility(View.INVISIBLE);
            btnArrowRightSecond.setVisibility(View.INVISIBLE);
            tvGraphicCurrencyFirst.setText("Choose more currencies");
            tvGraphicCurrencySecond.setText("Choose more currencies");
            return;
        }
        if (currencyList.size() == 1) {
            tvGraphicCurrencyFirst.setText(currencyList.get(0).get("name").toString());
            btnArrowLeftSecond.setVisibility(View.INVISIBLE);
            btnArrowRightSecond.setVisibility(View.INVISIBLE);
            tvGraphicCurrencySecond.setText("Choose more currencies");
            return;
        }
        tvGraphicCurrencyFirst.setText(currencyList.get(0).get("name").toString());
        tvGraphicCurrencySecond.setText(currencyList.get(1).get("name").toString());
        isDataInTextViewCorrect = true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_btnsubmit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnDone:
                if (!isDataInTextViewCorrect) {
                    Toast.makeText(getActivity(), "Add currency, please!", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent(getActivity(), ActivityGraphics.class);
                intent.putExtra("firstCurrency", (HashMap) currencyList.get(mapPosFirstTextView));
                intent.putExtra("secondCurrency", (HashMap) currencyList.get(mapPosSecondTextView));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private int currentPos = 0;
    @Override
    public void onClick(View v) {
        int maxPos = currencyList.size() - 1;
        String tmp;
        switch (v.getId()) {
            case R.id.btnArrowLeftFirst:
                if (currentPos == 0) {
                    currentPos = maxPos + 1;
                }
                tmp = currencyList.get(--currentPos).get("name").toString();
                if (tvGraphicCurrencySecond.getText().equals(tmp)) {
                    if (currentPos == 0) {
                        currentPos = maxPos + 1;
                    }
                    tvGraphicCurrencyFirst.setText(currencyList.get(--currentPos).get("name").toString());
                } else {
                    tvGraphicCurrencyFirst.setText(tmp);
                }
                mapPosFirstTextView = currentPos;
                break;
            case R.id.btnArrowLeftSecond:
                if (currentPos == 0) {
                    currentPos = maxPos + 1;
                }
                tmp = currencyList.get(--currentPos).get("name").toString();
                if (tvGraphicCurrencyFirst.getText().equals(tmp)) {
                    if (currentPos == 0) {
                        currentPos = maxPos + 1;
                    }
                    tvGraphicCurrencySecond.setText(currencyList.get(--currentPos).get("name").toString());
                } else {
                    tvGraphicCurrencySecond.setText(tmp);
                }
                mapPosSecondTextView = currentPos;
                break;
            case R.id.btnArrowRightFirst:
                if (currentPos == maxPos) {
                    currentPos = -1;
                }
                if (currentPos < maxPos) {
                    tmp = currencyList.get(++currentPos).get("name").toString();
                    if (tvGraphicCurrencySecond.getText().equals(tmp)) {
                        if (currentPos == maxPos) {
                            currentPos = -1;
                        }
                        tvGraphicCurrencyFirst.setText(currencyList.get(++currentPos).get("name").toString());
                    } else {
                        tvGraphicCurrencyFirst.setText(tmp);
                    }
                }
                mapPosFirstTextView = currentPos;
                break;
            case R.id.btnArrowRightSecond:
                if (currentPos == maxPos) {
                    currentPos = -1;
                }
                if (currentPos < maxPos) {
                    tmp = currencyList.get(++currentPos).get("name").toString();
                    if (tvGraphicCurrencyFirst.getText().equals(tmp)) {
                        if (currentPos == maxPos) {
                            currentPos = -1;
                        }
                        tvGraphicCurrencySecond.setText(currencyList.get(++currentPos).get("name").toString());
                    } else {
                        tvGraphicCurrencySecond.setText(tmp);
                    }
                }
                mapPosSecondTextView = currentPos;
                break;
        }
    }
}
