package maks.dev.diplom.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import maks.dev.diplom.Interface.DialogListener;
import maks.dev.diplom.R;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/13/17.
 */

public class DialogTheme
        extends DialogFragment
        implements View.OnClickListener {

    private View view;
    private TextView tvTheme;
    private DialogListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_theme, container, false);
        initItems();
        setDataOnTextView();
        getDialog().setTitle(getString(R.string.choose_your_theme));
        return view;
    }

    private void initItems() {
        ImageView btnArrowLeft = (ImageView) view.findViewById(R.id.btnArrowLeft);
        ImageView btnArrowRight = (ImageView) view.findViewById(R.id.btnArrowRight);
        Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvTheme = (TextView) view.findViewById(R.id.tvTheme);
        btnArrowLeft.setOnClickListener(this);
        btnArrowRight.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setDataOnTextView() {
        if (PreferenceUtils.isContainsKey(getActivity(), "appTheme")) {
            Integer tmp = PreferenceUtils.getInteger(getActivity(), "appTheme", R.style.AppTheme);
            switch (tmp) {
                case R.style.AppTheme:
                    tvTheme.setText(getString(R.string.theme_default));
                    break;
                case R.style.AppThemeWhite:
                    tvTheme.setText(getString(R.string.theme_light));
                    break;
            }
        }
    }

    private void onClickSetData() {
        String tmp = tvTheme.getText().toString();
        if (tmp.equals(getString(R.string.theme_default))) {
            tvTheme.setText(getString(R.string.theme_light));
        } else {
            tvTheme.setText(getString(R.string.theme_default));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnArrowLeft:
                onClickSetData();
                break;
            case R.id.btnArrowRight:
                onClickSetData();
                break;
            case R.id.btnSubmit:
                dismiss();
                switch (tvTheme.getText().toString()) {
                    case "Default":
                        mListener.onFinishThemeDialog(R.style.AppTheme);
                        break;
                    case "Light":
                        mListener.onFinishThemeDialog(R.style.AppThemeWhite);
                        break;
                    case "Стандартная":
                        mListener.onFinishThemeDialog(R.style.AppTheme);
                        break;
                    case "Светлая":
                        mListener.onFinishThemeDialog(R.style.AppThemeWhite);
                        break;
                }
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }
}
