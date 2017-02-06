package maks.dev.diplom.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maks.dev.diplom.R;

/**
 * Created by berezyckiy on 2/6/17.
 */
public class GrafficsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graffic_fragment, null);
    }
}
