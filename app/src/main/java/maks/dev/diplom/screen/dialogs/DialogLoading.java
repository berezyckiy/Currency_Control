package maks.dev.diplom.screen.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/17/17.
 */

public class DialogLoading
        extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(R.string.progress_dialog);
        builder.content(R.string.please_wait);
        builder.progress(true, 0);
        return builder.build();
    }
}
