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
public class GraphicsFragment
        extends Fragment
        implements View.OnClickListener {

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
            tvGraphicCurrencyFirst.setText(getString(R.string.add_currency_please));
            tvGraphicCurrencySecond.setText(getString(R.string.add_currency_please));
            return;
        }
        if (currencyList.size() == 1) {
            tvGraphicCurrencyFirst.setText(currencyList.get(0).get("name").toString());
            btnArrowLeftSecond.setVisibility(View.INVISIBLE);
            btnArrowRightSecond.setVisibility(View.INVISIBLE);
            tvGraphicCurrencySecond.setText(getString(R.string.add_currency_please));
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
                    Toast.makeText(getActivity(), getString(R.string.add_currency_please), Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent(getActivity(), ActivityGraphics.class);
                intent.putExtra("firstCurrency", (HashMap) currencyList.get(mapPosFirstTextView));
                intent.putExtra("secondCurrency", (HashMap) currencyList.get(mapPosSecondTextView));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private int currentPosTop = 0;
    private int currentPosBottom = 1;
    @Override
    public void onClick(View v) {
        int maxPos = currencyList.size() - 1;
        String tmp;
        switch (v.getId()) {
            case R.id.btnArrowLeftFirst:
                if (currentPosTop == 0) {
                    currentPosTop = maxPos + 1;
                }
                currentPosTop--;
                tmp = currencyList.get(currentPosTop).get("name").toString();
                if (tvGraphicCurrencySecond.getText().equals(tmp)) {
                    if (currentPosTop == 0) {
                        currentPosTop = maxPos + 1;
                    }
                    currentPosTop--;
                    tvGraphicCurrencyFirst.setText(currencyList.get(currentPosTop).get("name").toString());
                } else {
                    tvGraphicCurrencyFirst.setText(tmp);
                }
                mapPosFirstTextView = currentPosTop;
                break;

            case R.id.btnArrowRightFirst:
            if (currentPosTop == maxPos) {
                currentPosTop = -1;
            }
            if (currentPosTop < maxPos) {
                currentPosTop++;
                tmp = currencyList.get(currentPosTop).get("name").toString();
                if (tvGraphicCurrencySecond.getText().equals(tmp)) {
                    if (currentPosTop == maxPos) {
                        currentPosTop = -1;
                    }
                    currentPosTop++;
                    tvGraphicCurrencyFirst.setText(currencyList.get(currentPosTop).get("name").toString());
                } else {
                    tvGraphicCurrencyFirst.setText(tmp);
                }
            }
            mapPosFirstTextView = currentPosTop;
            break;

            case R.id.btnArrowLeftSecond:
                if (currentPosBottom == 0) {
                    currentPosBottom = maxPos + 1;
                }
                currentPosBottom--;
                tmp = currencyList.get(currentPosBottom).get("name").toString();
                if (tvGraphicCurrencyFirst.getText().equals(tmp)) {
                    if (currentPosBottom == 0) {
                        currentPosBottom = maxPos + 1;
                    }
                    currentPosBottom--;
                    tvGraphicCurrencySecond.setText(currencyList.get(currentPosBottom).get("name").toString());
                } else {
                    tvGraphicCurrencySecond.setText(tmp);
                }
                mapPosSecondTextView = currentPosBottom;
                break;

            case R.id.btnArrowRightSecond:
            if (currentPosBottom == maxPos) {
                    currentPosBottom = -1;
                }
                if (currentPosBottom < maxPos) {
                    currentPosBottom++;
                    tmp = currencyList.get(currentPosBottom).get("name").toString();
                    if (tvGraphicCurrencyFirst.getText().equals(tmp)) {
                        if (currentPosBottom == maxPos) {
                            currentPosBottom = -1;
                        }
                        currentPosBottom++;
                        tvGraphicCurrencySecond.setText(currencyList.get(currentPosBottom).get("name").toString());
                    } else {
                        tvGraphicCurrencySecond.setText(tmp);
                    }
                }
                mapPosSecondTextView = currentPosBottom;
                break;
        }
    }
}
