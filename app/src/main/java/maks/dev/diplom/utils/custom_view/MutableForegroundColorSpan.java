package maks.dev.diplom.utils.custom_view;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Created by walfud on 2015/5/29.
 */
public class MutableForegroundColorSpan
        extends CharacterStyle
        implements UpdateAppearance {

    private int mColor;

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(mColor);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
