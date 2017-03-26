package maks.dev.diplom.screen.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;

import maks.dev.diplom.BuildConfig;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class DialogAbout
        extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(R.string.app_name);
        String version = getString(R.string.version);
        String contacts = getString(R.string.contacts);
        String developer = getString(R.string.developer);
        builder.content(version + BuildConfig.VERSION_NAME + "\n" + contacts + "\n" + developer);
        builder.positiveText(R.string.button_close);
        return builder.build();
    }
}
