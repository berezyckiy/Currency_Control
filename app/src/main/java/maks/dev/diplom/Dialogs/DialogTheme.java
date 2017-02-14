package maks.dev.diplom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import maks.dev.diplom.Interface.ChangeThemeDialogListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/13/17.
 */

public class DialogTheme extends DialogFragment implements View.OnClickListener {

    private View view;
    private ImageView btnArrowLeft;
    private ImageView btnArrowRight;
    private Button btnSubmit;
    private Button btnCancel;
    private TextView tvTheme;
    private ChangeThemeDialogListener mListener;
    private SharedPreferences sPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_theme, container, false);
        initItems();
        setDataOnTextView();
        getDialog().setTitle("Choose your theme");
        return view;
    }

    private void initItems() {
        btnArrowLeft = (ImageView) view.findViewById(R.id.btnArrowLeft);
        btnArrowRight = (ImageView) view.findViewById(R.id.btnArrowRight);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvTheme = (TextView) view.findViewById(R.id.tvTheme);
        btnArrowLeft.setOnClickListener(this);
        btnArrowRight.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setDataOnTextView() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sPref.contains("appTheme")) {
            Integer tmp = sPref.getInt("appTheme", 0);
            switch (tmp) {
                case R.style.AppTheme:
                    tvTheme.setText("Default");
                    break;
                case R.style.AppThemeWhite:
                    tvTheme.setText("Light");
                    break;
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private String tmp;

    @Override
    public void onClick(View v) {
        tmp = tvTheme.getText().toString();
        switch (v.getId()) {
            case R.id.btnArrowLeft:
                if (tmp.equals("Default")) {
                    tvTheme.setText("Light");
                } else {
                    tvTheme.setText("Default");
                }
                break;
            case R.id.btnArrowRight:
                if (tmp.equals("Default")) {
                    tvTheme.setText("Light");
                } else {
                    tvTheme.setText("Default");
                }
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
            mListener = (ChangeThemeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ChangeThemeDialogListener");
        }
    }
}
