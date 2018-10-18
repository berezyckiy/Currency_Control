package maks.dev.diplom.base;

/**
 * Created by berezyckiy on 2/14/17.
 */

public interface DialogListener {

    void onFinishThemeDialog(Integer chosenThemeId);

    void onFinishLanguageDialog(String language);

    void onFinishSetDefaultDialog(String result);

}
