package maks.dev.diplom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import maks.dev.diplom.Interface.DialogListener;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class DialogLanguage
        extends DialogFragment {

    private DialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(R.string.choose_your_language);
        builder.items(R.array.languages).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                if (text.toString().equals("English")) {
                    mListener.onFinishLanguageDialog("en_US");
                } else {
                    mListener.onFinishLanguageDialog("ru");
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
