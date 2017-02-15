package maks.dev.diplom.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import maks.dev.diplom.BuildConfig;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class DialogAbout
        extends DialogFragment {

    private View view;
    private TextView tvAppVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_about, container, false);
        iniItems();
        getAppVersion();
        return view;
    }

    private void iniItems() {
        tvAppVersion = (TextView) view.findViewById(R.id.tvAppVersion);
        getDialog().setTitle(getString(R.string.about));
    }

    private void getAppVersion() {
        tvAppVersion.setText(BuildConfig.VERSION_NAME);
    }
}
