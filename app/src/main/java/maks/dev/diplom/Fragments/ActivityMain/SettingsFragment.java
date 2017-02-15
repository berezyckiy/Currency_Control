package maks.dev.diplom.Fragments.ActivityMain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maks.dev.diplom.Activities.ActivityMain.MainActivity;
import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */
public class SettingsFragment
        extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.nvView.setCheckedItem(R.id.nav_settings);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
