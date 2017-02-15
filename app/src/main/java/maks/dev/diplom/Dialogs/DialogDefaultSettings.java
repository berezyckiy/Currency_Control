package maks.dev.diplom.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import maks.dev.diplom.Interface.DialogListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class DialogDefaultSettings
        extends DialogFragment
        implements View.OnClickListener {

    private View view;
    private DialogListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_default, container, false);
        initItems();
        getDialog().setTitle(getString(R.string.choose_your_language));
        return view;
    }

    private void initItems() {
        Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                dismiss();
                mListener.onFinishSetDefaultDialog("yes");
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
