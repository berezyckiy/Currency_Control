package maks.dev.diplom.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import maks.dev.diplom.base.DialogListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/13/17.
 */

public class DialogTheme
        extends DialogFragment {

    private DialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(R.string.choose_your_theme);
        builder.items(R.array.themes).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                if (position == 0) {
                    mListener.onFinishThemeDialog(R.style.AppTheme);
                } else {
                    mListener.onFinishThemeDialog(R.style.AppThemeColors);
                }
            }
        });
        builder.negativeText(R.string.button_disagree);
        return builder.build();
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
