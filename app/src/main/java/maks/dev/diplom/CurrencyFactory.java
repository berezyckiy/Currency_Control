package maks.dev.diplom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by berezyckiy on 2/7/17.
 */

public class CurrencyFactory {
    private static CurrencyFactory sCurrencyFactory;
    private static List<Currency> mCurrencyList;

    public class Currency {
        private String currencyName;
        private int currencyValue;
        private String currencyCountry;

        public Currency(String name, int value, String fullName) {
            this.currencyName = name;
            this.currencyValue = value;
            this.currencyCountry = fullName;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public int getCurrencyValue() {
            return currencyValue;
        }

        public String getCurrencyCountry() {
            return currencyCountry;
        }
    }

    private CurrencyFactory() {
        mCurrencyList = new ArrayList<>();
        mCurrencyList.add(new Currency("BYN", 100, "Belarus"));
        mCurrencyList.add(new Currency("EUR", 100, "Germany"));
        mCurrencyList.add(new Currency("USA", 100, "America"));
        mCurrencyList.add(new Currency("RUR", 100, "Russia"));
        mCurrencyList.add(new Currency("PLN", 100, "Poland"));
        mCurrencyList.add(new Currency("BYN", 100, "Belarus"));
        mCurrencyList.add(new Currency("RUR", 100, "Russia"));
        mCurrencyList.add(new Currency("USA", 100, "America"));
        mCurrencyList.add(new Currency("PLN", 100, "Poland"));
        mCurrencyList.add(new Currency("USA", 100, "America"));
        mCurrencyList.add(new Currency("EUR", 100, "Germany"));
    }

    public static List<Currency> getCurrencyList() {
        if (sCurrencyFactory == null) {
            sCurrencyFactory = new CurrencyFactory();
        }
        return mCurrencyList;
    }

}
