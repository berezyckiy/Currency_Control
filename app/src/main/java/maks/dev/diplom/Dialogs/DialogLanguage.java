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

import maks.dev.diplom.Interface.ChangeThemeDialogListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class DialogLanguage extends DialogFragment implements View.OnClickListener {

    private View view;
    private ImageView btnArrowLeft;
    private ImageView btnArrowRight;
    private Button btnSubmit;
    private Button btnCancel;
    private TextView tvTheme;
    private ChangeThemeDialogListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_language, container, false);
        initItems();
        getDialog().setTitle("Choose your language");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnArrowLeft:

                break;
            case R.id.btnArrowRight:

                break;
            case R.id.btnSubmit:

                break;
            case R.id.btnCancel:

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
