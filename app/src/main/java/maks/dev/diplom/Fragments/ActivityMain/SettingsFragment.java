package maks.dev.diplom.Fragments.ActivityMain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.Dialogs.DialogTheme;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */
public class SettingsFragment extends Fragment {

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        initItems();
        return view;
    }

    private void initItems() {
        MainActivity.nvView.setCheckedItem(R.id.nav_settings);
    }
}
