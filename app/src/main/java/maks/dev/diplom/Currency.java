package maks.dev.diplom;

import android.graphics.Movie;

/**
 * Created by berezyckiy on 2/8/17.
 */

public class Currency {

    private String name;
    private double value;
    private String fullName;

    public Currency(String name) {
        this.name = name;
    }

    public Currency(String name, double value, String fullName) {
        this.name = name;
        this.value = value;
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
