package maks.dev.diplom.screen.fragments.activity_main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maks.dev.diplom.screen.activities.MainActivity;
import maks.dev.diplom.R;
import maks.dev.diplom.screen.custom_view.MyTextView;
import maks.dev.diplom.utils.PreferenceUtils;

/**
 * Created by berezyckiy on 2/6/17.
 */
public class SettingsFragment
        extends Fragment {

    private View view;
    private MyTextView mTvTheme;
    private MyTextView mTvLanguage;
    private MyTextView mTvDefault;
    private MyTextView mTvAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        initItems();
        startAnimationTextView();
        MainActivity.nvView.setCheckedItem(R.id.nav_settings);
        return view;
    }

    private void initItems() {
        mTvTheme = (MyTextView) view.findViewById(R.id.tvTheme);
        mTvTheme.setIsVisible(false);
        mTvLanguage = (MyTextView) view.findViewById(R.id.tvLanguage);
        mTvLanguage.setIsVisible(false);
        mTvDefault = (MyTextView) view.findViewById(R.id.tvDefault);
        mTvDefault.setIsVisible(false);
        mTvAbout = (MyTextView) view.findViewById(R.id.tvAbout);
        mTvAbout.setIsVisible(false);
    }

    private void setShadowLayer(MyTextView textView) {
        if (PreferenceUtils.getInteger(getActivity(), "appTheme", R.style.AppTheme) != R.style.AppTheme) {
            textView.setShadowLayer(5f, 5f, 5f, Color.GRAY);
        } else {
            textView.setShadowLayer(5f, 10f, 10f, Color.BLACK);
        }
    }

    private void startAnimationTextView() {
        setShadowLayer(mTvTheme);
        mTvTheme.toggle();
        setShadowLayer(mTvLanguage);
        mTvLanguage.toggle();
        setShadowLayer(mTvDefault);
        mTvDefault.toggle();
        setShadowLayer(mTvAbout);
        mTvAbout.toggle();
    }
}
