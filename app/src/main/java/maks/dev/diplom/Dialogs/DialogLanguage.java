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
 * Created by berezyckiy on 2/14/17.
 */

public class DialogLanguage
        extends DialogFragment
        implements View.OnClickListener {

    private View view;
    private Button btnSubmit;
    private Button btnCancel;
    private TextView tvLanguage;
    private DialogListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_language, container, false);
        initItems();
        setDataOnTextView();
        getDialog().setTitle(getString(R.string.choose_your_language));
        return view;
    }

    private void initItems() {
        ImageView btnArrowLeft = (ImageView) view.findViewById(R.id.btnArrowLeft);
        ImageView btnArrowRight = (ImageView) view.findViewById(R.id.btnArrowRight);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvLanguage = (TextView) view.findViewById(R.id.tvTheme);
        btnArrowLeft.setOnClickListener(this);
        btnArrowRight.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setDataOnTextView() {
        if (PreferenceUtils.isContainsKey(getActivity(), "appLanguage")) {
            String tmp = PreferenceUtils.getString(getActivity(), "appLanguage", "en");
            switch (tmp) {
                case "en":
                    tvLanguage.setText(getString(R.string.language_english));
                    break;
                case "ru":
                    tvLanguage.setText(getString(R.string.language_russian));
                    break;
            }
        } else {
            tvLanguage.setText(getString(R.string.language_english));
        }
    }

    @Override
    public void onClick(View v) {
        String tmp = tvLanguage.getText().toString();
        switch (v.getId()) {
            case R.id.btnArrowLeft:
                if (tmp.equals("English")) {
                    btnSubmit.setText("Принять");
                    btnCancel.setText("Отмена");
                    tvLanguage.setText("Русский");
                    getDialog().setTitle("Выберите ваш язык");
                } else {
                    btnSubmit.setText("Submit");
                    btnCancel.setText("Cancel");
                    tvLanguage.setText(getString(R.string.language_english));
                    getDialog().setTitle("Choose your language");
                }
                break;
            case R.id.btnArrowRight:
                if (tmp.equals("English")) {
                    btnSubmit.setText("Принять");
                    btnCancel.setText("Отмена");
                    tvLanguage.setText("Русский");
                    getDialog().setTitle("Выберите ваш язык");
                } else {
                    btnSubmit.setText("Submit");
                    btnCancel.setText("Cancel");
                    tvLanguage.setText(getString(R.string.language_english));
                    getDialog().setTitle("Choose your language");
                }
                break;
            case R.id.btnSubmit:
                dismiss();
                switch (tvLanguage.getText().toString()) {
                    case "English":
                        mListener.onFinishLanguageDialog("en");
                        break;
                    case "Русский":
                        mListener.onFinishLanguageDialog("ru");
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
