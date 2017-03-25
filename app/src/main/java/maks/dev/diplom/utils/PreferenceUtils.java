package maks.dev.diplom.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import maks.dev.diplom.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by berezyckiy on 2/14/17.
 */

public final class PreferenceUtils {

    public static void saveString(@NonNull Activity activity, @NonNull String key, @NonNull String value) {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(@NonNull Activity activity, @NonNull String key, @NonNull String defaultValue) {
        return activity.getPreferences(MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void saveInteger(@NonNull Activity activity, @NonNull String key, @NonNull Integer value) {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInteger(@NonNull Activity activity, @NonNull String key, @NonNull Integer defaultValue) {
        return activity.getPreferences(MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static Boolean isContainsKey(@NonNull Activity activity, @NonNull String key) {
        return activity.getPreferences(MODE_PRIVATE).contains(key);
    }

    public static Integer getImageIdOfCurrency(String currencyName) {

        switch (currencyName) {
            case "AUD":
                return R.mipmap.ic_aud;
            case "BGN":
                return R.mipmap.ic_bgn;
            case "BRL":
                return R.mipmap.ic_brl;
            case "BYN":
                return R.mipmap.ic_byn;
            case "CAD":
                return R.mipmap.ic_cad;
            case "CHF":
                return R.mipmap.ic_chf;
            case "CNY":
                return R.mipmap.ic_cny;
            case "CZK":
                return R.mipmap.ic_czk;
            case "DKK":
                return R.mipmap.ic_dkk;
            case "EUR":
                return R.mipmap.ic_eur;
            case "GBP":
                return R.mipmap.ic_gbp;
            case "HKD":
                return R.mipmap.ic_hkd;
            case "HRK":
                return R.mipmap.ic_hrk;
            case "HUF":
                return R.mipmap.ic_huf;
            case "IDR":
                return R.mipmap.ic_idr;
            case "ILS":
                return R.mipmap.ic_ils;
            case "INR":
                return R.mipmap.ic_inr;
            case "JPY":
                return R.mipmap.ic_jpy;
            case "KRW":
                return R.mipmap.ic_krw;
            case "MXN":
                return R.mipmap.ic_mxn;
            case "MYR":
                return R.mipmap.ic_myr;
            case "NOK":
                return R.mipmap.ic_nok;
            case "NZD":
                return R.mipmap.ic_nzd;
            case "PHP":
                return R.mipmap.ic_php;
            case "PLN":
                return R.mipmap.ic_pln;
            case "RON":
                return R.mipmap.ic_ron;
            case "RUB":
                return R.mipmap.ic_rub;
            case "SEK":
                return R.mipmap.ic_sek;
            case "SGD":
                return R.mipmap.ic_sgd;
            case "THB":
                return R.mipmap.ic_thb;
            case "TRY":
                return R.mipmap.ic_try;
            case "USD":
                return R.mipmap.ic_usd;
            case "ZAR":
                return R.mipmap.ic_zar;

            default:
                return R.mipmap.app_icon;
        }
    }

    public static String getFullNameOfCurrency(String currencyName) {
        switch (currencyName) {
            case "AUD":
                return "Australian Dollar";
            case "BGN":
                return "Bulgarian Lev";
            case "BRL":
                return "Brazilian Real";
            case "BYN":
                return "Belarusian Ruble";
            case "CAD":
                return "Canadian Dollar";
            case "CHF":
                return "Swiss Franc";
            case "CNY":
                return "Chinese Yuan Renminbi";
            case "CZK":
                return "Czech Koruna";
            case "DKK":
                return "Danish Krone";
            case "EUR":
                return "European Euro";
            case "GBP":
                return "Pound Sterling";
            case "HKD":
                return "Hong Kong Dollar";
            case "HRK":
                return "Croatian Kuna";
            case "HUF":
                return "Hungarian Forint";
            case "IDR":
                return "Indonesian Rupiah";
            case "ILS":
                return "Israeli new Shekel";
            case "INR":
                return "Indian Rupee";
            case "JPY":
                return "Japanese Yen";
            case "KRW":
                return "South Korean Won";
            case "MXN":
                return "Mexican Peso";
            case "MYR":
                return "Malaysian Ringgit";
            case "NOK":
                return "Norwegian Krone";
            case "NZD":
                return "New Zealand Dollar";
            case "PHP":
                return "Philippine Peso";
            case "PLN":
                return "Polish Zloty";
            case "RON":
                return "Romanian Leu";
            case "RUB":
                return "Russian Ruble";
            case "SEK":
                return "Swedish Krona";
            case "SGD":
                return "Singapore Dollar";
            case "THB":
                return "Thai Baht";
            case "TRY":
                return "Turkish Lira";
            case "USD":
                return "United States Dollar";
            case "ZAR":
                return "South African Rand";

            default:
                return "Error";
        }
    }
}
